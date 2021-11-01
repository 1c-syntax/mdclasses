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
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Value
@Builder
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
public class ChartOfCalculationTypes implements MDObject, AttributeOwner, FormOwner, CommandOwner, TemplateOwner,
  ModuleOwner, TabularSectionOwner {

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
  MDOType mdoType = MDOType.CHART_OF_CALCULATION_TYPES;

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
   * Список атрибутов
   */
  @Default
  List<Attribute> attributes = Collections.emptyList();

  /**
   * Список форм
   */
  @Default
  List<Form> forms = Collections.emptyList();

  /**
   * Список команд
   */
  @Default
  List<Command> commands = Collections.emptyList();

  /**
   * Список макетов
   */
  @Default
  List<Template> templates = Collections.emptyList();

  /**
   * Список модулей объекта
   */
  @Default
  List<Module> modules = Collections.emptyList();

  /**
   * Список табличных частей
   */
  @Default
  List<TabularSection> tabularSections = Collections.emptyList();

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
   * Форма списка по умолчанию
   */
  @Default
  String defaultListForm = "";

  /**
   * Форма объекта по умолчанию
   */
  @Default
  String defaultObjectForm = "";

  /**
   * Форма выбора по умолчанию
   */
  @Default
  String defaultChoiceForm = "";

  /**
   * Дополнительная форма объекта
   */
  @Default
  String auxiliaryObjectForm = "";

  /**
   * Дополнительная форма списка
   */
  @Default
  String auxiliaryListForm = "";

  /**
   * Дополнительная форма выбора
   */
  @Default
  String auxiliaryChoiceForm = "";

  /**
   * Дополнительная форма выбора
   */
  @Default
  String auxiliaryFolderChoiceForm = "";

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
  String listPresentation = "";

  /**
   * Расширенное представление в списке
   */
  @Default
  String extendedListPresentation = "";

  /**
   * Представление объекта
   */
  @Default
  String objectPresentation = "";

  /**
   * Расширенное представление объекта
   */
  @Default
  String extendedObjectPresentation = "";

  /**
   * Пояснение
   */
  @Default
  String explanation = "";

  // todo описания

  @Default
  int codeLength;
  int descriptionLength;
  @Default
  String codeType = "";
  @Default
  String codeAllowedLength = "";
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
  String onActionPeriod = "";
  @Default
  List<String> baseCalculationTypes = Collections.emptyList();
  boolean actionPeriodUse;

  @Override
  public void addCommonAttribute(@NonNull CommonAttribute commonAttribute) {
    requireNonNull(attributes);
    attributes.add(commonAttribute);
  }
}
