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

import com.github._1c_syntax.bsl.mdo.data_storage.DataCompositionSchema;
import com.github._1c_syntax.bsl.mdo.data_storage.QuerySource;
import com.github._1c_syntax.bsl.mdo.support.DataSetType;
import com.github._1c_syntax.bsl.mdo.support.SourcePosition;
import com.github._1c_syntax.mdclasses.unmarshal.ExtendReaderWrapper;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

@DesignerConverter
public class DataSetConverter implements Converter {

  private static final String NAME_NODE_NAME = "name";
  private static final String FIELD_NODE_NAME = "field";
  private static final String DATA_SOURCE_NODE_NAME = "dataSource";
  private static final String QUERY_SOURCE_NODE_NAME = "query";
  private static final String ITEM_SOURCE_NODE_NAME = "item";
  private static final String DATA_PATH_NODE_NAME = "dataPath";

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var dataSet = DataCompositionSchema.DataSet.builder();
    dataSet.type(DataSetType.fromValue(reader.getAttribute("type")));

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var nodeName = reader.getNodeName();
      if (NAME_NODE_NAME.equals(nodeName)) {
        dataSet.name(reader.getValue());
      } else if (DATA_SOURCE_NODE_NAME.equals(nodeName)) {
        dataSet.dataSource(reader.getValue());
      } else if (ITEM_SOURCE_NODE_NAME.equals(nodeName)) {
        dataSet.item((DataCompositionSchema.DataSet)
          context.convertAnother(reader, DataCompositionSchema.DataSet.class));
      } else if (FIELD_NODE_NAME.equals(nodeName)) {
        var dataPath = "";
        var field = "";
        while (reader.hasMoreChildren()) {
          reader.moveDown();
          var fieldNodeName = reader.getNodeName();
          if (FIELD_NODE_NAME.equals(fieldNodeName)) {
            field = reader.getValue();
          } else if (DATA_PATH_NODE_NAME.equals(fieldNodeName)) {
            dataPath = reader.getValue();
          }
          reader.moveUp();
        }
        dataSet.field(new DataCompositionSchema.DataSetField(dataPath, field));
      } else if (QUERY_SOURCE_NODE_NAME.equals(nodeName)) {
        var location = ((ExtendReaderWrapper) reader).getXMLStreamReader().getLocation();
        var query = reader.getValue();
        var position = new SourcePosition(location.getLineNumber(), location.getColumnNumber());
        dataSet.querySource(new QuerySource(position, query));
      }
      reader.moveUp();
    }
    return dataSet.build();
  }

  @Override
  public boolean canConvert(Class type) {
    return type == DataCompositionSchema.DataSet.class;
  }
}
