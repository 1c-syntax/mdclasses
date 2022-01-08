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
package com.github._1c_syntax.reader.common.converter;

import com.github._1c_syntax.bsl.mdo.storages.XdtoPackageData;
import com.github._1c_syntax.reader.common.TransformationUtils;
import com.github._1c_syntax.reader.designer.converter.DesignerConverter;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Используется для преобразования содержимого пакета XDTO
 */
@DesignerConverter
public class XdtoPackageDataConverter implements Converter {

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

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

    var builder = XdtoPackageData.builder();
    builder.targetNamespace(reader.getAttribute(TARGET_NAMESPACE_ATTRIBUTE_NAME));

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var node = reader.getNodeName();
      switch (node) {
        case IMPORT_NODE_NAME:
          builder.oneImport(reader.getAttribute(NAMESPACE_ATTRIBUTE_NAME));
          break;
        case PROPERTY_NODE_NAME:
          builder.property(readProperty(reader));
          break;
        case VALUE_TYPE_NODE_NAME:
          builder.valueType(readValueType(reader));
          break;
        case OBJECT_TYPE_NODE_NAME:
          builder.objectType(readObjectType(reader));
          break;
        default:
          // no-op
          break;
      }
      reader.moveUp();
    }

    return builder.build();
  }

  private XdtoPackageData.ObjectType readObjectType(HierarchicalStreamReader reader) {
    var builder = XdtoPackageData.ObjectType.builder()
      .name(reader.getAttribute(NAME_ATTRIBUTE_NAME));
    TransformationUtils.setValue(builder, BASE_ATTRIBUTE_NAME, reader.getAttribute(BASE_ATTRIBUTE_NAME));

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      builder.property(readProperty(reader));
      reader.moveUp();
    }

    return builder.build();
  }

  private static XdtoPackageData.ValueType readValueType(HierarchicalStreamReader reader) {
    var builder = XdtoPackageData.ValueType.builder()
      .name(reader.getAttribute(NAME_ATTRIBUTE_NAME));
    TransformationUtils.setValue(builder, BASE_ATTRIBUTE_NAME, reader.getAttribute(BASE_ATTRIBUTE_NAME));
    TransformationUtils.setValue(builder, VARIETY_ATTRIBUTE_NAME, reader.getAttribute(VARIETY_ATTRIBUTE_NAME));

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if (ENUMERATION_ATTRIBUTE_NAME.equals(reader.getNodeName())) {
        builder.enumeration(reader.getValue());
      }
      reader.moveUp();
    }

    return builder.build();
  }

  private static XdtoPackageData.Property readProperty(HierarchicalStreamReader reader) {
    var builder = XdtoPackageData.Property.builder()
      .name(reader.getAttribute(NAME_ATTRIBUTE_NAME));
    TransformationUtils.setValue(builder, TYPE_ATTRIBUTE_NAME, reader.getAttribute(TYPE_ATTRIBUTE_NAME));
    TransformationUtils.setValue(builder, FORM_ATTRIBUTE_NAME, reader.getAttribute(FORM_ATTRIBUTE_NAME));

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
      var node = reader.getNodeName();
      if (TYPE_DEF_NODE_NAME.equals(node)) {
        while (reader.hasMoreChildren()) {
          reader.moveDown();
          node = reader.getNodeName();
          if (PROPERTY_NODE_NAME.equals(node)) {
            builder.property(readProperty(reader));
          }
          reader.moveUp();
        }
      }
      reader.moveUp();
    }
  }

  @Override
  public boolean canConvert(Class type) {
    return XdtoPackageData.class.isAssignableFrom(type);
  }
}
