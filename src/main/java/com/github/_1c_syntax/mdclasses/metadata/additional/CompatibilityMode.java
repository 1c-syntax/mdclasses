/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2021
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
package com.github._1c_syntax.mdclasses.metadata.additional;

import lombok.Getter;

/**
 * Класс реализующий объект для хранения режима совместимости конфигурации
 */
public class CompatibilityMode {
  private static final String VERSION8_1 = "Version8_1";
  private static final String DONT_USE = "DontUse";
  @Getter
  private int major = 8;
  @Getter
  private int minor = 0;
  @Getter
  private int version = 0;

  public CompatibilityMode() {
    this(DONT_USE);
  }

  public CompatibilityMode(String value) {

    if (value.equalsIgnoreCase(DONT_USE) || value.isEmpty()) {
      setVersionComponents(3, 99);
      return;
    }

    if (value.equals(VERSION8_1)) {
      setVersionComponents(1, 0);
      return;
    }

    // парсим версию, например Version_8_3_10
    String newValue = value.toUpperCase().replace("VERSION_", "");

    String[] array = newValue.split("([_.])");
    setVersionComponents(Integer.parseInt(array[1]), Integer.parseInt(array[2]));

  }

  public CompatibilityMode(int minor, int version) {
    setVersionComponents(minor, version);
  }

  /**
   * Выполняет сравнение двух режимов совместимости
   *
   * @param versionA - Первый режим совместимости
   * @param versionB - Второй режим совместимости
   * @return - Результат сравнения
   * 0 - равны
   * 1 - вторая версия больше
   * -1 - первая версия больше
   */
  public static int compareTo(CompatibilityMode versionA, CompatibilityMode versionB) {

    // TODO: переделать в цикл
    if (versionA.major == versionB.major) {
      if (versionA.minor == versionB.minor) {
        if (versionA.version == versionB.version) {
          return 0;
        } else if (versionA.version >= versionB.version) {
          return -1;
        } else {
          return 1;
        }
      } else if (versionA.minor >= versionB.minor) {
        return -1;
      } else {
        return 1;
      }
    } else if (versionA.major >= versionB.major) {
      return -1;
    } else {
      return 1;
    }
  }

  /**
   * Выполняет сравнение двух режимов совместимости, используя в качестве второй версии - строковое представление
   *
   * @param versionA - Первый режим совместимости
   * @param versionB - Второй режим совместимости в виде строки вида Version_8_3_10
   * @return - Результат сравнения
   * 0 - равны
   * 1 - вторая версия больше
   * -1 - первая версия больше
   */
  public static int compareTo(CompatibilityMode versionA, String versionB) {
    return compareTo(versionA, new CompatibilityMode(versionB));
  }

  private void setVersionComponents(int minor, int version) {
    this.minor = minor;
    this.version = version;
  }

}
