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
package com.github._1c_syntax.bsl.mdclasses;

import com.github._1c_syntax.bsl.mdo.AccountingRegister;
import com.github._1c_syntax.bsl.mdo.AccumulationRegister;
import com.github._1c_syntax.bsl.mdo.Bot;
import com.github._1c_syntax.bsl.mdo.BusinessProcess;
import com.github._1c_syntax.bsl.mdo.CalculationRegister;
import com.github._1c_syntax.bsl.mdo.Catalog;
import com.github._1c_syntax.bsl.mdo.ChartOfAccounts;
import com.github._1c_syntax.bsl.mdo.ChartOfCalculationTypes;
import com.github._1c_syntax.bsl.mdo.ChartOfCharacteristicTypes;
import com.github._1c_syntax.bsl.mdo.CommandGroup;
import com.github._1c_syntax.bsl.mdo.CommonAttribute;
import com.github._1c_syntax.bsl.mdo.CommonCommand;
import com.github._1c_syntax.bsl.mdo.CommonForm;
import com.github._1c_syntax.bsl.mdo.CommonModule;
import com.github._1c_syntax.bsl.mdo.CommonPicture;
import com.github._1c_syntax.bsl.mdo.CommonTemplate;
import com.github._1c_syntax.bsl.mdo.Constant;
import com.github._1c_syntax.bsl.mdo.DataProcessor;
import com.github._1c_syntax.bsl.mdo.DefinedType;
import com.github._1c_syntax.bsl.mdo.Document;
import com.github._1c_syntax.bsl.mdo.DocumentJournal;
import com.github._1c_syntax.bsl.mdo.DocumentNumerator;
import com.github._1c_syntax.bsl.mdo.Enum;
import com.github._1c_syntax.bsl.mdo.EventSubscription;
import com.github._1c_syntax.bsl.mdo.ExchangePlan;
import com.github._1c_syntax.bsl.mdo.ExternalDataSource;
import com.github._1c_syntax.bsl.mdo.FilterCriterion;
import com.github._1c_syntax.bsl.mdo.FunctionalOption;
import com.github._1c_syntax.bsl.mdo.FunctionalOptionsParameter;
import com.github._1c_syntax.bsl.mdo.HTTPService;
import com.github._1c_syntax.bsl.mdo.InformationRegister;
import com.github._1c_syntax.bsl.mdo.IntegrationService;
import com.github._1c_syntax.bsl.mdo.Interface;
import com.github._1c_syntax.bsl.mdo.Language;
import com.github._1c_syntax.bsl.mdo.Report;
import com.github._1c_syntax.bsl.mdo.Role;
import com.github._1c_syntax.bsl.mdo.ScheduledJob;
import com.github._1c_syntax.bsl.mdo.Sequence;
import com.github._1c_syntax.bsl.mdo.SessionParameter;
import com.github._1c_syntax.bsl.mdo.SettingsStorage;
import com.github._1c_syntax.bsl.mdo.Style;
import com.github._1c_syntax.bsl.mdo.StyleItem;
import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.mdo.Task;
import com.github._1c_syntax.bsl.mdo.WSReference;
import com.github._1c_syntax.bsl.mdo.WebService;
import com.github._1c_syntax.bsl.mdo.XDTOPackage;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Доступ к объектам метаданных
 * Методы расположены по порядку дерева конфигурации
 */
public interface ConfigurationTree {

  /**
   * Подсистемы
   */
  List<Subsystem> getSubsystems();

  /**
   * Поиск подсистемы по условию
   */
  default Optional<Subsystem> findSubsystem(Predicate<? super Subsystem> predicate) {
    return getSubsystems().stream().filter(predicate).findFirst();
  }

  /**
   * Общие модули
   */
  List<CommonModule> getCommonModules();

  /**
   * Поиск общего модуля по имени
   */
  default Optional<CommonModule> findCommonModule(String name) {
    return getCommonModules().stream().filter(commonModule -> commonModule.getName().equalsIgnoreCase(name))
      .findFirst();
  }

  /**
   * Поиск общего модуля по условию
   */
  default Optional<CommonModule> findCommonModule(Predicate<? super CommonModule> predicate) {
    return getCommonModules().stream().filter(predicate).findFirst();
  }

  /**
   * Параметры сеанса
   */
  List<SessionParameter> getSessionParameters();

  /**
   * Поиск параметра сеанса по условию
   */
  default Optional<SessionParameter> findSessionParameter(Predicate<? super SessionParameter> predicate) {
    return getSessionParameters().stream().filter(predicate).findFirst();
  }

  /**
   * Роли
   */
  List<Role> getRoles();

