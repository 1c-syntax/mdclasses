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
package com.github._1c_syntax.reader.designer.converter;

import com.github._1c_syntax.bsl.mdo.storages.RoleRight;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Конвертор для объектов в формате конфигуратора, минуя класс враппер
 */
@Slf4j
@DesignerConverter
public class RoleRightConverter implements Converter {

  private static final String OBJECT_NODE_NAME = "object";
  private static final String NAME_NODE_NAME = "name";

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // no-op
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    Map<String, Object> properties = new HashMap<>();
    List<RoleRight> rights = new ArrayList<>();

    // линейно читаем файл
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var nodeName = reader.getNodeName();
      if (OBJECT_NODE_NAME.equals(nodeName)) {
        rights.add(readRight(reader));
      } else {
        properties.put(nodeName, Boolean.parseBoolean(reader.getValue()));
      }
      reader.moveUp();
    }

    properties.put("rights", rights);
    return properties;
  }

  @Override
  public boolean canConvert(Class type) {
    return RoleRight.class.isAssignableFrom(type);
  }

  private RoleRight readRight(HierarchicalStreamReader reader) {
    Map<String, Boolean> rights = new HashMap<>();
    var name = "";

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var nodeName = reader.getNodeName();
      if (NAME_NODE_NAME.equals(nodeName)) {
        name = reader.getValue();
      } else {
        String key = "";
        boolean value = false;
        while (reader.hasMoreChildren()) {
          reader.moveDown();
          if (NAME_NODE_NAME.equals(reader.getNodeName())) {
            key = reader.getValue();
          } else {
            value = Boolean.parseBoolean(reader.getValue());
          }
          reader.moveUp();
        }
        rights.put(key, value);
      }

      reader.moveUp();
    }

    return new RoleRight(name, rights);
  }
}
