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
import lombok.Singular;
import lombok.Value;

import java.util.List;
import java.util.stream.Stream;

/**
 * Индекс таблицы базы данных, создаваемый платформой автоматически
 */
@Value
@Builder(toBuilder = true)
public class PlatformIndex implements Index {

  /**
   * Ссылка на таблицу, для которой создан индекс
   */
  MdoReference table;

  /**
   * Признак кластерности индекса
   */
  boolean clustered;

  /**
   * Состав полей индекса в порядке их следования
   */
  @Singular("indexedField")
  List<MdoReference> fields;

  /**
   * Создает некластерный индекс таблицы
   *
   * @param table      Ссылка на таблицу
   * @param separators Префикс полей разделителей
   * @param fields     Состав полей индекса
   * @return Индекс таблицы
   */
  public static PlatformIndex index(MdoReference table, List<MdoReference> separators, MdoReference... fields) {
    return createIndex(table, false, separators, fields);
  }

  /**
   * Создает некластерный индекс таблицы
   *
   * @param table      Ссылка на таблицу
   * @param separators Префикс полей разделителей
   * @param fields     Состав полей индекса
   * @return Индекс таблицы
   */
  public static PlatformIndex index(MdoReference table, List<MdoReference> separators, List<MdoReference> fields) {
    return createIndex(table, false, separators, fields);
  }

  /**
   * Создает кластерный индекс таблицы
   *
   * @param table      Ссылка на таблицу
   * @param separators Префикс полей разделителей
   * @param fields     Состав полей индекса
   * @return Индекс таблицы
   */
  public static PlatformIndex clusteredIndex(MdoReference table, List<MdoReference> separators,
                                             MdoReference... fields) {
    return createIndex(table, true, separators, fields);
  }

  /**
   * Создает кластерный индекс таблицы
   *
   * @param table      Ссылка на таблицу
   * @param separators Префикс полей разделителей
   * @param fields     Состав полей индекса
   * @return Индекс таблицы
   */
  public static PlatformIndex clusteredIndex(MdoReference table, List<MdoReference> separators,
                                             List<MdoReference> fields) {
    return createIndex(table, true, separators, fields);
  }

  /**
   * Создает индекс таблицы с явным признаком кластерности
   *
   * @param table      Ссылка на таблицу
   * @param clustered  Признак кластерности индекса
   * @param separators Префикс полей разделителей
   * @param fields     Состав полей индекса
   * @return Индекс таблицы
   */
  public static PlatformIndex createIndex(MdoReference table, boolean clustered,
                                          List<MdoReference> separators, MdoReference... fields) {
    return createIndex(table, clustered, separators, List.of(fields));
  }

  /**
   * Создает индекс таблицы с явным признаком кластерности
   *
   * @param table      Ссылка на таблицу
   * @param clustered  Признак кластерности индекса
   * @param separators Префикс полей разделителей
   * @param fields     Состав полей индекса
   * @return Индекс таблицы
   */
  public static PlatformIndex createIndex(MdoReference table, boolean clustered,
                                          List<MdoReference> separators, List<MdoReference> fields) {
    var builder = PlatformIndex.builder().table(table).clustered(clustered);
    Stream.concat(separators.stream(), fields.stream()).forEach(builder::indexedField);
    return builder.build();
  }
}
