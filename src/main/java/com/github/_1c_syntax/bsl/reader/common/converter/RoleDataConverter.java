/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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

import com.github._1c_syntax.bsl.mdo.storage.RoleData;
import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

/**
 * Используется для преобразования содержимого пакета XDTO
 */
@CommonConverter
public class RoleDataConverter implements ReadConverter {

  private static final String OBJECT_NODE_NAME = "object";
  private static final String RIGHT_NODE_NAME = "right";

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

    var builder = RoleData.builder();

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var name = reader.getNodeName();
      if (name.equals(OBJECT_NODE_NAME)) {
        var objectRight = readObjectRight(reader, context);
        builder.objectRight(objectRight);
      } else {
        var fieldClass = (Class<?>) TransformationUtils.fieldType(builder, name);
        var value = context.convertAnother(fieldClass, fieldClass);
        TransformationUtils.setValue(builder, name, value);
      }

      reader.moveUp();
    }
    return builder.build();
  }

  private RoleData.ObjectRight readObjectRight(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var builder = RoleData.ObjectRight.builder();

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var name = reader.getNodeName();
      if (name.equals(RIGHT_NODE_NAME)) {
        var right = readRight(reader, context);
        builder.right(right);
      } else {
        var fieldClass = (Class<?>) TransformationUtils.fieldType(builder, name);
        var value = context.convertAnother(fieldClass, fieldClass);
        TransformationUtils.setValue(builder, name, value);
      }

      reader.moveUp();
    }
    return builder.build();
  }

  private RoleData.Right readRight(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var builder = RoleData.Right.builder();

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var name = reader.getNodeName();
      var fieldClass = (Class<?>) TransformationUtils.fieldType(builder, name);
      var value = context.convertAnother(fieldClass, fieldClass);
      TransformationUtils.setValue(builder, name, value);
      reader.moveUp();
    }
    return builder.build();
  }

  @Override
  public boolean canConvert(Class type) {
    return RoleData.class.isAssignableFrom(type);
  }
}
