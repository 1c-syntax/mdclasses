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
package com.github._1c_syntax.bsl.reader.edt.converter;

import com.github._1c_syntax.bsl.reader.common.converter.AdditionalIndexesWrapper;
import com.github._1c_syntax.bsl.reader.common.converter.AdditionalIndexesWrapper.AdditionalIndexItem;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Конвертер для чтения дополнительных индексов из файла {@code AdditionalIndexes.aindex}
 */
@EDTConverter
public class AdditionalIndexesConverter implements ReadConverter {

  private static final String INDEXES_NODE = "indexes";
  private static final String ID_NODE = "id";
  private static final String NAME_NODE = "name";
  private static final String TABLE_NODE = "table";
  private static final String INDEXED_FIELDS_NODE = "indexedFields";
  private static final String ADDITIONAL_FIELDS_NODE = "additionalFields";
  private static final String PATH_NODE = "path";

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var builder = AdditionalIndexesWrapper.builder();

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if (INDEXES_NODE.equals(reader.getNodeName())) {
        builder.index(readIndexItem(reader));
      }
      reader.moveUp();
    }

    return builder.build();
  }

  @Override
  public boolean canConvert(Class type) {
    return AdditionalIndexesWrapper.class.isAssignableFrom(type);
  }

  private AdditionalIndexItem readIndexItem(HierarchicalStreamReader reader) {
    String id = "";
    String name = "";
    String table = "";
    var indexedFields = new ArrayList<String>();
    var additionalFields = new ArrayList<String>();

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var nodeName = reader.getNodeName();
      switch (nodeName) {
        case ID_NODE -> id = reader.getValue();
        case NAME_NODE -> name = reader.getValue();
        case TABLE_NODE -> table = reader.getValue();
        case INDEXED_FIELDS_NODE -> indexedFields.addAll(readPaths(reader));
        case ADDITIONAL_FIELDS_NODE -> additionalFields.addAll(readPaths(reader));
        default -> {
          // пропуск неизвестных узлов
        }
      }
      reader.moveUp();
    }

    return AdditionalIndexItem.builder()
      .id(id)
      .name(name)
      .table(table)
      .indexedFields(indexedFields)
      .additionalFields(additionalFields)
      .build();
  }

  private List<String> readPaths(HierarchicalStreamReader reader) {
    var paths = new ArrayList<String>();
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if (PATH_NODE.equals(reader.getNodeName())) {
        paths.add(reader.getValue());
      }
      reader.moveUp();
    }
    return paths;
  }
}
