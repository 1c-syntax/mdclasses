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

  /**
   * Initializes the enum constant with primary and additional multi-language names.
   *
   * @param nameEn primary English name
   * @param nameEnAdd additional English name
   * @param nameRu Russian name used for both primary and additional representations
   */
  UsePurposes(String nameEn, String nameEnAdd, String nameRu) {
    this.fullName = MultiName.create(nameEn, nameRu);
    this.fullNameAdd = MultiName.create(nameEnAdd, nameRu);
  }

  /**
   * Finds a UsePurposes enum constant by an English or Russian name.
   *
   * @param string the English or Russian name to look up (case-insensitive)
   * @return the matching enum constant, or UNKNOWN if no match is found
   */
  public static UsePurposes valueByName(String string) {
    return KEYS.getOrDefault(string.toLowerCase(Locale.ROOT), UNKNOWN);
  }

  /**
   * Builds a lookup map that associates lowercase name variants with their corresponding UsePurposes constants.
   *
   * <p>Includes each enum element's primary English name, Russian name, and the additional English name.</p>
   *
   * @return a map whose keys are lowercase name variants (Locale.ROOT) and whose values are the corresponding UsePurposes
   */
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