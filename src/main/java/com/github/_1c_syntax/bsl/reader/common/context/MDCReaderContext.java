/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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

import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import com.github._1c_syntax.bsl.mdo.children.ObjectModule;
import com.github._1c_syntax.bsl.reader.MDOReader;
import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.github._1c_syntax.bsl.reader.common.converter.ProtectedModuleInfo;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.designer.DesignerPaths;
import com.github._1c_syntax.bsl.reader.edt.EDTPaths;
import com.github._1c_syntax.bsl.supconf.ParseSupportData;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
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
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Служебный класс для хранения контекста при "сборке" объекта при чтении из файла
 */
@Data
public class MDCReaderContext implements ReaderContext {

  private static final String UUID_FIELD_NAME = "uuid";
  private static final String SUPPORT_VALIANT_FIELD_NAME = "SupportVariant";
  private static final String CHILD_FILED_NAME = "child";

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
   * Имя прочитанного объекта
   */
  String name;

  /**
   * Режим совместимости
   */
  CompatibilityMode compatibilityMode;

  /**
   * Режим совместимости расширения
   */
  CompatibilityMode configurationExtensionCompatibilityMode;

  /**
   * Тип объекта ссылки
   */
  MDOType mdoType;

  /**
   * Дочерние метаданные
   */
  List<String> childrenNames;

  String lastName;
  Object lastValue;

  public MDCReaderContext(@NonNull Class<?> clazz, @NonNull HierarchicalStreamReader reader) {
    currentPath = ExtendXStream.getCurrentPath(reader);
    realClass = clazz;
    builder = TransformationUtils.builder(realClass);
    requireNonNull(builder);

    var uuid = reader.getAttribute(UUID_FIELD_NAME);
    supportVariant = ParseSupportData.getSupportVariantByMDO(uuid, currentPath);
    isDesignerFormat = MDOReader.getConfigurationSourceByMDOPath(currentPath) == ConfigurationSource.DESIGNER;
    mdoType = MDOType.CONFIGURATION;

    setValue(UUID_FIELD_NAME, uuid);
    setValue(SUPPORT_VALIANT_FIELD_NAME, supportVariant);

    childrenNames = new ArrayList<>();
  }

  @Override
  public Object build() {
    var mdoReference = MdoReference.create(mdoType, name);
    setValue("mdoReference", mdoReference);

    if (compatibilityMode == null) {
      setValue("compatibilityMode", configurationExtensionCompatibilityMode);
    }

    if (isDesignerFormat) {
      setValue("configurationSource", ConfigurationSource.DESIGNER);
    } else {
      setValue("configurationSource", ConfigurationSource.EDT);
    }

    if (ModuleOwner.class.isAssignableFrom(realClass)) {
      setValueModules(mdoReference);
    }

    setValueChildren();

    return TransformationUtils.build(builder);
  }

  @Override
  public final void setValue(String methodName, Object value) {
    if (value instanceof String string && MDOType.fromValue(methodName).isPresent()) {
      childrenNames.add(string);
    } else {
      ReaderContext.super.setValue(methodName, value);
    }
  }

  private void setValueChildren() {
    var reader = MDOReader.getReader(currentPath);
    childrenNames
      .forEach((String fullName) -> {
        MD child = (MD) reader.read(fullName);
        if (child != null) {
          var fieldName = child.getMdoType().getName();
          setValue(fieldName, child);
          setValue(CHILD_FILED_NAME, child);
        }
      });
  }

  private void setValueModules(MdoReference mdoReference) {
    var moduleTypes = ModuleType.byMDOType(mdoType);
    if (moduleTypes.isEmpty()) {
      return;
    }

    // todo переделать
    Path folder;
    if (isDesignerFormat) {
      folder = DesignerPaths.moduleFolder(currentPath, mdoType);
    } else {
      folder = EDTPaths.moduleFolder(currentPath, mdoType);
    }

    if (!folder.toFile().exists()) {
      return;
    }

    List<Module> modules = new ArrayList<>();
    moduleTypes.forEach((ModuleType moduleType) -> {
        Path modulePath;
        // todo переделать
        if (isDesignerFormat) {
          modulePath = DesignerPaths.modulePath(folder, name, moduleType);
        } else if (mdoType == MDOType.CONFIGURATION) {
          modulePath = EDTPaths.modulePath(folder, MDOType.CONFIGURATION.getName(), moduleType);
        } else {
          modulePath = EDTPaths.modulePath(folder, name, moduleType);
        }

        var protectedModuleInfo = new ProtectedModuleInfo(modulePath);
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
    setValue("modules", modules);
  }

}
