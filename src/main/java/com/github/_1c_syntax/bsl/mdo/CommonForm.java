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

import com.github._1c_syntax.bsl.mdo.support.ApplicationUsePurpose;
import com.github._1c_syntax.bsl.mdo.support.FormType;
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

import java.util.Collections;
import java.util.List;

@Value
@Builder
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
public class CommonForm implements Form {

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
  MDOType mdoType = MDOType.COMMON_FORM;

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
   * Тип формы
   */
  @Default
  FormType formType = FormType.MANAGED;

  /**
   * Список модулей объекта
   */
  @Default
  List<Module> modules = Collections.emptyList();

  /**
   * Вариант поддержки родительской конфигурации
   */
  @Default
  SupportVariant supportVariant = SupportVariant.NONE;

  /**
   * Использование стандартных команд интерфейса
   */
  boolean useStandardCommands;

  /**
   * Включать в описании справки
   */
  boolean includeHelpInContents;

  /**
   * Вариант применения
   */
  @Default
  List<ApplicationUsePurpose> usePurposes = Collections.emptyList();

  /**
   * Расширенное представление
   */
  @Default
  String extendedPresentation = "";

  /**
   * Пояснение
   */
  @Default
  String explanation = "";
}

// todo реализовать
//  - хранение информации о данных формы
//  /**
//   * Данные формы:
//   * + список элементов формы
//   * + список обработчиков формы
//   * + список реквизитов формы
//   */
//  FormData data;