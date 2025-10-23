/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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

import lombok.NonNull;

/**
 * Модель хранения информации о запросе СКД
 *
 * @param line      Номер строки позиции запроса в исходном файле
 * @param column    Номер первого символа позиции запроса в исходном файле
 * @param textQuery Текст запроса
 */
public record QuerySource(int line, int column, @NonNull String textQuery) {

  /**
   * Пустой запрос
   */
  public static final QuerySource EMPTY = new QuerySource(0, 0, "");
}
