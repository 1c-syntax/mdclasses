/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2024
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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Возможные права роли
 */
@AllArgsConstructor
@Getter
public enum RoleRight implements EnumWithValue {
  READ("Read", "Чтение"),
  INSERT("Insert", "Добавление"),
  UPDATE("Update", "Изменение"),
  DELETE("Delete", "Удаление"),
  POSTING("Posting", "Проведение"),
  UNDO_POSTING("UndoPosting", "ОтменаПроведения"),
  VIEW("View", "Просмотр"),
  INTERACTIVE_INSERT("InteractiveInsert", "ИнтерактивноеДобавление"),
  EDIT("Edit", "Редактирование"),
  INTERACTIVE_DELETE("InteractiveDelete", "ИнтерактивноеУдаление"),
  INTERACTIVE_SET_DELETION_MARK("InteractiveSetDeletionMark", "ИнтерактивнаяПометкаУдаления"),
  INTERACTIVE_CLEAR_DELETION_MARK("InteractiveClearDeletionMark", "ИнтерактивноеСнятиеПометкиУдаления"),
  INTERACTIVE_DELETE_MARKED("InteractiveDeleteMarked", "ИнтерактивноеУдалениеПомеченных"),
  INTERACTIVE_POSTING("InteractivePosting", "ИнтерактивноеПроведение"),
  INTERACTIVE_POSTING_REGULAR("InteractivePostingRegular", "ИнтерактивноеПроведениеНеОперативное"),
  INTERACTIVE_UNDO_POSTING("InteractiveUndoPosting", "ИнтерактивнаяОтменаПроведения"),
  INTERACTIVE_CHANGE_OF_POSTED("InteractiveChangeOfPosted", "ИнтерактивноеИзменениеПроведенных"),
  INPUT_BY_STRING("InputByString", "ВводПоСтроке"),
  TOTALS_CONTROL("TotalsControl", "УправлениеИтогами"),
  USE("Use", "Использование"),
  ADMINISTRATION("Administration", "Администрирование"),
  DATA_ADMINISTRATION("DataAdministration", "АдминистрированиеДанных"),
  EXCLUSIVE_MODE("ExclusiveMode", "МонопольныйРежим"),
  ACTIVE_USERS("ActiveUsers", "АктивныеПользователи"),
  EVENT_LOG("EventLog", "ЖурналРегистрации"),
  EXTERNAL_CONNECTION("ExternalConnection", "ВнешнееСоединение"),
  AUTOMATION("Automation", "Automation"),
  INTERACTIVE_OPEN_EXT_DATA_PROCESSORS("InteractiveOpenExtDataProcessors", "ИнтерактивноеОткрытиеВнешнихОбработок"),
  INTERACTIVE_OPEN_EXT_REPORTS("InteractiveOpenExtReports", "ИнтерактивноеОткрытиеВнешнихОтчетов"),
  GET("Get", "Получение"),
  SET("Set", "Установка"),
  INTERACTIVE_ACTIVATE("InteractiveActivate", "ИнтерактивнаяАктивация"),
  START("Start", "Старт"),
  INTERACTIVE_START("InteractiveStart", "ИнтерактивныйСтарт"),
  EXECUTE("Execute", "Выполнение"),
  INTERACTIVE_EXECUTE("InteractiveExecute", "ИнтерактивноеВыполнение"),
  OUTPUT("Output", "Вывод"),
  UPDATE_DATA_BASE_CONFIGURATION("UpdateDataBaseConfiguration", "ОбновлениеКонфигурацииБазыДанных"),
  THIN_CLIENT("ThinClient", "ТонкийКлиент"),
  WEB_CLIENT("WebClient", "ВебКлиент"),
  THICK_CLIENT("ThickClient", "ТолстыйКлиент"),
  ALL_FUNCTIONS_MODE("AllFunctionsMode", "РежимВсеФункции"),
  SAVE_USER_DATA("SaveUserData", "СохранениеДанныхПользователя"),
  STANDARD_AUTHENTICATION_CHANGE("StandardAuthenticationChange", "ИзменениеСтандартнойАутентификации"),
  SESSION_STANDARD_AUTHENTICATION_CHANGE("SessionStandardAuthenticationChange",
    "ИзменениеСтандартнойАутентификацииСеанса"),
  SESSION_OS_AUTHENTICATION_CHANGE("SessionOSAuthenticationChange", "ИзменениеАутентификацииОССеанса"),
  INTERACTIVE_DELETE_PREDEFINED_DATA("InteractiveDeletePredefinedData",
    "ИнтерактивноеУдалениеПредопределенныхДанных"),
  INTERACTIVE_SET_DELETION_MARK_PREDEFINED_DATA("InteractiveSetDeletionMarkPredefinedData",
    "ИнтерактивнаяПометкаУдаленияПредопределенныхДанных"),
  INTERACTIVE_CLEAR_DELETION_MARK_PREDEFINED_DATA("InteractiveClearDeletionMarkPredefinedData",
    "ИнтерактивноеСнятиеПометкиУдаленияПредопределенныхДанных"),
  INTERACTIVE_DELETE_MARKED_PREDEFINED_DATA("InteractiveDeleteMarkedPredefinedData",
    "ИнтерактивноеУдалениеПомеченныхПредопределенныхДанных"),
  CONFIGURATION_EXTENSIONS_ADMINISTRATION("ConfigurationExtensionsAdministration",
    "АдминистрированиеРасширенийКонфигурации"),
  READ_DATA_HISTORY("ReadDataHistory", "ЧтениеИсторииДанных"),
  VIEW_DATA_HISTORY("ViewDataHistory", "ПросмотрИсторииДанных"),
  READ_DATA_HISTORY_OF_MISSING_DATA("ReadDataHistoryOfMissingData",
    "ЧтениеИсторииДанныхОтсутстсвующихДанных"),
  UPDATE_DATA_HISTORY("UpdateDataHistory", "ИзменениеИсторииДанных"),
  UPDATE_DATA_HISTORY_OF_MISSING_DATA("UpdateDataHistoryOfMissingData",
    "ИзменениеИсторииДанныхОтсутствующихДанных"),
  UPDATE_DATA_HISTORY_SETTINGS("UpdateDataHistorySettings", "ИзменениеНастроекИсторииДанных"),
  UPDATE_DATA_HISTORY_VERSION_COMMENT("UpdateDataHistoryVersionComment",
    "ИзменениеКомментарияВерсииИсторииДанных"),
  EDIT_DATA_HISTORY_VERSION_COMMENT("EditDataHistoryVersionComment",
    "РедактированиеКомментарияВерсииИсторииДанных"),
  SWITCH_TO_DATA_HISTORY_VERSION("SwitchToDataHistoryVersion", "ПереходНаВерсиюИсторииДанных"),
  COLLABORATION_SYSTEM_INFO_BASE_REGISTRATION("CollaborationSystemInfoBaseRegistration",
    "РегистрацияИнформационнойБазыСистемыВзаимодействия"),
  MOBILE_CLIENT("MobileClient", "МобильныйКлиент"),
  MAIN_WINDOW_MODE_NORMAL("MainWindowModeNormal", "РежимОсновногоОкнаОбычный"),
  MAIN_WINDOW_MODE_WORKPLACE("MainWindowModeWorkplace", "РежимОсновногоОкнаРабочееМесто"),
  MAIN_WINDOW_MODE_EMBEDDED_WORKPLACE("MainWindowModeEmbeddedWorkplace",
    "РежимОсновногоОкнаВстроенноеРабочееМесто"),
  MAIN_WINDOW_MODE_FULLSCREEN_WORKPLACE("MainWindowModeFullscreenWorkplace",
    "РежимОсновногоОкнаПолноэкранноеРабочееМесто"),
  MAIN_WINDOW_MODE_KIOSK("MainWindowModeKiosk", "РежимОсновногоОкнаКиоск"),
  TECHNICAL_SPECIALIST_MODE("TechnicalSpecialistMode", "РежимТехническогоСпециалиста"),
  EXCLUSIVE_MODE_TERMINATION_AT_SESSION_START("ExclusiveModeTerminationAtSessionStart",
    "ЗавершениеМонопольногоРежимаПриНачалеСеанса"),
  ANALYTICS_SYSTEM_CLIENT("AnalyticsSystemClient", "КлиентСистемыАналитики"),
  REMOTE_DESKTOP_HOST("RemoteDesktopHost",
    "ПредоставлениеУдаленногоУправленияРабочимСтоломДругомуПользователю"),
  REMOTE_DESKTOP_CLIENT("RemoteDesktopClient",
    "УдаленноеУправлениеРабочимСтоломДругогоПользователя"),
  UNKNOWN("unknown", "unknown") {
    @Override
    public boolean isUnknown() {
      return true;
    }
  };

  private static final Map<String, RoleRight> keys = computeKeys();

  /**
   * Английское имя
   */
  @Accessors(fluent = true)
  private final String value;

  /**
   * Русское имя
   */
  @Accessors(fluent = true)
  private final String valueRu;

  /**
   * Ищет элемент перечисления по имени (рус, анг)
   *
   * @param string Имя искомого элемента
   * @return Найденное значение, если не найден - то unknown
   */
  public static RoleRight valueByString(String string) {
    return keys.getOrDefault(string, UNKNOWN);
  }

  private static Map<String, RoleRight> computeKeys() {
    Map<String, RoleRight> keysMap = new ConcurrentSkipListMap<>(String.CASE_INSENSITIVE_ORDER);
    for (var element : values()) {
      if (element.isUnknown()) {
        continue;
      }
      keysMap.put(element.value(), element);
      keysMap.put(element.valueRu(), element);
    }
    return keysMap;
  }
}
