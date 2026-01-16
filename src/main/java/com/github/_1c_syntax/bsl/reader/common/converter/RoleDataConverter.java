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

import com.github._1c_syntax.bsl.mdo.storage.RoleData;
import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.extern.slf4j.Slf4j;

/**
 * Используется для преобразования содержимого пакета XDTO
 */
@Slf4j
@CommonConverter
public class RoleDataConverter implements ReadConverter {

  private static final String OBJECT_NODE_NAME = "object";
  private static final String RIGHT_NODE_NAME = "right";
  private static final String RESTRICTION_TEMPLATE_NODE_NAME = "restrictionTemplate";

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    if (ExtendXStream.getCurrentMDReader(reader).getReadSettings().skipRoleData()) {
      return RoleData.EMPTY;
    }

    var builder = RoleData.builder();

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var name = reader.getNodeName();
      if (OBJECT_NODE_NAME.equals(name)) {
        var objectRight = readObjectRight(reader, context);
        builder.objectRight(objectRight);
      } else if (!RESTRICTION_TEMPLATE_NODE_NAME.equals(name)) { // рестрикций пока нет
        var fieldClass = (Class<?>) TransformationUtils.fieldType(builder, name);
        if (fieldClass != null) {
          var value = ExtendXStream.readValue(context, fieldClass);
          TransformationUtils.setValue(builder, name, value);
        } else {
          // пропустим без падения и будем надеяться, что о ворнинге кто-то скажет
          LOGGER.warn("Field type not found for {}", name);
        }
      }

      reader.moveUp();
    }
    return builder.build();
  }

  private static RoleData.ObjectRight readObjectRight(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var builder = RoleData.ObjectRight.builder();

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var name = reader.getNodeName();
      if (RIGHT_NODE_NAME.equals(name)) {
        var right = readRight(reader, context);
        builder.right(right);
      } else {
        var fieldClass = (Class<?>) TransformationUtils.fieldType(builder, name);
        if (fieldClass != null) {
          var value = ExtendXStream.readValue(context, fieldClass);
          TransformationUtils.setValue(builder, name, value);
        }
      }

      reader.moveUp();
    }
    return builder.build();
  }

  private static RoleData.Right readRight(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var builder = RoleData.Right.builder();

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var name = reader.getNodeName();
      var fieldClass = (Class<?>) TransformationUtils.fieldType(builder, name);
      if (fieldClass != null) {
        var value = ExtendXStream.readValue(context, fieldClass);
        TransformationUtils.setValue(builder, name, value);
      }
      reader.moveUp();
    }

    return RoleData.RIGHT_INTERNER.intern(builder.build());
  }

  @Override
  public boolean canConvert(Class type) {
    return RoleData.class.isAssignableFrom(type);
  }
}
