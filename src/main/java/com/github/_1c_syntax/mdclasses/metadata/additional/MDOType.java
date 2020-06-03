/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2020
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
package com.github._1c_syntax.mdclasses.metadata.additional;

import org.apache.commons.collections4.map.CaseInsensitiveMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Типы объектов метаданных
 */
public enum MDOType {
  ACCOUNTING_REGISTER("AccountingRegister", "AccountingRegisters", "РегистрБухгалтерии", "РегистрыБухгалтерии"),
  ACCUMULATION_REGISTER("AccumulationRegister", "AccumulationRegisters", "РегистрНакопления", "РегистрыНакопления"),
  BUSINESS_PROCESS("BusinessProcess", "BusinessProcesses", "БизнесПроцесс", "БизнесПроцессы"),
  CALCULATION_REGISTER("CalculationRegister", "CalculationRegisters", "РегистрРасчета", "РегистрыРасчета"),
  CATALOG("Catalog", "Catalogs", "Справочник", "Справочники"),
  CHART_OF_ACCOUNTS("ChartOfAccounts", "ChartsOfAccounts", "ПланСчетов", "ПланыСчетов"),
  CHART_OF_CALCULATION_TYPES("ChartOfCalculationTypes", "ChartsOfCalculationTypes", "ПланВидовРасчета", "ПланыВидовРасчета"),
  CHART_OF_CHARACTERISTIC_TYPES("ChartOfCharacteristicTypes", "ChartsOfCharacteristicTypes", "ПланВидовХарактеристик", "ПланыВидовХарактеристик"),
  COMMAND_GROUP("CommandGroup", "CommandGroups", "ГруппаКоманд", "ГруппыКоманд"),
  COMMON_ATTRIBUTE("CommonAttribute", "CommonAttributes", "ОбщийРеквизит", "ОбщиеРеквизиты"),
  COMMON_COMMAND("CommonCommand", "CommonCommands", "ОбщаяКоманда", "ОбщиеКоманды"),
  COMMON_FORM("CommonForm", "CommonForms", "ОбщаяФорма", "ОбщиеФормы"),
  COMMON_MODULE("CommonModule", "CommonModules", "ОбщийМодуль", "ОбщиеМодули"),
  COMMON_PICTURE("CommonPicture", "CommonPictures", "ОбщаяКартика", "ОбщиеКартинки"),
  COMMON_TEMPLATE("CommonTemplate", "CommonTemplates", "ОбщийМакет", "ОбщиеМакеты"),
  CONFIGURATION("Configuration", "", "Конфигурация", ""),
  CONSTANT("Constant", "Constants", "Константа", "Константы"),
  DATA_PROCESSOR("DataProcessor", "DataProcessors", "Обработка", "Обработки"),
  DEFINED_TYPE("DefinedType", "DefinedTypes", "ОпределяемыйТип", "ОпределяемыеТипы"),
  DOCUMENT_JOURNAL("DocumentJournal", "DocumentJournals", "ЖурналДокументов", "ЖурналыДокументов"),
  DOCUMENT_NUMERATOR("DocumentNumerator", "DocumentNumerators", "Нумератор", "Нумераторы"),
  DOCUMENT("Document", "Documents", "Документ", "Документы"),
  ENUM("Enum", "Enums", "Перечисление", "Перечисления"),
  EVENT_SUBSCRIPTION("EventSubscription", "EventSubscriptions", "ПодпискаНаСобытие", "ПодпискиНаСобытия"),
  EXCHANGE_PLAN("ExchangePlan", "ExchangePlans", "ПланОбмена", "ПланыОбмена"),
  FILTER_CRITERION("FilterCriterion", "FilterCriteria", "КритерийОтбора", "КритерииОтбора"),
  FUNCTIONAL_OPTION("FunctionalOption", "FunctionalOptions", "ФункциональнаяОпция", "ФункциональныеОпции"),
  FUNCTIONAL_OPTIONS_PARAMETER("FunctionalOptionsParameter", "FunctionalOptionsParameters", "ПараметрФункциональныхОпций", "ПараметрыФункциональныхОпций"),
  HTTP_SERVICE("HTTPService", "HTTPServices", "HTTPСервис", "HTTPСервисы"),
  INFORMATION_REGISTER("InformationRegister", "InformationRegisters", "РегистрСведений", "РегистрыСведений"),
  INTERFACE("Interface", "Interfaces", "Интерфейс", "Интерфейсы"),
  LANGUAGE("Language", "Languages", "Язык", "Языки"),
  REPORT("Report", "Reports", "Отчет", "Отчеты"),
  ROLE("Role", "Roles", "Роль", "Роли"),
  SCHEDULED_JOB("ScheduledJob", "ScheduledJobs", "РегламентноеЗадание", "РегламентныеЗадания"),
  SEQUENCE("Sequence", "Sequences", "Последовательность", "Последовательности"),
  SESSION_PARAMETER("SessionParameter", "SessionParameters", "ПараметрСеанса", "ПараметрыСеанса"),
  SETTINGS_STORAGE("SettingsStorage", "SettingsStorages", "ХранилищеНастроек", "ХранилищаНастроек"),
  STYLE_ITEM("StyleItem", "StyleItems", "ЭлементСтиля", "ЭлементыСтиля"),
  STYLE("Style", "Styles", "Стиль", "Стили"),
  SUBSYSTEM("Subsystem", "Subsystems", "Подсистема", "Подсистемы"),
  TASK("Task", "Tasks", "Задача", "Задачи"),
  WEB_SERVICE("WebService", "WebServices", "WebСервис", "WebСервисы"),
  WS_REFERENCE("WSReference", "WSReferences", "WSСсылка", "WSСсылки"),
  XDTO_PACKAGE("XDTOPackage", "XDTOPackages", "ПакетXDTO", "ПакетыXDTO"),

