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
public class InformationRegister implements MDObject, AttributeOwner, FormOwner, CommandOwner, TemplateOwner,
  ModuleOwner {

  /**
   * MDObject
   */

  /**
   * Тип метаданных
   */
  static final MDOType mdoType = MDOType.INFORMATION_REGISTER;

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
   * AttributeOwner
   */

  /**
   * Список атрибутов
   */
  @Default
  List<Attribute> attributes = Collections.emptyList();

  /**
   * FormOwner
   */

  /**
   * Список форм
   */
  @Default
  List<Form> forms = Collections.emptyList();

  /**
   * CommandOwner
   */

  /**
   * Список команд
   */
  @Default
  List<Command> commands = Collections.emptyList();

  /**
   * TemplateOwner
   */

  /**
   * Список макетов
   */
  @Default
  List<Template> templates = Collections.emptyList();

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
   * Форма записи по умолчанию
   */
  @Default
  MdoReference defaultRecordForm = MdoReference.EMPTY;

  /**
   * Дополнительная форма записи
   */
  @Default
  MdoReference auxiliaryRecordForm = MdoReference.EMPTY;

  /**
   * Дополнительная форма списка
   */
  @Default
  MdoReference auxiliaryListForm = MdoReference.EMPTY;

  /**
   * Режим редактирования
   */
  @Default
  String editType = "";

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
   * Представление записи
   */
  @Default
  MultiLanguageString recordPresentation = MultiLanguageString.EMPTY;

  /**
   * Расширенное представление записи
   */
  @Default
  MultiLanguageString extendedRecordPresentation = MultiLanguageString.EMPTY;

  /**
   * Пояснение
   */
  @Default
  MultiLanguageString explanation = MultiLanguageString.EMPTY;

  @Default
  String dataHistory = "";

  boolean updateDataHistoryImmediatelyAfterWrite;

  boolean executeAfterWriteDataHistoryVersionProcessing;

  @Default
  String informationRegisterPeriodicity = "";

  @Default
  String writeMode = "";

  /**
   * Основной отбор по периоду
   */
  boolean mainFilterOnPeriod;

  /**
   * Использовать итоги срез первых
   */
  boolean enableTotalsSliceFirst;

  /**
   * Использовать итоги срез последних
   */
  boolean enableTotalsSliceLast;

  /**
   * MDObject
   */

  @Override
  public MDOType getMdoType() {
    return mdoType;
  }
}
