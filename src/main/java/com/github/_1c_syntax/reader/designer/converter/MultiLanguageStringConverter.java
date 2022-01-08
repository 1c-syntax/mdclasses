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

import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * Конвертер для строк на нескольких языках
 */
@DesignerConverter
@Slf4j
public class MultiLanguageStringConverter implements Converter {

  private static final String LANG_NODE_NAME = "lang";
  private static final String CONTENT_NODE_NAME = "content";

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // no-op
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    if (!reader.hasMoreChildren()) {
      return MultiLanguageString.EMPTY;
    }
    HashMap<String, String> langContent = new HashMap<>();

    while (reader.hasMoreChildren()) { // v8:item
      reader.moveDown();
      var lang = "";
      var content = "";
      while (reader.hasMoreChildren()) {
        reader.moveDown();
        var node = reader.getNodeName();
        if (LANG_NODE_NAME.equals(node)) {
          lang = reader.getValue();
        } else if (CONTENT_NODE_NAME.equals(node)) {
          content = reader.getValue();
        } else {
          // no-op
        }
        reader.moveUp();
      }
      reader.moveUp();
      langContent.put(lang, content);
    }
    return new MultiLanguageString(langContent);
  }

  @Override
  public boolean canConvert(Class type) {
    return type == MultiLanguageString.class;
  }
}
