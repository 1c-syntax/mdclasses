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

import com.github._1c_syntax.bsl.types.EnumWithName;
import com.github._1c_syntax.bsl.types.MultiName;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Locale;
import java.util.Map;

/**
 * Признак принадлежности объекта к конфигурации
 */
@ToString(of = "fullName")
public enum ObjectBelonging implements EnumWithName {
  ADOPTED("Adopted", "Заимствованный"),
  OWN("Own", "Собственный"),
  UNKNOWN("unknown", "неизвестный");

  private static final Map<String, ObjectBelonging> KEYS = EnumWithName.computeKeys(values());

  @Getter
  @Accessors(fluent = true)
  private final MultiName fullName;

  /**
   * Initialize the enum constant with its English and Russian display names.
   *
   * @param nameEn the English display name
   * @param nameRu the Russian display name
   */
  ObjectBelonging(String nameEn, String nameRu) {
    this.fullName = MultiName.create(nameEn, nameRu);
  }

  /**
   * Lookup an ObjectBelonging by its English or Russian name using case-insensitive matching.
   *
   * @param string the English or Russian name to look up
   * @return the matching ObjectBelonging, or {@code UNKNOWN} if no match is found
   */
  public static ObjectBelonging valueByName(String string) {
    return KEYS.getOrDefault(string.toLowerCase(Locale.ROOT), UNKNOWN);
  }
}