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
package com.github._1c_syntax.bsl.smoke;

import com.github._1c_syntax.bsl.mdo.support.EnumWithValue;
import io.github.classgraph.ClassGraph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EnumsTest {

  @Test
  void unknown() {
    try (var scanResult = new ClassGraph()
      .enableClassInfo()
      .acceptPackages("com.github._1c_syntax.bsl")
      .scan()) {

      var classes = scanResult.getClassesImplementing(EnumWithValue.class);
      List<String> list = new ArrayList<>();
      classes.forEach(classInfo -> {
        if (unknown(classInfo.getName()) == null) {
          list.add(classInfo.getName());
        }
      });
      assertThat(list).isEmpty();
    }
  }

  @SuppressWarnings("unchecked")
  private static <T extends Enum<T> & EnumWithValue> T unknown(String className) {
    Class<T> clazz = null;
    try {
      clazz = (Class<T>) Class.forName(className);
    } catch (ClassNotFoundException e) {
      return null;
    }

    for (T item : clazz.getEnumConstants()) {
      if (item.isUnknown()) {
        return item;
      }
    }
    return null;
  }
}
