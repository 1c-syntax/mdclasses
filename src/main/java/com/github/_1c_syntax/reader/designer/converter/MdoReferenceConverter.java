/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2022
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
package com.github._1c_syntax.reader.designer.converter;

import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Используется для преобразования строковых представлений ссылки на объект
 */
@DesignerConverter
public class MdoReferenceConverter implements Converter {

  private static final String ITEM_NODE_NAME = "Item";
  private static final String METADATA_NODE_NAME = "Metadata";

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    // todo надо обработать разные типы
    var nodeName = reader.getNodeName();
    String value = "";
    if (ITEM_NODE_NAME.equals(nodeName) && reader.hasMoreChildren()) {
      while (reader.hasMoreChildren()) {
        reader.moveDown();
        var propertyName = reader.getNodeName();
        if (METADATA_NODE_NAME.equals(propertyName)) {
          value = reader.getValue();
          reader.moveUp();
          break;
        }
        reader.moveUp();
      }
    } else {
      value = reader.getValue();
    }

    if (value.isEmpty()) {
      return MdoReference.EMPTY;
    } else {
      return MdoReference.create(value);
    }
  }

  @Override
  public boolean canConvert(Class type) {
    return MdoReference.class.isAssignableFrom(type);
  }
}
