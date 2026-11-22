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
package com.github._1c_syntax.bsl.mdclasses;

import com.github._1c_syntax.bsl.types.EnumWithName;
import com.github._1c_syntax.bsl.types.MultiName;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.jspecify.annotations.Nullable;

import java.util.Locale;
import java.util.Map;

/**
 * Приоритет сервера из автономного состава
 */
@ToString(of = "fullName")
public enum StandaloneServerPriority implements EnumWithName {

  /**
   * Приоритет по умолчанию
   */
  DEFAULT("Default", "По умолчанию"),

  /**
   * Приоритет основного сервера
   */
  MAIN_SERVER("MainServer", "ОсновнойСервер"),

  /**
   * Приоритет локального сервера
   */
  LOCAL_SERVER("LocalServer", "ЛокальныйСервер"),

  /**
   * Значение не распознано
   */
  UNKNOWN("unknown", "неизвестный");

  private static final Map<String, StandaloneServerPriority> KEYS = EnumWithName.computeKeys(values());

  /**
   * Мультиязычное имя значения
   */
  @Getter
  @Accessors(fluent = true)
  private final MultiName fullName;

  StandaloneServerPriority(String nameEn, String nameRu) {
    this.fullName = MultiName.create(nameEn, nameRu);
  }

  /**
   * Ищет приоритет по имени из файла.
   *
   * @param string Имя приоритета
   * @return Найденное значение, если не найдено — {@link #UNKNOWN}
   */
  public static StandaloneServerPriority valueByName(@Nullable String string) {
    if (string == null) {
      return UNKNOWN;
    }
    return KEYS.getOrDefault(string.toLowerCase(Locale.ROOT), UNKNOWN);
  }
}
