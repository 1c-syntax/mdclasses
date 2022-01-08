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
package com.github._1c_syntax.reader.designer.converter;

import com.github._1c_syntax.bsl.mdo.ExchangePlan;
import com.github._1c_syntax.bsl.mdo.support.AutoRecordType;
import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Конвертор для объектов в формате конфигуратора, минуя класс враппер
 */
@Slf4j
@DesignerConverter
public class ExchangePlanContentConverter implements Converter {

  private static final String ITEM_NODE_NAME = "Item";
  private static final String METADATA_NODE_NAME = "Metadata";

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // no-op
  }

  @SneakyThrows
  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    List<ExchangePlan.Item> content = new ArrayList<>();

    // линейно читаем файл
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var nodeName = reader.getNodeName();
      if (ITEM_NODE_NAME.equals(nodeName)) {
        content.add(readItem(reader, context));
      }
      reader.moveUp();
    }

    return Map.of("content", content);
  }

  @Override
  public boolean canConvert(Class type) {
    return ExchangePlan.Item.class.isAssignableFrom(type);
  }

  private ExchangePlan.Item readItem(HierarchicalStreamReader reader, UnmarshallingContext context) {

    MdoReference metadata = MdoReference.EMPTY;
    AutoRecordType value = AutoRecordType.ALLOW;

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if (METADATA_NODE_NAME.equals(reader.getNodeName())) {
        metadata = (MdoReference) context.convertAnother(reader, MdoReference.class);
      } else {
        value = (AutoRecordType) context.convertAnother(reader, AutoRecordType.class);
      }
      reader.moveUp();
    }

    return new ExchangePlan.Item(metadata, value);
  }
}
