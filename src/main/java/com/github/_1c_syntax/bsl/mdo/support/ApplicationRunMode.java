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
 * Варианты запуска клиентского приложения
 */
@ToString(of = "fullName")
public enum ApplicationRunMode implements EnumWithName {
  AUTO("Auto", "Авто"),
  MANAGED_APPLICATION("ManagedApplication", "УправляемоеПриложение"),
  ORDINARY_APPLICATION("OrdinaryApplication", "ОбычноеПриложение"),
  UNKNOWN("unknown", "неизвестный");

  private static final Map<String, ApplicationRunMode> KEYS = EnumWithName.computeKeys(values());

  @Getter
  @Accessors(fluent = true)
  private final MultiName fullName;

  /**
   * Create an ApplicationRunMode with the given English and Russian display names.
   *
   * @param nameEn the English name for the mode
   * @param nameRu the Russian name for the mode
   */
  ApplicationRunMode(String nameEn, String nameRu) {
    this.fullName = MultiName.create(nameEn, nameRu);
  }

  /**
   * Finds an ApplicationRunMode by its English or Russian name (case-insensitive).
   *
   * @param string English or Russian name of the mode to look up
   * @return the matching ApplicationRunMode, or UNKNOWN if no match is found
   */
  public static ApplicationRunMode valueByName(String string) {
    return KEYS.getOrDefault(string.toLowerCase(Locale.ROOT), UNKNOWN);
  }
}