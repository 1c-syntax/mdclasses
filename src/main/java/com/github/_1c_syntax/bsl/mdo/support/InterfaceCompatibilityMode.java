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
 * Возможные варианты интерфейсов
 */
@ToString(of = "fullName")
public enum InterfaceCompatibilityMode implements EnumWithName {
  TAXI("Taxi", "Такси"),
  TAXI_ENABLE_VERSION_8_2("TaxiEnableVersion8_2", "ТаксиРазрешитьВерсия8_2"),
  TAXI_ENABLE_VERSION_8_5("TaxiEnableVersion8_5", "ТаксиРазрешитьВерсия8_5"),
  VERSION_8_2("Version8_2", "Версия8_2"),
  VERSION_8_2_ENABLE_TAXI("Version8_2EnableTaxi", "Версия8_2РазрешитьТакси"),
  VERSION_8_5("Version8_5", "Версия8_5"),
  VERSION_8_5_ENABLE_TAXI("Version8_5EnableTaxi", "Версия8_5РазрешитьТакси"),
  UNKNOWN("unknown", "неизвестный");

  private static final Map<String, InterfaceCompatibilityMode> KEYS = EnumWithName.computeKeys(values());

  @Getter
  @Accessors(fluent = true)
  private final MultiName fullName;

  InterfaceCompatibilityMode(String nameEn, String nameRu) {
    this.fullName = MultiName.create(nameEn, nameRu);
  }

  /**
   * Ищет элемент перечисления по именам (рус, анг)
   *
   * @param string Имя искомого элемента
   * @return Найденное значение, если не найден - то UNKNOWN
   */
  public static InterfaceCompatibilityMode valueByName(String string) {
    return KEYS.getOrDefault(string.toLowerCase(Locale.ROOT), UNKNOWN);
  }
}
