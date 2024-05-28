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

import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.children.ObjectModule;
import com.github._1c_syntax.bsl.reader.MDReader;
import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.github._1c_syntax.bsl.reader.common.converter.ProtectedModuleInfo;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.ModuleType;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.lang.reflect.ParameterizedType;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Сохраняемый контекст при чтении файла
 */
public abstract class AbstractReaderContext {

  protected static final String MODULES_FIELD_NAME = "modules";

  /**
   * Путь к текущему, читаемому файлу
   */
  @Getter
  protected final Path currentPath;

  /**
   * Ридер файла
   */
  @Getter
  protected final MDReader mdReader;

  /**
   * Класс будущего объекта
   */
  @Getter
  protected Class<?> realClass;

  /**
   * Билдер объекта
   */
  @Getter
  protected Object builder;

  /**
   * Режим поддержки
   */
  protected SupportVariant supportVariant;

  /**
   * Тип объекта ссылки
   */
  @Getter
  protected MDOType mdoType;

  /**
   * Ссылка на текущий объект
   */
  protected MdoReference mdoReference = MdoReference.EMPTY;

  /**
   * Имя прочитанного объекта
   */
  @Setter
  @Getter
  protected String name;

  /**
   * Последнее прочитанное поле
   */
  @Setter
  @Getter
  private String lastName;

  /**
   * Значение последнего прочитанного поля
   */
  @Setter
  @Getter
  private Object lastValue;

  protected AbstractReaderContext(@NonNull HierarchicalStreamReader reader) {
    currentPath = ExtendXStream.getCurrentPath(reader);
    mdReader = ExtendXStream.getCurrentMDReader(reader);
  }

  /**
   * Для установки значения поля собираемого объекта
   *
   * @param methodName Имя поля\метода
   * @param value      устанавливаемое значение
   */
  public void setValue(String methodName, Object value) {
    TransformationUtils.setValue(builder, methodName, value);
  }

  /**
   * Получение класса типа поля
   *
   * @param fieldName Имя поля\метода
   * @return Определенный класс
   */
  public Class<?> fieldType(String fieldName) {
    var fieldClass = TransformationUtils.fieldType(builder, fieldName);
    if (fieldClass instanceof ParameterizedType parameterizedType) {
      fieldClass = TransformationUtils.computeType(parameterizedType);
    }
    return (Class<?>) fieldClass;
  }

  /**
   * Сборка контекста в объект
   */
  public Object build() {
    return TransformationUtils.build(builder, currentPath);
  }

  protected void setValueModules() {
    var modules = readModules();
    if (!modules.isEmpty()) {
      setValue(MODULES_FIELD_NAME, modules);
    }
  }

  private List<Module> readModules() {
    var folder = mdReader.moduleFolder(currentPath, mdoType);
    if (!folder.toFile().exists()) {
      return Collections.emptyList();
    }

    var moduleTypes = ModuleType.byMDOType(mdoType);
    if (moduleTypes.isEmpty()) {
      return Collections.emptyList();
    }

    var isDesigner = mdReader.getConfigurationSource() == ConfigurationSource.DESIGNER;
    List<Module> modules = new ArrayList<>();
    moduleTypes.forEach((ModuleType moduleType) -> {
        var protectedModuleInfo = new ProtectedModuleInfo(mdReader.modulePath(folder, name, moduleType), isDesigner);
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
    return modules;
  }
}
