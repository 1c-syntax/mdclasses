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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Arrays;

/**
 * Возможные варианты интерфейсов
 */
@AllArgsConstructor
@ToString
@Getter
public enum InterfaceCompatibilityMode implements EnumWithValue {
  TAXI("Taxi"),
  TAXI_ENABLE_VERSION_8_2("TaxiEnableVersion8_2"),
  TAXI_ENABLE_VERSION_8_5("TaxiEnableVersion8_5"),
  VERSION_8_2("Version8_2"),
  VERSION_8_2_ENABLE_TAXI("Version8_2EnableTaxi"),
  VERSION_8_5("Version8_5"),
  VERSION_8_5_ENABLE_TAXI("Version8_5EnableTaxi"),
  UNKNOWN("unknown") {
    @Override
    public boolean isUnknown() {
      return true;
    }
  };

  @Accessors(fluent = true)
  private final String value;

  public static InterfaceCompatibilityMode getByName(String value) {
    return Arrays.stream(values())
      .filter(interfaceCompMode -> interfaceCompMode.value().equalsIgnoreCase(value))
      .findAny()
      .orElse(VERSION_8_2);
  }
}
