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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
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
   * Возвращает прочитанный объект
   */
  protected T getMDClass(String path) {
    return getMDClass(Paths.get(path));
  }

  /**
   * Проверяет базовый набор реквизитов
   */
  protected void mdcTest(MDClass mdc, ArgumentsAccessor argumentsAccessor) {

    storeUntestedFields(mdc);

    var name = argumentsAccessor.getString(1);
    assertThat(mdc.getName()).isEqualTo(name);
    assertThat(mdc.getUuid()).isEqualTo(argumentsAccessor.getString(2));
//
//    HashMap<String, String> context = new HashMap<>();
//    if (argumentsAccessor.getString(2) != null) {
//      context.put("en", argumentsAccessor.getString(2));
//    }
//    if (argumentsAccessor.getString(3) != null) {
//      context.put("ru", argumentsAccessor.getString(3));
//    }
//
//    var testLangContent = new HashMap<>(mdo.getSynonyms().getContent());
//    testLangContent.remove("ja"); // сложно передавать символы японского
//    assertThat(testLangContent).isEqualTo(context);
//
//    assertThat(mdo.getMdoReference())
//      .isNotNull().extracting(MdoReference::getType)
//      .isEqualTo(type);
//    assertThat(mdo.getMdoReference().getMdoRef())
//      .isEqualTo(type.getName() + "." + name);
//    assertThat(mdo.getMdoReference().getMdoRefRu())
//      .isEqualTo(type.getNameRu() + "." + name);
//
//    assertThat(mdo.getObjectBelonging()).isEqualTo(ObjectBelonging.OWN);
//    assertThat(mdo.getType()).isEqualTo(type);
//    assertThat(mdo.getMetadataName()).isEqualTo(argumentsAccessor.getString(4));
//    assertThat(mdo.getMetadataNameRu()).isEqualTo(argumentsAccessor.getString(5));
//
//    List<MDObject> children = new ArrayList<>();
//
//    if (mdo instanceof AttributeOwner) {
//      assertThat(((AttributeOwner) mdo).getAttributes()).hasSize(argumentsAccessor.getInteger(6));
//      children.addAll(((AttributeOwner) mdo).getAttributes());
//    }
//
//    if (mdo instanceof TabularSectionOwner) {
//      assertThat(((TabularSectionOwner) mdo).getTabularSections()).hasSize(argumentsAccessor.getInteger(7));
//      children.addAll(((TabularSectionOwner) mdo).getTabularSections());
//    }
//
//    if (mdo instanceof FormOwner) {
//      assertThat(((FormOwner) mdo).getForms()).hasSize(argumentsAccessor.getInteger(8));
//      children.addAll(((FormOwner) mdo).getForms());
//    }
//
//    if (mdo instanceof CommandOwner) {
//      assertThat(((CommandOwner) mdo).getCommands()).hasSize(argumentsAccessor.getInteger(9));
//      children.addAll(((CommandOwner) mdo).getCommands());
//    }
//
//    if (mdo instanceof TemplateOwner) {
//      assertThat(((TemplateOwner) mdo).getTemplates()).hasSize(argumentsAccessor.getInteger(10));
//      children.addAll(((TemplateOwner) mdo).getTemplates());
//    }
//
//    assertThat(
//      mdo.getChildren().stream()
//        .filter(mdObject -> !(mdObject instanceof Recalculation)
//          && !(mdObject instanceof HttpServiceUrlTemplate)
//          && !(mdObject instanceof IntegrationServiceChannel)
//          && !(mdObject instanceof WebServiceOperation))
//        .collect(Collectors.toList())
//    ).isEqualTo(children);
//
//    if (mdo instanceof ModuleOwner) {
//      assertThat(((ModuleOwner) mdo).getModules()).hasSize(argumentsAccessor.getInteger(11));
//    }
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

  protected void storeUntestedField(MDClass mdc, Field field, Class<?> fieldType, String key) throws IllegalAccessException {
    if (fieldType.isAssignableFrom(boolean.class) && Objects.equals(field.get(mdc), false)
      || fieldType.isAssignableFrom(String.class) && Objects.equals(field.get(mdc), "")
      || fieldType.isAssignableFrom(int.class) && Objects.equals(field.get(mdc), 0)
      || fieldType.isAssignableFrom(List.class) && ((ArrayList) field.get(mdc)).isEmpty()
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
