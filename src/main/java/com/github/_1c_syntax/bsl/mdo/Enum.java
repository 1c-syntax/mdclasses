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

import com.github._1c_syntax.bsl.mdo.children.EnumValue;
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
public class Enum implements MDObject, FormOwner, CommandOwner, TemplateOwner,
  ModuleOwner {

  /**
   * MDObject
   */

  /**
   * Тип метаданных
   */
  static final MDOType mdoType = MDOType.ENUM;

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
   * Форма списка по умолчанию
   */
  @Default
  MdoReference defaultListForm = MdoReference.EMPTY;

  /**
   * Форма выбора по умолчанию
   */
  @Default
  MdoReference defaultChoiceForm = MdoReference.EMPTY;

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
   * Пояснение
   */
  @Default
  MultiLanguageString explanation = MultiLanguageString.EMPTY;

  @Default
  List<String> characteristics = Collections.emptyList();

  boolean quickChoice;

  @Default
  String choiceMode = "";

  @Default
  String choiceHistoryOnInput = "";

  /**
   * Элементы перечисления
   */
  @Default
  List<EnumValue> children = Collections.emptyList();

  /**
   * MDObject
   */

  @Override
  public MDOType getMdoType() {
    return mdoType;
  }

  /**
   * FormOwner
   */

  @Override
  public List<MDObject> getChildren() {
    var superChildren = FormOwner.super.getChildren();
    superChildren.addAll(children);
    return superChildren;
  }
}
