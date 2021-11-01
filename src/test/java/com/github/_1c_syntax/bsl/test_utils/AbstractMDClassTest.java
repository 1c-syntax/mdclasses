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
import com.github._1c_syntax.bsl.types.MDOType;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.github._1c_syntax.bsl.test_utils.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract public class AbstractMDClassTest<T extends MDClass> {
  private final Class<T> clazz;

  protected AbstractMDClassTest(Class<T> clazz) {
    this.clazz = clazz;
  }

  private List<String> untestedFields;
  private List<String> testedFields;

  @BeforeAll
  void beforeTest() {
    untestedFields = new ArrayList<>();
    testedFields = new ArrayList<>();
  }

  @AfterAll
  void afterTest() {
    var message = new StringBuilder();
    untestedFields.forEach(field -> {
      message
        .append("\n\t")
        .append(field);
    });

    if (!message.toString().isEmpty()) {
      message.insert(0, "\nUntested field(-s):");
    }

    assertThat(message).isEmpty();
  }



  /**
   * Проверяет базовый набор реквизитов
   */
  protected void mdcTest(MDClass mdc, ArgumentsAccessor argumentsAccessor) {

    storeUntestedFields(mdc);

    var name = argumentsAccessor.getString(1);
    assertThat(mdc.getName()).isEqualTo(name);
    assertThat(mdc.getUuid()).isEqualTo(argumentsAccessor.getString(2));
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
      .filteredOn(mdObject -> mdObject.getMdoType() == type).hasSize(count);
  }

  protected void storeUntestedField(MDClass mdc, Field field, Class<?> fieldType, String key) throws IllegalAccessException {
    if (fieldType.isAssignableFrom(boolean.class) && Objects.equals(field.get(mdc), false)
      || fieldType.isAssignableFrom(String.class) && Objects.equals(field.get(mdc), "")
      || fieldType.isAssignableFrom(int.class) && Objects.equals(field.get(mdc), 0)
      || fieldType.isAssignableFrom(List.class) && ((List) field.get(mdc)).isEmpty()
      || fieldType.isAssignableFrom(Map.class) && ((HashMap) field.get(mdc)).isEmpty()) {

      if (!untestedFields.contains(key)) {
        untestedFields.add(key);
      }
    } else {
      testedFields.add(key);
      untestedFields.remove(key);
    }
  }

  @SneakyThrows
  private void storeUntestedFields(MDClass mdc) {

    var fields = clazz.getDeclaredFields();
    for (var field : fields) {
      assertThat(field, true).isNotNull(mdc);
      var fieldType = field.getType();

      var key = field.getName();

      if (testedFields.contains(key)) {
        untestedFields.remove(key);
        continue;
      }

      storeUntestedField(mdc, field, fieldType, key);
    }
  }

}
