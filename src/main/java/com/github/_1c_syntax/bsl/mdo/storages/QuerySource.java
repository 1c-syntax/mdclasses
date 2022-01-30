/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2022
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
package com.github._1c_syntax.bsl.mdo.storages;

import com.github._1c_syntax.bsl.mdo.support.SourcePosition;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Модель хранения информации о запросе СКД
 */
@Value
@RequiredArgsConstructor
public class QuerySource {

  /**
   * Пустой запрос
   */
  private static final QuerySource EMPTY = new QuerySource(new SourcePosition(0, 0), "");

  /**
   * Позиция запроса в исходном файле
   */
  SourcePosition position;

  /**
   * Текст запроса
   */
  String textQuery;

  /**
   * Ссылка на пустой запрос
   *
   * @return Пустой запрос
   */
  public static QuerySource empty() {
    return EMPTY;
  }
}
