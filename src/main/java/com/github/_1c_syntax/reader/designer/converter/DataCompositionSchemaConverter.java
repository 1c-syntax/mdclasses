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

import com.github._1c_syntax.bsl.mdo.storages.DataCompositionSchema;
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
public class DataCompositionSchemaConverter implements Converter {

  private static final String DATASET_NODE_NAME = "dataSet";

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // no-op
  }

  @SneakyThrows
  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    List<DataCompositionSchema.DataSet> dataSets = new ArrayList<>();

    // линейно читаем файл
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var nodeName = reader.getNodeName();
      if (DATASET_NODE_NAME.equals(nodeName)) {
        dataSets.add((DataCompositionSchema.DataSet)
          context.convertAnother(reader, DataCompositionSchema.DataSet.class));
      }
      reader.moveUp();
    }

    return Map.of("templateData", new DataCompositionSchema(dataSets));
  }

  @Override
  public boolean canConvert(Class type) {
    return DataCompositionSchema.class.isAssignableFrom(type);
  }
}
