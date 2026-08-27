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
import com.github._1c_syntax.bsl.mdo.CommonForm;
import com.github._1c_syntax.bsl.mdo.Form;
import com.github._1c_syntax.bsl.mdo.FormOwner;
import com.github._1c_syntax.bsl.mdo.support.FillChecking;
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import com.github._1c_syntax.bsl.types.value.V8ValueType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class FormItemsTest {

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, Catalogs.Справочник1",
    "false, mdclasses, Catalogs.Справочник1",
  })
  void shouldReadSimpleAttribute(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo)
      .isNotNull()
      .isInstanceOf(FormOwner.class);

    var formOwner = (FormOwner) mdo;
    var form = formOwner.getForms().stream()
      .filter(f -> f.getName().equals("ФормаЭлемента"))
      .findFirst()
      .orElse(null);

    assertThat(form).isNotNull();
    var formData = form.getData();
    assertThat(formData).isNotNull();
    assertThat(formData.isEmpty()).isFalse();

    var attributes = formData.getAttributes();
    assertThat(attributes).isNotEmpty();

    var objectAttr = attributes.stream()
      .filter(a -> a.getName().equals("Объект"))
      .findFirst()
      .orElse(null);

    assertThat(objectAttr).isNotNull();
    assertThat(objectAttr).isInstanceOf(FormAttribute.class);

    assertThat(objectAttr.getId()).isEqualTo(1);
    assertThat(objectAttr.getName()).isEqualTo("Объект");
    assertThat(objectAttr.isMainAttribute()).isTrue();
    assertThat(objectAttr.isSavedData()).isTrue();
    assertThat(objectAttr.getValueType().getTypes()).hasSize(1);
    assertThat(objectAttr.getFillCheck()).isEqualTo(FillChecking.DONT_CHECK);
    assertThat(objectAttr.getComment()).isEmpty();
    assertThat(objectAttr.getTitle()).isEqualTo(MultiLanguageString.EMPTY);
    assertThat(objectAttr.getColumns()).isEmpty();

    assertThat(formData.getEventHandlers()).isEmpty();
    assertThat(formData.getPlainEventHandlers()).isEmpty();
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, CommonForms.Форма",
    "false, mdclasses, CommonForms.Форма",
  })
  void shouldReadCommonFormData(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo)
      .isNotNull()
      .isInstanceOf(CommonForm.class);

    var form = (CommonForm) mdo;
    var formData = form.getData();
    assertThat(formData).isNotNull();

    assertThat(formData.getEventHandlers()).isNotNull().isEmpty();
    assertThat(formData.getPlainEventHandlers()).isNotNull().isEmpty();
    assertThat(formData.getCommands()).isNotNull();
    assertThat(formData.getParameters()).isNotNull();
  }

  @ParameterizedTest
  @CsvSource({
    "true, ssl_3_2, Documents.Анкета, Document.Анкета.Form.ФормаДокумента",
    "false, ssl_3_2, Documents.Анкета, Document.Анкета.Form.ФормаДокумента",
  })
  void shouldReadCommandsAttributesParameters(ArgumentsAccessor argumentsAccessor) {
    var formRef = argumentsAccessor.getString(3);
    assertThat(formRef).isNotNull();

    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isNotNull();
    assertThat(mdo).isInstanceOf(ChildrenOwner.class);

    var childOpt = ((ChildrenOwner) mdo).findChild(formRef);
    assertThat(childOpt).isPresent();

    var form = (Form) childOpt.get();
    var formData = form.getData();
    assertThat(formData).isNotNull();

    var commands = formData.getCommands();
    assertThat(commands).hasSize(6);

    var firstCmd = commands.getFirst();
    assertThat(firstCmd.getName()).isEqualTo("ФормаЗаполненияЗаписать");
    assertThat(firstCmd.getId()).isEqualTo(1);
    assertThat(firstCmd.getAction()).isEqualTo("ФормаЗаполненияЗаписать");
    assertThat(firstCmd.getShortcut()).isEqualTo("Ctrl+S");

    var params = formData.getParameters();
    assertThat(params).hasSize(3);

    assertThat(params.get(0).getName()).isEqualTo("ВозможностьПредварительногоСохранения");
    assertThat(params.get(0).isKeyParameter()).isFalse();
    assertThat(params.get(1).getName()).isEqualTo("ТолькоФормаЗаполнения");
    assertThat(params.get(1).isKeyParameter()).isFalse();
    assertThat(params.get(2).getName()).isEqualTo("Заголовок");
    assertThat(params.get(2).isKeyParameter()).isFalse();

    var attributes = formData.getAttributes();
    assertThat(attributes).isNotEmpty();

    var objectAttr = findAttr(attributes, "Объект");
    assertThat(objectAttr).isNotNull();
    assertThat(objectAttr.getId()).isEqualTo(1);
    assertThat(objectAttr.isMainAttribute()).isTrue();
    assertThat(objectAttr.isSavedData()).isTrue();
    assertThat(objectAttr.getValueType().getTypes()).hasSize(1);
    assertThat(objectAttr.getFillCheck()).isEqualTo(FillChecking.DONT_CHECK);
    assertThat(objectAttr.getComment()).isEmpty();
    assertThat(objectAttr.getTitle()).isEqualTo(MultiLanguageString.EMPTY);
    assertThat(objectAttr.getColumns()).isEmpty();

    var tableAttr = findAttr(attributes, "ТаблицаВопросовРаздела");
    assertThat(tableAttr).isNotNull();
    assertThat(tableAttr.getId()).isEqualTo(8);
    assertThat(tableAttr.getValueType().contains(V8ValueType.VALUE_TABLE)).isTrue();
    assertThat(tableAttr.getColumns()).hasSize(34);

    var treeAttr = findAttr(attributes, "ДеревоРазделов");
    assertThat(treeAttr).isNotNull();
    assertThat(treeAttr.getId()).isEqualTo(12);
    assertThat(treeAttr.getValueType().contains(V8ValueType.VALUE_TREE)).isTrue();
    assertThat(treeAttr.getTitle().isEmpty()).isFalse();
    assertThat(treeAttr.getColumns()).hasSize(6);
    assertThat(treeAttr.getColumns().get(1).getName()).isEqualTo("ПолныйКод");
    assertThat(treeAttr.getColumns().get(1).getId()).isEqualTo(3);

    var additionalCol = findAttr(tableAttr.getColumns(), "СоставТабличногоВопроса");
    assertThat(additionalCol).isNotNull();
    assertThat(additionalCol.getColumns()).hasSize(2);
    assertThat(additionalCol.getColumns().get(0).getName()).isEqualTo("ЭлементарныйВопрос");
    assertThat(additionalCol.getColumns().get(0).getId()).isEqualTo(1);
    assertThat(additionalCol.getColumns().get(1).getName()).isEqualTo("НомерСтроки");
    assertThat(additionalCol.getColumns().get(1).getId()).isEqualTo(2);

    var plain = formData.getPlainAttributes();
    assertThat(plain)
      .isNotNull()
      .hasSize(89);
    assertThat(plain.get("Объект")).isEqualTo(objectAttr);
    assertThat(plain.get("ТаблицаВопросовРаздела")).isEqualTo(tableAttr);
    assertThat(plain.get("ТаблицаВопросовРаздела.Наименование")).isNotNull();
    assertThat(plain.get("ТаблицаВопросовРаздела.Наименование").getId()).isEqualTo(2);

    assertThat(plain.get("ТаблицаВопросовРаздела.СоставТабличногоВопроса")).isEqualTo(additionalCol);
    assertThat(plain.get("ТаблицаВопросовРаздела.СоставТабличногоВопроса").getColumns()).hasSize(2);
    assertThat(plain.get("ТаблицаВопросовРаздела.СоставТабличногоВопроса.ЭлементарныйВопрос")).isNotNull();
    assertThat(plain.get("ТаблицаВопросовРаздела.СоставТабличногоВопроса.ЭлементарныйВопрос").getId())
      .isEqualTo(1);
    assertThat(plain.get("ТаблицаВопросовРаздела.СоставТабличногоВопроса.НомерСтроки")).isNotNull();
    assertThat(plain.get("ТаблицаВопросовРаздела.СоставТабличногоВопроса.НомерСтроки").getId()).isEqualTo(2);

    assertThat(plain.get("ДеревоРазделов")).isEqualTo(treeAttr);
    assertThat(plain.get("ДеревоРазделов.ПолныйКод")).isNotNull();
    assertThat(plain.get("ДеревоРазделов.ПолныйКод").getId()).isEqualTo(3);

    var eventHandlers = formData.getEventHandlers();
    assertThat(eventHandlers).hasSize(7);
    assertThat(eventHandlers)
      .extracting(FormEventHandler::event, FormEventHandler::handler)
      .contains(
        tuple("AfterWrite", "ПослеЗаписи"),
        tuple("AfterWriteAtServer", "ПослеЗаписиНаСервере"),
        tuple("OnReadAtServer", "ПриЧтенииНаСервере"),
        tuple("OnOpen", "ПриОткрытии"),
        tuple("BeforeClose", "ПередЗакрытием"),
        tuple("BeforeWriteAtServer", "ПередЗаписьюНаСервере"),
        tuple("OnCreateAtServer", "ПриСозданииНаСервере")
      );

    var plainEventHandlers = formData.getPlainEventHandlers();
    assertThat(plainEventHandlers).hasSize(10);
    assertThat(plainEventHandlers.get("Form.AfterWrite").handler()).isEqualTo("ПослеЗаписи");
    assertThat(plainEventHandlers.get("Form.AfterWriteAtServer").handler()).isEqualTo("ПослеЗаписиНаСервере");
    assertThat(plainEventHandlers.get("Form.OnReadAtServer").handler()).isEqualTo("ПриЧтенииНаСервере");
    assertThat(plainEventHandlers.get("Form.OnOpen").handler()).isEqualTo("ПриОткрытии");
    assertThat(plainEventHandlers.get("Form.BeforeClose").handler()).isEqualTo("ПередЗакрытием");
    assertThat(plainEventHandlers.get("Form.BeforeWriteAtServer").handler()).isEqualTo("ПередЗаписьюНаСервере");
    assertThat(plainEventHandlers.get("Form.OnCreateAtServer").handler()).isEqualTo("ПриСозданииНаСервере");
    assertThat(plainEventHandlers.get("Респондент.StartChoice").handler()).isEqualTo("РеспондентНачалоВыбора");
    assertThat(plainEventHandlers.get("Комментарий.StartChoice").handler()).isEqualTo("КомментарийНачалоВыбора");
    assertThat(plainEventHandlers.get("ДеревоРазделов.Selection").handler()).isEqualTo("ДеревоРазделовВыбор");
  }

  @ParameterizedTest
  @CsvSource({
    "true, ssl_3_2, Catalogs.ГруппыДоступа",
    "false, ssl_3_2, Catalogs.ГруппыДоступа",
  })
  void shouldReadAdditionalColumnsForObjectAttribute(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo)
      .isNotNull()
      .isInstanceOf(FormOwner.class);

    var formOwner = (FormOwner) mdo;
    var form = formOwner.getForms().stream()
      .filter(f -> f.getName().equals("ФормаЭлемента"))
      .findFirst()
      .orElse(null);

    assertThat(form).isNotNull();
    var formData = form.getData();
    assertThat(formData).isNotNull();

    var objectAttr = findAttr(formData.getAttributes(), "Объект");
    assertThat(objectAttr).isNotNull();
    assertThat(objectAttr).isInstanceOf(FormSimpleAttribute.class);

    var additionalColumns = objectAttr.getColumns().stream()
      .map(FormAdditionalColumnsAttribute.class::cast)
      .toList();
    assertThat(additionalColumns)
      .extracting(FormAttribute::getName)
      .containsExactly("Объект.Пользователи", "Объект.ВидыДоступа", "Объект.ЗначенияДоступа");

    var users = additionalColumns.stream()
      .filter(col -> col.getName().equals("Объект.Пользователи"))
      .findFirst()
      .orElseThrow();
    assertThat(users.getColumns()).hasSize(1);
    assertThat(users.getColumns().getFirst().getName()).isEqualTo("НомерКартинки");
    assertThat(users.getColumns().getFirst().getId()).isEqualTo(1);

    var kinds = additionalColumns.stream()
      .filter(col -> col.getName().equals("Объект.ВидыДоступа"))
      .findFirst()
      .orElseThrow();
    assertThat(kinds.getColumns()).hasSize(2);
    assertThat(kinds.getColumns()).extracting(FormAttribute::getName)
      .containsExactly("ВидДоступаПредставление", "ВсеРазрешеныПредставление");

    var values = additionalColumns.stream()
      .filter(col -> col.getName().equals("Объект.ЗначенияДоступа"))
      .findFirst()
      .orElseThrow();
    assertThat(values.getColumns()).hasSize(1);
    assertThat(values.getColumns().getFirst().getName()).isEqualTo("НомерСтрокиПоВиду");

    var plain = formData.getPlainAttributes();
    assertThat(plain.get("Объект.Пользователи")).isEqualTo(users);
    assertThat(plain.get("Объект.Пользователи.НомерКартинки")).isNotNull();
    assertThat(plain.get("Объект.Пользователи.НомерКартинки").getId()).isEqualTo(1);
  }

  @ParameterizedTest
  @CsvSource({
    "true, ssl_3_2, DataProcessors.РаботаСФайлами",
    "false, ssl_3_2, DataProcessors.РаботаСФайлами",
  })
  void shouldReadConditionalAppearanceFields(ArgumentsAccessor argumentsAccessor) {
    // given
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isNotNull();
    var form = (Form) ((ChildrenOwner) mdo)
      .findChild("DataProcessor.РаботаСФайлами.Form.ВерсияПрисоединенногоФайла")
      .orElseThrow();

    // when
    var fields = form.getData().getConditionalAppearanceFields();

    // then
    assertThat(fields)
      .as("оформляемое поле и поле условия — оба читаются формой")
      .contains("Том0", "ОбъектПрототип.Том");
  }

  @ParameterizedTest
  @CsvSource({
    "true, ssl_3_1, Reports.ПрогрессОтложенногоОбновления",
    "false, ssl_3_1, Reports.ПрогрессОтложенногоОбновления",
  })
  void shouldReadRowPictureDataPath(ArgumentsAccessor argumentsAccessor) {
    // given
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isNotNull();
    var form = (Form) ((ChildrenOwner) mdo)
      .findChild("Report.ПрогрессОтложенногоОбновления.Form.ЗарегистрированныеДанные")
      .orElseThrow();

    // when
    var table = form.getData().getPlainElements().stream()
      .filter(FormTable.class::isInstance)
      .map(FormTable.class::cast)
      .findFirst()
      .orElseThrow();

    // then
    assertThat(table.getRowPictureDataPath())
      .as("путь отдаётся как записан: в этой выгрузке конфигуратор пишет его "
        + "с тильдой, а EDT — без неё")
      .endsWith("Список.DefaultPicture");
  }

  private static FormAttribute findAttr(List<? extends FormAttribute> attrs, String name) {
    return attrs.stream().filter(a -> a.getName().equals(name)).findFirst().orElse(null);
  }
}
