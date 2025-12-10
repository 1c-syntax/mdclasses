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
package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.mdo.children.ObjectAttribute;
import com.github._1c_syntax.bsl.mdo.children.ObjectCommand;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdo.children.StandardAttribute;
import com.github._1c_syntax.bsl.mdo.support.AttributeKind;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.ValueTypes;
import com.github._1c_syntax.bsl.types.value.PrimitiveValueType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class CatalogTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, Catalogs.Справочник1, _edt",
      "false, mdclasses, Catalogs.Справочник1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(Catalog.class);
    var catalog = (Catalog) mdo;
    assertThat(catalog.getAllAttributes()).hasSize(12);
    assertThat(catalog.getChildren())
      .hasSize(18)
      .anyMatch(ObjectAttribute.class::isInstance)
      .anyMatch(StandardAttribute.class::isInstance)
      .anyMatch(ObjectCommand.class::isInstance)
      .anyMatch(ObjectForm.class::isInstance)
      .anyMatch(TabularSection.class::isInstance)
    ;
    assertThat(catalog.getPlainChildren()).hasSize(21);
    assertThat(catalog.getAttributes()).hasSize(12);
    assertThat(catalog.getTabularSections()).hasSize(1);
    assertThat(catalog.getForms()).hasSize(3);
    assertThat(catalog.getTemplates()).hasSize(1);
    assertThat(catalog.getCommands()).hasSize(1);
    assertThat(catalog.getModules())
      .hasSize(2)
      .anyMatch(Module::isProtected)
    ;
    assertThat(catalog.getAllModules()).hasSize(6);
    assertThat(catalog.getStorageFields())
      .hasSize(13)
      .anyMatch(ObjectAttribute.class::isInstance)
      .anyMatch(StandardAttribute.class::isInstance)
      .anyMatch(TabularSection.class::isInstance)
      .noneMatch(ObjectCommand.class::isInstance)
      .noneMatch(ObjectForm.class::isInstance)
    ;
    assertThat(catalog.getPlainStorageFields())
      .hasSize(16)
      .anyMatch(ObjectAttribute.class::isInstance)
      .anyMatch(StandardAttribute.class::isInstance)
      .anyMatch(TabularSection.class::isInstance)
      .noneMatch(ObjectCommand.class::isInstance)
      .noneMatch(ObjectForm.class::isInstance)
    ;

//    var formData = catalog.getForms().stream().filter(form -> form.getName().equals("ФормаСписка"))
//      .findFirst().get().getData();
//    checkExtInfo(formData);
  }

  @ParameterizedTest
  @CsvSource({
    "true, ssl_3_1, Catalogs.Заметки, _edt",
    "false, ssl_3_1, Catalogs.Заметки"
  })
  void testSSL(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo)
      .isInstanceOf(Catalog.class);

    var catalog = (Catalog) mdo;
    assertThat(catalog.getChildren())
      .hasSize(23)
      .anyMatch(ObjectAttribute.class::isInstance)
      .anyMatch(ObjectCommand.class::isInstance)
      .anyMatch(ObjectForm.class::isInstance)
    ;
    assertThat(catalog.getStorageFields())
      .hasSize(17)
      .anyMatch(ObjectAttribute.class::isInstance)
      .noneMatch(ObjectCommand.class::isInstance)
      .noneMatch(ObjectForm.class::isInstance)
    ;
    assertThat(catalog.getPlainStorageFields())
      .hasSize(17)
      .anyMatch(ObjectAttribute.class::isInstance)
      .noneMatch(ObjectCommand.class::isInstance)
      .noneMatch(ObjectForm.class::isInstance)
    ;

    assertThat(catalog.getSynonym().isEmpty()).isFalse();
    assertThat(catalog.getSynonym().get("ru")).isEqualTo("Заметки");
    assertThat(catalog.getDescription()).isEqualTo("Заметки");
    assertThat(catalog.getDescription("ru")).isEqualTo("Заметки");
    assertThat(catalog.getDescription("en")).isEqualTo("Заметки");

    var child = catalog.findChild(MdoReference.create("Catalog.Заметки.Attribute.Автор"));
    assertThat(child).isPresent();
    var attribute = (ObjectAttribute) child.get();
    assertThat(attribute.getSynonym().isEmpty()).isFalse();
    assertThat(attribute.getSynonym().get("ru")).isEqualTo("Автор");
    assertThat(attribute.getDescription()).isEqualTo("Автор");
    assertThat(attribute.getDescription("ru")).isEqualTo("Автор");
    assertThat(attribute.getDescription("en")).isEqualTo("Автор");
    assertThat(attribute.getValueType()).isNotNull();
    assertThat(attribute.getValueType()
      .contains(Objects.requireNonNull(ValueTypes.get("CatalogRef.Пользователи")))).isTrue();

    child = catalog.findChild(MdoReference.create("Catalog.Заметки.StandardAttribute.PredefinedDataName"));
    assertThat(child).isPresent();
    var stdAttribute = (StandardAttribute) child.get();
    assertThat(stdAttribute.getName()).isEqualTo("PredefinedDataName");
    assertThat(stdAttribute.getFullName().getRu()).isEqualTo("ИмяПредопределенныхДанных");
    assertThat(stdAttribute.getMdoReference())
      .isEqualTo(MdoReference.create("Catalog.Заметки.StandardAttribute.PredefinedDataName"));
    assertThat(stdAttribute.getKind()).isEqualTo(AttributeKind.STANDARD);
    assertThat(stdAttribute.getSynonym().isEmpty()).isTrue();
    assertThat(stdAttribute.getDescription()).isEqualTo("ИмяПредопределенныхДанных");
    assertThat(stdAttribute.getDescription("ru")).isEqualTo("ИмяПредопределенныхДанных");
    assertThat(stdAttribute.getDescription("en")).isEqualTo("PredefinedDataName");
    assertThat(stdAttribute.getValueType()).isNotNull();
    assertThat(stdAttribute.getValueType().contains(PrimitiveValueType.STRING)).isTrue();
  }

  /**
   * Проверяет, что для справочника "Заметки" поле checkUnique установлено в false.
   * <p>
   * В формате Designer: в XML файле явно указано {@code <CheckUnique>false</CheckUnique>}.
   * В формате EDT: в XML файле поле отсутствует, используется значение по умолчанию false.
   *
   * @param argumentsAccessor параметры теста (формат, имя пакета, ссылка на MDO, постфикс фикстуры)
   */
  @ParameterizedTest
  @CsvSource({
    "true, ssl_3_1, Catalogs.Заметки, _edt",
    "false, ssl_3_1, Catalogs.Заметки"
  })
  void testCheckUniqueFalse(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo)
      .isInstanceOf(Catalog.class);

    var catalog = (Catalog) mdo;
    assertThat(catalog.isCheckUnique())
      .as("Поле checkUnique должно быть false для справочника Заметки")
      .isFalse();
  }

  @ParameterizedTest
  @CsvSource({
    "true, ssl_3_1, Catalogs.ВерсииФайлов, _edt",
    "false, ssl_3_1, Catalogs.ВерсииФайлов"
  })
  void testSSLFixture(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo)
      .isInstanceOf(Catalog.class);
  }
}
