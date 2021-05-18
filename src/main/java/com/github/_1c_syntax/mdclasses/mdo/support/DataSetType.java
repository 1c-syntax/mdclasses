/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2021
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
package com.github._1c_syntax.mdclasses.mdo.support;

public enum DataSetType implements EnumWithValue {
  DATA_SET_QUERY("DataSetQuery"),
  DATA_SET_UNION("DataSetUnion"),
  DATA_SET_OBJECT("DataSetObject");

  private final String value;

  DataSetType(String value) {
    this.value = value;
  }

  public static DataSetType fromValue(String value) {
    for (DataSetType dataSetType : DataSetType.values()) {
      if (dataSetType.value.equals(value)) {
        return dataSetType;
      }
    }
    throw new IllegalArgumentException(value);
  }

  @Override
  public String value() {
    return value;
  }
}
