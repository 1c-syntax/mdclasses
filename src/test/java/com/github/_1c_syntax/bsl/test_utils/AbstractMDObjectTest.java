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

import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.mdo.Attribute;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import com.github._1c_syntax.mdclasses.utils.MDOPathUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.github._1c_syntax.bsl.test_utils.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract public class AbstractMDObjectTest<T extends MDObject> {
  private final Class<T> clazz;
  private static final String SRC_DESIGNER = "src/test/resources/metadata/original";
  private static final String SRC_EDT = "src/test/resources/metadata/edt/src";
  private static final String SRC_DESIGNER18 = "src/test/resources/metadata/original_3_18";

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

  protected AbstractMDObjectTest(Class<T> clazz) {
    this.clazz = clazz;
  }

  /**
   * Возвращает прочитанный объект
   */
  protected T getMDObject(String name) {
    return getMDObject(Paths.get(SRC_DESIGNER,
      name + MDOPathUtils.mdoExtension(ConfigurationSource.DESIGNER, true)));
  }

  /**
   * Возвращает прочитанный объект
   */
  protected T getMDObjectEDT(String name) {
    return getMDObject(Paths.get(SRC_EDT,
      name + MDOPathUtils.mdoExtension(ConfigurationSource.EDT, true)));
  }

  /**
   * Возвращает прочитанный объект
   */
  protected T getMDObject18(String name) {
    return getMDObject(Paths.get(SRC_DESIGNER18,
      name + MDOPathUtils.mdoExtension(ConfigurationSource.DESIGNER, true)));
  }

  /**
   * Проверяет базовый набор реквизитов
   */
  @SneakyThrows
  protected void checkBaseField(MDObject mdo, MDOType type, String name, String uuid, ObjectBelonging objectBelonging) {

    var fields = clazz.getDeclaredFields();
    for (var field : fields) {
      assertThat(field, true).isNotNull(mdo);
      var fieldType = field.getType();

      var key = field.getName();

      if (testedFields.contains(key)) {
        untestedFields.remove(key);
        continue;
      }

      if (fieldType.isAssignableFrom(boolean.class) && Objects.equals(field.get(mdo), false)
        || fieldType.isAssignableFrom(String.class) && Objects.equals(field.get(mdo), "")
        || fieldType.isAssignableFrom(int.class) && Objects.equals(field.get(mdo), 0)
        || fieldType.isAssignableFrom(List.class) && ((ArrayList) field.get(mdo)).isEmpty()
        || fieldType.isAssignableFrom(Map.class) && ((HashMap) field.get(mdo)).isEmpty()) {

        if (!untestedFields.contains(key)) {
          untestedFields.add(key);
        }
      } else {
        testedFields.add(key);
        untestedFields.remove(key);
      }
    }

    assertThat(mdo.getType()).isEqualTo(type);
    assertThat(mdo.getName()).isEqualTo(name);

    assertThat(mdo.getUuid()).isEqualTo(uuid);
    assertThat(mdo.getMdoReference())
      .isNotNull().extracting(MdoReference::getType)
      .isEqualTo(type);
    assertThat(mdo.getMdoReference().getMdoRef())
      .isEqualTo(type.getName() + "." + name);
    assertThat(mdo.getMdoReference().getMdoRefRu())
      .isEqualTo(type.getNameRu() + "." + name);

    assertThat(mdo.getObjectBelonging()).isEqualTo(objectBelonging);
  }

  @SneakyThrows
  protected void checkAttributeField(Attribute mdo, String name, String uuid) {

    var attributeClass = mdo.getClass();
    var fields = attributeClass.getDeclaredFields();
    for (var field : fields) {
      assertThat(field, true).isNotNull(mdo);
      var fieldType = field.getType();

      var key = attributeClass.getName() + "." + field.getName();

      if (testedFields.contains(key)) {
        untestedFields.remove(key);
        continue;
      }

      if (fieldType.isAssignableFrom(boolean.class) && Objects.equals(field.get(mdo), false)
        || fieldType.isAssignableFrom(String.class) && Objects.equals(field.get(mdo), "")
        || fieldType.isAssignableFrom(int.class) && Objects.equals(field.get(mdo), 0)
        || fieldType.isAssignableFrom(List.class) && ((ArrayList) field.get(mdo)).isEmpty()
        || fieldType.isAssignableFrom(Map.class) && ((HashMap) field.get(mdo)).isEmpty()) {

        if (!untestedFields.contains(key)) {
          untestedFields.add(key);
        }
      } else {
        testedFields.add(key);
        untestedFields.remove(key);
      }
    }

    assertThat(mdo.getName()).isEqualTo(name);
    assertThat(mdo.getUuid()).isEqualTo(uuid);
  }

  protected T getMDObject(Path path) {
    var mdo = MDOFactory.readMDObject(path);
    assertThat(mdo).isPresent();
    return clazz.cast(MDClasses.build(mdo.get().buildMDObject()));
  }
}

// todo добавить проверки
// - что дочерние заполнены (включая и их)
