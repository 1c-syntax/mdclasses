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
 * Возможные варианты "использования"
 */
@ToString(of = "fullName")
public enum UseMode implements EnumWithName {
  DONT_USE("DontUse", "НеИспользовать"),
  USE("Use", "Использовать"),
  USE_WITH_WARNINGS("UseWithWarnings", "ИспользоватьСПредупреждениями"),
  UNKNOWN("unknown", "неизвестный");

  private static final Map<String, UseMode> KEYS = EnumWithName.computeKeys(values());

  @Getter
  @Accessors(fluent = true)
  private final MultiName fullName;

  /**
   * Create a UseMode with the specified English and Russian names.
   *
   * @param nameEn English name for the enum constant
   * @param nameRu Russian name for the enum constant
   */
  UseMode(String nameEn, String nameRu) {
    this.fullName = MultiName.create(nameEn, nameRu);
  }

  /**
   * Finds an enum constant by its English or Russian name.
   *
   * @param string the English or Russian name to look up
   * @return the matching UseMode, or UNKNOWN if no match is found
   */
  public static UseMode valueByName(String string) {
    return KEYS.getOrDefault(string.toLowerCase(Locale.ROOT), UNKNOWN);
  }
}