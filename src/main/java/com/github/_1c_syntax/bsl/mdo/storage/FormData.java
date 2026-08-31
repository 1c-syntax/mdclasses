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
package com.github._1c_syntax.bsl.mdo.storage;

import com.github._1c_syntax.bsl.mdo.storage.form.FormAttribute;
import com.github._1c_syntax.bsl.mdo.storage.form.FormCommand;
import com.github._1c_syntax.bsl.mdo.storage.form.FormElement;
import com.github._1c_syntax.bsl.mdo.storage.form.FormElementOwner;
import com.github._1c_syntax.bsl.mdo.storage.form.FormEventHandler;
import com.github._1c_syntax.bsl.mdo.storage.form.FormParameter;
import com.github._1c_syntax.bsl.types.MultiLanguageString;

import java.util.List;
import java.util.Map;

/**
 * Интерфейс содержимого форм
 */
public interface FormData extends FormElementOwner {
  /**
   * Признак пустого содержимого
   */
  default boolean isEmpty() {
    return this == EmptyFormData.EMPTY;
  }

  /**
   * Заголовок формы
   */
  default MultiLanguageString getTitle() {
    return MultiLanguageString.EMPTY;
  }

  /**
   * Обработчики событий формы
   */
  default List<FormEventHandler> getEventHandlers() {
    return List.of();
  }

  /**
   * Список визуальных элементов формы первого уровня (т.е. с родителем - форма)
   */
  @Override
  default List<FormElement> getElements() {
    return List.of();
  }

  /**
   * Список всех визуальных элементов формы
   */
  @Override
  default List<FormElement> getPlainElements() {
    return List.of();
  }

  /**
   * Список реквизитов формы
   */
  default List<FormAttribute> getAttributes() {
    return List.of();
  }

  /**
   * Плоское представление атрибутов и их колонок (включая вложенные).
   * Ключ — имя с точками (например, "Объект.Наименование.Подстрока")
   */
  default Map<String, FormAttribute> getPlainAttributes() {
    return Map.of();
  }

  /**
   * Пути к данным, названные в условном оформлении формы: и оформляемые поля,
   * и поля условий ({@code Список.ПометкаУдаления}, {@code Объект.Том}).
   * Форма читает такое поле независимо от того, показывает ли его элемент
   */
  default List<String> getConditionalAppearanceFields() {
    return List.of();
  }

  /**
   * Плоское представление обработчиков событий формы,
   * где ключ — имя события
   */
  default Map<String, FormEventHandler> getPlainEventHandlers() {
    return Map.of();
  }

  /**
   * Список команд формы
   */
  default List<FormCommand> getCommands() {
    return List.of();
  }

  /**
   * Список параметров формы
   */
  default List<FormParameter> getParameters() {
    return List.of();
  }
}
