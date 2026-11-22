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
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import java.util.HashSet;
import java.util.Set;

/**
 * Конвертор варианта отчета схемы компоновки данных.
 * <p>
 * Представление варианта может быть как обычной строкой, так и мультиязычной строкой
 * ({@code v8:LocalStringType}), при этом структура XML одинакова для форматов ЕДТ и Конфигуратора.
 */
@CommonConverter
public class SettingsVariantConverter implements ReadConverter {

  private static final String NAME_NODE_NAME = "name";
  private static final String PRESENTATION_NODE_NAME = "presentation";
  private static final String ITEM_NODE_NAME = "item";
  private static final String LANG_NODE_NAME = "lang";
  private static final String CONTENT_NODE_NAME = "content";

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var name = "";
    var presentation = MultiLanguageString.EMPTY;

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      switch (reader.getNodeName()) {
        case NAME_NODE_NAME -> name = reader.getValue();
        case PRESENTATION_NODE_NAME -> presentation = readPresentation(reader);
        default -> {
          // no-op
        }
      }
      reader.moveUp();
    }

    return new DataCompositionSchema.SettingsVariant(name, presentation);
  }

  @Override
  public boolean canConvert(Class type) {
    return type == DataCompositionSchema.SettingsVariant.class;
  }

  private static MultiLanguageString readPresentation(HierarchicalStreamReader reader) {
    if (!reader.hasMoreChildren()) {
      var value = reader.getValue();
      return value.isEmpty() ? MultiLanguageString.EMPTY : MultiLanguageString.create("", value);
    }

    Set<MultiLanguageString.Entry> langContent = new HashSet<>();
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if (ITEM_NODE_NAME.equals(reader.getNodeName())) {
        langContent.add(multiLanguageString(reader));
      }
      reader.moveUp();
    }
    return MultiLanguageString.create(langContent);
  }

  private static MultiLanguageString.Entry multiLanguageString(HierarchicalStreamReader reader) {
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
    return MultiLanguageString.Entry.create(lang, content);
  }
}
