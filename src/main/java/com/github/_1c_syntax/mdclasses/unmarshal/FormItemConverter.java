/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2020
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
package com.github._1c_syntax.mdclasses.unmarshal;

import com.github._1c_syntax.mdclasses.mdo.FormItem;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Конвертирует в FormItem элемент формы
 */
public class FormItemConverter implements Converter {
  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    // если свойство type отсутствует, тип элемента заполняется из атрибута
    var nodeName = reader.getNodeName();
    var type = getItemType(nodeName, reader.getAttribute("xsi:type"));
    var item = (FormItem) context.convertAnother(reader, FormItem.class,
      XStreamFactory.getReflectionConverter());
    if (item.getType().isEmpty()) {
      item.setType(type);
    }
    return item;
  }

  @Override
  public boolean canConvert(Class type) {
    return type == FormItem.class;
  }

  private String getItemType(String nodeName, String attribute) {
    if (nodeName.equals("items")) {
      return attribute;
    } else {
      return nodeName;
    }
  }
}
