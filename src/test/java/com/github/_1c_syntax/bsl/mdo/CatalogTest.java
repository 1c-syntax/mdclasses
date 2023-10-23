/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

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
    assertThat(catalog.getAllAttributes()).hasSize(3);
    assertThat(catalog.getChildren())
      .hasSize(9)
      .anyMatch(child -> child instanceof ObjectAttribute)
      .anyMatch(child -> child instanceof ObjectCommand)
      .anyMatch(child -> child instanceof ObjectForm)
      .anyMatch(child -> child instanceof TabularSection)
    ;
    assertThat(catalog.getPlainChildren()).hasSize(11);
    assertThat(catalog.getAttributes()).hasSize(3);
    assertThat(catalog.getTabularSections()).hasSize(1);
    assertThat(catalog.getForms()).hasSize(3);
    assertThat(catalog.getTemplates()).hasSize(1);
    assertThat(catalog.getCommands()).hasSize(1);
    assertThat(catalog.getModules()).hasSize(2);
    assertThat(catalog.getAllModules()).hasSize(6);
    assertThat(catalog.getMDOChildren())
      .hasSize(4)
      .anyMatch(child -> child instanceof ObjectAttribute)
      .anyMatch(child -> child instanceof TabularSection)
      .noneMatch(child -> child instanceof ObjectCommand)
      .noneMatch(child -> child instanceof ObjectForm)
    ;
    assertThat(catalog.getMDOPlainChildren())
      .hasSize(6)
      .anyMatch(child -> child instanceof ObjectAttribute)
      .anyMatch(child -> child instanceof TabularSection)
      .noneMatch(child -> child instanceof ObjectCommand)
      .noneMatch(child -> child instanceof ObjectForm)
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
      .hasSize(14)
      .anyMatch(child -> child instanceof ObjectAttribute)
      .anyMatch(child -> child instanceof ObjectCommand)
      .anyMatch(child -> child instanceof ObjectForm)
    ;
    assertThat(catalog.getMDOChildren())
      .hasSize(8)
      .anyMatch(child -> child instanceof ObjectAttribute)
      .noneMatch(child -> child instanceof ObjectCommand)
      .noneMatch(child -> child instanceof ObjectForm)
    ;
    assertThat(catalog.getMDOPlainChildren())
      .hasSize(8)
      .anyMatch(child -> child instanceof ObjectAttribute)
      .noneMatch(child -> child instanceof ObjectCommand)
      .noneMatch(child -> child instanceof ObjectForm)
    ;
  }

//  private void checkExtInfo(FormDataOLD formData) {
//    var extInfo = (DynamicListExtInfo) formData.getAttributes().get(1).getExtInfo();
//    assertThat(extInfo)
//      .isNotNull()
//      .isInstanceOf(DynamicListExtInfo.class);
//
//    assertThat(extInfo.isCustomQuery()).isTrue();
//    assertThat(extInfo.getQuery().getTextQuery()).isNotEmpty();
//  }
}