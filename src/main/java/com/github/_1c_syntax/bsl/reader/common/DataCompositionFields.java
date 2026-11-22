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
package com.github._1c_syntax.bsl.reader.common;

import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Сбор полей, названных в настройках компоновки данных — в отборе, порядке,
 * условном оформлении и оформляемых полях.
 * <p>
 * Настройки устроены одинаково в обоих форматах и различаются только префиксом
 * пространства имён, который ридер снимает. Полем названы узел {@code field}
 * (оформляемое поле, поле порядка, поле группировки) и операнд сравнения
 * {@code left}/{@code right}, если он объявлен полем, а не значением
 */
public final class DataCompositionFields {

  private static final String FIELD_NODE = "field";
  private static final String LEFT_NODE = "left";
  private static final String RIGHT_NODE = "right";
  private static final String TYPE_ATTRIBUTE = "type";
  private static final String FIELD_TYPE_SUFFIX = "Field";

  private DataCompositionFields() {
  }

  /**
   * Собирает поля из поддерева настроек. Читатель должен стоять на узле
   * настроек — обходятся все его потомки
   *
   * @param reader читатель, стоящий на узле настроек
   * @return пути к данным в порядке появления, с повторами
   */
  public static List<String> collect(HierarchicalStreamReader reader) {
    var fields = new ArrayList<String>();
    collectInto(reader, fields);
    return fields;
  }

  private static void collectInto(HierarchicalStreamReader reader, List<String> fields) {
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var name = reader.getNodeName();
      if (FIELD_NODE.equals(name) || isFieldOperand(reader, name)) {
        var value = reader.getValue();
        if (value != null && !value.isBlank()) {
          fields.add(value.trim());
        }
      } else {
        collectInto(reader, fields);
      }
      reader.moveUp();
    }
  }

  /**
   * Операнд сравнения бывает и полем, и значением: поле объявлено типом
   * {@code dcscor:Field}, значение — своим типом ({@code xs:boolean} и прочие)
   */
  private static boolean isFieldOperand(HierarchicalStreamReader reader, String name) {
    if (!LEFT_NODE.equals(name) && !RIGHT_NODE.equals(name)) {
      return false;
    }
    var type = reader.getAttribute(TYPE_ATTRIBUTE);
    return type != null && type.endsWith(FIELD_TYPE_SUFFIX);
  }
}