  FORM("Form", "Forms", "Форма", "Формы"),
  COMMAND("Command", "Commands", "Команда", "Команды"),
  TEMPLATE("Template", "Templates", "Макет", "Макеты"),
  ATTRIBUTE("Attribute", "Attributes", "Реквизит", "Реквизиты"),
  UNKNOWN("", "", "", "");

  private final String name;
  private final String groupName;
  private final String nameRu;
  private final String groupNameRu;

  private static final Map<String, MDOType> mapTypes = computeMapTypes();
  private static final Set<MDOType> childTypes = computeChildTypes();

  MDOType(String nameEn, String groupNameEn, String nameRu, String groupNameRu) {
    this.name = nameEn;
    this.groupName = groupNameEn;
    this.nameRu = nameRu;
    this.groupNameRu = groupNameRu;
  }

  public String getName() {
    return name;
  }

  public String getGroupName() {
    return groupName;
  }

  public String getNameRu() {
    return nameRu;
  }

  public String getGroupNameRu() {
    return groupNameRu;
  }

  /**
   * Возвращает список элементов перечисления с возможностью фильтрации
   *
   * @param withoutChildren - возможность исключить дочерние типы
   * @return - список с примененным фильтром
   */
  public static List<MDOType> values(boolean withoutChildren) {
    if (withoutChildren) {
      return Arrays.stream(values()).filter(mdoType ->
        !childTypes.contains(mdoType) && mdoType != UNKNOWN)
        .collect(Collectors.toList());
    }
    return Arrays.asList(values());
  }

  /**
   * Возвращает MDOType по строковому идентификатору
   *
   * @param value - Строковый идентификатор типа. Может быть на русском или английском языках,
   *              а так же во множественном или единственном числе
   * @return - Найденный тип
   */
  public static Optional<MDOType> fromValue(String value) {
    return Optional.ofNullable(mapTypes.get(value));
  }

  private static Map<String, MDOType> computeMapTypes() {
    Map<String, MDOType> map = new CaseInsensitiveMap<>();
    for (MDOType mdoType : MDOType.values()) {
      map.put(mdoType.getName(), mdoType);
      map.put(mdoType.getGroupName(), mdoType);
      map.put(mdoType.getNameRu(), mdoType);
      map.put(mdoType.getGroupNameRu(), mdoType);
    }
    return map;
  }

  private static Set<MDOType> computeChildTypes() {
    return Set.of(FORM, COMMAND, TEMPLATE, ATTRIBUTE);
  }

}