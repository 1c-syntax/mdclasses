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

import com.github._1c_syntax.bsl.mdo.support.DynamicListKeyType;
import com.github._1c_syntax.bsl.mdo.support.FillChecking;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import com.github._1c_syntax.bsl.types.ValueTypeDescription;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;

import java.util.List;

/**
 * Динамический список формы (реквизит типа динамический список)
 */
@Value
@Builder(toBuilder = true)
@ToString(of = "name")
public class FormDynamicListAttribute implements FormAttribute {

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
   * Состав полей динамического списка - поля, которые список объявляет поверх
   * основной таблицы или текста запроса.
   * Заполняется только для реквизитов с типом {@code ДинамическийСписок}
   */
  @Singular("addFields")
  List<FormDynamicListField> fields;

  /**
   * Вид ключа строки: чем список адресует свою строку.
   * Значение по умолчанию: {@link DynamicListKeyType#AUTO}
   */
  @Default
  DynamicListKeyType keyType = DynamicListKeyType.AUTO;

  /**
   * Поля ключа строки. Заполняются при виде ключа
   * {@link DynamicListKeyType#FIELD_VALUE} и {@link DynamicListKeyType#ROW_KEY};
   * полей бывает несколько
   */
  @Singular("addKeyFields")
  List<String> keyFields;

  /**
   * Колонки
   */
  @Singular("addColumns")
  List<FormAttribute> columns;

  /**
   * Пути к данным полей, помеченных «использовать всегда»
   */
  @Singular("addUseAlwaysFields")
  List<String> useAlwaysFields;

  /**
   * Поля, названные в настройках самого списка: в отборе, порядке и условном
   * оформлении. Имена даны относительно списка ({@code ПометкаУдаления}),
   * а список читает такое поле независимо от того, показывает ли его элемент
   */
  @Singular("addSettingsFields")
  List<String> settingsFields;

  @Override
  public ValueTypeDescription getValueType() {
    return type;
  }
}