  /**
   * Поиск роли по условию
   */
  default Optional<Role> findRole(Predicate<? super Role> predicate) {
    return getRoles().stream().filter(predicate).findFirst();
  }

  /**
   * Общие реквизиты
   */
  List<CommonAttribute> getCommonAttributes();

  /**
   * Поиск общего реквизита по условию
   */
  default Optional<CommonAttribute> findCommonAttribute(Predicate<? super CommonAttribute> predicate) {
    return getCommonAttributes().stream().filter(predicate).findFirst();
  }

  /**
   * Планы обмена
   */
  List<ExchangePlan> getExchangePlans();

  /**
   * Поиск плана обмена по условию
   */
  default Optional<ExchangePlan> findExchangePlan(Predicate<? super ExchangePlan> predicate) {
    return getExchangePlans().stream().filter(predicate).findFirst();
  }

  /**
   * Критерии отбора
   */
  List<FilterCriterion> getFilterCriteria();

  /**
   * Поиск критерия отбора по условию
   */
  default Optional<FilterCriterion> findFilterCriterion(Predicate<? super FilterCriterion> predicate) {
    return getFilterCriteria().stream().filter(predicate).findFirst();
  }

  /**
   * Подписки на события
   */
  List<EventSubscription> getEventSubscriptions();

  /**
   * Поиск подписки на событие по условию
   */
  default Optional<EventSubscription> findEventSubscription(Predicate<? super EventSubscription> predicate) {
    return getEventSubscriptions().stream().filter(predicate).findFirst();
  }

  /**
   * Регламентные задания
   */
  List<ScheduledJob> getScheduledJobs();

  /**
   * Поиск регламентного задания по условию
   */
  default Optional<ScheduledJob> findScheduledJob(Predicate<? super ScheduledJob> predicate) {
    return getScheduledJobs().stream().filter(predicate).findFirst();
  }

  /**
   * Боты
   */
  List<Bot> getBots();

  /**
   * Поиск бота по условию
   */
  default Optional<Bot> findBot(Predicate<? super Bot> predicate) {
    return getBots().stream().filter(predicate).findFirst();
  }

  /**
   * Функциональные опции
   */
  List<FunctionalOption> getFunctionalOptions();

  /**
   * Поиск функциональной опции по условию
   */
  default Optional<FunctionalOption> findFunctionalOption(Predicate<? super FunctionalOption> predicate) {
    return getFunctionalOptions().stream().filter(predicate).findFirst();
  }

  /**
   * Параметры функциональных опций
   */
  List<FunctionalOptionsParameter> getFunctionalOptionsParameters();

  /**
   * Поиск параметра функциональных опций по условию
   */
  default Optional<FunctionalOptionsParameter> findFunctionalOptionsParameter(
    Predicate<? super FunctionalOptionsParameter> predicate) {
    return getFunctionalOptionsParameters().stream().filter(predicate).findFirst();
  }

  /**
   * Определяемые типы
   */
  List<DefinedType> getDefinedTypes();

  /**
   * Поиск определяемого типа по условию
   */
  default Optional<DefinedType> findDefinedType(Predicate<? super DefinedType> predicate) {
    return getDefinedTypes().stream().filter(predicate).findFirst();
  }

  /**
   * Хранилища настроек
   */
  List<SettingsStorage> getSettingsStorages();

  /**
   * Поиск хранилища настроек по условию
   */
  default Optional<SettingsStorage> findSettingsStorage(Predicate<? super SettingsStorage> predicate) {
    return getSettingsStorages().stream().filter(predicate).findFirst();
  }

  /**
   * Общие формы
   */
  List<CommonForm> getCommonForms();

  /**
   * Поиск общей формы по условию
   */
  default Optional<CommonForm> findCommonForm(Predicate<? super CommonForm> predicate) {
    return getCommonForms().stream().filter(predicate).findFirst();
  }

  /**
   * Общие команды
   */
  List<CommonCommand> getCommonCommands();

  /**
   * Поиск общей команды по условию
   */
  default Optional<CommonCommand> findCommonCommand(Predicate<? super CommonCommand> predicate) {
    return getCommonCommands().stream().filter(predicate).findFirst();
  }

  /**
   * Группы команд
   */
  List<CommandGroup> getCommandGroups();

  /**
   * Поиск группы команд по условию
   */
  default Optional<CommandGroup> findCommandGroup(Predicate<? super CommandGroup> predicate) {
    return getCommandGroups().stream().filter(predicate).findFirst();
  }

  /**
   * Общие макеты
   */
  List<CommonTemplate> getCommonTemplates();

  /**
   * Поиск общего макета по условию
   */
  default Optional<CommonTemplate> findCommonTemplate(Predicate<? super CommonTemplate> predicate) {
    return getCommonTemplates().stream().filter(predicate).findFirst();
  }

