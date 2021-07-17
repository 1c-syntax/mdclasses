/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2021
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

import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.mdo.support.ReturnValueReuse;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ModuleType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.net.URI;

@Value
@Builder
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
public class CommonModule implements MDObject, Module {

  /**
   * Имя
   */
  String name;

  /**
   * Уникальный идентификатор
   */
  String uuid;

  /**
   * Принадлежность объекта конфигурации (собственный или заимствованный)
   */
  ObjectBelonging objectBelonging;

  /**
   * Тип метаданных
   */
  MDOType type;

  /**
   * Имя метаданных объекта
   */
  String metadataName;

  /**
   * Имя метаданных объекта на русском языке
   */
  String metadataNameRu;

  /**
   * Синонимы объекта
   */
  MultiLanguageString synonyms;

  /**
   * MDO-Ссылка на объект
   */
  MdoReference mdoReference;

  /**
   * Тип модуля
   */
  ModuleType moduleType;

  /**
   * Ссылка на файл bsl модуля
   * todo может все таки path?
   */
  URI uri;

  /**
   * Признак Сервер
   */
  boolean server;

  /**
   * Признак Глобальный
   */
  boolean global;

  /**
   * Признак Клиент-управляемое приложение
   */
  boolean clientManagedApplication;

  /**
   * Признак Внешнее соединение
   */
  boolean externalConnection;

  /**
   * Признак Клиент-обычное приложение
   */
  boolean clientOrdinaryApplication;

  /**
   * Признак Вызов сервера
   */
  boolean serverCall;

  /**
   * Признак Привилегированный режим
   */
  boolean privileged;

  /**
   * Режим повторного использования значений
   */
  ReturnValueReuse returnValuesReuse;
}
