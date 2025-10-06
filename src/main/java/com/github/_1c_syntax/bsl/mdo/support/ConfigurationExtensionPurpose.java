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
 * Возможные виды расширений
 */
@ToString(of = "fullName")
public enum ConfigurationExtensionPurpose implements EnumWithName {
  CUSTOMIZATION("Customization", "Адаптация"),
  ADD_ON("AddOn", "Дополнение"),
  PATCH("Patch", "Исправление"),
  UNDEFINED("Undefined", "Неопределено"),
  UNKNOWN("unknown", "неизвестный");

  private static final Map<String, ConfigurationExtensionPurpose> KEYS = EnumWithName.computeKeys(values());

  @Getter
  @Accessors(fluent = true)
  private final MultiName fullName;

  /**
   * Creates an enum constant with the specified English and Russian names.
   *
   * @param nameEn the English name for the enum constant
   * @param nameRu the Russian name for the enum constant
   */
  ConfigurationExtensionPurpose(String nameEn, String nameRu) {
    this.fullName = MultiName.create(nameEn, nameRu);
  }

  /**
   * Finds an enum constant by its English or Russian name.
   *
   * <p>Lookup is case-insensitive (uses Locale.ROOT).</p>
   *
   * @param string the English or Russian name to look up
   * @return `UNKNOWN` if no match is found, otherwise the matching enum constant
   */
  public static ConfigurationExtensionPurpose valueByName(String string) {
    return KEYS.getOrDefault(string.toLowerCase(Locale.ROOT), UNKNOWN);
  }
}