/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
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
 * Использование разделяемых данных общего реквизита-разделителя.
 * Определяет режим разделения данных: независимо или независимо и совместно.
 */
@ToString(of = "fullName")
public enum CommonAttributeSeparatedDataUse implements EnumWithName {
  /**
   * Независимо
   */
  INDEPENDENTLY("Independently", "Независимо"),
  /**
   * Независимо и совместно
   */
  INDEPENDENTLY_AND_SIMULTANEOUSLY("IndependentlyAndSimultaneously", "НезависимоИСовместно"),
  UNKNOWN("unknown", "неизвестный");

  private static final Map<String, CommonAttributeSeparatedDataUse> KEYS = EnumWithName.computeKeys(values());

  /**
   * Полное имя элемента перечисления (на русском и английском языках)
   */
  @Getter
  @Accessors(fluent = true)
  private final MultiName fullName;

  /**
   * Конструктор элемента перечисления
   *
   * @param nameEn Английское имя элемента
   * @param nameRu Русское имя элемента
   */
  CommonAttributeSeparatedDataUse(String nameEn, String nameRu) {
    this.fullName = MultiName.create(nameEn, nameRu);
  }

  /**
   * Ищет элемент перечисления по именам (рус, анг).
   * Поиск выполняется без учета регистра.
   *
   * @param string Имя искомого элемента
   * @return Найденное значение, если не найден - то UNKNOWN
   */
  public static CommonAttributeSeparatedDataUse valueByName(String string) {
    return KEYS.getOrDefault(string.toLowerCase(Locale.ROOT), UNKNOWN);
  }
}
