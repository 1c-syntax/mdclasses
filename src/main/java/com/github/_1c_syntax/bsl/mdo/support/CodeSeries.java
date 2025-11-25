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

import java.util.Locale;
import java.util.Map;

import com.github._1c_syntax.bsl.types.EnumWithName;
import com.github._1c_syntax.bsl.types.MultiName;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Серия кодов справочника.
 * Определяет область действия уникальности кода справочника.
 */
@ToString(of = "fullName")
public enum CodeSeries implements EnumWithName {
  /**
   * Весь справочник - уникальность кода проверяется во всем справочнике
   */
  WHOLE_CATALOG("WholeCatalog", "ВесьСправочник"),
  /**
   * В пределах подчинения - уникальность кода проверяется в пределах подчинения
   */
  WITHIN_SUBORDINATION("WithinSubordination", "ВПределахПодчинения"),
  /**
   * В пределах подчинения владельцу - уникальность кода проверяется в пределах подчинения владельцу
   */
  WITHIN_OWNER_SUBORDINATION("WithinOwnerSubordination", "ВПределахПодчиненияВладельцу");

  private static final Map<String, CodeSeries> KEYS = EnumWithName.computeKeys(values());

  /**
   * Полное имя элемента перечисления (на русском и английском языках)
   */
  @Getter
  @Accessors(fluent = true)
  private final MultiName fullName;

  /**
   * Constructs an enum constant with English and Russian full names.
   *
   * @param nameEn English full name for the enum constant
   * @param nameRu Russian full name for the enum constant
   */
  CodeSeries(String nameEn, String nameRu) {
    this.fullName = MultiName.create(nameEn, nameRu);
  }

  /**
   * Finds a CodeSeries by its English or Russian name.
   *
   * Lookup is case-insensitive; if no match is found the WHOLE_CATALOG constant is returned.
   *
   * @param string the English or Russian name to look up
   * @return the matching CodeSeries, or WHOLE_CATALOG if no match is found
   */
  public static CodeSeries valueByName(String string) {
    return KEYS.getOrDefault(string.toLowerCase(Locale.ROOT), WHOLE_CATALOG);
  }
}
