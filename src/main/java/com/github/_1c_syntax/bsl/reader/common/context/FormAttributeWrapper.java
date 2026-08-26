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
package com.github._1c_syntax.bsl.reader.common.context;

import com.github._1c_syntax.bsl.mdo.ValueTypeOwner;
import com.github._1c_syntax.bsl.mdo.storage.form.FormAdditionalColumnsAttribute;
import com.github._1c_syntax.bsl.mdo.storage.form.FormAttribute;
import com.github._1c_syntax.bsl.mdo.storage.form.FormDynamicListAttribute;
import com.github._1c_syntax.bsl.mdo.storage.form.FormDynamicListField;
import com.github._1c_syntax.bsl.mdo.storage.form.FormItem;
import com.github._1c_syntax.bsl.mdo.storage.form.FormSimpleAttribute;
import com.github._1c_syntax.bsl.mdo.storage.form.FormTableAttribute;
import com.github._1c_syntax.bsl.mdo.support.DynamicListKeyType;
import com.github._1c_syntax.bsl.mdo.support.FillChecking;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import com.github._1c_syntax.bsl.types.ValueTypeDescription;
import com.github._1c_syntax.bsl.types.value.V8ValueType;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Сырое (промежуточное) представление атрибута формы при чтении.
 * Ридеры наполняют его данными, а {@link #toFormAttribute()} выполняет
 * слияние колонок с дополнительными колонками и собирает конкретный класс.
 */
@Value
@Builder(toBuilder = true)
@ToString(of = "name")
public class FormAttributeWrapper implements FormItem, ValueTypeOwner {

  /**
   * Идентификатор
   */
  @Default
  int id = -1;

  /**
   * Имя
   */
  String name;

  /**
   * Заголовок
   */
  @Default
  MultiLanguageString title = MultiLanguageString.EMPTY;

  /**
   * Тип значения
   */
  @Default
  ValueTypeDescription type = ValueTypeDescription.EMPTY;

  /**
   * Признак основного реквизита
   */
  @Default
  boolean mainAttribute = false;

  /**
   * Признак сохранения с формой
   */
  @Default
  boolean savedData = false;

  /**
   * Проверка заполнения
   */
  @Default
  FillChecking fillCheck = FillChecking.DONT_CHECK;

  /**
   * Комментарий
   */
  @Default
  String comment = "";

  /**
   * Основная таблица динамического списка (например Catalog.Номенклатура).
   * Заполняется только для реквизитов с типом {@code ДинамическийСписок}
   */
  @Default
  String mainTable = "";

  /**
   * Признак произвольного запроса динамического списка.
   * Заполняется только для реквизитов с типом {@code ДинамическийСписок}
   */
  @Default
  boolean customQuery = false;

  /**
   * Текст запроса динамического списка.
   * Заполняется только для реквизитов с типом {@code ДинамическийСписок}
   */
  @Default
  String queryText = "";

  /**
   * Состав полей динамического списка.
   * Заполняется только для реквизитов с типом {@code ДинамическийСписок}
   */
  @Singular("addFields")
  List<FormDynamicListField> fields;

  /**
   * Вид ключа строки динамического списка.
   * Заполняется только для реквизитов с типом {@code ДинамическийСписок}
   */
  @Default
  DynamicListKeyType keyType = DynamicListKeyType.AUTO;

  /**
   * Поля ключа строки динамического списка.
   * Заполняются только для реквизитов с типом {@code ДинамическийСписок}
   */
  @Singular("addKeyFields")
  List<String> keyFields;

  /**
   * Колонки таблицы
   */
  @Singular("addColumns")
  List<FormAttributeWrapper> columns;

  @Override
  public ValueTypeDescription getValueType() {
    return type;
  }

  /**
   * Сборка конкретного класса атрибута формы:
   * сначала колонки сливаются с дополнительными колонками,
   * затем реквизит диспетчеризуется по типу значения.
   */
  public FormAttribute toFormAttribute() {
    var merged = mergeColumns(getColumns());
    var columns = merged.stream().map(FormAttributeWrapper::toFormAttribute).toList();
    return dispatch(columns);
  }

  /**
   * Слияние колонок с дополнительными колонками.
   * Дополнительная колонка (имя с точкой), совпавшая по последнему сегменту
   * с обычной колонкой, вливается в нее. Несовпавшие сохраняются как есть.
   */
  private List<FormAttributeWrapper> mergeColumns(@Nullable List<FormAttributeWrapper> source) {
    if (source == null || source.isEmpty()) {
      return List.of();
    }
    var regular = new ArrayList<FormAttributeWrapper>();
    var additional = new ArrayList<FormAttributeWrapper>();
    for (var wrapper : source) {
      (wrapper.getName().contains(".") ? additional : regular).add(wrapper);
    }
    if (additional.isEmpty()) {
      return source;
    }

    var unmatched = new ArrayList<FormAttributeWrapper>();
    for (var addCol : additional) {
      var baseName = addCol.getName().substring(addCol.getName().lastIndexOf('.') + 1);
      var matched = false;
      for (int i = 0; i < regular.size(); i++) {
        if (regular.get(i).getName().equals(baseName)) {
          var mergedChildren = new ArrayList<>(regular.get(i).getColumns());
          mergedChildren.addAll(addCol.getColumns());
          regular.set(i, regular.get(i).toBuilder()
            .clearColumns()
            .columns(mergedChildren)
            .build());
          matched = true;
          break;
        }
      }
      if (!matched) {
        unmatched.add(addCol);
      }
    }

    var result = new ArrayList<>(regular);
    result.addAll(unmatched);
    return result;
  }

  private FormAttribute dispatch(List<FormAttribute> columns) {
    if (type.contains(V8ValueType.VALUE_TABLE) || type.contains(V8ValueType.VALUE_TREE)) {
      return FormTableAttribute.builder()
        .id(id)
        .name(name)
        .title(title)
        .type(type)
        .mainAttribute(mainAttribute)
        .savedData(savedData)
        .fillCheck(fillCheck)
        .comment(comment)
        .columns(columns)
        .build();
    }
    if (type.contains(V8ValueType.DYNAMIC_LIST)) {
      return FormDynamicListAttribute.builder()
        .id(id)
        .name(name)
        .title(title)
        .type(type)
        .mainAttribute(mainAttribute)
        .savedData(savedData)
        .fillCheck(fillCheck)
        .comment(comment)
        .mainTable(mainTable)
        .customQuery(customQuery)
        .queryText(queryText)
        .fields(fields)
        .keyType(keyType)
        .keyFields(keyFields)
        .columns(columns)
        .build();
    }
    if (isAdditional()) {
      return FormAdditionalColumnsAttribute.builder()
        .name(name)
        .columns(columns)
        .build();
    }
    return FormSimpleAttribute.builder()
      .id(id)
      .name(name)
      .title(title)
      .type(type)
      .mainAttribute(mainAttribute)
      .savedData(savedData)
      .fillCheck(fillCheck)
      .comment(comment)
      .columns(columns)
      .build();
  }

  private boolean isAdditional() {
    return name.contains(".") && id == -1;
  }
}