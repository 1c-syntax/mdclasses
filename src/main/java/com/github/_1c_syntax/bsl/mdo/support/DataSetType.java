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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum DataSetType implements EnumWithValue {
  DATA_SET_QUERY("DataSetQuery"),
  DATA_SET_UNION("DataSetUnion"),
  DATA_SET_OBJECT("DataSetObject"),
  UNKNOWN("unknown") {
    @Override
    public boolean isUnknown() {
      return true;
    }
  };

  private static final Map<String, DataSetType> KEYS = computeKeys();

  @Accessors(fluent = true)
  private final String value;

  /**
   * Выполняет преобразование из строкового представления в значение
   *
   * @param value Строковое представление
   * @return Найденный тип
   */
  public static DataSetType fromString(String value) {
    return KEYS.getOrDefault(value, UNKNOWN);
  }

  private static Map<String, DataSetType> computeKeys() {
    Map<String, DataSetType> keys = new HashMap<>();
    for (DataSetType dataSetType : DataSetType.values()) {
      keys.put(dataSetType.value, dataSetType);
    }
    return Collections.unmodifiableMap(keys);
  }
}
