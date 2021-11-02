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
package com.github._1c_syntax.reader.common.converter;

import com.github._1c_syntax.bsl.mdo.data_storage.XdtoPackageData;
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
  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

    var builder = XdtoPackageData.builder();
    builder.targetNamespace(reader.getAttribute("targetNamespace"));

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var node = reader.getNodeName();
      switch (node) {
        case "import":
          builder.oneImport(reader.getAttribute("namespace"));
          break;
        case "property":
          builder.property(readProperty(reader));
          break;
        case "valueType":
          builder.valueType(readValueType(reader));
          break;
        case "objectType":
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
      .name(reader.getAttribute("name"));
    var baseType = reader.getAttribute("base");
    if (baseType != null) {
      builder.base(baseType);
    }

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      builder.property(readProperty(reader));
      reader.moveUp();
    }

    return builder.build();
  }

  private XdtoPackageData.ValueType readValueType(HierarchicalStreamReader reader) {
    var builder = XdtoPackageData.ValueType.builder()
      .name(reader.getAttribute("name"));
    var value = reader.getAttribute("base");
    if (value != null) {
      builder.base(value);
    }

    value = reader.getAttribute("variety");
    if (value != null) {
      builder.variety(value);
    }

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if ("enumeration".equals(reader.getNodeName())) {
        builder.enumeration(reader.getValue());
      }
      reader.moveUp();
    }

    return builder.build();
  }

  private XdtoPackageData.Property readProperty(HierarchicalStreamReader reader) {
    var builder = XdtoPackageData.Property.builder()
      .name(reader.getAttribute("name"));
    var value = reader.getAttribute("type");
    if (value != null) {
      builder.type(value);
    }

    value = reader.getAttribute("form");
    if (value != null) {
      builder.form(value);
    }

    value = reader.getAttribute("lowerBound");
    if (value != null) {
      builder.lowerBound(Integer.parseInt(value));
    }

    value = reader.getAttribute("upperBound");
    if (value != null) {
      builder.upperBound(Integer.parseInt(value));
    }

    value = reader.getAttribute("nillable");
    if (value != null) {
      builder.nillable(Boolean.parseBoolean(value));
    }

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var node = reader.getNodeName();
      if ("typeDef".equals(node)) {
        while (reader.hasMoreChildren()) {
          reader.moveDown();
          node = reader.getNodeName();
          if ("property".equals(node)) {
            builder.property(readProperty(reader));
          }
          reader.moveUp();
        }
      }
      reader.moveUp();
    }

    return builder.build();
  }

  @Override
  public boolean canConvert(Class type) {
    return XdtoPackageData.class.isAssignableFrom(type);
  }
}
