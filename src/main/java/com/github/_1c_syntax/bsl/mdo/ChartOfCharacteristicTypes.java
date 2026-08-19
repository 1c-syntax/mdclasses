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
import com.github._1c_syntax.bsl.mdo.children.PredefinedValue;
import com.github._1c_syntax.bsl.mdo.storage.AdditionalIndex;
import com.github._1c_syntax.bsl.mdo.storage.Characteristic;
import com.github._1c_syntax.bsl.mdo.support.ChoiceDataGetMode;
import com.github._1c_syntax.bsl.mdo.support.ChoiceHistoryOnInputMode;
import com.github._1c_syntax.bsl.mdo.support.CodeSeries;
import com.github._1c_syntax.bsl.mdo.support.DataLockControlMode;
import com.github._1c_syntax.bsl.mdo.support.SearchStringMode;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.mdo.support.DefaultFormKind;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.mdo.support.RoleRight;
import com.github._1c_syntax.bsl.mdo.utils.LazyLoader;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import com.github._1c_syntax.bsl.types.ValueTypeDescription;
import lombok.AccessLevel;
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
public class ChartOfCharacteristicTypes
  implements MutableReferenceObject, ValueTypeOwner, PredefinedDataOwner {

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
   * ValueTypeOwner
   */

  @Default
  @Getter(AccessLevel.NONE)
  ValueTypeDescription type = ValueTypeDescription.EMPTY;

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
   * Ссылка на форму группы по умолчанию
   */
  @Default
  MdoReference defaultFolderForm = MdoReference.EMPTY;

  /**
   * Ссылка на форму выбора группы по умолчанию
   */
  @Default
  MdoReference defaultFolderChoiceForm = MdoReference.EMPTY;

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
   * Ссылка на дополнительную форму группы
   */
  @Default
  MdoReference auxiliaryFolderForm = MdoReference.EMPTY;

  /**
   * Ссылка на дополнительную форму выбора группы
   */
  @Default
  MdoReference auxiliaryFolderChoiceForm = MdoReference.EMPTY;

  /**
   * Возможные формы по умолчанию
   */
  @Getter(lazy = true)
  Map<DefaultFormKind, MdoReference> defaultFormMap = createDefaultFormMap();

  /**
   * Предопределенные значения
   */
  @Singular
  List<PredefinedValue> predefinedValues;

  /**
   * Дополнительные индексы
   */
  @Singular("addAdditionalIndex")
  List<AdditionalIndex> additionalIndexes;

  /**
   * Пояснение
   */
  @Default
  MultiLanguageString explanation = MultiLanguageString.EMPTY;

  /**
   * Режим полнотекстового поиска
   */
  @Default
  UseMode fullTextSearch = UseMode.USE;

  /**
   * Определяет, является ли план видов характеристик иерархическим.
   */
  @Default
  boolean hierarchical = false;

  /**
   * Группы сверху.
   * Определяет расположение групп в списке.
   */
  @Default
  boolean foldersOnTop = false;

  /**
   * Проверять уникальность кода плана видов характеристик.
   * Определяет, нужно ли проверять уникальность кода плана видов характеристик.
   * Если значение равно true, то код плана видов характеристик должен быть уникальным в пределах области,
   * определяемой свойством {@link #codeSeries}. Если false, проверка уникальности не выполняется.
   */
  @Default
  boolean checkUnique = false;

  /**
   * Серия кодов плана видов характеристик.
   * Определяет область действия уникальности кода плана видов характеристик.
   * Значение по умолчанию: {@link CodeSeries#WHOLE_CATALOG}.
   * Для формата EDT: если поле отсутствует, автоматически устанавливается значение WHOLE_CATALOG.
   */
  @Default
  CodeSeries codeSeries = CodeSeries.WHOLE_CATALOG;

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
   * Характеристики объекта
   */
  @Singular("addCharacteristics")
  List<Characteristic> characteristics;

  /**
   * Расширенные значения характеристик
   */
  @Default
  MdoReference characteristicExtValues = MdoReference.EMPTY;

  /**
   * Возвращает перечень возможных прав доступа
   */
  public static List<RoleRight> possibleRights() {
    return Catalog.possibleRights();
  }

  @Override
  public ValueTypeDescription getValueType() {
    return type;
  }

  private Map<DefaultFormKind, MdoReference> createDefaultFormMap() {
    return Map.ofEntries(
      Map.entry(DefaultFormKind.OBJECT_FORM, getDefaultObjectForm()),
      Map.entry(DefaultFormKind.LIST_FORM, getDefaultListForm()),
      Map.entry(DefaultFormKind.CHOICE_FORM, getDefaultChoiceForm()),
      Map.entry(DefaultFormKind.FOLDER_FORM, getDefaultFolderForm()),
      Map.entry(DefaultFormKind.FOLDER_CHOICE_FORM, getDefaultFolderChoiceForm()),
      Map.entry(DefaultFormKind.AUX_OBJECT_FORM, getAuxiliaryObjectForm()),
      Map.entry(DefaultFormKind.AUX_LIST_FORM, getAuxiliaryListForm()),
      Map.entry(DefaultFormKind.AUX_CHOICE_FORM, getAuxiliaryChoiceForm()),
      Map.entry(DefaultFormKind.AUX_FOLDER_FORM, getAuxiliaryFolderForm()),
      Map.entry(DefaultFormKind.AUX_FOLDER_CHOICE_FORM, getAuxiliaryFolderChoiceForm())
    );
  }
}
