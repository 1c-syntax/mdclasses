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
package com.github._1c_syntax.mdclasses.mdo.support;

/**
 * Все права доступа
 */
public enum RightType implements EnumWithValue {

    ALL_FUNCTIONS_MODE("AllFunctionsMode"), // РежимВсеФункции
    INTERACTIVE_CHANGE_OF_POSTED("InteractiveChangeOfPosted"), // ИнтерактивноеИзменениеПроведенных

    AUTOMATION("Automation"), // Automation
    ADMINISTRATION("Administration"), // Администрирование
    DATA_ADMINISTRATION("DataAdministration"), // АдминистрированиеДанных
    CONFIGURATION_EXTENSIONS_ADMINISTRATION("ConfigurationExtensionsAdministration"), // АдминистрированиеРасширенийКонфигурации
    ACTIVE_USERS("ActiveUsers"), // АктивныеПользователи
    INPUT_BY_STRING("InputByString"), // Ввод по строке
    WEB_CLIENT("WebClient"), // ВебКлиент
    MOBILE_CLIENT("MobileClient"), // ВебКлиент
    EXTERNAL_CONNECTION("ExternalConnection"), // ВнешнееСоединение
    OUTPUT("Output"), // Вывод
    EXECUTE("Execute"), // Выполнение
    INSERT("Insert"), // Добавление
    EVENTLOG("EventLog"), // ЖурналРегистрации
    UPDATE("Update"), // Изменение
    SESSION_OS_AUTHENTICATION_CHANGE("SessionOSAuthenticationChange"), // ИзменениеАутентификацииОССеанса
    UPDATE_DATA_HISTORY("UpdateDataHistory"), // ИзменениеИсторииДанных
    UPDATE_MISSING_DATA_HISTORY("UpdateMissingDataHistory"), // ИзменениеИсторииДанныхОтсутствующихДанных
    UPDATE_DATA_HISTORY_VERSION_COMMENT("UpdateDataHistoryVersionComment"), // ИзменениеКомментарияВерсииИсторииДанных
    UPDATE_DATA_HISTORY_SETTINGS("UpdateDataHistorySettings"), // ИзменениеНастроекИсторииДанных
    STANDARD_AUTHENTICATION_CHANGE("StandardAuthenticationChange"), // ИзменениеСтандартнойАутентификации
    SESSION_STANDARD_AUTHENTICATION_CHANGE("SessionStandardAuthenticationChange"), // ИзменениеСтандартнойАутентификацииСеанса
    INTERACTIVE_ACTIVATE("InteractiveActivate"), // ИнтерактивнаяАктивация
    INTERACTIVE_UNDO_POSTING("InteractiveUndoPosting"), // ИнтерактивнаяОтменаПроведения
    INTERACTIVE_SET_DELETION_MARK("InteractiveSetDeletionMark"), // ИнтерактивнаяПометкаУдаления
    INTERACTIVE_SET_DELETION_MARK_PREDEFINED_DATA("InteractiveSetDeletionMarkPredefinedData"), // ИнтерактивнаяПометкаУдаленияПредопределенныхДанных
    INTERACTIVE_EXECUTE("InteractiveExecute"), // ИнтерактивноеВыполнение
    INTERACTIVE_INSERT("InteractiveInsert"), // ИнтерактивноеДобавление
    INTERACTIVE_CHANGE_POSTED("InteractiveChangePosted"), // ИнтерактивноеИзменениеПроведенных
    INTERACTIVE_OPEN_EXT_DATA_PROCESSORS("InteractiveOpenExtDataProcessors"), // ИнтерактивноеОткрытиеВнешнихОбработок
    INTERACTIVE_OPEN_EXT_REPORTS("InteractiveOpenExtReports"), // ИнтерактивноеОткрытиеВнешнихОтчетов
    INTERACTIVE_POSTING("InteractivePosting"), // ИнтерактивноеПроведение
    INTERACTIVE_POSTING_REGULAR("InteractivePostingRegular"), // ИнтерактивноеПроведениеНеОперативное
    INTERACTIVE_CLEAR_DELETION_MARK("InteractiveClearDeletionMark"), // ИнтерактивноеСнятиеПометкиУдаления
    INTERACTIVE_CLEAR_DELETION_MARK_PREDEFINED_DATA("InteractiveClearDeletionMarkPredefinedData"), // ИнтерактивноеСнятиеПометкиУдаленияПредопределенныхДанных
    INTERACTIVE_DELETE("InteractiveDelete"), // ИнтерактивноеУдаление
    INTERACTIVE_DELETE_MARKED("InteractiveDeleteMarked"), // ИнтерактивноеУдалениеПомеченных
    INTERACTIVE_DELETE_MARKED_PREDEFINED_DATA("InteractiveDeleteMarkedPredefinedData"), // ИнтерактивноеУдалениеПомеченныхПредопределенныхДанных
    INTERACTIVE_DELETE_PREDEFINED_DATA("InteractiveDeletePredefinedData"), // ИнтерактивноеУдалениеПредопределенныхДанных
    INTERACTIVE_START("InteractiveStart"), // ИнтерактивныйСтарт
    USE("Use"), // Использование
    ANALYTICS_SYSTEM_CLIENT("AnalyticsSystemClient"), // КлиентСистемыАналитики
    EXCLUSIVE_MODE("ExclusiveMode"), // МонопольныйРежим
    UPDATE_DATABASE_CONFIGURATION("UpdateDataBaseConfiguration"), // ОбновлениеКонфигурацииБазыДанных
    UNDO_POSTING("UndoPosting"), // ОтменаПроведения
    SWITCH_TO_DATA_HISTORY_VERSION("SwitchToDataHistoryVersion"), // ПереходНаВерсиюИсторииДанных
    GET("Get"), // Получение
    POSTING("Posting"), // Проведение
    VIEW("View"), // Просмотр
    VIEW_DATA_HISTORY("ViewDataHistory"), // ПросмотрИсторииДанных
    COLLABORATION_SYSTEM_INFOBASE_REGISTRATION("CollaborationSystemInfoBaseRegistration"), // РегистрацияИнформационнойБазыСистемыВзаимодействия
    EDIT("Edit"), // Редактирование
    EDIT_DATA_HISTORY_VERSION_COMMENT("EditDataHistoryVersionComment"), // РедактированиеКомментарияВерсииИсторииДанных
    MAIN_WINDOW_MODE_EMBEDDED_WORKPLACE("MainWindowModeEmbeddedWorkplace"), // РежимОсновногоОкнаВстроенноеРабочееМесто
    MAIN_WINDOW_MODE_KIOSK("MainWindowModeKiosk"), // РежимОсновногоОкнаКиоск
    MAIN_WINDOW_MODE_NORMAL("MainWindowModeNormal"), // РежимОсновногоОкнаОбычный
    MAIN_WINDOW_MODE_FULLSCREEN_WORKPLACE("MainWindowModeFullscreenWorkplace"), // РежимОсновногоОкнаПолноэкранноеРабочееМесто
    MAIN_WINDOW_MODE_WORKPLACE("MainWindowModeWorkplace"), // РежимОсновногоОкнаРабочееМесто
    TECHNICAL_SPECIALIST_MODE("TechnicalSpecialistMode"), // РежимТехническогоСпециалиста
    SAVE_USER_DATA("SaveUserData"), // СохранениеДанныхПользователя
    START("Start"), // Старт
    THICK_CLIENT("ThickClient"), // ТолстыйКлиент
    THIN_CLIENT("ThinClient"), // ТонкийКлиент
    DELETE("Delete"), // Удаление
    TOTALS_CONTROL("TotalsControl"), // УправлениеИтогами
    SET("Set"), // Установка
    READ("Read"), // Чтение
    READ_DATA_HISTORY("ReadDataHistory"), // ЧтениеИсторииДанных
    READ_MISSING_DATA_HISTORY("ReadMissingDataHistory"), // ЧтениеИсторииДанныхОтсутствующихДанных
    EXCLUSIVE_MODE_TERMINATION_AT_SESSION_START("ExclusiveModeTerminationAtSessionStart"); // ЗавершениеМонопольногоРежимаПриНачалеСеанса

    private final String value;

    RightType(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return this.value;
    }
}
