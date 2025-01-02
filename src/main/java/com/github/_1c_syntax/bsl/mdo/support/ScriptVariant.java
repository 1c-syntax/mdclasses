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
package com.github._1c_syntax.bsl.mdo.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Возможные варианты языков, на которых разрабатывается код
 */
@AllArgsConstructor
@Getter
public enum ScriptVariant implements EnumWithValue {
  ENGLISH("English", "Английский", "en"),
  RUSSIAN("Russian", "Русский", "ru"),
  UNKNOWN("unknown", "unknown", "--") {
    @Override
    public boolean isUnknown() {
      return true;
    }
  };

  private static final Map<String, ScriptVariant> keys = computeKeys();

  /**
   * Английское имя
   */
  @Accessors(fluent = true)
  private final String value;

  /**
   * Русское имя
   */
  @Accessors(fluent = true)
  private final String valueRu;

  /**
   * Сокращенное имя
   */
  @Accessors(fluent = true)
  private final String shortName;

  /**
   * Ищет элемент перечисления по именам (рус, анг, короткое)
   *
   * @param string Имя искомого элемента
   * @return Найденное значение, если не найден - то RUSSIAN
   */
  public static ScriptVariant valueByString(String string) {
    return keys.getOrDefault(string, RUSSIAN);
  }

  private static Map<String, ScriptVariant> computeKeys() {
    Map<String, ScriptVariant> keysMap = new ConcurrentSkipListMap<>(String.CASE_INSENSITIVE_ORDER);
    for (var element : values()) {
      if (element.isUnknown()) {
        continue;
      }
      keysMap.put(element.value(), element);
      keysMap.put(element.valueRu(), element);
      keysMap.put(element.shortName(), element);
    }
    return keysMap;
  }
}
