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
package com.github._1c_syntax.bsl.test_utils;

import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.types.MDOType;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.github._1c_syntax.bsl.test_utils.Assertions.assertThat;

abstract public class AbstractMDClassTest<T extends MDClass> {
  private final Class<T> clazz;

  protected AbstractMDClassTest(Class<T> clazz) {
    this.clazz = clazz;
  }

  /**
   * Возвращает прочитанный объект
   */
  protected T getMDClass(String path) {
    return getMDClass(Paths.get(path));
  }

  /**
   * Проверяет базовый набор реквизитов
   */
  @SneakyThrows
  protected void checkBaseField(MDClass mdc, String name, String uuid) {

    var fields = clazz.getDeclaredFields();
    for (var field : fields) {
      assertThat(field, true).isNotNull(mdc);
    }

    assertThat(mdc.getUuid()).isEqualTo(uuid);
    assertThat(mdc.getName()).isEqualTo(name);
  }

  protected void checkChildCount(MDClass mdc, MDOType type, int count) {
    assertThat(mdc.getChildren())
      .filteredOn(mdObject -> mdObject.getType() == type).hasSize(count);
  }

  protected T getMDClass(Path path) {
    return clazz.cast(MDClasses.createConfiguration(path));
  }
}
