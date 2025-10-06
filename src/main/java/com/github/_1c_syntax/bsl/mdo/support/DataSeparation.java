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
 * Возможные варианты свойства `Разделение данных`
 */
@ToString(of = "fullName")
public enum DataSeparation implements EnumWithName {
  DONT_USE("DontUse", "НеИспользовать"),
  SEPARATE("Separate", "Разделять"),
  UNKNOWN("unknown", "неизвестный");

  private static final Map<String, DataSeparation> KEYS = EnumWithName.computeKeys(values());

  @Getter
  @Accessors(fluent = true)
  private final MultiName fullName;

  /**
   * Constructs a DataSeparation constant and initializes its combined `fullName`.
   *
   * @param nameEn the English name used to build the fullName
   * @param nameRu the Russian name used to build the fullName
   */
  DataSeparation(String nameEn, String nameRu) {
    this.fullName = MultiName.create(nameEn, nameRu);
  }

  /**
   * Finds a DataSeparation constant matching the given name in English or Russian.
   *
   * @param string the name to look up (case-insensitive, English or Russian)
   * @return the matching DataSeparation constant, or {@link #UNKNOWN} if no match is found
   */
  public static DataSeparation valueByName(String string) {
    return KEYS.getOrDefault(string.toLowerCase(Locale.ROOT), UNKNOWN);
  }
}