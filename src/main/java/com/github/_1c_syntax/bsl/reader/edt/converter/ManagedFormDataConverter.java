/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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

import com.github._1c_syntax.bsl.mdo.storage.ManagedFormData;
import com.github._1c_syntax.bsl.mdo.storage.form.FormAttribute;
import com.github._1c_syntax.bsl.mdo.storage.form.FormHandler;
import com.github._1c_syntax.bsl.mdo.storage.form.FormItem;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Конвертор данных управляемой формы в формате ЕДТ
 */
@EDTConverter
public class ManagedFormDataConverter implements ReadConverter {

  private static final String TITLE_NODE_NAME = "title";
  private static final String HANDLERS_NODE_NAME = "handlers";
  private static final String ITEMS_NODE_NAME = "items";
  private static final String ATTRIBUTES_NODE_NAME = "attributes";


  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var currentPath = ExtendXStream.getCurrentPath(reader);
    var builder = ManagedFormData.builder().dataPath(currentPath);

    List<MultiLanguageString> titles = new ArrayList<>();
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var node = reader.getNodeName();
      if (TITLE_NODE_NAME.equals(node)) {
        titles.add((MultiLanguageString) context.convertAnother(MultiLanguageString.class, MultiLanguageString.class));
      } else if (HANDLERS_NODE_NAME.equals(node)) {
        var handler = (FormHandler) context.convertAnother(FormHandler.class, FormHandler.class);
        if (handler != null) {
          builder.handler(handler);
        }
      } else if (ITEMS_NODE_NAME.equals(node)) {
        var item = (FormItem) context.convertAnother(FormItem.class, FormItem.class);
        if (item != null) {
          builder.item(item);
        }
      } else if (ATTRIBUTES_NODE_NAME.equals(node)) {
        var attribute = (FormAttribute) context.convertAnother(FormAttribute.class, FormAttribute.class);
        if (attribute != null) {
          builder.attribute(attribute);
        }
      }
      reader.moveUp();
    }

    builder.title(MultiLanguageString.of(titles));
    return builder.build();
  }

  @Override
  public boolean canConvert(Class type) {
    return type == ManagedFormData.class;
  }
}