  /**
   * Общие картинки
   */
  List<CommonPicture> getCommonPictures();

  /**
   * Поиск общей картинки по условию
   */
  default Optional<CommonPicture> findCommonPicture(Predicate<? super CommonPicture> predicate) {
    return getCommonPictures().stream().filter(predicate).findFirst();
  }

  /**
   * Интерфейсы
   */
  List<Interface> getInterfaces();

  /**
   * Поиск интерфейса по условию
   */
  default Optional<Interface> findInterface(Predicate<? super Interface> predicate) {
    return getInterfaces().stream().filter(predicate).findFirst();
  }

  /**
   * XDTO-пакеты
   */
  List<XDTOPackage> getXDTOPackages();

  /**
   * Поиск XDTO-пакета по условию
   */
  default Optional<XDTOPackage> findXDTOPackage(Predicate<? super XDTOPackage> predicate) {
    return getXDTOPackages().stream().filter(predicate).findFirst();
  }

  /**
   * Web-сервисы
   */
  List<WebService> getWebServices();

  /**
   * Поиск Web-сервиса по условию
   */
  default Optional<WebService> findWebService(Predicate<? super WebService> predicate) {
    return getWebServices().stream().filter(predicate).findFirst();
  }

  /**
   * HTTP-сервисы
   */
  List<HTTPService> getHttpServices();

  /**
   * Поиск HTTP-сервиса по условию
   */
  default Optional<HTTPService> findHttpService(Predicate<? super HTTPService> predicate) {
    return getHttpServices().stream().filter(predicate).findFirst();
  }

  /**
   * WS-ссылки
   */
  List<WSReference> getWsReferences();

  /**
   * Поиск WS-ссылки по условию
   */
  default Optional<WSReference> findWSReference(Predicate<? super WSReference> predicate) {
    return getWsReferences().stream().filter(predicate).findFirst();
  }

  /**
   * Сервисы интеграции
   */
  List<IntegrationService> getIntegrationServices();

  /**
   * Поиск сервиса интеграции по условию
   */
  default Optional<IntegrationService> findIntegrationService(Predicate<? super IntegrationService> predicate) {
    return getIntegrationServices().stream().filter(predicate).findFirst();
  }

  /**
   * Элементы стиля
   */
  List<StyleItem> getStyleItems();

  /**
   * Поиск элемента стиля по условию
   */
  default Optional<StyleItem> findStyleItem(Predicate<? super StyleItem> predicate) {
    return getStyleItems().stream().filter(predicate).findFirst();
  }

  /**
   * Стили
   */
  List<Style> getStyles();

  /**
   * Поиск стиля по условию
   */
  default Optional<Style> findStyle(Predicate<? super Style> predicate) {
    return getStyles().stream().filter(predicate).findFirst();
  }

  /**
   * Языки
   */
  List<Language> getLanguages();

  /**
   * Поиск языка по условию
   */
  default Optional<Language> findLanguage(Predicate<? super Language> predicate) {
    return getLanguages().stream().filter(predicate).findFirst();
  }

  /**
   * Константы
   */
  List<Constant> getConstants();

  /**
   * Поиск константы по условию
   */
  default Optional<Constant> findConstant(Predicate<? super Constant> predicate) {
    return getConstants().stream().filter(predicate).findFirst();
  }

  /**
   * Справочники
   */
  List<Catalog> getCatalogs();

  /**
   * Поиск справочника по условию
   */
  default Optional<Catalog> findCatalog(Predicate<? super Catalog> predicate) {
    return getCatalogs().stream().filter(predicate).findFirst();
  }

  /**
   * Документы
   */
  List<Document> getDocuments();

  /**
   * Поиск документа по условию
   */
  default Optional<Document> findDocument(Predicate<? super Document> predicate) {
    return getDocuments().stream().filter(predicate).findFirst();
  }

  /**
   * Нумераторы
   */
  List<DocumentNumerator> getDocumentNumerators();

  /**
   * Поиск нумератора по условию
   */
  default Optional<DocumentNumerator> findDocumentNumerator(Predicate<? super DocumentNumerator> predicate) {
    return getDocumentNumerators().stream().filter(predicate).findFirst();
  }

  /**
   * Последовательности
   */
  List<Sequence> getSequences();

  /**
   * Поиск последовательности по условию
   */
  default Optional<Sequence> findSequence(Predicate<? super Sequence> predicate) {
    return getSequences().stream().filter(predicate).findFirst();
  }

  /**
   * Журналы документов
   */
  List<DocumentJournal> getDocumentJournals();

