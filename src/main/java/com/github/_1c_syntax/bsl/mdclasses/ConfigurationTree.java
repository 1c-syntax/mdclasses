/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2022
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
import com.github._1c_syntax.bsl.mdo.FilterCriterion;
import com.github._1c_syntax.bsl.mdo.FunctionalOption;
import com.github._1c_syntax.bsl.mdo.FunctionalOptionsParameter;
import com.github._1c_syntax.bsl.mdo.HttpService;
import com.github._1c_syntax.bsl.mdo.InformationRegister;
import com.github._1c_syntax.bsl.mdo.IntegrationService;
import com.github._1c_syntax.bsl.mdo.Interface;
import com.github._1c_syntax.bsl.mdo.Language;
import com.github._1c_syntax.bsl.mdo.MDObject;
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
import com.github._1c_syntax.bsl.mdo.XdtoPackage;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Доступ к объектам метаданных
 * Методы расположены по порядку дерева конфигурации
 */
public interface ConfigurationTree {

  /**
   * Подсистемы
   */
  default List<Subsystem> getSubsystems() {
    return getChildrenByType(Subsystem.class);
  }

  /**
   * Поиск подсистемы по условию
   */
  default Optional<Subsystem> findSubsystem(Predicate<? super Subsystem> predicate) {
    return getSubsystems().stream().filter(predicate).findFirst();
  }

