/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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

import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * Конвертер для строк на нескольких языках
 */
@Slf4j
@CommonConverter
public class MultiLanguageStringConverter implements ReadConverter {

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    if (!reader.hasMoreChildren()) {
      return MultiLanguageString.EMPTY;
    }

    Set<MultiLanguageString.Entry> langContent = new HashSet<>();
    if (ExtendXStream.getCurrentMDReader(reader).getConfigurationSource() == ConfigurationSource.DESIGNER) {
      while (reader.hasMoreChildren()) { // v8:item
        reader.moveDown();
        langContent.add(multiLanguageString(reader, "lang", "content"));
        reader.moveUp();
      }
    } else {
      langContent.add(multiLanguageString(reader, "key", "value"));
    }
    return MultiLanguageString.create(langContent);
  }

  @Override
  public boolean canConvert(Class type) {
    return type == MultiLanguageString.class;
  }

  private static MultiLanguageString.Entry multiLanguageString(HierarchicalStreamReader reader,
                                                               String langNodeName,
                                                               String contentNodeName) {
    var lang = "";
    var content = "";
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var node = reader.getNodeName();
      if (langNodeName.equals(node)) {
        lang = reader.getValue();
      } else if (contentNodeName.equals(node)) {
        content = reader.getValue();
      } else {
        // no-op
      }
      reader.moveUp();
    }
    return MultiLanguageString.Entry.create(lang, content);
  }
}
