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
package com.github._1c_syntax.mdclasses.unmarshal;

import com.github._1c_syntax.mdclasses.metadata.additional.QuerySource;
import com.github._1c_syntax.mdclasses.metadata.additional.SourcePosition;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.XppReader;
import lombok.SneakyThrows;
import org.xmlpull.mxp1.MXParser;

import java.lang.reflect.Field;

public class QuerySourceConverter implements Converter {
  private static final Field parserField = getParserField();

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var query = reader.getValue();
    var position = getSourcePosition(reader, query);
    return new QuerySource(position, query);
  }

  @Override
  public boolean canConvert(Class type) {
    return type == QuerySource.class;
  }

  @SneakyThrows
  private static SourcePosition getSourcePosition(HierarchicalStreamReader reader, String query) {
    var lines = query.split("\n").length;
    var parser = (MXParser) parserField.get(reader);
    return new SourcePosition(parser.getLineNumber() - lines + 1, 0);
  }

  @SneakyThrows
  private static Field getParserField() {
    var filed = XppReader.class.getDeclaredField("parser");
    // проектное решение, правильно делать через свой reader
    filed.setAccessible(true);
    return filed;
  }
}
