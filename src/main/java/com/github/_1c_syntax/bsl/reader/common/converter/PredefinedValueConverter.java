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

import com.github._1c_syntax.bsl.mdo.children.PredefinedValue;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

/**
 * Читает один предопределенный элемент ({@link PredefinedValue}) из файла объекта (EDT)
 * или из файла предопределенных данных (Конфигуратор).
 * <p>
 * Поддерживает иерархию: вложенные предопределенные элементы читаются рекурсивно.
 * Ссылка ({@code mdoReference}) и владелец ({@code owner}) проставляются позднее, при сборке
 * родительского объекта.
 */
@CommonConverter
public class PredefinedValueConverter implements ReadConverter {

  private static final String ID_ATTRIBUTE = "id";
  private static final String CHILD_ITEMS_NODE = "ChildItems";
  private static final String ITEMS_NODE = "items";
  private static final String VALUE_NODE = "value";

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var builder = PredefinedValue.builder();

    var id = reader.getAttribute(ID_ATTRIBUTE);
    if (id != null) {
      builder.uuid(id);
    }

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var node = reader.getNodeName();
      switch (node) {
        case "Name", "name" -> builder.name(reader.getValue());
        case "Description", "description" -> builder.description(reader.getValue());
        case "Code", "code" -> builder.code(readCode(reader));
        case "IsFolder", "isFolder" -> builder.folder(Boolean.parseBoolean(reader.getValue()));
        case CHILD_ITEMS_NODE -> readChildItems(reader, context, builder);
        case ITEMS_NODE -> builder.childItem((PredefinedValue) context.convertAnother(null, PredefinedValue.class));
        default -> {
          // прочие свойства игнорируем
        }
      }
      reader.moveUp();
    }

    return builder.build();
  }

  /**
   * Чтение кода: в Конфигураторе - простое значение, в EDT - вложенный элемент {@code <value>}
   */
  private static String readCode(HierarchicalStreamReader reader) {
    if (!reader.hasMoreChildren()) {
      return reader.getValue();
    }
    var code = "";
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if (VALUE_NODE.equals(reader.getNodeName())) {
        code = reader.getValue();
      }
      reader.moveUp();
    }
    return code;
  }

  /**
   * Чтение вложенных предопределенных элементов из обертки {@code <ChildItems>} (Конфигуратор)
   */
  private static void readChildItems(HierarchicalStreamReader reader,
                                     UnmarshallingContext context,
                                     PredefinedValue.PredefinedValueBuilder builder) {
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      builder.childItem((PredefinedValue) context.convertAnother(null, PredefinedValue.class));
      reader.moveUp();
    }
  }

  @Override
  public boolean canConvert(Class type) {
    return PredefinedValue.class.isAssignableFrom(type);
  }
}
