/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2024
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * MDClasses is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * MDClasses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with MDClasses.
 */
package com.github._1c_syntax.bsl.reader.common.context;

import com.github._1c_syntax.bsl.mdo.ChildrenOwner;
import com.github._1c_syntax.bsl.mdo.Form;
import com.github._1c_syntax.bsl.mdo.MDChild;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.mdo.children.ObjectModule;
import com.github._1c_syntax.bsl.mdo.storage.EmptyFormData;
import com.github._1c_syntax.bsl.mdo.storage.FormData;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.reader.MDOReader;
import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.github._1c_syntax.bsl.reader.common.converter.ProtectedModuleInfo;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.designer.DesignerPaths;
import com.github._1c_syntax.bsl.reader.edt.EDTPaths;
import com.github._1c_syntax.bsl.supconf.ParseSupportData;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.ModuleType;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.Data;
import lombok.NonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Для хранения контекста при чтении MD и ExternalSource объектов
 */
@Data
public class MDReaderContext implements ReaderContext {

  private static final String MDO_REFERENCE_FIELD_NAME = "mdoReference";
  private static final String OWNER_FIELD_NAME = "owner";
  private static final String PARENT_SUBSYSTEM_FIELD_NAME = "parentSubsystem";
  private static final String URI_FIELD_NAME = "uri";
  private static final String IS_PROTECTED_FIELD_NAME = "isProtected";
  private static final String UUID_FIELD_NAME = "uuid";
  private static final String SUPPORT_VALIANT_FIELD_NAME = "SupportVariant";
  private static final String DATA_FIELD_NAME = "data";
  private static final String MODULES_FIELD_NAME = "modules";

  /**
   * Строковое имя объекта
   */
  String realClassName;

  /**
   * Класс будущего объекта
   */
  Class<?> realClass;

  /**
   * Билдер объекта
   */
  Object builder;

  /**
   * Путь к текущему, читаемому файлу
   */
  Path currentPath;

  /**
   * Вариант исходников в формате конфигуратора
   */
  boolean isDesignerFormat;

  /**
   * Режим поддержки
   */
  SupportVariant supportVariant = SupportVariant.NONE;

  /**
   * Коллекция билдеров для дочерних объектов, которые надо доделать
   */
  Map<String, List<MDReaderContext>> childrenContexts;

  /**
   * Имя прочитанного объекта
   */
  String name;

  /**
   * Тип макета
   */
  TemplateType templateType;

  /**
   * Тип объекта ссылки
   */
  MDOType mdoType;

  /**
   * Ссылка на текущий объект
   */
  MdoReference mdoReference = MdoReference.EMPTY;

  /**
   * Ссылка на родительский объект
   */
  MdoReference owner = MdoReference.EMPTY;

  String lastName;
  Object lastValue;

  public MDReaderContext(@NonNull HierarchicalStreamReader reader) {
    currentPath = ExtendXStream.getCurrentPath(reader);
    isDesignerFormat = MDOReader.getConfigurationSourceByMDOPath(currentPath) == ConfigurationSource.DESIGNER;
    realClassName = reader.getNodeName();
    realClass = MDOReader.getReader(currentPath).getEXStream().getRealClass(realClassName);

    builder = TransformationUtils.builder(realClass);
    requireNonNull(builder);

    mdoType = MDOType.fromValue(realClassName).orElse(MDOType.UNKNOWN);
    childrenContexts = new HashMap<>();

    var uuid = reader.getAttribute(UUID_FIELD_NAME);
    supportVariant = ParseSupportData.getSupportVariantByMDO(uuid, currentPath);

    setValue(UUID_FIELD_NAME, uuid);
    setValue(SUPPORT_VALIANT_FIELD_NAME, supportVariant);
  }

  @Override
  public final void setValue(String methodName, Object value) {
    if (value instanceof MDReaderContext child) {
      saveChildName(methodName, child);
    } else {
      ReaderContext.super.setValue(methodName, value);
    }
  }

  @Override
  public Object build() {
    mdoReference = MdoReference.create(owner, mdoType, name);
    setValue(MDO_REFERENCE_FIELD_NAME, mdoReference);

    if (MDChild.class.isAssignableFrom(realClass)) {
      setValue(OWNER_FIELD_NAME, owner);
    }

    if (Subsystem.class.isAssignableFrom(realClass)) {
      setValue(PARENT_SUBSYSTEM_FIELD_NAME, owner);
    }

    if (Form.class.isAssignableFrom(realClass)) {
      setValue(DATA_FIELD_NAME, readFormData());
    }

    if (ChildrenOwner.class.isAssignableFrom(realClass)) {
      setValueChildren();
    }

    if (ModuleOwner.class.isAssignableFrom(realClass)) {
      setValueModules();
    }

    return TransformationUtils.build(builder);
  }

  private void saveChildName(String collectionName, MDReaderContext child) {
    var collection = childrenContexts.get(collectionName);
    if (collection == null) {
      collection = new ArrayList<>();
    }
    collection.add(child);
    childrenContexts.put(collectionName, collection);
  }

  private void setValueModules() {
    var folder = getModuleFolder();
    if (!folder.toFile().exists()) {
      return;
    }

    var moduleTypes = ModuleType.byMDOType(mdoType);
    if (moduleTypes.isEmpty()) {
      return;
    }

    List<Module> modules = new ArrayList<>();
    moduleTypes.forEach((ModuleType moduleType) -> {
        var protectedModuleInfo = getModuleInfo(folder, moduleType);
        if (protectedModuleInfo.getModulePath().toFile().exists()) {
          modules.add(ObjectModule.builder()
            .moduleType(moduleType)
            .uri(protectedModuleInfo.getModulePath().toUri())
            .owner(mdoReference)
            .supportVariant(supportVariant)
            .isProtected(protectedModuleInfo.isProtected())
            .build());
        }
      }
    );
    setValue(MODULES_FIELD_NAME, modules);
  }

  private void setValueChildren() {
    childrenContexts.forEach((String collectionName, List<MDReaderContext> collectionSource) -> {
      if (collectionName.endsWith("s")) {
        var collection = collectionSource.parallelStream()
          .map((MDReaderContext childContext) -> {
            childContext.setOwner(mdoReference);
            return childContext.build();
          }).toList();
        setValue(collectionName, collection);
      } else {
        collectionSource.stream()
          .filter(Objects::nonNull) // исключаем не прочитанное
          .forEach((MDReaderContext childContext) -> {
            childContext.setOwner(mdoReference);
            setValue(collectionName, childContext.build());
          });
      }
    });
  }

  private FormData readFormData() {
    Path formDataPath;
    if (isDesignerFormat) {
      formDataPath = DesignerPaths.formDataPath(currentPath, name);
    } else {
      formDataPath = EDTPaths.formDataPath(currentPath, mdoType, name);
    }

    if (!formDataPath.toFile().exists()) {
      return EmptyFormData.getEmpty();
    }

    return (FormData) MDOReader.getReader(currentPath).read(formDataPath);
  }

  private Path getModuleFolder() {
    if (isDesignerFormat) {
      return DesignerPaths.moduleFolder(currentPath, mdoType);
    } else {
      return EDTPaths.moduleFolder(currentPath, mdoType);
    }
  }

  private ProtectedModuleInfo getModuleInfo(Path folder, ModuleType moduleType) {
    Path modulePath;
    if (isDesignerFormat) {
      modulePath = DesignerPaths.modulePath(folder, name, moduleType);
    } else {
      modulePath = EDTPaths.modulePath(folder, name, moduleType);
    }
    return new ProtectedModuleInfo(modulePath, isDesignerFormat);
  }
}
