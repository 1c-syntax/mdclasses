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

import java.util.List;

/**
 * Индекс таблицы базы данных, создаваемый платформой или заданный разработчиком
 */
public interface Index {

  /**
   * Ссылка на таблицу, для которой создан индекс
   *
   * @return ссылка на объект метаданных или его табличную часть
   */
  MdoReference getTable();

  /**
   * Признак кластерности индекса
   *
   * @return истина, если индекс кластерный
   */
  boolean isClustered();

  /**
   * Состав полей индекса в порядке их следования
   *
   * @return упорядоченный список ссылок на поля индекса
   */
  List<MdoReference> getFields();
}
