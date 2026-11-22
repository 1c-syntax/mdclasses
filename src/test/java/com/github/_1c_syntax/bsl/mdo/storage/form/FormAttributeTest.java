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
import com.github._1c_syntax.bsl.mdo.support.DynamicListFieldKind;
import com.github._1c_syntax.bsl.mdo.support.DynamicListKeyType;
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.types.ValueTypeDescription;
import com.github._1c_syntax.bsl.types.value.PrimitiveValueType;
import com.github._1c_syntax.bsl.types.value.V8ValueType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    assertThat(attr)
      .isNotNull()
      .isInstanceOf(FormDynamicListAttribute.class);
    var dynList = (FormDynamicListAttribute) attr;
    assertThat(dynList.getType().contains(V8ValueType.DYNAMIC_LIST)).isTrue();
    assertThat(dynList.getMainTable()).isEqualTo("Catalog.МойСправочник");
    assertThat(dynList.isCustomQuery()).isTrue();
    assertThat(dynList.getQueryText()).contains("Справочник.МойСправочник");
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses_3_27, Catalogs.Справочник1",
    "false, mdclasses_3_27, Catalogs.Справочник1",
  })
  void shouldReadSimpleDynamicList(ArgumentsAccessor argumentsAccessor) {
    var form = getForm(argumentsAccessor, "Catalog.Справочник1.Form.ФормаСписка");

    var attr = findAttr(form.getData().getAttributes(), "Список");
    assertThat(attr)
      .isNotNull()
      .isInstanceOf(FormDynamicListAttribute.class);
    var dynList = (FormDynamicListAttribute) attr;
    assertThat(dynList.getType().contains(V8ValueType.DYNAMIC_LIST)).isTrue();
    assertThat(dynList.getMainTable()).isEqualTo("Catalog.Справочник1");
    assertThat(dynList.isCustomQuery()).isFalse();
    assertThat(dynList.getQueryText()).isEmpty();
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, Catalogs.Справочник1",
    "false, mdclasses, Catalogs.Справочник1",
  })
  void shouldReadDynamicListFields(ArgumentsAccessor argumentsAccessor) {
    // given
    var form = getForm(argumentsAccessor, "Catalog.Справочник1.Form.ФормаСписка");

    // when
    var dynList = (FormDynamicListAttribute) findAttr(form.getData().getAttributes(), "МойСписок");

    // then
    assertThat(dynList.getFields())
      .extracting(FormDynamicListField::getDataPath)
      .containsExactly("Ссылка", "Наименование", "Группа", "Группа.Реквизит1");

    // имя поля отличается от пути к данным у полей, сгруппированных под общим префиксом
    assertThat(dynList.getFields())
      .extracting(FormDynamicListField::getName)
      .containsExactly("Ссылка", "Наименование", "Группа", "Реквизит1");

    // вложенный набор данных - не поле, а группа, под которой они лежат
    assertThat(dynList.getFields())
      .extracting(FormDynamicListField::getKind)
      .containsExactly(DynamicListFieldKind.FIELD, DynamicListFieldKind.FIELD,
        DynamicListFieldKind.NESTED_DATA_SET, DynamicListFieldKind.FIELD);

    // тип у поля состава указывается редко, поэтому по умолчанию он пустой
    var fieldsByPath = dynList.getFields().stream()
      .collect(Collectors.toMap(FormDynamicListField::getDataPath, Function.identity()));
    assertThat(fieldsByPath.get("Ссылка").getValueType()).isEqualTo(ValueTypeDescription.EMPTY);
    assertThat(fieldsByPath.get("Наименование").getValueType().contains(PrimitiveValueType.STRING)).isTrue();
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, Catalogs.Справочник1",
    "false, mdclasses, Catalogs.Справочник1",
  })
  void shouldReadRowKeyOfDynamicList(ArgumentsAccessor argumentsAccessor) {
    // given
    var form = getForm(argumentsAccessor, "Catalog.Справочник1.Form.ФормаСписка");

    // when
    var dynList = (FormDynamicListAttribute) findAttr(form.getData().getAttributes(), "МойСписок");

    // then
    assertThat(dynList.getKeyType()).isEqualTo(DynamicListKeyType.FIELD_VALUE);
    assertThat(dynList.getKeyFields())
      .as("полей ключа бывает несколько")
      .containsExactly("Ссылка", "Код");
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses_3_27, Catalogs.Справочник1",
    "false, mdclasses_3_27, Catalogs.Справочник1",
  })
  void shouldKeepRowKeyDefaultWhenNotDeclared(ArgumentsAccessor argumentsAccessor) {
    // given
    var form = getForm(argumentsAccessor, "Catalog.Справочник1.Form.ФормаСписка");

    // when
    var dynList = (FormDynamicListAttribute) findAttr(form.getData().getAttributes(), "Список");

    // then
    assertThat(dynList.getKeyType())
      .as("вид ключа по умолчанию платформа в выгрузку не пишет")
      .isEqualTo(DynamicListKeyType.AUTO);
    assertThat(dynList.getKeyFields()).isEmpty();
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses_3_27, Catalogs.Справочник1",
    "false, mdclasses_3_27, Catalogs.Справочник1",
  })
  void shouldKeepFieldsEmptyWithoutFieldComposition(ArgumentsAccessor argumentsAccessor) {
    // given
    var form = getForm(argumentsAccessor, "Catalog.Справочник1.Form.ФормаСписка");

    // when
    var dynList = (FormDynamicListAttribute) findAttr(form.getData().getAttributes(), "Список");

    // then
    assertThat(dynList.getFields()).isEmpty();
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, Catalogs.Справочник1",
    "false, mdclasses, Catalogs.Справочник1",
  })
  void shouldKeepDefaultsForNonDynamicAttribute(ArgumentsAccessor argumentsAccessor) {
    var form = getForm(argumentsAccessor, "Catalog.Справочник1.Form.ФормаЭлемента");

    var attr = findAttr(form.getData().getAttributes(), "Объект");
    assertThat(attr).isNotNull().isInstanceOf(FormSimpleAttribute.class);
  }

  @ParameterizedTest
  @CsvSource({
    "true, ssl_3_1, Tasks.ЗадачаИсполнителя",
    "false, ssl_3_1, Tasks.ЗадачаИсполнителя",
  })
  void shouldReadUseAlwaysFields(ArgumentsAccessor argumentsAccessor) {
    // given
    var form = getForm(argumentsAccessor, "Task.ЗадачаИсполнителя.Form.ЗадачиПоБизнесПроцессу");

    // when
    var dynList = (FormDynamicListAttribute) findAttr(form.getData().getAttributes(), "Список");

    // then
    assertThat(dynList.getUseAlwaysFields())
      .as("поля, которые форма читает независимо от того, показывает ли их элемент")
      .containsExactlyInAnyOrder("Список.BusinessProcess", "Список.Ref");
  }

  @ParameterizedTest
  @CsvSource({
    "true, ssl_3_1, Tasks.ЗадачаИсполнителя",
    "false, ssl_3_1, Tasks.ЗадачаИсполнителя",
  })
  void shouldReadSettingsFieldsOfDynamicList(ArgumentsAccessor argumentsAccessor) {
    // given
    var form = getForm(argumentsAccessor, "Task.ЗадачаИсполнителя.Form.ЗадачиПоБизнесПроцессу");

    // when
    var dynList = (FormDynamicListAttribute) findAttr(form.getData().getAttributes(), "Список");

    // then
    assertThat(dynList.getSettingsFields())
      .as("поле, названное в порядке списка, список читает независимо от колонок")
      .contains("Number");
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses_3_27, Catalogs.Справочник1",
    "false, mdclasses_3_27, Catalogs.Справочник1",
  })
  void shouldKeepUseAlwaysFieldsEmptyWhenNotDeclared(ArgumentsAccessor argumentsAccessor) {
    // given
    var form = getForm(argumentsAccessor, "Catalog.Справочник1.Form.ФормаСписка");

    // when
    var dynList = (FormDynamicListAttribute) findAttr(form.getData().getAttributes(), "Список");

    // then
    assertThat(dynList.getUseAlwaysFields()).isEmpty();
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