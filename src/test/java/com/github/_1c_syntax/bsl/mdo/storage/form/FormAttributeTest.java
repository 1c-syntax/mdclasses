/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
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
package com.github._1c_syntax.bsl.mdo.storage.form;

import com.github._1c_syntax.bsl.mdo.ChildrenOwner;
import com.github._1c_syntax.bsl.mdo.Form;
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.types.value.V8ValueType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FormAttributeTest {

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, Catalogs.Справочник1",
    "false, mdclasses, Catalogs.Справочник1",
  })
  void shouldReadCustomQueryDynamicList(ArgumentsAccessor argumentsAccessor) {
    var form = getForm(argumentsAccessor, "Catalog.Справочник1.Form.ФормаСписка");

    var attr = findAttr(form.getData().getAttributes(), "МойСписок");
    assertThat(attr).isNotNull();
    assertThat(attr.getType().contains(V8ValueType.DYNAMIC_LIST)).isTrue();
    assertThat(attr.getMainTable()).isEqualTo("Catalog.МойСправочник");
    assertThat(attr.isCustomQuery()).isTrue();
    assertThat(attr.getQueryText()).contains("Справочник.МойСправочник");
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses_3_27, Catalogs.Справочник1",
    "false, mdclasses_3_27, Catalogs.Справочник1",
  })
  void shouldReadSimpleDynamicList(ArgumentsAccessor argumentsAccessor) {
    var form = getForm(argumentsAccessor, "Catalog.Справочник1.Form.ФормаСписка");

    var attr = findAttr(form.getData().getAttributes(), "Список");
    assertThat(attr).isNotNull();
    assertThat(attr.getType().contains(V8ValueType.DYNAMIC_LIST)).isTrue();
    assertThat(attr.getMainTable()).isEqualTo("Catalog.Справочник1");
    assertThat(attr.isCustomQuery()).isFalse();
    assertThat(attr.getQueryText()).isEmpty();
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, Catalogs.Справочник1",
    "false, mdclasses, Catalogs.Справочник1",
  })
  void shouldKeepDefaultsForNonDynamicAttribute(ArgumentsAccessor argumentsAccessor) {
    var form = getForm(argumentsAccessor, "Catalog.Справочник1.Form.ФормаЭлемента");

    var attr = findAttr(form.getData().getAttributes(), "Объект");
    assertThat(attr).isNotNull();
    assertThat(attr.getMainTable()).isEmpty();
    assertThat(attr.isCustomQuery()).isFalse();
    assertThat(attr.getQueryText()).isEmpty();
  }

  private static Form getForm(ArgumentsAccessor argumentsAccessor, String formRef) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isNotNull();
    var childOpt = ((ChildrenOwner) mdo).findChild(formRef);
    assertThat(childOpt).isPresent();
    return (Form) childOpt.get();
  }

  private static FormAttribute findAttr(List<? extends FormAttribute> attrs, String name) {
    return attrs.stream().filter(a -> a.getName().equals(name)).findFirst().orElse(null);
  }
}