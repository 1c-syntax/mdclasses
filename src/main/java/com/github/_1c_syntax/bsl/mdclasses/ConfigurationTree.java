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
import com.github._1c_syntax.bsl.mdo.form.CommonForm;
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

  //todo дописать для остальных

  /**
   * Роли
   */
  default List<Role> getRoles() {
    return getChildrenByType(Role.class);
  }

  /**
   * Общие реквизиты
   */
  default List<CommonAttribute> getCommonAttributes() {
    return getChildrenByType(CommonAttribute.class);
  }

  /**
   * Планы обмена
   */
  default List<ExchangePlan> getExchangePlans() {
    return getChildrenByType(ExchangePlan.class);
  }

  /**
   * Критерии отбора
   */
  default List<FilterCriterion> getFilterCriteria() {
    return getChildrenByType(FilterCriterion.class);
  }

  /**
   * Подписки на события
   */
  default List<EventSubscription> getEventSubscriptions() {
    return getChildrenByType(EventSubscription.class);
  }

  /**
   * Регламентные задания
   */
  default List<ScheduledJob> getScheduledJobs() {
    return getChildrenByType(ScheduledJob.class);
  }

  /**
   * Боты
   */
  default List<Bot> getBots() {
    return getChildrenByType(Bot.class);
  }

  /**
   * Функциональные опции
   */
  default List<FunctionalOption> getFunctionalOptions() {
    return getChildrenByType(FunctionalOption.class);
  }

  /**
   * Параметры функциональных опций
   */
  default List<FunctionalOptionsParameter> getFunctionalOptionsParameters() {
    return getChildrenByType(FunctionalOptionsParameter.class);
  }

  /**
   * Определяемые типы
   */
  default List<DefinedType> getDefinedTypes() {
    return getChildrenByType(DefinedType.class);
  }

  /**
   * Хранилища настроек
   */
  default List<SettingsStorage> getSettingsStorages() {
    return getChildrenByType(SettingsStorage.class);
  }

  /**
   * Общие формы
   */
  default List<CommonForm> getCommonForms() {
    return getChildrenByType(CommonForm.class);
  }

  /**
   * Общие команды
   */
  default List<CommonCommand> getCommonCommands() {
    return getChildrenByType(CommonCommand.class);
  }

  /**
   * Группы команд
   */
  default List<CommandGroup> getCommandGroups() {
    return getChildrenByType(CommandGroup.class);
  }

  /**
   * Общие макеты
   */
  default List<CommonTemplate> getCommonTemplates() {
    return getChildrenByType(CommonTemplate.class);
  }

  /**
   * Общие картинки
   */
  default List<CommonPicture> getCommonPictures() {
    return getChildrenByType(CommonPicture.class);
  }

  /**
   * Интерфейсы
   */
  default List<Interface> getInterfaces() {
    return getChildrenByType(Interface.class);
  }

  /**
   * XDTO-пакеты
   */
  default List<XdtoPackage> getXdtoPackages() {
    return getChildrenByType(XdtoPackage.class);
  }

  /**
   * Web-сервисы
   */
  default List<WebService> getWebServices() {
    return getChildrenByType(WebService.class);
  }

  /**
   * HTTP-сервисы
   */
  default List<HttpService> getHttpServices() {
    return getChildrenByType(HttpService.class);
  }

  /**
   * WS-ссылки
   */
  default List<WSReference> getWsReferences() {
    return getChildrenByType(WSReference.class);
  }

  /**
   * Сервисы интеграции
   */
  default List<IntegrationService> getIntegrationServices() {
    return getChildrenByType(IntegrationService.class);
  }

  /**
   * Элементы стиля
   */
  default List<StyleItem> getStyleItems() {
    return getChildrenByType(StyleItem.class);
  }

  /**
   * Стили
   */
  default List<Style> getStyles() {
    return getChildrenByType(Style.class);
  }

  /**
   * Языки
   */
  default List<Language> getLanguages() {
    return getChildrenByType(Language.class);
  }

  /**
   * Константы
   */
  default List<Constant> getConstants() {
    return getChildrenByType(Constant.class);
  }

  /**
   * Справочники
   */
  default List<Catalog> getCatalogs() {
    return getChildrenByType(Catalog.class);
  }

  /**
   * Документы
   */
  default List<Document> getDocuments() {
    return getChildrenByType(Document.class);
  }

  /**
   * Нумераторы
   */
  default List<DocumentNumerator> getDocumentNumerators() {
    return getChildrenByType(DocumentNumerator.class);
  }

  /**
   * Последовательности
   */
  default List<Sequence> getSequences() {
    return getChildrenByType(Sequence.class);
  }

  /**
   * Журналы документов
   */
  default List<DocumentJournal> getDocumentJournals() {
    return getChildrenByType(DocumentJournal.class);
  }

  /**
   * Перечисления
   */
  default List<Enum> getEnums() {
    return getChildrenByType(Enum.class);
  }

  /**
   * Отчеты
   */
  default List<Report> getReports() {
    return getChildrenByType(Report.class);
  }

  /**
   * Обработки
   */
  default List<DataProcessor> getDataProcessors() {
    return getChildrenByType(DataProcessor.class);
  }

  /**
   * Планы видов характеристик
   */
  default List<ChartOfCharacteristicTypes> getChartOfCharacteristicTypes() {
    return getChildrenByType(ChartOfCharacteristicTypes.class);
  }

  /**
   * Планы счетов
   */
  default List<ChartOfAccounts> getChartOfAccounts() {
    return getChildrenByType(ChartOfAccounts.class);
  }

  /**
   * Планы видов расчета
   */
  default List<ChartOfCalculationTypes> getChartOfCalculationTypes() {
    return getChildrenByType(ChartOfCalculationTypes.class);
  }

  /**
   * Регистры сведений
   */
  default List<InformationRegister> getInformationRegisters() {
    return getChildrenByType(InformationRegister.class);
  }

  /**
   * Регистры накопления
   */
  default List<AccumulationRegister> getAccumulationRegisters() {
    return getChildrenByType(AccumulationRegister.class);
  }

  /**
   * Регистры бухгалтерии
   */
  default List<AccountingRegister> getAccountingRegisters() {
    return getChildrenByType(AccountingRegister.class);
  }

  /**
   * Регистры расчета
   */
  default List<CalculationRegister> getCalculationRegisters() {
    return getChildrenByType(CalculationRegister.class);
  }

  /**
   * Бизнес-процессы
   */
  default List<BusinessProcess> getBusinessProcesses() {
    return getChildrenByType(BusinessProcess.class);
  }

  /**
   * Задачи
   */
  default List<Task> getTasks() {
    return getChildrenByType(Task.class);
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
