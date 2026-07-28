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

import com.github._1c_syntax.bsl.types.MdoReference;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;

/**
 * Описание характеристики объекта метаданных
 */
@Value
@Builder
public class Characteristic {

  /**
   * Виды характеристик - ссылка на источник видов
   */
  @Default
  MdoReference characteristicTypes = MdoReference.EMPTY;

  /**
   * Поле ключа
   */
  @Default
  MdoReference keyField = MdoReference.EMPTY;

  /**
   * Поле отбора видов
   */
  @Default
  MdoReference typesFilterField = MdoReference.EMPTY;

  /**
   * Поле пути к данным
   */
  @Default
  MdoReference dataPathField = MdoReference.EMPTY;

  /**
   * Поле использования множественных значений
   */
  @Default
  MdoReference multipleValuesUseField = MdoReference.EMPTY;

  /**
   * Значения характеристик - ссылка на источник значений
   */
  @Default
  MdoReference characteristicValues = MdoReference.EMPTY;

  /**
   * Поле объекта
   */
  @Default
  MdoReference objectField = MdoReference.EMPTY;

  /**
   * Поле вида
   */
  @Default
  MdoReference typeField = MdoReference.EMPTY;

  /**
   * Поле значения
   */
  @Default
  MdoReference valueField = MdoReference.EMPTY;

  /**
   * Поле ключа множественных значений
   */
  @Default
  MdoReference multipleValuesKeyField = MdoReference.EMPTY;

  /**
   * Поле порядка множественных значений
   */
  @Default
  MdoReference multipleValuesOrderField = MdoReference.EMPTY;
}
