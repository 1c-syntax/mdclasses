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

import com.github._1c_syntax.bsl.mdo.storage.form.FormHandler;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

/**
 * Конвертор обработчика события формы в формате ЕДТ
 */
@EDTConverter
public class FormHandlerConverter implements ReadConverter {

  private static final String NAME_NODE_NAME = "name";
  private static final String EVENT_NODE_NAME = "event";

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var event = "";
    var name = "";
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var node = reader.getNodeName();
      if (NAME_NODE_NAME.equals(node)) {
        name = reader.getValue().intern();
      } else if (EVENT_NODE_NAME.equals(node)) {
        event = reader.getValue().intern();
      } else {
        // no-op
      }
      reader.moveUp();
    }

    if (event.isEmpty() && name.isEmpty()) {
      return null;
    }

    return new FormHandler(event, name);
  }

  @Override
  public boolean canConvert(Class type) {
    return type == FormHandler.class;
  }
}
