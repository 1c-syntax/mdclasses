/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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
package com.github._1c_syntax.bsl.reader.common.converter;

import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.bsl.types.AllowedLength;
import com.github._1c_syntax.bsl.types.DateFractions;
import com.github._1c_syntax.bsl.types.Qualifier;
import com.github._1c_syntax.bsl.types.qualifiers.BinaryDataQualifiers;
import com.github._1c_syntax.bsl.types.qualifiers.DateQualifiers;
import com.github._1c_syntax.bsl.types.qualifiers.EmptyQualifiers;
import com.github._1c_syntax.bsl.types.qualifiers.NumberQualifiers;
import com.github._1c_syntax.bsl.types.qualifiers.StringQualifiers;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

/**
 * Конвертор обработчика типа значения в формате ЕДТ
 */
@Slf4j
@CommonConverter
public class ValueTypeQualifierConverter implements ReadConverter {
  private static final String STRING_QUALIFIERS_NODE_NAME = "StringQualifiers";
  private static final String DATE_QUALIFIERS_NODE_NAME = "DateQualifiers";
  private static final String NUMBER_QUALIFIERS_NODE_NAME = "NumberQualifiers";
  private static final String BINARY_DATA_QUALIFIERS_NODE_NAME = "BinaryDataQualifiers";
  private static final String LENGTH_NODE_NAME = "length";
  private static final String ALLOWED_LENGTH_NODE_NAME = "allowedLength";
  private static final String DATE_FRACTIONS_NODE_NAME = "dateFractions";
  private static final String SCALE_NODE_NAME = "scale";
  private static final String PRECISION_NODE_NAME = "precision";
  private static final String NON_NEGATIVE_NODE_NAME = "nonNegative";
  private static final String DIGITS_NODE_NAME = "Digits";
  private static final String FRACTION_DIGITS_NODE_NAME = "FractionDigits";
  private static final String ALLOWED_SIGN_NODE_NAME = "AllowedSign";
  private static final String NONNEGATIVE_VALUE = "Nonnegative";

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    // запоминаем тип
    var nodeName = reader.getNodeName();
    var length = 0;
    var allowedLength = AllowedLength.VARIABLE;
    var dateFractions = DateFractions.DATETIME;
    var precision = 0;
    var scale = 0;
    var nonNegative = false;

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if (LENGTH_NODE_NAME.equalsIgnoreCase(reader.getNodeName())) {
        length = Integer.parseInt(reader.getValue());
      } else if (ALLOWED_LENGTH_NODE_NAME.equalsIgnoreCase(reader.getNodeName())) {
        allowedLength = AllowedLength.valueOf(reader.getValue().toUpperCase(Locale.ROOT));
      } else if (DATE_FRACTIONS_NODE_NAME.equalsIgnoreCase(reader.getNodeName())) {
        dateFractions = DateFractions.valueOf(reader.getValue().toUpperCase(Locale.ROOT));
      } else if (SCALE_NODE_NAME.equalsIgnoreCase(reader.getNodeName())
        || FRACTION_DIGITS_NODE_NAME.equalsIgnoreCase(reader.getNodeName())) {
        scale = Integer.parseInt(reader.getValue());
      } else if (PRECISION_NODE_NAME.equalsIgnoreCase(reader.getNodeName())
      || DIGITS_NODE_NAME.equalsIgnoreCase(reader.getNodeName())) {
        precision = Integer.parseInt(reader.getValue());
      } else if (NON_NEGATIVE_NODE_NAME.equalsIgnoreCase(reader.getNodeName())) {
        nonNegative = Boolean.parseBoolean(reader.getValue());
      } else if (ALLOWED_SIGN_NODE_NAME.equalsIgnoreCase(reader.getNodeName())) {
        nonNegative = NONNEGATIVE_VALUE.equalsIgnoreCase(reader.getValue());
      }
      reader.moveUp();
    }

    if (STRING_QUALIFIERS_NODE_NAME.equalsIgnoreCase(nodeName)) {
      return StringQualifiers.create(length, allowedLength);
    } else if (DATE_QUALIFIERS_NODE_NAME.equalsIgnoreCase(nodeName)) {
      return DateQualifiers.create(dateFractions);
    } else if (NUMBER_QUALIFIERS_NODE_NAME.equalsIgnoreCase(nodeName)) {
      return NumberQualifiers.create(precision, scale, nonNegative);
    } else if (BINARY_DATA_QUALIFIERS_NODE_NAME.equalsIgnoreCase(nodeName)) {
      return BinaryDataQualifiers.create(length, allowedLength);
    } else { // квалификаторы пока не обрабатываются
      LOGGER.warn("Unknown qualifiers {}", nodeName);
      return EmptyQualifiers.EMPTY;
    }
  }

  @Override
  public boolean canConvert(Class type) {
    return type == Qualifier.class;
  }
}
