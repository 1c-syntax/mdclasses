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
package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.mdo.children.ObjectCommand;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdo.children.ObjectTemplate;
import com.github._1c_syntax.bsl.mdo.support.ChoiceDataGetMode;
import com.github._1c_syntax.bsl.mdo.support.ChoiceHistoryOnInputMode;
import com.github._1c_syntax.bsl.mdo.support.DataLockControlMode;
import com.github._1c_syntax.bsl.mdo.support.DefaultFormKind;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.mdo.support.RoleRight;
import com.github._1c_syntax.bsl.mdo.support.SearchStringMode;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.mdo.utils.LazyLoader;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Value
@Builder(toBuilder = true)
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
public class Document implements ReferenceObject, AccessRightsOwner {

  private static final List<RoleRight> POSSIBLE_RIGHTS = computePossibleRights();

  /*
   * ReferenceObject
   */

  @Default
  String uuid = "";
  @Default
  String name = "";
  @Default
  MdoReference mdoReference = MdoReference.EMPTY;
  @Default
  ObjectBelonging objectBelonging = ObjectBelonging.OWN;

  @Singular("addBasedOn")
  List<MdoReference> basedOn;

  @Default
  String comment = "";
  @Default
  MultiLanguageString synonym = MultiLanguageString.EMPTY;
  @Default
  SupportVariant supportVariant = SupportVariant.NONE;

  @Default
  List<Module> modules = Collections.emptyList();
  @Getter(lazy = true)
  List<Module> allModules = LazyLoader.computeAllModules(this);

  @Singular
  List<ObjectCommand> commands;

  @Singular
  List<Attribute> attributes;

  @Singular
  List<TabularSection> tabularSections;

  @Getter(lazy = true)
  List<MD> storageFields = LazyLoader.computeStorageFields(this);
  @Getter(lazy = true)
  List<MD> plainStorageFields = LazyLoader.computePlainStorageFields(this);

  @Singular
  List<ObjectForm> forms;

  @Singular
  List<ObjectTemplate> templates;

  @Getter(lazy = true)
  List<MD> children = LazyLoader.computeChildren(this);
  @Getter(lazy = true)
  List<MD> plainChildren = LazyLoader.computePlainChildren(this);

  /*
   * Свое
   */

  /**
   * Ссылка на форму объекта по умолчанию
   */
  @Default
  MdoReference defaultObjectForm = MdoReference.EMPTY;

  /**
   * Ссылка на форму списка по умолчанию
   */
  @Default
  MdoReference defaultListForm = MdoReference.EMPTY;

  /**
   * Ссылка на форму выбора по умолчанию
   */
  @Default
  MdoReference defaultChoiceForm = MdoReference.EMPTY;

  /**
   * Ссылка на дополнительную форму объекта
   */
  @Default
  MdoReference auxiliaryObjectForm = MdoReference.EMPTY;

  /**
   * Ссылка на дополнительную форму списка
   */
  @Default
  MdoReference auxiliaryListForm = MdoReference.EMPTY;

  /**
   * Ссылка на дополнительную форму выбора
   */
  @Default
  MdoReference auxiliaryChoiceForm = MdoReference.EMPTY;

  /**
   * Возможные формы по умолчанию
   */
  @Getter(lazy = true)
  Map<DefaultFormKind, MdoReference> defaultFormMap = createDefaultFormMap();

  /**
   * Список связанных регистров (где документ является регистратором)
   */
  @Singular("addRegisterRecords")
  List<MdoReference> registerRecords;

  /**
   * Пояснение
   */
  @Default
  MultiLanguageString explanation = MultiLanguageString.EMPTY;

  /**
   * Ввод по строке - список реквизитов для ввода по строке
   */
  @Singular("addInputByString")
  List<MdoReference> inputByString;

  /**
   * Режим полнотекстового поиска при вводе по строке
   */
  @Default
  UseMode fullTextSearchOnInputByString = UseMode.USE;

  /**
   * Режим поиска при вводе по строке
   */
  @Default
  SearchStringMode searchStringModeOnInputByString = SearchStringMode.BEGIN;

  /**
   * Режим получения данных выбора при вводе по строке
   */
  @Default
  ChoiceDataGetMode choiceDataGetModeOnInputByString = ChoiceDataGetMode.DIRECTLY;

  /**
   * Создание при вводе
   */
  @Default
  UseMode createOnInput = UseMode.USE;

  /**
   * Режим истории выбора при вводе по строке
   */
  @Default
  ChoiceHistoryOnInputMode choiceHistoryOnInput = ChoiceHistoryOnInputMode.AUTO;

  /**
   * Поля блокировки данных - список реквизитов для блокировки данных
   */
  @Singular("addDataLockFields")
  List<MdoReference> dataLockFields;

  /**
   * Режим управления блокировкой данных
   */
  @Default
  DataLockControlMode dataLockControlMode = DataLockControlMode.AUTOMATIC;

  /**
   * Возвращает перечень возможных прав доступа
   */
  public static List<RoleRight> possibleRights() {
    return POSSIBLE_RIGHTS;
  }

  private static List<RoleRight> computePossibleRights() {
    return List.of(
      RoleRight.INSERT,
      RoleRight.READ,
      RoleRight.UPDATE,
      RoleRight.DELETE,
      RoleRight.VIEW,
      RoleRight.EDIT,
      RoleRight.INPUT_BY_STRING,
      RoleRight.POSTING,
      RoleRight.UNDO_POSTING,
      RoleRight.INTERACTIVE_DELETE,
      RoleRight.INTERACTIVE_INSERT,
      RoleRight.INTERACTIVE_SET_DELETION_MARK,
      RoleRight.INTERACTIVE_CLEAR_DELETION_MARK,
      RoleRight.INTERACTIVE_DELETE_MARKED,
      RoleRight.INTERACTIVE_POSTING,
      RoleRight.INTERACTIVE_POSTING_REGULAR,
      RoleRight.INTERACTIVE_UNDO_POSTING,
      RoleRight.INTERACTIVE_CHANGE_OF_POSTED,
      RoleRight.READ_DATA_HISTORY,
      RoleRight.VIEW_DATA_HISTORY,
      RoleRight.READ_DATA_HISTORY_OF_MISSING_DATA,
      RoleRight.UPDATE_DATA_HISTORY,
      RoleRight.UPDATE_DATA_HISTORY_OF_MISSING_DATA,
      RoleRight.UPDATE_DATA_HISTORY_SETTINGS,
      RoleRight.UPDATE_DATA_HISTORY_VERSION_COMMENT,
      RoleRight.EDIT_DATA_HISTORY_VERSION_COMMENT,
      RoleRight.SWITCH_TO_DATA_HISTORY_VERSION
    );
  }

  private Map<DefaultFormKind, MdoReference> createDefaultFormMap() {
    return Map.ofEntries(
      Map.entry(DefaultFormKind.OBJECT_FORM, getDefaultObjectForm()),
      Map.entry(DefaultFormKind.LIST_FORM, getDefaultListForm()),
      Map.entry(DefaultFormKind.CHOICE_FORM, getDefaultChoiceForm()),
      Map.entry(DefaultFormKind.AUX_OBJECT_FORM, getAuxiliaryObjectForm()),
      Map.entry(DefaultFormKind.AUX_LIST_FORM, getAuxiliaryListForm()),
      Map.entry(DefaultFormKind.AUX_CHOICE_FORM, getAuxiliaryChoiceForm())
    );
  }
}
