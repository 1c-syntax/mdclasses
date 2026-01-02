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
package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import com.github._1c_syntax.bsl.types.ScriptVariant;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

/**
 * ВНИМАНИЕ!
 * Для формата EDT хранится в файле Configuration.mdo, т.е. отдельного файла нет
 */
@Value
@Builder
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
public class Language implements MDObject {

  public static final Language DEFAULT = defaultLanguage();

  public static final Language ENGLISH = newLanguage("English", "en");
  public static final Language RUSSIAN = newLanguage("Русский", "ru");

  /*
   * MDObject
   */

  @Default
  String uuid = "";
  @Default
  String name = "";
  @Default
  MdoReference mdoReference = MdoReference.EMPTY;
  @Default
  ObjectBelonging objectBelonging = ObjectBelonging.OWN;
  @Default
  String comment = "";
  @Default
  MultiLanguageString synonym = MultiLanguageString.EMPTY;
  @Default
  SupportVariant supportVariant = SupportVariant.NONE;

  /*
   * Свое
   */

  /**
   * Код языка
   */
  @Default
  String languageCode = "";

  /**
   * Возвращает объект языка если вдруг его не оказалось в данных конфигурации
   *
   * @param scriptVariant - вариант языка конфигурации
   * @return - созданный и минимально заполненный объект языка
   */
  public static Language defaultLanguage(ScriptVariant scriptVariant) {
    if (scriptVariant == ScriptVariant.ENGLISH) {
      return ENGLISH;
    } else {
      return RUSSIAN;
    }
  }

  public static Language defaultLanguage() {
    return defaultLanguage(ScriptVariant.RUSSIAN);
  }

  private static Language newLanguage(String name, String code) {
    return Language.builder()
      .name(name)
      .uuid("")
      .synonym(MultiLanguageString.create(code, name))
      .mdoReference(MdoReference.create(
        MDOType.LANGUAGE,
        MDOType.LANGUAGE.groupName() + "." + name,
        MDOType.LANGUAGE.groupNameRu() + "." + name))
      .languageCode(code)
      .build();
  }
}
