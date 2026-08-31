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

import com.github._1c_syntax.bsl.mdo.ValueTypeOwner;
import com.github._1c_syntax.bsl.mdo.support.FillChecking;
import com.github._1c_syntax.bsl.types.MultiLanguageString;

import java.util.List;

/**
 * Атрибут формы (реквизит, таблица, динамический список или дополнительные колонки)
 */
public interface FormAttribute extends FormItem, ValueTypeOwner {

  /**
   * Идентификатор
   */
  int getId();

  /**
   * Заголовок
   */
  MultiLanguageString getTitle();

  /**
   * Признак основного реквизита
   */
  boolean isMainAttribute();

  /**
   * Признак сохранения с формой
   */
  boolean isSavedData();

  /**
   * Проверка заполнения
   */
  FillChecking getFillCheck();

  /**
   * Комментарий
   */
  String getComment();

  /**
   * Колонки (включая дополнительные колонки табличной части)
   */
  List<FormAttribute> getColumns();

  /**
   * Пути к данным полей реквизита, помеченных «использовать всегда»
   * ({@code Список.Ссылка}). Форма читает такое поле независимо от того,
   * показывает ли его хоть один элемент
   */
  List<String> getUseAlwaysFields();
}