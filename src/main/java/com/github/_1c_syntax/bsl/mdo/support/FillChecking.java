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
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.Map;

/**
 * Проверка заполнения реквизита формы
 */
@Slf4j
@ToString(of = "fullName")
public enum FillChecking implements EnumWithName {
  DONT_CHECK("DontCheck", "НеПроверять"),
  SHOW_ERROR("ShowError", "ВыдаватьОшибку");

  private static final Map<String, FillChecking> KEYS = EnumWithName.computeKeys(values());

  @Getter
  @Accessors(fluent = true)
  private final MultiName fullName;

  FillChecking(String nameEn, String nameRu) {
    this.fullName = MultiName.create(nameEn, nameRu);
  }

  /**
   * Ищет элемент перечисления по имени
   *
   * @param string Имя искомого элемента
   * @return Найденное значение или DONT_CHECK
   */
  public static FillChecking valueByName(String string) {
    var result = KEYS.get(string.toLowerCase(Locale.ROOT));
    if (result == null) {
      LOGGER.warn("Unknown fill checking: {}", string);
    }
    return DONT_CHECK;
  }
}
