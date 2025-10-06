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

import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.bsl.types.Qualifier;
import com.github._1c_syntax.bsl.types.ValueType;
import com.github._1c_syntax.bsl.types.ValueTypeDescription;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Конвертор обработчика типа значения в формате ЕДТ
 */
@Slf4j
@CommonConverter
public class ValueTypeDescriptionConverter implements ReadConverter {
  private static final List<String> TYPE_NODE_NAMES = List.of("types", "Type", "TypeSet");

  /**
   * Builds a ValueTypeDescription by parsing child nodes from the given reader.
   *
   * Parses child elements whose names match TYPE_NODE_NAMES into ValueType instances
   * and elements whose names end with "Qualifiers" into Qualifier instances; other
   * child elements are ignored (a warning is logged).
   *
   * @param reader  the hierarchical reader positioned at the element to unmarshal
   * @param context the unmarshalling context used to create nested objects
   * @return a ValueTypeDescription containing the parsed value types and qualifiers
   */
  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    List<ValueType> types = new ArrayList<>();
    List<Qualifier> qualifiers = new ArrayList<>();
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var nodeName = reader.getNodeName();
      if (TYPE_NODE_NAMES.contains(nodeName)) {
        types.add(ExtendXStream.readValue(context, ValueType.class));
      } else if (nodeName.endsWith("Qualifiers")) {
        qualifiers.add(ExtendXStream.readValue(context, Qualifier.class));
      } else { // что-то еще
        LOGGER.warn("Unknown type description field {}", nodeName);
      }
      reader.moveUp();
    }
    return ValueTypeDescription.create(types, qualifiers);
  }

  /**
   * Indicates whether this converter supports the given type.
   *
   * @return {@code true} if {@code type} is {@code ValueTypeDescription.class}, {@code false} otherwise.
   */
  @Override
  public boolean canConvert(Class type) {
    return type == ValueTypeDescription.class;
  }
}