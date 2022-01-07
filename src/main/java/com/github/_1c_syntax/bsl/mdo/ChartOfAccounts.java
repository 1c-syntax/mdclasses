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

import com.github._1c_syntax.bsl.mdo.support.DataLockControlMode;
import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
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
public class ChartOfAccounts implements MDObject, AttributeOwner, FormOwner, CommandOwner, TemplateOwner,
  ModuleOwner, TabularSectionOwner {

  /**
   * MDObject
   */

  /**
   * Тип метаданных
   */
  static final MDOType mdoType = MDOType.CHART_OF_ACCOUNTS;

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
   * ChildrenOwner
   */

  @Default
  List<MDObject> children = Collections.emptyList();

  /**
   * ModuleOwner
   */

  /**
   * Список модулей объекта
   */
  @Default
  List<Module> modules = Collections.emptyList();

  /**
   * Custom
   */

  /**
   * Использование стандартных команд интерфейса
   */
  boolean useStandardCommands;

  /**
   * Включать в описании справки
   */
  boolean includeHelpInContents;

  /**
   * Форма списка по умолчанию
   */
  @Default
  MdoReference defaultListForm = MdoReference.EMPTY;

  /**
   * Форма объекта по умолчанию
   */
  @Default
  MdoReference defaultObjectForm = MdoReference.EMPTY;

  /**
   * Форма выбора по умолчанию
   */
  @Default
  MdoReference defaultChoiceForm = MdoReference.EMPTY;

  /**
   * Дополнительная форма объекта
   */
  @Default
  MdoReference auxiliaryObjectForm = MdoReference.EMPTY;

  /**
   * Дополнительная форма списка
   */
  @Default
  MdoReference auxiliaryListForm = MdoReference.EMPTY;

  /**
   * Дополнительная форма выбора
   */
  @Default
  MdoReference auxiliaryChoiceForm = MdoReference.EMPTY;

  /**
   * Режим редактирования
   */
  @Default
  String editType = "";

  /**
   * Ввод по строке
   */
  @Default
  List<String> inputByString = Collections.emptyList();

  /**
   * Создание при вводе
   */
  @Default
  String createOnInput = "";

  /**
   * Режим блокировки данных
   */
  @Default
  DataLockControlMode dataLockControlMode = DataLockControlMode.AUTOMATIC;

  /**
   * Использовать полнотекстовый поиск
   */
  @Default
  UseMode fullTextSearch = UseMode.DONT_USE;

  /**
   * Представление в списке
   */
  @Default
  MultiLanguageString listPresentation = MultiLanguageString.EMPTY;

  /**
   * Расширенное представление в списке
   */
  @Default
  MultiLanguageString extendedListPresentation = MultiLanguageString.EMPTY;

  /**
   * Представление объекта
   */
  @Default
  MultiLanguageString objectPresentation = MultiLanguageString.EMPTY;

  /**
   * Расширенное представление объекта
   */
  @Default
  MultiLanguageString extendedObjectPresentation = MultiLanguageString.EMPTY;

  /**
   * Пояснение
   */
  @Default
  MultiLanguageString explanation = MultiLanguageString.EMPTY;

  // todo описания

  int codeLength;

  int descriptionLength;

  @Default
  String codeSeries = "";

  boolean checkUnique;

  boolean autonumbering;

  @Default
  String defaultPresentation = "";

  @Default
  List<String> characteristics = Collections.emptyList();

  @Default
  String predefinedDataUpdate = "";

  boolean quickChoice;

  @Default
  String choiceMode = "";

  @Default
  String searchStringModeOnInputByString = "";

  @Default
  String fullTextSearchOnInputByString = "";

  @Default
  String choiceDataGetModeOnInputByString = "";

  @Default

  List<String> basedOn = Collections.emptyList();

  @Default
  List<String> dataLockFields = Collections.emptyList();

  @Default
  String choiceHistoryOnInput = "";

  @Default
  List<String> extDimensionTypes = Collections.emptyList();

  int maxExtDimensionCount;

  @Default
  String codeMask = "";

  boolean autoOrderByCode;

  int orderLength;

  /**
   * MDObject
   */

  @Override
  public MDOType getMdoType() {
    return mdoType;
  }
}
