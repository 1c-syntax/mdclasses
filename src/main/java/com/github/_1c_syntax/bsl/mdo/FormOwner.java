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
package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdo.support.DefaultFormKind;
import com.github._1c_syntax.bsl.types.MdoReference;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Расширение - Владелец дочерних форм
 */
public interface FormOwner extends ChildrenOwner {
  /**
   * Список форм объекта
   */
  List<ObjectForm> getForms();

  /**
   * Соответствие типов форм по умолчанию и ссылок на соответствующие формы.
   * <p>
   * Каждая реализация предоставляет свой набор в зависимости от того,
   * какие виды форм применимы для данного объекта метаданных.
   *
   * @return соответствие видов форм по умолчанию
   */
  Map<DefaultFormKind, MdoReference> getDefaultFormMap();

  /**
   * Возвращает ссылку на форму по умолчанию по типу.
   *
   * @param kind Тип формы по умолчанию
   * @return Ссылка на форму или {@link MdoReference#EMPTY}, если её нет либо тип неприменим
   */
  default MdoReference getDefaultFormLink(DefaultFormKind kind) {
    return getDefaultFormMap().getOrDefault(kind, MdoReference.EMPTY);
  }

  /**
   * Возвращает форму по умолчанию по типу.
   * Ищет форму в {@link #getForms()} по ссылке, полученной из {@link #getDefaultFormLink}.
   *
   * @param kind Тип формы по умолчанию
   * @return Форма или {@link Optional#empty()}, если ссылка не указана или форма не найдена в списке
   */
  default Optional<ObjectForm> getDefaultForm(DefaultFormKind kind) {
    MdoReference link = getDefaultFormLink(kind);
    if (link.isEmpty()) return Optional.empty();
    return getFormByLink(link);
  }

  /**
   * Поиск формы по произвольной ссылке.
   *
   * @param reference Ссылка на форму
   * @return Форма или {@link Optional#empty()}, если форма с указанной ссылкой не найдена
   */
  default Optional<ObjectForm> getFormByLink(MdoReference reference) {
    return getForms().stream()
      .filter(f -> f.getMdoReference().equals(reference))
      .findFirst();
  }
}
