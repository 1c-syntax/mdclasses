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
 * Таблица формы (реквизит типа таблица значений или дерево значений)
 */
@Value
@Builder
@ToString(of = "name")
public class FormTableAttribute implements FormAttribute {

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
   * Колонки таблицы
   */
  @Singular("addColumns")
  List<FormAttribute> columns;

  @Override
  public ValueTypeDescription getValueType() {
    return type;
  }
}