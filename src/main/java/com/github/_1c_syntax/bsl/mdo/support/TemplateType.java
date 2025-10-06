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

@ToString(of = "fullName")
public enum TemplateType implements EnumWithName {
  ADD_IN("AddIn", "ВнешняяКомпонента"),
  BINARY_DATA("BinaryData", "ДвоичныеДанные"),
  DATA_COMPOSITION_SCHEME("DataCompositionSchema", "СхемаКомпоновкиДанных"),
  DATA_COMPOSITION_APPEARANCE_TEMPLATE("DataCompositionAppearanceTemplate", "МакетОформленияКомпоновкиДанных"),
  GRAPHICAL_SCHEME("GraphicalSchema", "ГрафическаяСхема"),
  HTML_DOCUMENT("HTMLDocument", "HTMLДокумент"),
  SPREADSHEET_DOCUMENT("SpreadsheetDocument", "ТабличныйДокумент"),
  ACTIVE_DOCUMENT("ActiveDocument", "ActiveDocument"),
  GEOGRAPHICAL_SCHEMA("GeographicalSchema", "ГеографическаяСхема"),
  TEXT_DOCUMENT("TextDocument", "ТекстовыйДокумент"),
  UNKNOWN("unknown", "неизвестный");

  private static final Map<String, TemplateType> KEYS = EnumWithName.computeKeys(values());

  @Getter
  @Accessors(fluent = true)
  private final MultiName fullName;

  /**
   * Initializes the enum constant's bilingual full name.
   *
   * @param nameEn English name of the template
   * @param nameRu Russian name of the template
   */
  TemplateType(String nameEn, String nameRu) {
    this.fullName = MultiName.create(nameEn, nameRu);
  }

  /**
   * Finds the TemplateType constant matching the given English or Russian name (case-insensitive).
   *
   * @param string the name to look up in English or Russian; comparison uses case-insensitive matching
   * @return the matching TemplateType, or `UNKNOWN` if no match is found
   */
  public static TemplateType valueByName(String string) {
    return KEYS.getOrDefault(string.toLowerCase(Locale.ROOT), UNKNOWN);
  }
}