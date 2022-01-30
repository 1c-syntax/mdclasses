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
package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.mdo.support.ReturnValueReuse;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ModuleType;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.net.URI;
import java.util.List;

@Value
@Builder
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
public class CommonModule implements MDObject, Module, ModuleOwner {

  /**
   * MDObject
   */

  /**
   * Тип метаданных
   */
  static final MDOType mdoType = MDOType.COMMON_MODULE;

  /**
   * Уникальный идентификатор
   */
  String uuid;

  /**
   * Имя
   */
  String name;

  /**
   * Синонимы объекта
   */
  @Default
  MultiLanguageString synonym = MultiLanguageString.EMPTY;

  /**
   * MDO-Ссылка на объект
   */
  @Default
  MdoReference mdoReference = MdoReference.EMPTY;

  /**
   * Принадлежность объекта конфигурации (собственный или заимствованный)
   */
  @Default
  ObjectBelonging objectBelonging = ObjectBelonging.OWN;

  /**
   * Вариант поддержки родительской конфигурации
   */
  @Default
  SupportVariant supportVariant = SupportVariant.NONE;

  /**
   * Комментарий
   */
  @Default
  String comment = "";

  /**
   * Module
   */

  /**
   * Тип модуля
   */
  @Default
  ModuleType moduleType = ModuleType.CommonModule;

  /**
   * Ссылка на файл bsl модуля
   */
  @Default
  URI uri = URI.create("");

  /**
   * Custom
   */

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
  @Default
  ReturnValueReuse returnValuesReuse = ReturnValueReuse.DONT_USE;

  /**
   * MDObject
   */

  @Override
  public MDOType getMdoType() {
    return mdoType;
  }

  /**
   * ModuleOwner
   */

  /**
   * Общий модуль сам является модулем
   *
   * @return Список модулей
   */
  @Override
  public List<Module> getModules() {
    return List.of(this);
  }
}
