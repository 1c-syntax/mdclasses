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

import com.github._1c_syntax.bsl.mdo.support.ValueType;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Используется для преобразования типа значения
 */
@DesignerConverter
public class ValueTypeConverter implements Converter {

  private static final String TYPE_NODE_NAME = "Type";
  private static final String TYPE_SET_NODE_NAME = "TypeSet";
  private static final String DATE_QUALIFIERS_NODE_NAME = "DateQualifiers";
  private static final String NUMBER_QUALIFIERS_NODE_NAME = "NumberQualifiers";
  private static final String STRING_QUALIFIERS_NODE_NAME = "StringQualifiers";
  private static final String DATE_FRACTIONS_NODE_NAME = "DateFractions";
  private static final String DIGITS_NODE_NAME = "Digits";
  private static final String FRACTION_DIGITS_NODE_NAME = "FractionDigits";
  private static final String LENGTH_NODE_NAME = "Length";

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

    List<ValueType.Type> types = new ArrayList<>();
    List<ValueType.Qualifier> qualifiers = new ArrayList<>();

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var node = reader.getNodeName();
      if (TYPE_NODE_NAME.equals(node) || TYPE_SET_NODE_NAME.equals(node)) {
        types.add(ValueType.Type.create(reader.getValue()));
      } else if (DATE_QUALIFIERS_NODE_NAME.equals(node)) {
        qualifiers.add(readDateQualifier(reader));
      } else if (NUMBER_QUALIFIERS_NODE_NAME.equals(node)) {
        qualifiers.add(readNumberQualifier(reader));
      } else if (STRING_QUALIFIERS_NODE_NAME.equals(node)) {
        qualifiers.add(readStringQualifier(reader));
      } else {
        // no-op
      }
      reader.moveUp();
    }

    return new ValueType(types, qualifiers);

  }

  @Override
  public boolean canConvert(Class type) {
    return ValueType.class.isAssignableFrom(type);
  }

  private static ValueType.StringQualifier readStringQualifier(HierarchicalStreamReader reader) {
    var length = 0;
    var allowedLength = ValueType.AllowedLength.VARIABLE;
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if (LENGTH_NODE_NAME.equals(reader.getNodeName())) {
        length = Integer.parseInt(reader.getValue());
      } else {
        allowedLength = ValueType.AllowedLength.valueOf(reader.getValue().toUpperCase(Locale.ROOT));
      }
      reader.moveUp();
    }
    return new ValueType.StringQualifier(length, allowedLength);
  }

  private static ValueType.DateQualifier readDateQualifier(HierarchicalStreamReader reader) {
    var dateFraction = ValueType.DateFraction.DATE;
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if (DATE_FRACTIONS_NODE_NAME.equals(reader.getNodeName())) {
        dateFraction = ValueType.DateFraction.valueOf(reader.getValue().toUpperCase(Locale.ROOT));
      }
      reader.moveUp();
    }
    return new ValueType.DateQualifier(dateFraction);
  }

  private static ValueType.NumberQualifier readNumberQualifier(HierarchicalStreamReader reader) {
    var digits = 0;
    var fractionDigits = 0;
    var allowedSign = ValueType.AllowedSign.ANY;

    var nodeName = "";
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      nodeName = reader.getNodeName();
      if (DIGITS_NODE_NAME.equals(nodeName)) {
        digits = Integer.parseInt(reader.getValue());
      } else if (FRACTION_DIGITS_NODE_NAME.equals(nodeName)) {
        fractionDigits = Integer.parseInt(reader.getValue());
      } else {
        allowedSign = ValueType.AllowedSign.valueOf(reader.getValue().toUpperCase(Locale.ROOT));
      }
      reader.moveUp();
    }
    return new ValueType.NumberQualifier(digits, fractionDigits, allowedSign);
  }
}
