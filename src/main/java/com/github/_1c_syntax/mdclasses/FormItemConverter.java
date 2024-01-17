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
package com.github._1c_syntax.mdclasses;

import com.github._1c_syntax.bsl.reader.MDOReader;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.mdclasses.mdo.children.form.FormItem;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

/**
 * Конвертирует в FormItem элемент формы
 */
//@CommonConverter
public class FormItemConverter implements ReadConverter {

  private static final String NODE_NAME = "items";

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    // если свойство type отсутствует, тип элемента заполняется из атрибута
    var nodeName = reader.getNodeName();
    var type = getItemType(nodeName, reader.getAttribute("type"));
    var item = (FormItem) context.convertAnother(context, FormItem.class,
      MDOReader.getReflectionConverter(reader));
    if (item.getType().isEmpty()) {
      item.setType(type);
    }
    return item;
  }

  @Override
  public boolean canConvert(Class type) {
    return type == FormItem.class;
  }

  private static String getItemType(String nodeName, String attribute) {
    if (NODE_NAME.equals(nodeName)) {
      return attribute;
    } else {
      return nodeName;
    }
  }
}