  /**
   * Поиск журнала документов по условию
   */
  default Optional<DocumentJournal> findDocumentJournal(Predicate<? super DocumentJournal> predicate) {
    return getDocumentJournals().stream().filter(predicate).findFirst();
  }

  /**
   * Перечисления
   */
  List<Enum> getEnums();

  /**
   * Поиск перечисления по условию
   */
  default Optional<Enum> findEnum(Predicate<? super Enum> predicate) {
    return getEnums().stream().filter(predicate).findFirst();
  }

  /**
   * Отчеты
   */
  List<Report> getReports();

  /**
   * Поиск отчета по условию
   */
  default Optional<Report> findReport(Predicate<? super Report> predicate) {
    return getReports().stream().filter(predicate).findFirst();
  }

  /**
   * Обработки
   */
  List<DataProcessor> getDataProcessors();

  /**
   * Поиск обработки по условию
   */
  default Optional<DataProcessor> findDataProcessor(Predicate<? super DataProcessor> predicate) {
    return getDataProcessors().stream().filter(predicate).findFirst();
  }

  /**
   * Планы видов характеристик
   */
  List<ChartOfCharacteristicTypes> getChartsOfCharacteristicTypes();

  /**
   * Поиск плана видов характеристик по условию
   */
  default Optional<ChartOfCharacteristicTypes> findChartOfCharacteristicTypes(
    Predicate<? super ChartOfCharacteristicTypes> predicate) {
    return getChartsOfCharacteristicTypes().stream().filter(predicate).findFirst();
  }

  /**
   * Планы счетов
   */
  List<ChartOfAccounts> getChartsOfAccounts();

  /**
   * Поиск плана счетов по условию
   */
  default Optional<ChartOfAccounts> findChartOfAccounts(Predicate<? super ChartOfAccounts> predicate) {
    return getChartsOfAccounts().stream().filter(predicate).findFirst();
  }

  /**
   * Планы видов расчета
   */
  List<ChartOfCalculationTypes> getChartsOfCalculationTypes();

  /**
   * Поиск плана видов расчета по условию
   */
  default Optional<ChartOfCalculationTypes> findChartOfCalculationTypes(
    Predicate<? super ChartOfCalculationTypes> predicate) {
    return getChartsOfCalculationTypes().stream().filter(predicate).findFirst();
  }

  /**
   * Регистры сведений
   */
  List<InformationRegister> getInformationRegisters();

  /**
   * Поиск регистра сведений по условию
   */
  default Optional<InformationRegister> findInformationRegister(Predicate<? super InformationRegister> predicate) {
    return getInformationRegisters().stream().filter(predicate).findFirst();
  }

  /**
   * Регистры накопления
   */
  List<AccumulationRegister> getAccumulationRegisters();

  /**
   * Поиск регистра накопления по условию
   */
  default Optional<AccumulationRegister> findAccumulationRegister(Predicate<? super AccumulationRegister> predicate) {
    return getAccumulationRegisters().stream().filter(predicate).findFirst();
  }

  /**
   * Регистры бухгалтерии
   */
  List<AccountingRegister> getAccountingRegisters();

  /**
   * Поиск регистра бухгалтерии по условию
   */
  default Optional<AccountingRegister> findAccountingRegister(Predicate<? super AccountingRegister> predicate) {
    return getAccountingRegisters().stream().filter(predicate).findFirst();
  }

  /**
   * Регистры расчета
   */
  List<CalculationRegister> getCalculationRegisters();

  /**
   * Поиск регистра расчета по условию
   */
  default Optional<CalculationRegister> findCalculationRegister(Predicate<? super CalculationRegister> predicate) {
    return getCalculationRegisters().stream().filter(predicate).findFirst();
  }

  /**
   * Бизнес-процессы
   */
  List<BusinessProcess> getBusinessProcesses();

  /**
   * Поиск бизнес-процесса по условию
   */
  default Optional<BusinessProcess> findBusinessProcess(Predicate<? super BusinessProcess> predicate) {
    return getBusinessProcesses().stream().filter(predicate).findFirst();
  }

  /**
   * Задачи
   */
  List<Task> getTasks();

  /**
   * Поиск задачи по условию
   */
  default Optional<Task> findTask(Predicate<? super Task> predicate) {
    return getTasks().stream().filter(predicate).findFirst();
  }

  /**
   * Внешние источники данных
   */
  List<ExternalDataSource> getExternalDataSources();

  /**
   * Поиск внешнего источника данных по условию
   */
  default Optional<ExternalDataSource> findExternalDataSource(Predicate<? super ExternalDataSource> predicate) {
    return getExternalDataSources().stream().filter(predicate).findFirst();
  }
}
