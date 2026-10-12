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
package com.github._1c_syntax.bsl.mdo.support;

import com.github._1c_syntax.bsl.types.EnumWithName;
import com.github._1c_syntax.bsl.types.MultiName;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Locale;
import java.util.Map;

/**
 * Типы форм по умолчанию
 */
@ToString(of = "fullName")
public enum DefaultFormKind implements EnumWithName {
  OBJECT_FORM("ObjectForm", "ФормаОбъекта"),
  LIST_FORM("ListForm", "ФормаСписка"),
  CHOICE_FORM("ChoiceForm", "ФормаВыбора"),
  FOLDER_FORM("FolderForm", "ФормаГруппы"),
  FOLDER_CHOICE_FORM("FolderChoiceForm", "ФормаВыбораГруппы"),
  RECORD_FORM("RecordForm", "ФормаЗаписи"),
  DEFAULT_FORM("Form", "Форма"),
  SETTINGS_FORM("SettingsForm", "ФормаНастройки"),
  VARIANT_FORM("VariantForm", "ФормаВарианта"),
  SAVE_FORM("SaveForm", "ФормаЗаписи"),
  LOAD_FORM("LoadForm", "ФормаОткрытия"),
  CONSTANTS_FORM("ConstantsForm", "ФормаКонстант"),
  REPORT_FORM("ReportForm", "ФормаОтчета"),
  REPORT_VARIANT_FORM("ReportVariantForm", "ФормаВариантаОтчета"),
  REPORT_SETTINGS_FORM("ReportSettingsForm", "ФормаНастроекОтчета"),
  DYNAMIC_LIST_SETTINGS_FORM("DynamicListSettingsForm", "ФормаНастроекДинамическогоСписка"),
  SEARCH_FORM("SearchForm", "ФормаПоиска"),
  DATA_HISTORY_CHANGE_HISTORY_FORM("DataHistoryChangeHistoryForm", "ФормаИсторииИзмененийИсторииДанных"),
  DATA_HISTORY_VERSION_DATA_FORM("DataHistoryVersionDataForm", "ФормаДанныхВерсииИсторииДанных"),
  DATA_HISTORY_VERSION_DIFFERENCES_FORM("DataHistoryVersionDifferencesForm", "ФормаРазличийВерсийИсторииДанных"),
  COLLABORATION_SYSTEM_USERS_CHOICE_FORM("CollaborationSystemUsersChoiceForm", "ФормаВыбораПользователейСистемыВзаимодействия"),
  AUX_OBJECT_FORM("AuxiliaryObjectForm", "ДополнительнаяФормаОбъекта"),
  AUX_FOLDER_FORM("AuxiliaryFolderForm", "ДополнительнаяФормаГруппы"),
  AUX_LIST_FORM("AuxiliaryListForm", "ДополнительнаяФормаСписка"),
  AUX_CHOICE_FORM("AuxiliaryChoiceForm", "ДополнительнаяФормаВыбора"),
  AUX_FOLDER_CHOICE_FORM("AuxiliaryFolderChoiceForm", "ДополнительнаяФормаВыбораГруппы"),
  AUX_RECORD_FORM("AuxiliaryRecordForm", "ДополнительнаяФормаЗаписи"),
  AUX_FORM("AuxiliaryForm", "ДополнительнаяФорма"),
  AUX_SETTINGS_FORM("AuxiliarySettingsForm", "ДополнительнаяФормаНастройки"),
  AUX_VARIANT_FORM("AuxiliaryVariantForm", "ДополнительнаяФормаВарианта"),
  AUX_REPORT_FORM("AuxiliaryReportForm", "ДополнительнаяФормаОтчета"),
  AUX_REPORT_VARIANT_FORM("AuxiliaryReportVariantForm", "ДополнительнаяФормаВариантаОтчета"),
  AUX_REPORT_SETTINGS_FORM("AuxiliaryReportSettingsForm", "ДополнительнаяФормаНастроекОтчета"),
  AUX_DYNAMIC_LIST_SETTINGS_FORM("AuxiliaryDynamicListSettingsForm", "ДополнительнаяФормаНастроекДинамическогоСписка"),
  AUX_SAVE_FORM("AuxiliarySaveForm", "ДополнительнаяФормаЗаписи"),
  AUX_LOAD_FORM("AuxiliaryLoadForm", "ДополнительнаяФормаОткрытия"),
  AUX_DATA_HISTORY_CHANGE_HISTORY_FORM("AuxiliaryDataHistoryChangeHistoryForm", "ДополнительнаяФормаИсторииИзмененийИсторииДанных"),
  AUX_DATA_HISTORY_VERSION_DATA_FORM("AuxiliaryDataHistoryVersionDataForm", "ДополнительнаяФормаДанныхВерсииИсторииДанных"),
  AUX_DATA_HISTORY_VERSION_DIFFERENCES_FORM("AuxiliaryDataHistoryVersionDifferencesForm", "ДополнительнаяФормаРазличийВерсийИсторииДанных"),
  AUX_COLLABORATION_SYSTEM_USERS_CHOICE_FORM("AuxiliaryCollaborationSystemUsersChoiceForm", "ДополнительнаяФормаВыбораПользователейСистемыВзаимодействия");

  private static final Map<String, DefaultFormKind> KEYS = EnumWithName.computeKeys(values());

  @Getter
  @Accessors(fluent = true)
  private final MultiName fullName;

  DefaultFormKind(String nameEn, String nameRu) {
    this.fullName = MultiName.create(nameEn, nameRu);
  }

  public static DefaultFormKind valueByName(String string) {
    return KEYS.getOrDefault(string.toLowerCase(Locale.ROOT), null);
  }
}
