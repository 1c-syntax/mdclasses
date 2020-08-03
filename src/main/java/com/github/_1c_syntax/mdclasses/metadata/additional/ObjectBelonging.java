/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2020
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
package com.github._1c_syntax.mdclasses.metadata.additional;

/**
 * Признак принадлежности объекта к конфигурации
 */
public enum ObjectBelonging {
  ADOPTED("Adopted"),
  OWN("Own");

  private final String value;

  ObjectBelonging(String value) {
    this.value = value;
  }

  public static ObjectBelonging fromValue(String value) {
    for (ObjectBelonging objectBelonging : ObjectBelonging.values()) {
      if (objectBelonging.value.equals(value)) {
        return objectBelonging;
      }
    }
    throw new IllegalArgumentException(value);
  }

  public String value() {
    return value;
  }

}
