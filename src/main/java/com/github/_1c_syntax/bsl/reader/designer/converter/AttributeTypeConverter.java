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
package com.github._1c_syntax.bsl.reader.designer.converter;

import com.github._1c_syntax.bsl.mdo.support.AttributeType;
import com.github._1c_syntax.bsl.mdo.support.AttributeTypeImpl;
import com.github._1c_syntax.bsl.mdo.support.NumberQualifier;
import com.github._1c_syntax.bsl.mdo.support.StringQualifier;
import com.github._1c_syntax.bsl.mdo.support.TypeDescription;
import com.github._1c_syntax.bsl.mdo.support.TypeCategory;
import com.github._1c_syntax.bsl.reader.common.converter.AttributeTypeConverterBase;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Конвертер типов данных для Designer формата
 */
@DesignerConverter
public class AttributeTypeConverter extends AttributeTypeConverterBase {

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    // В designer формате Type элемент содержит v8:Type и квалификаторы
    List<String> typeNames = new ArrayList<>();
    Optional<StringQualifier> stringQualifier = Optional.empty();
    Optional<NumberQualifier> numberQualifier = Optional.empty();
    
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      String nodeName = reader.getNodeName();
      
      if ("v8:Type".equals(nodeName)) {
        typeNames.add(normalizeTypeName(reader.getValue()));
      } else if ("v8:StringQualifiers".equals(nodeName)) {
        stringQualifier = Optional.of(parseStringQualifiers(reader));
      } else if ("v8:NumberQualifiers".equals(nodeName)) {
        numberQualifier = Optional.of(parseNumberQualifiers(reader));
      }
      
      reader.moveUp();
    }
    
    return createAttributeType(typeNames, stringQualifier, numberQualifier);
  }
  
  @Override
  public boolean canConvert(Class type) {
    return AttributeType.class.isAssignableFrom(type);
  }
  
  
  /**
   * Парсит строковые квалификаторы из XML
   * @param reader XML reader
   * @return объект StringQualifier
   */
  private StringQualifier parseStringQualifiers(HierarchicalStreamReader reader) {
    StringQualifier.StringQualifierBuilder builder = StringQualifier.builder();
    
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      String nodeName = reader.getNodeName();
      String value = reader.getValue();
      
      switch (nodeName) {
        case "v8:Length":
          try {
            builder.length(Integer.parseInt(value));
          } catch (NumberFormatException e) {
            // Игнорируем некорректные значения длины
          }
          break;
        case "v8:AllowedLength":
          builder.allowedLength("Fixed".equals(value) ? StringQualifier.AllowedLength.FIXED : StringQualifier.AllowedLength.VARIABLE);
          break;
        default:
          // Игнорируем неизвестные элементы
          break;
      }
      
      reader.moveUp();
    }
    
    return builder.build();
  }
  
  /**
   * Парсит числовые квалификаторы из XML
   * @param reader XML reader
   * @return объект NumberQualifier
   */
  private NumberQualifier parseNumberQualifiers(HierarchicalStreamReader reader) {
    NumberQualifier.NumberQualifierBuilder builder = NumberQualifier.builder();
    
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      String nodeName = reader.getNodeName();
      String value = reader.getValue();
      
      switch (nodeName) {
        case "v8:Digits":
          try {
            builder.precision(Integer.parseInt(value));
          } catch (NumberFormatException e) {
            // Игнорируем некорректные значения точности
          }
          break;
        case "v8:FractionDigits":
          try {
            builder.fractionDigits(Integer.parseInt(value));
          } catch (NumberFormatException e) {
            // Игнорируем некорректные значения дробных разрядов
          }
          break;
        case "v8:AllowedSign":
          builder.allowedSign("Nonnegative".equals(value) ? NumberQualifier.AllowedSign.NONNEGATIVE : NumberQualifier.AllowedSign.ANY);
          break;
        default:
          // Игнорируем неизвестные элементы
          break;
      }
      
      reader.moveUp();
    }
    
    return builder.build();
  }
  
  /**
   * Нормализует имя типа, удаляя префиксы пространств имен
   * @param typeName исходное имя типа
   * @return нормализованное имя типа
   */
  private String normalizeTypeName(String typeName) {
    if (typeName == null) {
      return "";
    }
    String trimmed = typeName.trim();
    if (trimmed.startsWith("xs:")) {
      return trimmed.substring(3);
    }
    if (trimmed.startsWith("cfg:")) {
      return trimmed.substring(4);
    }
    return trimmed;
  }
  
}