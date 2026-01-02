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
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Назначения использования приложения и форм
 */
@ToString(of = "fullName")
public enum UsePurposes implements EnumWithName {
  PLATFORM_APPLICATION("PersonalComputer", "PlatformApplication",
    "ПриложениеДляПлатформы"),
  MOBILE_PLATFORM_APPLICATION("MobileDevice", "MobilePlatformApplication",
    "ПриложениеДляМобильнойПлатформы"),
  UNKNOWN("unknown", "unknown", "неизвестный");


  private static final Map<String, UsePurposes> KEYS = computeKeys();

  @Getter
  @Accessors(fluent = true)
  private final MultiName fullName;

  @Getter
  @Accessors(fluent = true)
  private final MultiName fullNameAdd;

  UsePurposes(String nameEn, String nameEnAdd, String nameRu) {
    this.fullName = MultiName.create(nameEn, nameRu);
    this.fullNameAdd = MultiName.create(nameEnAdd, nameRu);
  }

  /**
   * Ищет элемент перечисления по именам (рус, анг)
   *
   * @param string Имя искомого элемента
   * @return Найденное значение, если не найден - то UNKNOWN
   */
  public static UsePurposes valueByName(String string) {
    return KEYS.getOrDefault(string.toLowerCase(Locale.ROOT), UNKNOWN);
  }

  private static Map<String, UsePurposes> computeKeys() {
    Map<String, UsePurposes> keysMap = new ConcurrentSkipListMap<>();
    for (var element : values()) {
      keysMap.put(element.nameEn().toLowerCase(Locale.ROOT), element);
      keysMap.put(element.nameRu().toLowerCase(Locale.ROOT), element);
      keysMap.put(element.fullNameAdd().getEn().toLowerCase(Locale.ROOT), element);
    }
    return keysMap;
  }
}
