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

import com.github._1c_syntax.bsl.mdo.storage.ManagedFormData;
import com.github._1c_syntax.bsl.mdo.storage.form.FormAttribute;
import com.github._1c_syntax.bsl.mdo.storage.form.FormHandler;
import com.github._1c_syntax.bsl.mdo.storage.form.SimpleFormItem;
import com.github._1c_syntax.bsl.reader.MDOReader;
import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.Data;
import lombok.NonNull;

import java.nio.file.Path;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Для хранения контекста при чтении элементов форм
 */
@Data
public class FormElementReaderContext implements ReaderContext {

  private static final Map<String, Class<?>> CLASSES = Map.of(
    "Form", ManagedFormData.class,
    "attributes", FormAttribute.class,
    "Attribute", FormAttribute.class,
    "items", SimpleFormItem.class,
    "Events", FormHandler.class,
    "ChildItems", SimpleFormItem.class,
    ManagedFormData.class.getName(), ManagedFormData.class
  );

  private static final Class<?> DEFAULT_CLASS_FORM_ITEM = SimpleFormItem.class;

  String lastName;
  Object lastValue;

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

  public FormElementReaderContext(@NonNull String name, @NonNull HierarchicalStreamReader reader) {
    currentPath = ExtendXStream.getCurrentPath(reader);
    realClassName = name;
    isDesignerFormat = MDOReader.getConfigurationSourceByMDOPath(currentPath) == ConfigurationSource.DESIGNER;

    realClass = realClassByName(realClassName);
    if (realClass == null) {
      realClass = DEFAULT_CLASS_FORM_ITEM;
    }
    builder = TransformationUtils.builder(realClass);
    requireNonNull(builder);
  }

  @Override
  public Object build() {
    return TransformationUtils.build(builder);
  }

  @Override
  public Class<?> fieldType(String fieldName) {
    var clazz = ReaderContext.super.fieldType(fieldName);
    if (clazz == null) {
      clazz = realClassByName(fieldName);
    }
    return clazz;
  }

  private static Class<?> realClassByName(String name) {
    return CLASSES.get(name);
  }
}
