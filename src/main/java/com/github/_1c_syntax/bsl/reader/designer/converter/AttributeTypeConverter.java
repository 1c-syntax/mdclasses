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
import com.github._1c_syntax.bsl.reader.common.converter.AbstractReadConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Конвертер типов данных для Designer формата
 */
@DesignerConverter
public class AttributeTypeConverter extends AbstractReadConverter {

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    // В designer формате Type элемент содержит v8:Type и квалификаторы
    String typeName = null;
    Optional<StringQualifier> stringQualifier = Optional.empty();
    Optional<NumberQualifier> numberQualifier = Optional.empty();
    
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      String nodeName = reader.getNodeName();
      
      if ("v8:Type".equals(nodeName)) {
        typeName = normalizeTypeName(reader.getValue());
      } else if ("v8:StringQualifiers".equals(nodeName)) {
        stringQualifier = Optional.of(parseStringQualifiers(reader));
      } else if ("v8:NumberQualifiers".equals(nodeName)) {
        numberQualifier = Optional.of(parseNumberQualifiers(reader));
      }
      
      reader.moveUp();
    }
    
    if (typeName == null || typeName.isEmpty()) {
      return AttributeTypeImpl.EMPTY;
    }
    
    TypeDescription.TypeDescriptionBuilder builder = TypeDescription.builder()
      .typeName(typeName)
      .category(determineTypeCategory(typeName));
    
    if (stringQualifier.isPresent()) {
      builder.qualifier(Optional.of(stringQualifier.get()));
    } else if (numberQualifier.isPresent()) {
      builder.qualifier(Optional.of(numberQualifier.get()));
    }
    
    return AttributeTypeImpl.builder()
      .typeDescriptions(List.of(builder.build()))
      .build();
  }
  
  @Override
  public boolean canConvert(Class type) {
    return AttributeType.class.isAssignableFrom(type);
  }
  
  private TypeDescription parseTypeWithQualifiers(HierarchicalStreamReader reader, String typeName) {
    TypeDescription.TypeDescriptionBuilder builder = TypeDescription.builder()
      .typeName(normalizeTypeName(typeName))
      .category(determineTypeCategory(typeName));
    
    // Ищем квалификаторы на том же уровне что и v8:Type
    HierarchicalStreamReader parent = reader;
    while (parent.hasMoreChildren()) {
      parent.moveDown();
      String qualifierName = parent.getNodeName();
      
      if ("v8:StringQualifiers".equals(qualifierName)) {
        StringQualifier qualifier = parseStringQualifiers(parent);
        builder.qualifier(Optional.of(qualifier));
      } else if ("v8:NumberQualifiers".equals(qualifierName)) {
        NumberQualifier qualifier = parseNumberQualifiers(parent);
        builder.qualifier(Optional.of(qualifier));
      }
      
      parent.moveUp();
    }
    
    return builder.build();
  }
  
  private StringQualifier parseStringQualifiers(HierarchicalStreamReader reader) {
    StringQualifier.StringQualifierBuilder builder = StringQualifier.builder();
    
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      String nodeName = reader.getNodeName();
      String value = reader.getValue();
      
      switch (nodeName) {
        case "v8:Length":
          builder.length(Integer.parseInt(value));
          break;
        case "v8:AllowedLength":
          builder.allowedLength("Fixed".equals(value) ? StringQualifier.AllowedLength.FIXED : StringQualifier.AllowedLength.VARIABLE);
          break;
      }
      
      reader.moveUp();
    }
    
    return builder.build();
  }
  
  private NumberQualifier parseNumberQualifiers(HierarchicalStreamReader reader) {
    NumberQualifier.NumberQualifierBuilder builder = NumberQualifier.builder();
    
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      String nodeName = reader.getNodeName();
      String value = reader.getValue();
      
      switch (nodeName) {
        case "v8:Digits":
          builder.precision(Integer.parseInt(value));
          break;
        case "v8:FractionDigits":
          builder.fractionDigits(Integer.parseInt(value));
          break;
        case "v8:AllowedSign":
          builder.allowedSign("Nonnegative".equals(value) ? NumberQualifier.AllowedSign.NONNEGATIVE : NumberQualifier.AllowedSign.ANY);
          break;
      }
      
      reader.moveUp();
    }
    
    return builder.build();
  }
  
  private String normalizeTypeName(String typeName) {
    if (typeName.startsWith("xs:")) {
      return typeName.substring(3);
    }
    if (typeName.startsWith("cfg:")) {
      return typeName.substring(4);
    }
    return typeName;
  }
  
  private TypeCategory determineTypeCategory(String typeName) {
    if (typeName.contains("Ref.") || typeName.contains("Object.")) {
      return TypeCategory.REFERENCE;
    }
    if (typeName.startsWith("DefinedType.")) {
      return TypeCategory.DEFINED;
    }
    return TypeCategory.PRIMITIVE;
  }
}