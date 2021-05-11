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
import com.thoughtworks.xstream.io.ReaderWrapper;
import com.thoughtworks.xstream.io.xml.StaxReader;
import lombok.SneakyThrows;

import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamReader;
import java.lang.reflect.Field;

public class QuerySourceConverter implements Converter {
  private static final Field STAX_READER_FLD = getStaxReaderFld();
  private static final Field XML_STREAM_READER_FLD = getXmlStreamReaderFld();

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var location = getLocation(reader);
    var query = reader.getValue();
    var position = new SourcePosition(location.getLineNumber(), location.getColumnNumber());
    return new QuerySource(position, query);
  }

  @SneakyThrows
  private Location getLocation(HierarchicalStreamReader reader) {
    // TODO проектное решение, правильно делать через свой reader
    return ((XMLStreamReader) XML_STREAM_READER_FLD.get(STAX_READER_FLD.get(reader))).getLocation();
  }

  @Override
  public boolean canConvert(Class type) {
    return type == QuerySource.class;
  }

  @SneakyThrows
  private static Field getStaxReaderFld() {
    var filed = ReaderWrapper.class.getDeclaredField("wrapped");
    // TODO проектное решение, правильно делать через свой reader
    filed.setAccessible(true);
    return filed;
  }

  @SneakyThrows
  private static Field getXmlStreamReaderFld() {
    var filed = StaxReader.class.getDeclaredField("in");
    // TODO проектное решение, правильно делать через свой reader
    filed.setAccessible(true);
    return filed;
  }
}
