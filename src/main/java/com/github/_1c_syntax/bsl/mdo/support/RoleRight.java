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
 * Возможные права роли
 */
@ToString(of = "fullName")
public enum RoleRight implements EnumWithName {
  ACTIVE_USERS("ActiveUsers", "АктивныеПользователи"),
  ADMINISTRATION("Administration", "Администрирование"),
  ALL_FUNCTIONS_MODE("AllFunctionsMode", "РежимВсеФункции"),
  ANALYTICS_SYSTEM_CLIENT("AnalyticsSystemClient", "КлиентСистемыАналитики"),
  AUTOMATION("Automation", "Automation"),
  COLLABORATION_SYSTEM_INFO_BASE_REGISTRATION(
    "CollaborationSystemInfoBaseRegistration", "РегистрацияИнформационнойБазыСистемыВзаимодействия"),
  CONFIGURATION_EXTENSIONS_ADMINISTRATION(
    "ConfigurationExtensionsAdministration", "АдминистрированиеРасширенийКонфигурации"),
  DATA_ADMINISTRATION("DataAdministration", "АдминистрированиеДанных"),
  DELETE("Delete", "Удаление"),
  EDIT("Edit", "Редактирование"),
  EDIT_DATA_HISTORY_VERSION_COMMENT(
    "EditDataHistoryVersionComment", "РедактированиеКомментарияВерсииИсторииДанных"),
  EVENT_LOG("EventLog", "ЖурналРегистрации"),
  EXCLUSIVE_MODE("ExclusiveMode", "МонопольныйРежим"),
  EXCLUSIVE_MODE_TERMINATION_AT_SESSION_START(
    "ExclusiveModeTerminationAtSessionStart", "ЗавершениеМонопольногоРежимаПриНачалеСеанса"),
  EXECUTE("Execute", "Выполнение"),
  EXTERNAL_CONNECTION("ExternalConnection", "ВнешнееСоединение"),
  GET("Get", "Получение"),
  INPUT_BY_STRING("InputByString", "ВводПоСтроке"),
  INSERT("Insert", "Добавление"),
  INTERACTIVE_ACTIVATE("InteractiveActivate", "ИнтерактивнаяАктивация"),
  INTERACTIVE_CHANGE_OF_POSTED("InteractiveChangeOfPosted", "ИнтерактивноеИзменениеПроведенных"),
  INTERACTIVE_CLEAR_DELETION_MARK("InteractiveClearDeletionMark", "ИнтерактивноеСнятиеПометкиУдаления"),
  INTERACTIVE_CLEAR_DELETION_MARK_PREDEFINED_DATA(
    "InteractiveClearDeletionMarkPredefinedData", "ИнтерактивноеСнятиеПометкиУдаленияПредопределенныхДанных"),
  INTERACTIVE_DELETE("InteractiveDelete", "ИнтерактивноеУдаление"),
  INTERACTIVE_DELETE_MARKED("InteractiveDeleteMarked", "ИнтерактивноеУдалениеПомеченных"),
  INTERACTIVE_DELETE_MARKED_PREDEFINED_DATA(
    "InteractiveDeleteMarkedPredefinedData", "ИнтерактивноеУдалениеПомеченныхПредопределенныхДанных"),
  INTERACTIVE_DELETE_PREDEFINED_DATA("InteractiveDeletePredefinedData", "ИнтерактивноеУдалениеПредопределенныхДанных"),
  INTERACTIVE_EXECUTE("InteractiveExecute", "ИнтерактивноеВыполнение"),
  INTERACTIVE_INSERT("InteractiveInsert", "ИнтерактивноеДобавление"),
  INTERACTIVE_OPEN_EXT_DATA_PROCESSORS("InteractiveOpenExtDataProcessors", "ИнтерактивноеОткрытиеВнешнихОбработок"),
  INTERACTIVE_OPEN_EXT_REPORTS("InteractiveOpenExtReports", "ИнтерактивноеОткрытиеВнешнихОтчетов"),
  INTERACTIVE_POSTING("InteractivePosting", "ИнтерактивноеПроведение"),
  INTERACTIVE_POSTING_REGULAR("InteractivePostingRegular", "ИнтерактивноеПроведениеНеОперативное"),
  INTERACTIVE_SET_DELETION_MARK("InteractiveSetDeletionMark", "ИнтерактивнаяПометкаУдаления"),
  INTERACTIVE_SET_DELETION_MARK_PREDEFINED_DATA(
    "InteractiveSetDeletionMarkPredefinedData", "ИнтерактивнаяПометкаУдаленияПредопределенныхДанных"),
  INTERACTIVE_START("InteractiveStart", "ИнтерактивныйСтарт"),
  INTERACTIVE_UNDO_POSTING("InteractiveUndoPosting", "ИнтерактивнаяОтменаПроведения"),
  MAIN_WINDOW_MODE_EMBEDDED_WORKPLACE("MainWindowModeEmbeddedWorkplace", "РежимОсновногоОкнаВстроенноеРабочееМесто"),
  MAIN_WINDOW_MODE_FULLSCREEN_WORKPLACE(
    "MainWindowModeFullscreenWorkplace", "РежимОсновногоОкнаПолноэкранноеРабочееМесто"),
  MAIN_WINDOW_MODE_KIOSK("MainWindowModeKiosk", "РежимОсновногоОкнаКиоск"),
  MAIN_WINDOW_MODE_NORMAL("MainWindowModeNormal", "РежимОсновногоОкнаОбычный"),
  MAIN_WINDOW_MODE_WORKPLACE("MainWindowModeWorkplace", "РежимОсновногоОкнаРабочееМесто"),
  MOBILE_CLIENT("MobileClient", "МобильныйКлиент"),
  OUTPUT("Output", "Вывод"),
  POSTING("Posting", "Проведение"),
  READ("Read", "Чтение"),
  READ_DATA_HISTORY("ReadDataHistory", "ЧтениеИсторииДанных"),
  READ_DATA_HISTORY_OF_MISSING_DATA("ReadDataHistoryOfMissingData", "ЧтениеИсторииДанныхОтсутстсвующихДанных"),
  REMOTE_DESKTOP_CLIENT("RemoteDesktopClient", "УдаленноеУправлениеРабочимСтоломДругогоПользователя"),
  REMOTE_DESKTOP_HOST("RemoteDesktopHost", "ПредоставлениеУдаленногоУправленияРабочимСтоломДругомуПользователю"),
  SAVE_USER_DATA("SaveUserData", "СохранениеДанныхПользователя"),
  SESSION_OS_AUTHENTICATION_CHANGE("SessionOSAuthenticationChange", "ИзменениеАутентификацииОССеанса"),
  SESSION_STANDARD_AUTHENTICATION_CHANGE(
    "SessionStandardAuthenticationChange", "ИзменениеСтандартнойАутентификацииСеанса"),
  SET("Set", "Установка"),
  STANDARD_AUTHENTICATION_CHANGE("StandardAuthenticationChange", "ИзменениеСтандартнойАутентификации"),
  START("Start", "Старт"),
  SWITCH_TO_DATA_HISTORY_VERSION("SwitchToDataHistoryVersion", "ПереходНаВерсиюИсторииДанных"),
  TECHNICAL_SPECIALIST_MODE("TechnicalSpecialistMode", "РежимТехническогоСпециалиста"),
  THICK_CLIENT("ThickClient", "ТолстыйКлиент"),
  THIN_CLIENT("ThinClient", "ТонкийКлиент"),
  TOTALS_CONTROL("TotalsControl", "УправлениеИтогами"),
  UNDO_POSTING("UndoPosting", "ОтменаПроведения"),
  UNKNOWN("unknown", "unknown"),
  UPDATE("Update", "Изменение"),
  UPDATE_DATA_BASE_CONFIGURATION("UpdateDataBaseConfiguration", "ОбновлениеКонфигурацииБазыДанных"),
  UPDATE_DATA_HISTORY("UpdateDataHistory", "ИзменениеИсторииДанных"),
  UPDATE_DATA_HISTORY_OF_MISSING_DATA("UpdateDataHistoryOfMissingData", "ИзменениеИсторииДанныхОтсутствующихДанных"),
  UPDATE_DATA_HISTORY_SETTINGS("UpdateDataHistorySettings", "ИзменениеНастроекИсторииДанных"),
  UPDATE_DATA_HISTORY_VERSION_COMMENT("UpdateDataHistoryVersionComment", "ИзменениеКомментарияВерсииИсторииДанных"),
  USE("Use", "Использование"),
  VIEW("View", "Просмотр"),
  VIEW_DATA_HISTORY("ViewDataHistory", "ПросмотрИсторииДанных"),
  WEB_CLIENT("WebClient", "ВебКлиент");

  private static final Map<String, RoleRight> KEYS = EnumWithName.computeKeys(values());

  @Getter
  @Accessors(fluent = true)
  private final MultiName fullName;

  RoleRight(String nameEn, String nameRu) {
    this.fullName = MultiName.create(nameEn, nameRu);
  }

  /**
   * Ищет элемент перечисления по именам (рус, анг)
   *
   * @param string Имя искомого элемента
   * @return Найденное значение, если не найден - то UNKNOWN
   */
  public static RoleRight valueByName(String string) {
    return KEYS.getOrDefault(string.toLowerCase(Locale.ROOT), UNKNOWN);
  }
}
