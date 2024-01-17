/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2024
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
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendReaderWrapper;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Конвертор схемы компоновки данных. Для форматов ЕДТ и Конфигуратора одинаков
 */
@Slf4j
@CommonConverter
public class DataCompositionSchemaConverter implements ReadConverter {

  private static final String DATASET_NODE_NAME = "dataSet";

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

    return new DataCompositionSchema(dataSets, ((ExtendReaderWrapper) reader).getPath());
  }

  @Override
  public boolean canConvert(Class type) {
    return DataCompositionSchema.class.isAssignableFrom(type);
  }
}
