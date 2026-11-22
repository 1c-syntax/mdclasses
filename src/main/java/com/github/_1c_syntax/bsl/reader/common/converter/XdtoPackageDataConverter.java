/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
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

import com.github._1c_syntax.bsl.mdo.storage.XdtoPackageData;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.utils.StringInterner;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

/**
 * Используется для преобразования содержимого пакета XDTO
 */
@CommonConverter
public class XdtoPackageDataConverter implements ReadConverter {

  private static final String PROPERTY_NODE_NAME = "property";
  private static final String IMPORT_NODE_NAME = "import";
  private static final String VALUE_TYPE_NODE_NAME = "valueType";
  private static final String OBJECT_TYPE_NODE_NAME = "objectType";
  private static final String TYPE_DEF_NODE_NAME = "typeDef";

  private static final String TARGET_NAMESPACE_ATTRIBUTE_NAME = "targetNamespace";
  private static final String NAME_ATTRIBUTE_NAME = "name";
  private static final String NAMESPACE_ATTRIBUTE_NAME = "namespace";
  private static final String BASE_ATTRIBUTE_NAME = "base";
  private static final String VARIETY_ATTRIBUTE_NAME = "variety";
  private static final String ENUMERATION_ATTRIBUTE_NAME = "enumeration";
  private static final String TYPE_ATTRIBUTE_NAME = "type";
  private static final String FORM_ATTRIBUTE_NAME = "form";
  private static final String LOWER_BOUND_ATTRIBUTE_NAME = "lowerBound";
  private static final String UPPER_BOUND_ATTRIBUTE_NAME = "upperBound";
  private static final String NILLABLE_ATTRIBUTE_NAME = "nillable";

  private static final StringInterner stringInterner = new StringInterner();

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

    if (ExtendXStream.getCurrentMDReader(reader).getReadSettings().skipXdtoPackage()) {
      return XdtoPackageData.EMPTY;
    }

    var builder = XdtoPackageData.builder();
    builder.targetNamespace(reader.getAttribute(TARGET_NAMESPACE_ATTRIBUTE_NAME));

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var node = reader.getNodeName();
      switch (node) {
        case IMPORT_NODE_NAME -> builder.oneImport(reader.getAttribute(NAMESPACE_ATTRIBUTE_NAME));
        case PROPERTY_NODE_NAME -> builder.property(readProperty(reader));
        case VALUE_TYPE_NODE_NAME -> builder.valueType(readValueType(reader));
        case OBJECT_TYPE_NODE_NAME -> builder.objectType(readObjectType(reader));
        default -> {
          // no-op
        }
      }
      reader.moveUp();
    }

    return builder.build();
  }

  private static XdtoPackageData.ObjectType readObjectType(HierarchicalStreamReader reader) {
    var builder = XdtoPackageData.ObjectType.builder()
      .name(stringInterner.intern(reader.getAttribute(NAME_ATTRIBUTE_NAME)));
    builder.base(getAttribute(reader, BASE_ATTRIBUTE_NAME));

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      builder.property(readProperty(reader));
      reader.moveUp();
    }

    return builder.build();
  }

  private static XdtoPackageData.ValueType readValueType(HierarchicalStreamReader reader) {
    var builder = XdtoPackageData.ValueType.builder()
      .name(stringInterner.intern(reader.getAttribute(NAME_ATTRIBUTE_NAME)))
      .base(getAttribute(reader, BASE_ATTRIBUTE_NAME))
      .variety(getAttribute(reader, VARIETY_ATTRIBUTE_NAME));

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if (ENUMERATION_ATTRIBUTE_NAME.equals(reader.getNodeName())) {
        builder.enumeration(stringInterner.intern(reader.getValue()));
      }
      reader.moveUp();
    }

    return builder.build();
  }

  private static XdtoPackageData.Property readProperty(HierarchicalStreamReader reader) {
    var builder = XdtoPackageData.Property.builder()
      .name(stringInterner.intern(reader.getAttribute(NAME_ATTRIBUTE_NAME)))
      .type(getAttribute(reader, TYPE_ATTRIBUTE_NAME))
      .form(getAttribute(reader, FORM_ATTRIBUTE_NAME));

    var value = reader.getAttribute(LOWER_BOUND_ATTRIBUTE_NAME);
    if (value != null) {
      builder.lowerBound(Integer.parseInt(value));
    }

    value = reader.getAttribute(UPPER_BOUND_ATTRIBUTE_NAME);
    if (value != null) {
      builder.upperBound(Integer.parseInt(value));
    }

    value = reader.getAttribute(NILLABLE_ATTRIBUTE_NAME);
    if (value != null) {
      builder.nillable(Boolean.parseBoolean(value));
    }

    readTypeDef(reader, builder);

    return builder.build();
  }

  private static void readTypeDef(HierarchicalStreamReader reader, XdtoPackageData.Property.PropertyBuilder builder) {
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if (!TYPE_DEF_NODE_NAME.equals(reader.getNodeName())) {
        // пропустим и пойдем дальше
        reader.moveUp();
        continue;
      }

      while (reader.hasMoreChildren()) {
        reader.moveDown();
        if (PROPERTY_NODE_NAME.equals(reader.getNodeName())) {
          builder.property(readProperty(reader));
        }
        reader.moveUp();
      }
      reader.moveUp();
    }
  }

  @Override
  public boolean canConvert(Class type) {
    return XdtoPackageData.class.isAssignableFrom(type);
  }

  private static String getAttribute(HierarchicalStreamReader reader, String name) {
    var value = reader.getAttribute(name);
    if (value == null) {
      value = "";
    }
    return stringInterner.intern(value);
  }
}
