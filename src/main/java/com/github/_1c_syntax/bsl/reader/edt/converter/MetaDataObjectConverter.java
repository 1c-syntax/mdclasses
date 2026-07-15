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

import com.github._1c_syntax.bsl.reader.common.context.MDReaderContext;
import com.github._1c_syntax.bsl.reader.common.converter.DesignerRootWrapper;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * Конвертор для объектов в формате конфигуратора: стили и интерфейсы
 */
@Slf4j
@EDTConverter
public class MetaDataObjectConverter implements ReadConverter {

  @SneakyThrows
  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    reader.moveDown();
    var nodeClassName = reader.getNodeName();
    Class<?> realClass = ExtendXStream.getRealClass(reader, nodeClassName);
    if (realClass == null) {
      LOGGER.error("Unexpected type `{}`, path: `{}`", nodeClassName, ExtendXStream.getCurrentPath(reader));
      throw new IllegalStateException("Unexpected type: " + nodeClassName);
    }

    var readerContext = new MDReaderContext(reader);

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var name = reader.getNodeName();
      if ("Properties".equals(name)) {
        readDesignerProperties(reader, context, readerContext);
      }
      reader.moveUp();
    }

    return readerContext.build();
  }

  private static void readDesignerProperties(HierarchicalStreamReader reader, UnmarshallingContext context, MDReaderContext readerContext) {
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var propName = reader.getNodeName();
      if ("Name".equals(propName)) {
        var value = ExtendXStream.readValue(context, String.class);
        readerContext.setName(value);
        readerContext.setValue("name", value);
      } else if ("Synonym".equals(propName)) {
        Set<MultiLanguageString.Entry> langContent = new HashSet<>();
        while (reader.hasMoreChildren()) { // v8:item
          reader.moveDown();
          langContent.add(multiLanguageString(reader));
          reader.moveUp();
        }
        var value = MultiLanguageString.create(langContent);
        readerContext.setValue("synonym", value);
      }
      reader.moveUp();
    }
  }

  @Override
  public boolean canConvert(Class type) {
    return DesignerRootWrapper.class.isAssignableFrom(type);
  }

  private static MultiLanguageString.Entry multiLanguageString(HierarchicalStreamReader reader) {
    var lang = "";
    var content = "";
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var node = reader.getNodeName();
      if ("lang".equals(node)) {
        lang = reader.getValue();
      } else if ("content".equals(node)) {
        content = reader.getValue();
      } else {
        // no-op
      }
      reader.moveUp();
    }
    return MultiLanguageString.Entry.create(lang, content);
  }
}
