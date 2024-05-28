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
import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Map;

/**
 * Для хранения контекста при чтении элементов форм
 */
@EqualsAndHashCode(callSuper = true)
public class FormElementReaderContext extends AbstractReaderContext {

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

  public FormElementReaderContext(@NonNull String elementName, @NonNull HierarchicalStreamReader reader) {
    super(reader);
    name = elementName;
    realClass = realClassByName(elementName);
    if (realClass == null) {
      realClass = DEFAULT_CLASS_FORM_ITEM;
    }
    builder = TransformationUtils.builder(realClass);
  }

  @Override
  public Class<?> fieldType(String fieldName) {
    var clazz = super.fieldType(fieldName);
    if (clazz == null) {
      clazz = realClassByName(fieldName);
    }
    return clazz;
  }

  private static Class<?> realClassByName(String name) {
    return CLASSES.get(name);
  }
}
