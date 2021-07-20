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
import com.github._1c_syntax.bsl.mdo.AttributeOwner;
import com.github._1c_syntax.bsl.mdo.CommandOwner;
import com.github._1c_syntax.bsl.mdo.FormOwner;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import com.github._1c_syntax.bsl.mdo.TabularSectionOwner;
import com.github._1c_syntax.bsl.mdo.TemplateOwner;
import com.github._1c_syntax.bsl.mdo.children.HttpServiceUrlTemplate;
import com.github._1c_syntax.bsl.mdo.children.IntegrationServiceChannel;
import com.github._1c_syntax.bsl.mdo.children.Recalculation;
import com.github._1c_syntax.bsl.mdo.children.WebServiceOperation;
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
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
   * Проверяет базовый набор реквизитов атрибута
   *
   * @param excludes используется для исключения атрибутов из проверки заполненности не дефолтным значением
   *                 (когда значение может быть только дефолтным)
   */
  @SneakyThrows
  protected void checkAttributeField(Attribute mdo, String name, String uuid, List<String> excludes) {

    var attributeClass = mdo.getClass();
    var fields = attributeClass.getDeclaredFields();
    for (var field : fields) {
      assertThat(field, true).isNotNull(mdo);
      if (excludes.contains(field.getName())) {
        continue;
      }

      var fieldType = field.getType();
      var key = attributeClass.getName() + "." + field.getName();

      if (testedFields.contains(key)) {
        untestedFields.remove(key);
        continue;
      }

      storeUntestedField(mdo, field, fieldType, key);
    }

    assertThat(mdo.getName()).isEqualTo(name);
    assertThat(mdo.getUuid()).isEqualTo(uuid);
  }

  /**
   * Проверяет базовый набор реквизитов дочернего объекта
   */
  @SneakyThrows
  protected void checkChildField(MDObject child, String name, String uuid) {

    var childClass = child.getClass();
    var fields = childClass.getDeclaredFields();
    for (var field : fields) {
      assertThat(field, true).isNotNull(child);

      var fieldType = field.getType();
      var key = childClass.getName() + "." + field.getName();

      if (testedFields.contains(key)) {
        untestedFields.remove(key);
        continue;
      }

      storeUntestedField(child, field, fieldType, key);
    }

    assertThat(child.getName()).isEqualTo(name);
    assertThat(child.getUuid()).isEqualTo(uuid);
  }

  /**
   * Проверяет базовый набор реквизитов
   */
  protected void mdoTest(MDObject mdo, MDOType type, ArgumentsAccessor argumentsAccessor) {

    storeUntestedFields(mdo);

    var name = argumentsAccessor.getString(0);
    assertThat(mdo.getName()).isEqualTo(name);
    assertThat(mdo.getUuid()).isEqualTo(argumentsAccessor.getString(1));

    HashMap<String, String> context = new HashMap<>();
    if (argumentsAccessor.getString(2) != null) {
      context.put("en", argumentsAccessor.getString(2));
    }
    if (argumentsAccessor.getString(3) != null) {
      context.put("ru", argumentsAccessor.getString(3));
    }

    var testLangContent = new HashMap<>(mdo.getSynonyms().getContent());
    testLangContent.remove("ja"); // сложно передавать символы японского
    assertThat(testLangContent).isEqualTo(context);

    assertThat(mdo.getMdoReference())
      .isNotNull().extracting(MdoReference::getType)
      .isEqualTo(type);
    assertThat(mdo.getMdoReference().getMdoRef())
      .isEqualTo(type.getName() + "." + name);
    assertThat(mdo.getMdoReference().getMdoRefRu())
      .isEqualTo(type.getNameRu() + "." + name);

    assertThat(mdo.getObjectBelonging()).isEqualTo(ObjectBelonging.OWN);
    assertThat(mdo.getType()).isEqualTo(type);
    assertThat(mdo.getMetadataName()).isEqualTo(argumentsAccessor.getString(4));
    assertThat(mdo.getMetadataNameRu()).isEqualTo(argumentsAccessor.getString(5));

    List<MDObject> children = new ArrayList<>();

    if (mdo instanceof AttributeOwner) {
      assertThat(((AttributeOwner) mdo).getAttributes()).hasSize(argumentsAccessor.getInteger(6));
      children.addAll(((AttributeOwner) mdo).getAttributes());
    }

    if (mdo instanceof TabularSectionOwner) {
      assertThat(((TabularSectionOwner) mdo).getTabularSections()).hasSize(argumentsAccessor.getInteger(7));
      children.addAll(((TabularSectionOwner) mdo).getTabularSections());
    }

    if (mdo instanceof FormOwner) {
      assertThat(((FormOwner) mdo).getForms()).hasSize(argumentsAccessor.getInteger(8));
      children.addAll(((FormOwner) mdo).getForms());
    }

    if (mdo instanceof CommandOwner) {
      assertThat(((CommandOwner) mdo).getCommands()).hasSize(argumentsAccessor.getInteger(9));
      children.addAll(((CommandOwner) mdo).getCommands());
    }

    if (mdo instanceof TemplateOwner) {
      assertThat(((TemplateOwner) mdo).getTemplates()).hasSize(argumentsAccessor.getInteger(10));
      children.addAll(((TemplateOwner) mdo).getTemplates());
    }

    assertThat(
      mdo.getChildren().stream()
        .filter(mdObject -> !(mdObject instanceof Recalculation)
          && !(mdObject instanceof HttpServiceUrlTemplate)
          && !(mdObject instanceof IntegrationServiceChannel)
          && !(mdObject instanceof WebServiceOperation))
        .collect(Collectors.toList())
    ).isEqualTo(children);

    if (mdo instanceof ModuleOwner) {
      assertThat(((ModuleOwner) mdo).getModules()).hasSize(argumentsAccessor.getInteger(11));
    }
  }

  protected void checkAttributeField(Attribute mdo, String name, String uuid) {
    checkAttributeField(mdo, name, uuid, Collections.emptyList());
  }

  protected T getMDObject(Path path) {
    var mdo = MDOFactory.readMDObject(path);
    assertThat(mdo).isPresent();
    return clazz.cast(MDClasses.build(mdo.get().buildMDObject()));
  }

  protected void storeUntestedField(MDObject mdo, Field field, Class<?> fieldType, String key) throws IllegalAccessException {
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

  @SneakyThrows
  private void storeUntestedFields(MDObject mdo) {

    var fields = clazz.getDeclaredFields();
    for (var field : fields) {
      assertThat(field, true).isNotNull(mdo);
      var fieldType = field.getType();

      var key = field.getName();

      if (testedFields.contains(key)) {
        untestedFields.remove(key);
        continue;
      }

      storeUntestedField(mdo, field, fieldType, key);
    }
  }
}

// todo добавить проверки
// - что дочерние заполнены (включая и их)
