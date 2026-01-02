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

import com.github._1c_syntax.bsl.mdo.storage.DataCompositionSchema;
import com.github._1c_syntax.bsl.mdo.storage.QuerySource;
import com.github._1c_syntax.bsl.mdo.support.DataSetType;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

/**
 * Конвертор данных типа DataSet, применяемый в формах и СКД
 */
@CommonConverter
public class DataSetConverter implements ReadConverter {

  private static final String NAME_NODE_NAME = "name";
  private static final String FIELD_NODE_NAME = "field";
  private static final String TYPE_ATTRIBUTE_NAME = "type";
  private static final String DATA_SOURCE_NODE_NAME = "dataSource";
  private static final String QUERY_SOURCE_NODE_NAME = "query";
  private static final String ITEM_SOURCE_NODE_NAME = "item";
  private static final String DATA_PATH_NODE_NAME = "dataPath";

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var dataSet = DataCompositionSchema.DataSet.builder();
    dataSet
      .name("") // по умолчанию
      .dataSource("") // по умолчанию
      .querySource(QuerySource.EMPTY) // по умолчанию
      .type(DataSetType.valueByName(reader.getAttribute(TYPE_ATTRIBUTE_NAME)));

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      switch (reader.getNodeName()) {
        case NAME_NODE_NAME -> dataSet.name(reader.getValue());
        case DATA_SOURCE_NODE_NAME -> dataSet.dataSource(reader.getValue());
        case ITEM_SOURCE_NODE_NAME ->
          dataSet.item(ExtendXStream.readValue(context, DataCompositionSchema.DataSet.class));
        case FIELD_NODE_NAME -> dataSet.field(readField(reader));
        case QUERY_SOURCE_NODE_NAME -> dataSet.querySource(ExtendXStream.readValue(context, QuerySource.class));
        default -> {
          // no-op
        }
      }
      reader.moveUp();
    }
    return dataSet.build();
  }

  private static DataCompositionSchema.DataSetField readField(HierarchicalStreamReader reader) {
    var dataPath = "";
    var field = "";
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      switch (reader.getNodeName()) {
        case FIELD_NODE_NAME -> field = reader.getValue();
        case DATA_PATH_NODE_NAME -> dataPath = reader.getValue();
        default -> {
          // no-op
        }
      }
      reader.moveUp();
    }
    return new DataCompositionSchema.DataSetField(dataPath, field);
  }

  @Override
  public boolean canConvert(Class type) {
    return type == DataCompositionSchema.DataSet.class;
  }
}