  /**
   * Общие модули
   */
  default List<CommonModule> getCommonModules() {
    return getChildrenByType(CommonModule.class);
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
  default List<SessionParameter> getSessionParameters() {
    return getChildrenByType(SessionParameter.class);
  }

  /**
   * Поиск параметра сеанса по условию
   */
  default Optional<SessionParameter> findSessionParameter(Predicate<? super SessionParameter> predicate) {
    return getSessionParameters().stream().filter(predicate).findFirst();
  }

  /**
   * Роли
   */
  default List<Role> getRoles() {
    return getChildrenByType(Role.class);
  }

  /**
   * Поиск Роли по условию
   */
  default Optional<Role> findRole(Predicate<? super Role> predicate) {
    return getRoles().stream().filter(predicate).findFirst();
  }

  /**
   * Общие реквизиты
   */
  default List<CommonAttribute> getCommonAttributes() {
    return getChildrenByType(CommonAttribute.class);
  }

  /**
   * Поиск общего реквизита по условию
   */
  default Optional<CommonAttribute> findCommonAttribute(Predicate<? super CommonAttribute> predicate) {
    return getCommonAttributes().stream().filter(predicate).findFirst();
  }

  /**
   * Планы обмена
   */
  default List<ExchangePlan> getExchangePlans() {
    return getChildrenByType(ExchangePlan.class);
  }

  /**
   * Поиск плана обмена по условию
   */
  default Optional<ExchangePlan> findExchangePlan(Predicate<? super ExchangePlan> predicate) {
    return getExchangePlans().stream().filter(predicate).findFirst();
  }

  /**
   * Критерии отбора
   */
  default List<FilterCriterion> getFilterCriteria() {
    return getChildrenByType(FilterCriterion.class);
  }

  /**
   * Поиск критерия отбора по условию
   */
  default Optional<FilterCriterion> findFilterCriterion(Predicate<? super FilterCriterion> predicate) {
    return getFilterCriteria().stream().filter(predicate).findFirst();
  }

  /**
   * Подписки на события
   */
  default List<EventSubscription> getEventSubscriptions() {
    return getChildrenByType(EventSubscription.class);
  }

  /**
   * Поиск подписки на событие по условию
   */
  default Optional<EventSubscription> findEventSubscription(Predicate<? super EventSubscription> predicate) {
    return getEventSubscriptions().stream().filter(predicate).findFirst();
  }

  /**
   * Регламентные задания
   */
  default List<ScheduledJob> getScheduledJobs() {
    return getChildrenByType(ScheduledJob.class);
  }

  /**
   * Поиск регламентного задания по условию
   */
  default Optional<ScheduledJob> findScheduledJob(Predicate<? super ScheduledJob> predicate) {
    return getScheduledJobs().stream().filter(predicate).findFirst();
  }

  /**
   * Боты
   */
  default List<Bot> getBots() {
    return getChildrenByType(Bot.class);
  }

  /**
   * Поиск бота по условию
   */
  default Optional<Bot> findBot(Predicate<? super Bot> predicate) {
    return getBots().stream().filter(predicate).findFirst();
  }

  /**
   * Функциональные опции
   */
  default List<FunctionalOption> getFunctionalOptions() {
    return getChildrenByType(FunctionalOption.class);
  }

  /**
   * Поиск функциональной опции по условию
   */
  default Optional<FunctionalOption> findFunctionalOption(Predicate<? super FunctionalOption> predicate) {
    return getFunctionalOptions().stream().filter(predicate).findFirst();
  }

  /**
   * Параметры функциональных опций
   */
  default List<FunctionalOptionsParameter> getFunctionalOptionsParameters() {
    return getChildrenByType(FunctionalOptionsParameter.class);
  }

  /**
   * Поиск параметра функциональной опции по условию
   */
  default Optional<FunctionalOptionsParameter> findFunctionalOptionsParameter(
    Predicate<? super FunctionalOptionsParameter> predicate) {
    return getFunctionalOptionsParameters().stream().filter(predicate).findFirst();
  }

  /**
   * Определяемые типы
   */
  default List<DefinedType> getDefinedTypes() {
    return getChildrenByType(DefinedType.class);
  }

  /**
   * Поиск определяемого типа по условию
   */
  default Optional<DefinedType> findDefinedType(Predicate<? super DefinedType> predicate) {
    return getDefinedTypes().stream().filter(predicate).findFirst();
  }

  /**
   * Хранилища настроек
   */
  default List<SettingsStorage> getSettingsStorages() {
    return getChildrenByType(SettingsStorage.class);
  }

  /**
   * Поиск хранилища настроек по условию
   */
  default Optional<SettingsStorage> findSettingsStorage(Predicate<? super SettingsStorage> predicate) {
    return getSettingsStorages().stream().filter(predicate).findFirst();
  }

  /**
   * Общие формы
   */
  default List<CommonForm> getCommonForms() {
    return getChildrenByType(CommonForm.class);
  }

  /**
   * Поиск общей формы по условию
   */
  default Optional<CommonForm> findCommonForm(Predicate<? super CommonForm> predicate) {
    return getCommonForms().stream().filter(predicate).findFirst();
  }

  /**
   * Общие команды
   */
  default List<CommonCommand> getCommonCommands() {
    return getChildrenByType(CommonCommand.class);
  }

  /**
   * Поиск общей команды по условию
   */
  default Optional<CommonCommand> findCommonCommand(Predicate<? super CommonCommand> predicate) {
    return getCommonCommands().stream().filter(predicate).findFirst();
  }

  /**
   * Группы команд
   */
  default List<CommandGroup> getCommandGroups() {
    return getChildrenByType(CommandGroup.class);
  }

  /**
   * Поиск группы команд по условию
   */
  default Optional<CommandGroup> findCommandGroup(Predicate<? super CommandGroup> predicate) {
    return getCommandGroups().stream().filter(predicate).findFirst();
  }

  /**
   * Общие макеты
   */
  default List<CommonTemplate> getCommonTemplates() {
    return getChildrenByType(CommonTemplate.class);
  }

  /**
   * Поиск общего макета по условию
   */
  default Optional<CommonTemplate> findCommonTemplate(Predicate<? super CommonTemplate> predicate) {
    return getCommonTemplates().stream().filter(predicate).findFirst();
  }

  /**
   * Общие картинки
   */
  default List<CommonPicture> getCommonPictures() {
    return getChildrenByType(CommonPicture.class);
  }

  /**
   * Поиск общей картинки по условию
   */
  default Optional<CommonPicture> findCommonPicture(Predicate<? super CommonPicture> predicate) {
    return getCommonPictures().stream().filter(predicate).findFirst();
  }

  /**
   * Интерфейсы
   */
  default List<Interface> getInterfaces() {
    return getChildrenByType(Interface.class);
  }

  /**
   * Поиск интерфейса по условию
   */
  default Optional<Interface> findInterface(Predicate<? super Interface> predicate) {
    return getInterfaces().stream().filter(predicate).findFirst();
  }

  /**
   * XDTO-пакеты
   */
  default List<XdtoPackage> getXdtoPackages() {
    return getChildrenByType(XdtoPackage.class);
  }

  /**
   * Поиск XDTO пакета по условию
   */
  default Optional<XdtoPackage> findXdtoPackage(Predicate<? super XdtoPackage> predicate) {
    return getXdtoPackages().stream().filter(predicate).findFirst();
  }

  /**
   * Web-сервисы
   */
  default List<WebService> getWebServices() {
    return getChildrenByType(WebService.class);
  }

  /**
   * Поиск веб-сервиса по условию
   */
  default Optional<WebService> findWebService(Predicate<? super WebService> predicate) {
    return getWebServices().stream().filter(predicate).findFirst();
  }

  /**
   * HTTP-сервисы
   */
  default List<HttpService> getHttpServices() {
    return getChildrenByType(HttpService.class);
  }

  /**
   * Поиск http-сервиса по условию
   */
  default Optional<HttpService> findHttpService(Predicate<? super HttpService> predicate) {
    return getHttpServices().stream().filter(predicate).findFirst();
  }

  /**
   * WS-ссылки
   */
  default List<WSReference> getWsReferences() {
    return getChildrenByType(WSReference.class);
  }

  /**
   * Поиск ws-ссылки по условию
   */
  default Optional<WSReference> findWsReference(Predicate<? super WSReference> predicate) {
    return getWsReferences().stream().filter(predicate).findFirst();
  }

  /**
   * Сервисы интеграции
   */
  default List<IntegrationService> getIntegrationServices() {
    return getChildrenByType(IntegrationService.class);
  }

  /**
   * Поиск сервиса интеграции по условию
   */
  default Optional<IntegrationService> findIntegrationService(Predicate<? super IntegrationService> predicate) {
    return getIntegrationServices().stream().filter(predicate).findFirst();
  }

  /**
   * Элементы стиля
   */
  default List<StyleItem> getStyleItems() {
    return getChildrenByType(StyleItem.class);
  }

  /**
   * Поиск элемента стиля по условию
   */
  default Optional<StyleItem> findStyleItem(Predicate<? super StyleItem> predicate) {
    return getStyleItems().stream().filter(predicate).findFirst();
  }

  /**
   * Стили
   */
  default List<Style> getStyles() {
    return getChildrenByType(Style.class);
  }

  /**
   * Поиск стиля по условию
   */
  default Optional<Style> findStyle(Predicate<? super Style> predicate) {
    return getStyles().stream().filter(predicate).findFirst();
  }

  /**
   * Языки
   */
  default List<Language> getLanguages() {
    return getChildrenByType(Language.class);
  }

  /**
   * Поиск языка по условию
   */
  default Optional<Language> findLanguage(Predicate<? super Language> predicate) {
    return getLanguages().stream().filter(predicate).findFirst();
  }

  /**
   * Константы
   */
  default List<Constant> getConstants() {
    return getChildrenByType(Constant.class);
  }

  /**
   * Поиск константы по условию
   */
  default Optional<Constant> findConstant(Predicate<? super Constant> predicate) {
    return getConstants().stream().filter(predicate).findFirst();
  }

  /**
   * Справочники
   */
  default List<Catalog> getCatalogs() {
    return getChildrenByType(Catalog.class);
  }

  /**
   * Поиск справочника по условию
   */
  default Optional<Catalog> findCatalog(Predicate<? super Catalog> predicate) {
    return getCatalogs().stream().filter(predicate).findFirst();
  }

  /**
   * Документы
   */
  default List<Document> getDocuments() {
    return getChildrenByType(Document.class);
  }

  /**
   * Поиск документа по условию
   */
  default Optional<Document> findDocument(Predicate<? super Document> predicate) {
    return getDocuments().stream().filter(predicate).findFirst();
  }

  /**
   * Нумераторы
   */
  default List<DocumentNumerator> getDocumentNumerators() {
    return getChildrenByType(DocumentNumerator.class);
  }

  /**
   * Поиск нумератора по условию
   */
  default Optional<DocumentNumerator> findDocumentNumerator(Predicate<? super DocumentNumerator> predicate) {
    return getDocumentNumerators().stream().filter(predicate).findFirst();
  }

  /**
   * Последовательности
   */
  default List<Sequence> getSequences() {
    return getChildrenByType(Sequence.class);
  }

  /**
   * Поиск Последовательности по условию
   */
  default Optional<Sequence> findSequence(Predicate<? super Sequence> predicate) {
    return getSequences().stream().filter(predicate).findFirst();
  }

  /**
   * Журналы документов
   */
  default List<DocumentJournal> getDocumentJournals() {
    return getChildrenByType(DocumentJournal.class);
  }

  /**
   * Поиск журнала документов по условию
   */
  default Optional<DocumentJournal> findDocumentJournal(Predicate<? super DocumentJournal> predicate) {
    return getDocumentJournals().stream().filter(predicate).findFirst();
  }

  /**
   * Перечисления
   */
  default List<Enum> getEnums() {
    return getChildrenByType(Enum.class);
  }

  /**
   * Поиск Перечисления по условию
   */
  default Optional<Enum> findEnum(Predicate<? super Enum> predicate) {
    return getEnums().stream().filter(predicate).findFirst();
  }

  /**
   * Отчеты
   */
  default List<Report> getReports() {
    return getChildrenByType(Report.class);
  }

  /**
   * Поиск отчета по условию
   */
  default Optional<Report> findReport(Predicate<? super Report> predicate) {
    return getReports().stream().filter(predicate).findFirst();
  }

  /**
   * Обработки
   */
  default List<DataProcessor> getDataProcessors() {
    return getChildrenByType(DataProcessor.class);
  }

  /**
   * Поиск обработки по условию
   */
  default Optional<DataProcessor> findDataProcessor(Predicate<? super DataProcessor> predicate) {
    return getDataProcessors().stream().filter(predicate).findFirst();
  }

  /**
   * Планы видов характеристик
   */
  default List<ChartOfCharacteristicTypes> getChartOfCharacteristicTypes() {
    return getChildrenByType(ChartOfCharacteristicTypes.class);
  }

  /**
   * Поиск ПВХ по условию
   */
  default Optional<ChartOfCharacteristicTypes> findChartOfCharacteristicTypes(
    Predicate<? super ChartOfCharacteristicTypes> predicate) {
    return getChartOfCharacteristicTypes().stream().filter(predicate).findFirst();
  }

  /**
   * Планы счетов
   */
  default List<ChartOfAccounts> getChartOfAccounts() {
    return getChildrenByType(ChartOfAccounts.class);
  }

  /**
   * Поиск плана счетов по условию
   */
  default Optional<ChartOfAccounts> findChartOfAccounts(Predicate<? super ChartOfAccounts> predicate) {
    return getChartOfAccounts().stream().filter(predicate).findFirst();
  }

  /**
   * Планы видов расчета
   */
  default List<ChartOfCalculationTypes> getChartOfCalculationTypes() {
    return getChildrenByType(ChartOfCalculationTypes.class);
  }

  /**
   * Поиск ПВР по условию
   */
  default Optional<ChartOfCalculationTypes> findChartOfCalculationTypes(
    Predicate<? super ChartOfCalculationTypes> predicate) {
    return getChartOfCalculationTypes().stream().filter(predicate).findFirst();
  }

  /**
   * Регистры сведений
   */
  default List<InformationRegister> getInformationRegisters() {
    return getChildrenByType(InformationRegister.class);
  }

  /**
   * Поиск регистра сведений по условию
   */
  default Optional<InformationRegister> findInformationRegister(Predicate<? super InformationRegister> predicate) {
    return getInformationRegisters().stream().filter(predicate).findFirst();
  }

  /**
   * Регистры накопления
   */
  default List<AccumulationRegister> getAccumulationRegisters() {
    return getChildrenByType(AccumulationRegister.class);
  }

  /**
   * Поиск регистра накопления по условию
   */
  default Optional<AccumulationRegister> findAccumulationRegister(Predicate<? super AccumulationRegister> predicate) {
    return getAccumulationRegisters().stream().filter(predicate).findFirst();
  }

  /**
   * Регистры бухгалтерии
   */
  default List<AccountingRegister> getAccountingRegisters() {
    return getChildrenByType(AccountingRegister.class);
  }

  /**
   * Поиск регистра бухгалтерии по условию
   */
  default Optional<AccountingRegister> findAccountingRegister(Predicate<? super AccountingRegister> predicate) {
    return getAccountingRegisters().stream().filter(predicate).findFirst();
  }

  /**
   * Регистры расчета
   */
  default List<CalculationRegister> getCalculationRegisters() {
    return getChildrenByType(CalculationRegister.class);
  }

  /**
   * Поиск регистра расчетов по условию
   */
  default Optional<CalculationRegister> findCalculationRegister(Predicate<? super CalculationRegister> predicate) {
    return getCalculationRegisters().stream().filter(predicate).findFirst();
  }

  /**
   * Бизнес-процессы
   */
  default List<BusinessProcess> getBusinessProcesses() {
    return getChildrenByType(BusinessProcess.class);
  }

  /**
   * Поиск бизнес-процесса по условию
   */
  default Optional<BusinessProcess> findBusinessProcess(Predicate<? super BusinessProcess> predicate) {
    return getBusinessProcesses().stream().filter(predicate).findFirst();
  }

  /**
   * Задачи
   */
  default List<Task> getTasks() {
    return getChildrenByType(Task.class);
  }

  /**
   * Поиск задачи по условию
   */
  default Optional<Task> findTask(Predicate<? super Task> predicate) {
    return getTasks().stream().filter(predicate).findFirst();
  }

  /**
   * Получение списка дочерних элементов по типу
   *
   * @param clazz Класс дочернего элемента
   * @param <T>   Тип дочернего элемента
   * @return Список дочерних элементов
   */
  private <T extends MDObject> List<T> getChildrenByType(Class<T> clazz) {
    if (this instanceof MDClass) {
      return ((MDClass) this).getChildren().stream()
        .filter(clazz::isInstance)
        .map(clazz::cast)
        .collect(Collectors.toUnmodifiableList());
    }
    return Collections.emptyList();
  }
}
