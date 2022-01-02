/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2021
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

import com.github._1c_syntax.bsl.mdo.support.ValueType;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * Используется для преобразования типа значения
 */
@DesignerConverter
public class ValueTypeConverter implements Converter {
  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

    List<String> types = new ArrayList<>();
    ValueType.StringQualifier stringQualifier = ValueType.StringQualifier.EMPTY;
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var node = reader.getNodeName();
      if (node.equals("Type")) {
        types.add(reader.getValue());
      } else if (node.equals("StringQualifiers")) {
        int length = 0;
        String allowedLength = "";
        while (reader.hasMoreChildren()) {
          reader.moveDown();
          node = reader.getNodeName();
          if (node.equals("Length")) {
            length = Integer.parseInt(reader.getValue());
          } else {
            allowedLength = reader.getValue();
          }
          reader.moveUp();
        }
        if (!allowedLength.isEmpty()) {
          stringQualifier = new ValueType.StringQualifier(length, allowedLength);
        }
      }
//      if ("Properties".equals(reader.getNodeName())) {
//        properties.putAll(DesignerConverterCommon.readProperties(builder, mdoType, reader, context));
//      }
      reader.moveUp();
    }

    if (stringQualifier != ValueType.StringQualifier.EMPTY) {
      return new ValueType(stringQualifier);
    } else {
      return new ValueType(types);
    }

  }

  @Override
  public boolean canConvert(Class type) {
    return ValueType.class.isAssignableFrom(type);
  }
}
