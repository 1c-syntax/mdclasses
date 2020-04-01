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
package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.utils.MDOUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class MetaDataObjectTest {
  private static final String SRC_EDT = "src/test/resources/metadata/edt/src";
  private static final String SRC_DESIGNER = "src/test/resources/metadata/original";

  @Test
  void metaDataObjectTestEDT() {

    var mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.ACCOUNTING_REGISTER, getMDOPathEDT("AccountingRegisters/РегистрБухгалтерии1/РегистрБухгалтерии1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof AccountingRegister).isTrue();
    assertThat(mdo.getName()).isEqualTo("РегистрБухгалтерии1");
    assertThat(mdo.getUuid()).isEqualTo("e5930f2f-15d9-48a1-ac69-379ad990b02a");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.ACCUMULATION_REGISTER, getMDOPathEDT("AccumulationRegisters/РегистрНакопления1/РегистрНакопления1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof AccumulationRegister).isTrue();
    assertThat(mdo.getName()).isEqualTo("РегистрНакопления1");
    assertThat(mdo.getUuid()).isEqualTo("8ea07f36-d671-4649-bc7a-94daa939e77f");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.BUSINESS_PROCESS, getMDOPathEDT("BusinessProcesses/БизнесПроцесс1/БизнесПроцесс1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof BusinessProcess).isTrue();
    assertThat(mdo.getName()).isEqualTo("БизнесПроцесс1");
    assertThat(mdo.getUuid()).isEqualTo("560a32ca-028d-4b88-b6f2-6b7212bf31f8");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.CALCULATION_REGISTER, getMDOPathEDT("CalculationRegisters/РегистрРасчета1/РегистрРасчета1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof CalculationRegister).isTrue();
    assertThat(mdo.getName()).isEqualTo("РегистрРасчета1");
    assertThat(mdo.getUuid()).isEqualTo("90587c7d-b950-4476-ac14-426e4a83d9c4");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.CATALOG, getMDOPathEDT("Catalogs/Справочник1/Справочник1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof Catalog).isTrue();
    assertThat(mdo.getName()).isEqualTo("Справочник1");
    assertThat(mdo.getUuid()).isEqualTo("eeef463d-d5e7-42f2-ae53-10279661f59d");
    assertThat(mdo.getForms()).isNotEmpty();
    assertThat(mdo.getForms()).hasSize(3);
    assertThat(mdo.getForms().stream().anyMatch(form -> form.getName().equals("ФормаЭлемента"))).isTrue();
    assertThat(mdo.getCommands()).isNotEmpty();
    assertThat(mdo.getCommands()).hasSize(1);
    assertThat(mdo.getCommands().stream().anyMatch(command -> command.getName().equals("Команда1"))).isTrue();

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.CHART_OF_ACCOUNTS, getMDOPathEDT("ChartsOfAccounts/ПланСчетов1/ПланСчетов1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof ChartOfAccounts).isTrue();
    assertThat(mdo.getName()).isEqualTo("ПланСчетов1");
    assertThat(mdo.getUuid()).isEqualTo("2766f353-abd2-4e7f-9a95-53f05c83f5d4");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.CHART_OF_CALCULATION_TYPES, getMDOPathEDT("ChartsOfCalculationTypes/ПланВидовРасчета1/ПланВидовРасчета1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof ChartOfCalculationTypes).isTrue();
    assertThat(mdo.getName()).isEqualTo("ПланВидовРасчета1");
    assertThat(mdo.getUuid()).isEqualTo("1755c534-9ccd-49c4-9f8b-2aa066424aaa");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.CHART_OF_CHARACTERISTIC_TYPES, getMDOPathEDT("ChartsOfCharacteristicTypes/ПланВидовХарактеристик1/ПланВидовХарактеристик1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof ChartOfCharacteristicTypes).isTrue();
    assertThat(mdo.getName()).isEqualTo("ПланВидовХарактеристик1");
    assertThat(mdo.getUuid()).isEqualTo("f53a24c3-f1dc-43b7-8dcf-eeb8c0b7f452");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.COMMAND_GROUP, getMDOPathEDT("CommandGroups/ГруппаКоманд1/ГруппаКоманд1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof CommandGroup).isTrue();
    assertThat(mdo.getName()).isEqualTo("ГруппаКоманд1");
    assertThat(mdo.getUuid()).isEqualTo("9bd3b0b1-b276-4b0e-9811-44a41ebb0c7c");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.COMMON_ATTRIBUTE, getMDOPathEDT("CommonAttributes/ОбщийРеквизит1/ОбщийРеквизит1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof CommonAttribute).isTrue();
    assertThat(mdo.getName()).isEqualTo("ОбщийРеквизит1");
    assertThat(mdo.getUuid()).isEqualTo("d4f0c0ac-ed26-4085-a1b4-e52314b973ad");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.COMMON_COMMAND, getMDOPathEDT("CommonCommands/ОбщаяКоманда1/ОбщаяКоманда1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof CommonCommand).isTrue();
    assertThat(mdo.getName()).isEqualTo("ОбщаяКоманда1");
    assertThat(mdo.getUuid()).isEqualTo("a608f796-f58e-4f8a-b63f-272342b32f35");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.COMMON_FORM, getMDOPathEDT("CommonForms/Форма/Форма.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof CommonForm).isTrue();
    assertThat(mdo.getName()).isEqualTo("Форма");
    assertThat(mdo.getUuid()).isEqualTo("5ac59104-28a5-40b1-ab5b-2857fb41991a");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.COMMON_MODULE, getMDOPathEDT("CommonModules/ГлобальныйОбщийМодуль/ГлобальныйОбщийМодуль.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof CommonModule).isTrue();
    assertThat(mdo.getName()).isEqualTo("ГлобальныйОбщийМодуль");
    assertThat(mdo.getUuid()).isEqualTo("9e9c021c-bdbd-4804-a53a-9442ba9eb18c");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.COMMON_PICTURE, getMDOPathEDT("CommonPictures/ОбщаяКартинка1/ОбщаяКартинка1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof CommonPicture).isTrue();
    assertThat(mdo.getName()).isEqualTo("ОбщаяКартинка1");
    assertThat(mdo.getUuid()).isEqualTo("db84513d-2535-494b-843e-6d8931cb2f82");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.COMMON_TEMPLATE, getMDOPathEDT("CommonTemplates/Макет/Макет.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof CommonTemplate).isTrue();
    assertThat(mdo.getName()).isEqualTo("Макет");
    assertThat(mdo.getUuid()).isEqualTo("799e0ae7-f5ea-4b50-8853-e2c58ef5d9cd");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.CONSTANT, getMDOPathEDT("Constants/Константа1/Константа1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof Constant).isTrue();
    assertThat(mdo.getName()).isEqualTo("Константа1");
    assertThat(mdo.getUuid()).isEqualTo("61e6a6f2-7057-4e93-96c3-7bd2559217f4");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.DATA_PROCESSOR, getMDOPathEDT("DataProcessors/Обработка1/Обработка1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof DataProcessor).isTrue();
    assertThat(mdo.getName()).isEqualTo("Обработка1");
    assertThat(mdo.getUuid()).isEqualTo("a7c57ba0-75d8-487d-b8ea-ae5083d8a503");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.DEFINED_TYPE, getMDOPathEDT("DefinedTypes/ОпределяемыйТип1/ОпределяемыйТип1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof DefinedType).isTrue();
    assertThat(mdo.getName()).isEqualTo("ОпределяемыйТип1");
    assertThat(mdo.getUuid()).isEqualTo("e8c616d9-4957-48ab-a917-afb6847f6840");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.DOCUMENT_JOURNAL, getMDOPathEDT("DocumentJournals/ЖурналДокументов1/ЖурналДокументов1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof DocumentJournal).isTrue();
    assertThat(mdo.getName()).isEqualTo("ЖурналДокументов1");
    assertThat(mdo.getUuid()).isEqualTo("c6743657-4787-40de-9a45-2493c630f626");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.DOCUMENT_NUMERATOR, getMDOPathEDT("DocumentNumerators/НумераторДокументов1/НумераторДокументов1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof DocumentNumerator).isTrue();
    assertThat(mdo.getName()).isEqualTo("НумераторДокументов1");
    assertThat(mdo.getUuid()).isEqualTo("e401f835-6bfc-4cd4-8d87-5e6b6332a3f6");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.DOCUMENT, getMDOPathEDT("Documents/Документ1/Документ1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof Document).isTrue();
    assertThat(mdo.getName()).isEqualTo("Документ1");
    assertThat(mdo.getUuid()).isEqualTo("ce4fb46b-4af7-493e-9fcb-76ad8c4f8acd");
    assertThat(mdo.getForms()).isNotNull();
    assertThat(mdo.getCommands()).isNotNull();
    var forms = mdo.getForms();
    assertThat(forms).hasSize(3);
    assertThat(forms.stream().anyMatch(form -> form.getName().equals("ФормаДокумента"))).isTrue();
    assertThat(forms.stream().anyMatch(form -> !form.getModulesByType().isEmpty())).isTrue();
    var commands = mdo.getCommands();
    assertThat(commands).hasSize(2);
    assertThat(commands.stream().anyMatch(command -> command.getName().equals("Команда2"))).isTrue();
    assertThat(commands.stream().anyMatch(command -> !command.getModulesByType().isEmpty())).isTrue();

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.ENUM, getMDOPathEDT("Enums/Перечисление1/Перечисление1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof MDOEnum).isTrue();
    assertThat(mdo.getName()).isEqualTo("Перечисление1");
    assertThat(mdo.getUuid()).isEqualTo("f11f3441-4b64-4344-b1a0-0e4b3e466e03");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.EVENT_SUBSCRIPTION, getMDOPathEDT("EventSubscriptions/ПодпискаНаСобытие1/ПодпискаНаСобытие1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof EventSubscription).isTrue();
    assertThat(mdo.getName()).isEqualTo("ПодпискаНаСобытие1");
    assertThat(mdo.getUuid()).isEqualTo("4da21a7b-3d07-4e6d-b91f-7e1c8ddcffcd");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.EXCHANGE_PLAN, getMDOPathEDT("ExchangePlans/ПланОбмена1/ПланОбмена1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof ExchangePlan).isTrue();
    assertThat(mdo.getName()).isEqualTo("ПланОбмена1");
    assertThat(mdo.getUuid()).isEqualTo("242cb07d-3d2b-4689-b590-d3ed23ac9d10");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.FILTER_CRITERION, getMDOPathEDT("FilterCriteria/КритерийОтбора1/КритерийОтбора1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof FilterCriterion).isTrue();
    assertThat(mdo.getName()).isEqualTo("КритерийОтбора1");
    assertThat(mdo.getUuid()).isEqualTo("6e9d3381-0607-43df-866d-14ee5d65a294");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.FUNCTIONAL_OPTION, getMDOPathEDT("FunctionalOptions/ФункциональнаяОпция1/ФункциональнаяОпция1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof FunctionalOption).isTrue();
    assertThat(mdo.getName()).isEqualTo("ФункциональнаяОпция1");
    assertThat(mdo.getUuid()).isEqualTo("d3b7fd71-6570-4047-91e0-b3df75dba08d");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.FUNCTIONAL_OPTIONS_PARAMETER, getMDOPathEDT("FunctionalOptionsParameters/ПараметрФункциональныхОпций/ПараметрФункциональныхОпций.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof FunctionalOptionsParameter).isTrue();
    assertThat(mdo.getName()).isEqualTo("ПараметрФункциональныхОпций");
    assertThat(mdo.getUuid()).isEqualTo("9fae7345-6220-4e5b-b4c1-84bb921a58b7");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.HTTP_SERVICE, getMDOPathEDT("HTTPServices/HTTPСервис1/HTTPСервис1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof HTTPService).isTrue();
    assertThat(mdo.getName()).isEqualTo("HTTPСервис1");
    assertThat(mdo.getUuid()).isEqualTo("3f029e1e-5a9e-4446-b74f-cbcb79b1e2fe");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.INFORMATION_REGISTER, getMDOPathEDT("InformationRegisters/РегистрСведений1/РегистрСведений1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof InformationRegister).isTrue();
    assertThat(mdo.getName()).isEqualTo("РегистрСведений1");
    assertThat(mdo.getUuid()).isEqualTo("184d9d78-9523-4cfa-9542-a7ba72efe4dd");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.REPORT, getMDOPathEDT("Reports/Отчет1/Отчет1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof Report).isTrue();
    assertThat(mdo.getName()).isEqualTo("Отчет1");
    assertThat(mdo.getUuid()).isEqualTo("34d3754d-298c-4786-92f6-a487db249fc7");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.ROLE, getMDOPathEDT("Roles/Роль1/Роль1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof Role).isTrue();
    assertThat(mdo.getName()).isEqualTo("Роль1");
    assertThat(mdo.getUuid()).isEqualTo("ecad0539-4f9f-491b-b0f2-f8f42d9a7c41");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.SCHEDULED_JOB, getMDOPathEDT("ScheduledJobs/РегламентноеЗадание1/РегламентноеЗадание1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof ScheduledJob).isTrue();
    assertThat(mdo.getName()).isEqualTo("РегламентноеЗадание1");
    assertThat(mdo.getUuid()).isEqualTo("0de0c839-4427-46d9-be68-302f88ac162c");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.SEQUENCE, getMDOPathEDT("Sequences/Последовательность1/Последовательность1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof Sequence).isTrue();
    assertThat(mdo.getName()).isEqualTo("Последовательность1");
    assertThat(mdo.getUuid()).isEqualTo("514bbcf4-7fc4-4a3e-9245-598fad397eec");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.SESSION_PARAMETER, getMDOPathEDT("SessionParameters/ПараметрСеанса1/ПараметрСеанса1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof SessionParameter).isTrue();
    assertThat(mdo.getName()).isEqualTo("ПараметрСеанса1");
    assertThat(mdo.getUuid()).isEqualTo("66844df5-823b-40f1-ab8a-b07c1cb7462f");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.SETTINGS_STORAGE, getMDOPathEDT("SettingsStorages/ХранилищеНастроек1/ХранилищеНастроек1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof SettingsStorage).isTrue();
    assertThat(mdo.getName()).isEqualTo("ХранилищеНастроек1");
    assertThat(mdo.getUuid()).isEqualTo("e7a9947d-7565-4681-b75c-c9a229b45042");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.STYLE_ITEM, getMDOPathEDT("StyleItems/ЭлементСтиля1/ЭлементСтиля1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof StyleItem).isTrue();
    assertThat(mdo.getName()).isEqualTo("ЭлементСтиля1");
    assertThat(mdo.getUuid()).isEqualTo("68047ae8-62aa-4696-9780-d364feb3cc10");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.STYLE, getMDOPathEDT("Styles/Стиль/Стиль.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof Style).isTrue();
    assertThat(mdo.getName()).isEqualTo("Стиль");
    assertThat(mdo.getUuid()).isEqualTo("d6aaa851-cba7-486d-92f4-ab31b1628c6b");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.SUBSYSTEM, getMDOPathEDT("Subsystems/ПерваяПодсистема/ПерваяПодсистема.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof Subsystem).isTrue();
    assertThat(mdo.getName()).isEqualTo("ПерваяПодсистема");
    assertThat(mdo.getUuid()).isEqualTo("3d00f7d6-e3b0-49cf-8093-e2e4f6ea2293");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.TASK, getMDOPathEDT("Tasks/Задача1/Задача1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof Task).isTrue();
    assertThat(mdo.getName()).isEqualTo("Задача1");
    assertThat(mdo.getUuid()).isEqualTo("c251fcec-ec02-4ef4-8f70-4d70db6631ea");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.WEB_SERVICE, getMDOPathEDT("WebServices/WebСервис1/WebСервис1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof WebService).isTrue();
    assertThat(mdo.getName()).isEqualTo("WebСервис1");
    assertThat(mdo.getUuid()).isEqualTo("d7f9b06b-0799-486e-adff-c45a2d5b8101");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.WS_REFERENCE, getMDOPathEDT("WSReferences/WSСсылка/WSСсылка.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof WSReference).isTrue();
    assertThat(mdo.getName()).isEqualTo("WSСсылка");
    assertThat(mdo.getUuid()).isEqualTo("95b745f2-e1fa-4f94-b7f9-f3f0224fc8c7");

    mdo = MDOUtils.getMDObject(ConfigurationSource.EDT, MDOType.XDTO_PACKAGE, getMDOPathEDT("XDTOPackages/ПакетXDTO1/ПакетXDTO1.mdo"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof XDTOPackage).isTrue();
    assertThat(mdo.getName()).isEqualTo("ПакетXDTO1");
    assertThat(mdo.getUuid()).isEqualTo("b8a93cce-56e4-4507-b281-5c525a466a0f");
  }

  @Test
  void metaDataObjectTestDesigner() {

    var mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.ACCOUNTING_REGISTER, getMDOPathDesigner("AccountingRegisters/РегистрБухгалтерии1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof AccountingRegister).isTrue();
    assertThat(mdo.getName()).isEqualTo("РегистрБухгалтерии1");
    assertThat(mdo.getUuid()).isEqualTo("e5930f2f-15d9-48a1-ac69-379ad990b02a");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.ACCUMULATION_REGISTER, getMDOPathDesigner("AccumulationRegisters/РегистрНакопления1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof AccumulationRegister).isTrue();
    assertThat(mdo.getName()).isEqualTo("РегистрНакопления1");
    assertThat(mdo.getUuid()).isEqualTo("8ea07f36-d671-4649-bc7a-94daa939e77f");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.BUSINESS_PROCESS, getMDOPathDesigner("BusinessProcesses/БизнесПроцесс1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof BusinessProcess).isTrue();
    assertThat(mdo.getName()).isEqualTo("БизнесПроцесс1");
    assertThat(mdo.getUuid()).isEqualTo("560a32ca-028d-4b88-b6f2-6b7212bf31f8");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.CALCULATION_REGISTER, getMDOPathDesigner("CalculationRegisters/РегистрРасчета1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof CalculationRegister).isTrue();
    assertThat(mdo.getName()).isEqualTo("РегистрРасчета1");
    assertThat(mdo.getUuid()).isEqualTo("90587c7d-b950-4476-ac14-426e4a83d9c4");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.CATALOG, getMDOPathDesigner("Catalogs/Справочник1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof Catalog).isTrue();
    assertThat(mdo.getName()).isEqualTo("Справочник1");
    assertThat(mdo.getUuid()).isEqualTo("eeef463d-d5e7-42f2-ae53-10279661f59d");
    assertThat(mdo.getForms()).isNotEmpty();
    assertThat(mdo.getForms()).hasSize(3);
    assertThat(mdo.getForms().stream().anyMatch(form -> form.getName().equals("ФормаЭлемента"))).isTrue();
    assertThat(mdo.getCommands()).isNotEmpty();
    assertThat(mdo.getCommands()).hasSize(1);
    assertThat(mdo.getCommands().stream().anyMatch(command -> command.getName().equals("Команда1"))).isTrue();

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.CHART_OF_ACCOUNTS, getMDOPathDesigner("ChartsOfAccounts/ПланСчетов1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof ChartOfAccounts).isTrue();
    assertThat(mdo.getName()).isEqualTo("ПланСчетов1");
    assertThat(mdo.getUuid()).isEqualTo("2766f353-abd2-4e7f-9a95-53f05c83f5d4");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.CHART_OF_CALCULATION_TYPES, getMDOPathDesigner("ChartsOfCalculationTypes/ПланВидовРасчета1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof ChartOfCalculationTypes).isTrue();
    assertThat(mdo.getName()).isEqualTo("ПланВидовРасчета1");
    assertThat(mdo.getUuid()).isEqualTo("1755c534-9ccd-49c4-9f8b-2aa066424aaa");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.CHART_OF_CHARACTERISTIC_TYPES, getMDOPathDesigner("ChartsOfCharacteristicTypes/ПланВидовХарактеристик1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof ChartOfCharacteristicTypes).isTrue();
    assertThat(mdo.getName()).isEqualTo("ПланВидовХарактеристик1");
    assertThat(mdo.getUuid()).isEqualTo("f53a24c3-f1dc-43b7-8dcf-eeb8c0b7f452");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.COMMAND_GROUP, getMDOPathDesigner("CommandGroups/ГруппаКоманд1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof CommandGroup).isTrue();
    assertThat(mdo.getName()).isEqualTo("ГруппаКоманд1");
    assertThat(mdo.getUuid()).isEqualTo("9bd3b0b1-b276-4b0e-9811-44a41ebb0c7c");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.COMMON_ATTRIBUTE, getMDOPathDesigner("CommonAttributes/ОбщийРеквизит1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof CommonAttribute).isTrue();
    assertThat(mdo.getName()).isEqualTo("ОбщийРеквизит1");
    assertThat(mdo.getUuid()).isEqualTo("d4f0c0ac-ed26-4085-a1b4-e52314b973ad");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.COMMON_COMMAND, getMDOPathDesigner("CommonCommands/ОбщаяКоманда1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof CommonCommand).isTrue();
    assertThat(mdo.getName()).isEqualTo("ОбщаяКоманда1");
    assertThat(mdo.getUuid()).isEqualTo("a608f796-f58e-4f8a-b63f-272342b32f35");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.COMMON_FORM, getMDOPathDesigner("CommonForms/Форма.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof CommonForm).isTrue();
    assertThat(mdo.getName()).isEqualTo("Форма");
    assertThat(mdo.getUuid()).isEqualTo("5ac59104-28a5-40b1-ab5b-2857fb41991a");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.COMMON_MODULE, getMDOPathDesigner("CommonModules/ГлобальныйОбщийМодуль.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof CommonModule).isTrue();
    assertThat(mdo.getName()).isEqualTo("ГлобальныйОбщийМодуль");
    assertThat(mdo.getUuid()).isEqualTo("9e9c021c-bdbd-4804-a53a-9442ba9eb18c");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.COMMON_PICTURE, getMDOPathDesigner("CommonPictures/ОбщаяКартинка1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof CommonPicture).isTrue();
    assertThat(mdo.getName()).isEqualTo("ОбщаяКартинка1");
    assertThat(mdo.getUuid()).isEqualTo("db84513d-2535-494b-843e-6d8931cb2f82");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.COMMON_TEMPLATE, getMDOPathDesigner("CommonTemplates/Макет.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof CommonTemplate).isTrue();
    assertThat(mdo.getName()).isEqualTo("Макет");
    assertThat(mdo.getUuid()).isEqualTo("799e0ae7-f5ea-4b50-8853-e2c58ef5d9cd");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.CONSTANT, getMDOPathDesigner("Constants/Константа1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof Constant).isTrue();
    assertThat(mdo.getName()).isEqualTo("Константа1");
    assertThat(mdo.getUuid()).isEqualTo("61e6a6f2-7057-4e93-96c3-7bd2559217f4");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.DATA_PROCESSOR, getMDOPathDesigner("DataProcessors/Обработка1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof DataProcessor).isTrue();
    assertThat(mdo.getName()).isEqualTo("Обработка1");
    assertThat(mdo.getUuid()).isEqualTo("a7c57ba0-75d8-487d-b8ea-ae5083d8a503");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.DEFINED_TYPE, getMDOPathDesigner("DefinedTypes/ОпределяемыйТип1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof DefinedType).isTrue();
    assertThat(mdo.getName()).isEqualTo("ОпределяемыйТип1");
    assertThat(mdo.getUuid()).isEqualTo("e8c616d9-4957-48ab-a917-afb6847f6840");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.DOCUMENT_JOURNAL, getMDOPathDesigner("DocumentJournals/ЖурналДокументов1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof DocumentJournal).isTrue();
    assertThat(mdo.getName()).isEqualTo("ЖурналДокументов1");
    assertThat(mdo.getUuid()).isEqualTo("c6743657-4787-40de-9a45-2493c630f626");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.DOCUMENT_NUMERATOR, getMDOPathDesigner("DocumentNumerators/НумераторДокументов1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof DocumentNumerator).isTrue();
    assertThat(mdo.getName()).isEqualTo("НумераторДокументов1");
    assertThat(mdo.getUuid()).isEqualTo("e401f835-6bfc-4cd4-8d87-5e6b6332a3f6");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.DOCUMENT, getMDOPathDesigner("Documents/Документ1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof Document).isTrue();
    assertThat(mdo.getName()).isEqualTo("Документ1");
    assertThat(mdo.getUuid()).isEqualTo("ce4fb46b-4af7-493e-9fcb-76ad8c4f8acd");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.DOCUMENT, getMDOPathDesigner("Documents/ПоступлениеТоваровУслуг.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof Document).isTrue();
    assertThat(mdo.getForms()).isNotNull();
    var forms = mdo.getForms();
    assertThat(forms).hasSize(6);
    assertThat(forms.stream().anyMatch(form -> form.getName().equals("ФормаПодбораТоваров�?зЗаказа"))).isTrue();
    assertThat(forms.stream().anyMatch(form -> !form.getModulesByType().isEmpty())).isTrue();
    assertThat(mdo.getCommands()).isNotNull();
    var commands = mdo.getCommands();
    assertThat(commands).hasSize(2);
    assertThat(commands.stream().anyMatch(command -> command.getName().equals("ДокументыПоступления"))).isTrue();
    assertThat(commands.stream().anyMatch(command -> !command.getModulesByType().isEmpty())).isTrue();

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.ENUM, getMDOPathDesigner("Enums/Перечисление1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof MDOEnum).isTrue();
    assertThat(mdo.getName()).isEqualTo("Перечисление1");
    assertThat(mdo.getUuid()).isEqualTo("f11f3441-4b64-4344-b1a0-0e4b3e466e03");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.EVENT_SUBSCRIPTION, getMDOPathDesigner("EventSubscriptions/ПодпискаНаСобытие1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof EventSubscription).isTrue();
    assertThat(mdo.getName()).isEqualTo("ПодпискаНаСобытие1");
    assertThat(mdo.getUuid()).isEqualTo("4da21a7b-3d07-4e6d-b91f-7e1c8ddcffcd");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.EXCHANGE_PLAN, getMDOPathDesigner("ExchangePlans/ПланОбмена1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof ExchangePlan).isTrue();
    assertThat(mdo.getName()).isEqualTo("ПланОбмена1");
    assertThat(mdo.getUuid()).isEqualTo("242cb07d-3d2b-4689-b590-d3ed23ac9d10");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.FILTER_CRITERION, getMDOPathDesigner("FilterCriteria/КритерийОтбора1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof FilterCriterion).isTrue();
    assertThat(mdo.getName()).isEqualTo("КритерийОтбора1");
    assertThat(mdo.getUuid()).isEqualTo("6e9d3381-0607-43df-866d-14ee5d65a294");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.FUNCTIONAL_OPTION, getMDOPathDesigner("FunctionalOptions/ФункциональнаяОпция1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof FunctionalOption).isTrue();
    assertThat(mdo.getName()).isEqualTo("ФункциональнаяОпция1");
    assertThat(mdo.getUuid()).isEqualTo("d3b7fd71-6570-4047-91e0-b3df75dba08d");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.FUNCTIONAL_OPTIONS_PARAMETER, getMDOPathDesigner("FunctionalOptionsParameters/ПараметрФункциональныхОпций1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof FunctionalOptionsParameter).isTrue();
    assertThat(mdo.getName()).isEqualTo("ПараметрФункциональныхОпций1");
    assertThat(mdo.getUuid()).isEqualTo("8e2f9f0c-8727-4ffc-a6ea-f510b37814eb");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.HTTP_SERVICE, getMDOPathDesigner("HTTPServices/HTTPСервис1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof HTTPService).isTrue();
    assertThat(mdo.getName()).isEqualTo("HTTPСервис1");
    assertThat(mdo.getUuid()).isEqualTo("3f029e1e-5a9e-4446-b74f-cbcb79b1e2fe");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.INFORMATION_REGISTER, getMDOPathDesigner("InformationRegisters/РегистрСведений1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof InformationRegister).isTrue();
    assertThat(mdo.getName()).isEqualTo("РегистрСведений1");
    assertThat(mdo.getUuid()).isEqualTo("184d9d78-9523-4cfa-9542-a7ba72efe4dd");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.REPORT, getMDOPathDesigner("Reports/Отчет1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof Report).isTrue();
    assertThat(mdo.getName()).isEqualTo("Отчет1");
    assertThat(mdo.getUuid()).isEqualTo("34d3754d-298c-4786-92f6-a487db249fc7");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.ROLE, getMDOPathDesigner("Roles/Роль1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof Role).isTrue();
    assertThat(mdo.getName()).isEqualTo("Роль1");
    assertThat(mdo.getUuid()).isEqualTo("ecad0539-4f9f-491b-b0f2-f8f42d9a7c41");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.SCHEDULED_JOB, getMDOPathDesigner("ScheduledJobs/РегламентноеЗадание1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof ScheduledJob).isTrue();
    assertThat(mdo.getName()).isEqualTo("РегламентноеЗадание1");
    assertThat(mdo.getUuid()).isEqualTo("0de0c839-4427-46d9-be68-302f88ac162c");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.SEQUENCE, getMDOPathDesigner("Sequences/Последовательность1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof Sequence).isTrue();
    assertThat(mdo.getName()).isEqualTo("Последовательность1");
    assertThat(mdo.getUuid()).isEqualTo("514bbcf4-7fc4-4a3e-9245-598fad397eec");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.SESSION_PARAMETER, getMDOPathDesigner("SessionParameters/ПараметрСеанса1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof SessionParameter).isTrue();
    assertThat(mdo.getName()).isEqualTo("ПараметрСеанса1");
    assertThat(mdo.getUuid()).isEqualTo("66844df5-823b-40f1-ab8a-b07c1cb7462f");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.SETTINGS_STORAGE, getMDOPathDesigner("SettingsStorages/ХранилищеНастроек1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof SettingsStorage).isTrue();
    assertThat(mdo.getName()).isEqualTo("ХранилищеНастроек1");
    assertThat(mdo.getUuid()).isEqualTo("e7a9947d-7565-4681-b75c-c9a229b45042");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.STYLE_ITEM, getMDOPathDesigner("StyleItems/ЭлементСтиля1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof StyleItem).isTrue();
    assertThat(mdo.getName()).isEqualTo("ЭлементСтиля1");
    assertThat(mdo.getUuid()).isEqualTo("68047ae8-62aa-4696-9780-d364feb3cc10");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.STYLE, getMDOPathDesigner("Styles/Стиль1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof Style).isTrue();
    assertThat(mdo.getName()).isEqualTo("Стиль1");
    assertThat(mdo.getUuid()).isEqualTo("2ef7f6ca-b11c-4e2d-a233-5c5b01675e9a");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.SUBSYSTEM, getMDOPathDesigner("Subsystems/ПерваяПодсистема.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof Subsystem).isTrue();
    assertThat(mdo.getName()).isEqualTo("ПерваяПодсистема");
    assertThat(mdo.getUuid()).isEqualTo("3d00f7d6-e3b0-49cf-8093-e2e4f6ea2293");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.TASK, getMDOPathDesigner("Tasks/Задача1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof Task).isTrue();
    assertThat(mdo.getName()).isEqualTo("Задача1");
    assertThat(mdo.getUuid()).isEqualTo("c251fcec-ec02-4ef4-8f70-4d70db6631ea");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.WEB_SERVICE, getMDOPathDesigner("WebServices/WebСервис1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof WebService).isTrue();
    assertThat(mdo.getName()).isEqualTo("WebСервис1");
    assertThat(mdo.getUuid()).isEqualTo("d7f9b06b-0799-486e-adff-c45a2d5b8101");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.WS_REFERENCE, getMDOPathDesigner("WSReferences/WSСсылка1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof WSReference).isTrue();
    assertThat(mdo.getName()).isEqualTo("WSСсылка1");
    assertThat(mdo.getUuid()).isEqualTo("7b8d6924-7aa9-4699-b794-6797c79d83c7");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.XDTO_PACKAGE, getMDOPathDesigner("XDTOPackages/ПакетXDTO1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof XDTOPackage).isTrue();
    assertThat(mdo.getName()).isEqualTo("ПакетXDTO1");
    assertThat(mdo.getUuid()).isEqualTo("b8a93cce-56e4-4507-b281-5c525a466a0f");

    // only designer
    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.LANGUAGE, getMDOPathDesigner("Languages/Русский.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof Language).isTrue();
    assertThat(mdo.getName()).isEqualTo("Русский");
    assertThat(mdo.getUuid()).isEqualTo("1b5f5cd6-14b2-422e-ab6c-1103fd375982");

    mdo = MDOUtils.getMDObject(ConfigurationSource.DESIGNER, MDOType.INTERFACE, getMDOPathDesigner("Interfaces/�?нтерфейс1.xml"));
    assertThat(mdo).isNotNull();
    assertThat(mdo instanceof MDOInterface).isTrue();
    assertThat(mdo.getName()).isEqualTo("�?нтерфейс1");
    assertThat(mdo.getUuid()).isEqualTo("874d641c-12f7-4db7-bde2-dd72c3d5b522");
  }

  @Test
  void childrenTest() {
    var allChildren = MDOUtils.getAllChildren(ConfigurationSource.EDT,
      Paths.get(FilenameUtils.getPath(SRC_EDT)), false);
    assertThat(allChildren).isNotNull();
    assertThat(allChildren).hasSize(48);

    allChildren = MDOUtils.getAllChildren(ConfigurationSource.DESIGNER,
      Paths.get(SRC_DESIGNER), true);
    assertThat(allChildren).isNotNull();
    assertThat(allChildren).hasSize(54);
  }

  private Path getMDOPathEDT(String path) {
    return Paths.get(SRC_EDT, path);
  }

  private Path getMDOPathDesigner(String path) {
    return Paths.get(SRC_DESIGNER, path);
  }
}