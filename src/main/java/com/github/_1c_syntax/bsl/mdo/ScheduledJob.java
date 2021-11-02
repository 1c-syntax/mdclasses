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

import com.github._1c_syntax.bsl.mdo.support.Handler;
import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MDOType;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
public class ScheduledJob implements MDObject {

  /**
   * Имя
   */
  String name;

  /**
   * Уникальный идентификатор
   */
  String uuid;

  /**
   * Комментарий
   */
  @Default
  String comment = "";

  /**
   * Принадлежность объекта конфигурации (собственный или заимствованный)
   */
  @Default
  ObjectBelonging objectBelonging = ObjectBelonging.OWN;

  /**
   * Тип метаданных
   */
  @Default
  MDOType mdoType = MDOType.SCHEDULED_JOB;

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
   * Вариант поддержки родительской конфигурации
   */
  @Default
  SupportVariant supportVariant = SupportVariant.NONE;

  /**
   * Полное имя метода, включающее имя общего модуля
   */
  Handler handler; // todo fake handler

  @Default
  String description = "";
  @Default
  String key = "";
  boolean use;
  boolean predefined;
  int restartCountOnFailure;
=======
  Handler handler = Handler.EMPTY;

  /**
   * Представление регламентного задания
   */
  @Default
  String description = "";

  /**
   * Ключ уникальности
   */
  @Default
  String key = "";

  /**
   * Использование по умолчанию
   */
  boolean use;

  /**
   * Предопределенный
   */
  boolean predefined;

  /**
   * Количество попыток перезапуска при ошибке
   */
  int restartCountOnFailure;

  /**
   * Интервал перезапуска при ошибке
   */
>>>>>>> 5ae33c50 (Первая порция изменений)
  int restartIntervalOnFailure;
}
