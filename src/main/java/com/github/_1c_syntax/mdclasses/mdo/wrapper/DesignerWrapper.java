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
package com.github._1c_syntax.mdclasses.mdo.wrapper;

import com.github._1c_syntax.mdclasses.unmarshal.annotation.TypeAlias;


/**
 * Класс-обертка для чтения xml описания объектов в формате конфигуратора
 */
@TypeAlias(designerName = "MetaDataObject")
public class DesignerWrapper {
}

//
///**
// * Класс-обертка для чтения xml описания объектов в формате конфигуратора
// */
//@Data
//@Slf4j
//public class DesignerWrapper {
//
//  private static final Map<MDOType, Class<?>> TYPE_CLASSES = computeTypesClasses();
//
////  @XStreamAlias("AccountingRegister")
////  protected DesignerMDO accountingRegister;
////  @XStreamAlias("AccumulationRegister")
////  protected DesignerMDO accumulationRegister;
////  @XStreamAlias("BusinessProcess")
////  protected DesignerMDO businessProcess;
////  @XStreamAlias("CalculationRegister")
////  protected DesignerMDO calculationRegister;
////  @XStreamAlias("Catalog")
////  protected DesignerMDO catalog;
////  @XStreamAlias("ChartOfAccounts")
////  protected DesignerMDO chartOfAccounts;
////  @XStreamAlias("ChartOfCalculationTypes")
////  protected DesignerMDO chartOfCalculationTypes;
////  @XStreamAlias("ChartOfCharacteristicTypes")
////  protected DesignerMDO chartOfCharacteristicTypes;
////  @XStreamAlias("CommandGroup")
////  protected DesignerMDO commandGroup;
////  @XStreamAlias("CommonAttribute")
////  protected DesignerMDO commonAttribute;
////  @XStreamAlias("CommonCommand")
////  protected DesignerMDO commonCommand;
////  @XStreamAlias("CommonForm")
////  protected DesignerMDO commonForm;
////  @XStreamAlias("CommonModule")
////  protected DesignerMDO commonModule;
////  @XStreamAlias("CommonPicture")
////  protected DesignerMDO commonPicture;
////  @XStreamAlias("CommonTemplate")
////  protected DesignerMDO commonTemplate;
////  @XStreamAlias("Configuration")
////  protected DesignerMDO configuration;
////  @XStreamAlias("Constant")
////  protected DesignerMDO constant;
////  @XStreamAlias("Cube")
////  protected DesignerMDO cube;
////  @XStreamAlias("DataProcessor")
////  protected DesignerMDO dataProcessor;
////  @XStreamAlias("DefinedType")
////  protected DesignerMDO definedType;
////  @XStreamAlias("DimensionTable")
////  protected DesignerMDO dimensionTable;
////  @XStreamAlias("Document")
////  protected DesignerMDO document;
////  @XStreamAlias("DocumentJournal")
////  protected DesignerMDO documentJournal;
////  @XStreamAlias("DocumentNumerator")
////  protected DesignerMDO documentNumerator;
////  @XStreamAlias("Enum")
////  protected DesignerMDO _enum;
////  @XStreamAlias("EventSubscription")
////  protected DesignerMDO eventSubscription;
////  @XStreamAlias("ExchangePlan")
////  protected DesignerMDO exchangePlan;
////  @XStreamAlias("ExternalDataProcessor")
////  protected DesignerMDO externalDataProcessor;
////  @XStreamAlias("ExternalDataSource")
////  protected DesignerMDO externalDataSource;
////  @XStreamAlias("ExternalReport")
////  protected DesignerMDO externalReport;
////  @XStreamAlias("FilterCriterion")
////  protected DesignerMDO filterCriterion;
////  @XStreamAlias("Form")
////  protected DesignerMDO form;
////  @XStreamAlias("FunctionalOption")
////  protected DesignerMDO functionalOption;
////  @XStreamAlias("FunctionalOptionsParameter")
////  protected DesignerMDO functionalOptionsParameter;
////  @XStreamAlias("HTTPService")
////  protected DesignerMDO httpService;
////  @XStreamAlias("InformationRegister")
////  protected DesignerMDO informationRegister;
////  @XStreamAlias("Interface")
////  protected DesignerMDO _interface;
////  @XStreamAlias("Language")
////  protected DesignerMDO language;
////  @XStreamAlias("Recalculation")
////  protected DesignerMDO recalculation;
////  @XStreamAlias("Report")
////  protected DesignerMDO report;
////  @XStreamAlias("Role")
////  protected DesignerMDO role;
////  @XStreamAlias("ScheduledJob")
////  protected DesignerMDO scheduledJob;
////  @XStreamAlias("Sequence")
////  protected DesignerMDO sequence;
////  @XStreamAlias("SessionParameter")
////  protected DesignerMDO sessionParameter;
////  @XStreamAlias("SettingsStorage")
////  protected DesignerMDO settingsStorage;
////  @XStreamAlias("Style")
////  protected DesignerMDO style;
////  @XStreamAlias("StyleItem")
////  protected DesignerMDO styleItem;
////  @XStreamAlias("Subsystem")
////  protected DesignerMDO subsystem;
////  @XStreamAlias("Table")
////  protected DesignerMDO table;
////  @XStreamAlias("Task")
////  protected DesignerMDO task;
////  @XStreamAlias("Template")
////  protected DesignerMDO template;
////  @XStreamAlias("WSReference")
////  protected DesignerMDO wsReference;
////  @XStreamAlias("WebService")
////  protected DesignerMDO webService;
////  @XStreamAlias("XDTOPackage")
////  protected DesignerMDO xdtoPackage;
//
//  /**
//   * @param type - Тип читаемого объекта, по нему определяется имя свойства
//   * @return - Возвращает MDObjectBase объект
//   */
//  public Optional<MDObjectBase> getPropertyByType(MDOType type, Path mdoPath) {
////    var value = getPropertyValue(type);
////    if (value.isPresent()) {
////      Class<?> clazz = TYPE_CLASSES.get(type);
////      if (clazz != null) {
////        var designerMDO = value.get();
////        designerMDO.setMdoPath(mdoPath);
////        try {
////          return Optional.of((MDObjectBase) clazz.getConstructor(DesignerMDO.class)
////            .newInstance(designerMDO));
////        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
////          | NoSuchMethodException e) {
////          LOGGER.error("Can't read property for type `{}`", type, e);
////        }
////      } else {
////        LOGGER.error("Unknown mdo type `{}`", type);
////      }
////    }
//    return Optional.empty();
//  }
//
//  private Optional<DesignerMDO> getPropertyValue(MDOType type) {
//    Optional<DesignerMDO> result;
////    switch (type) {
////      case CONFIGURATION:
////        result = Optional.of(getConfiguration());
////        break;
////      case ENUM:
////        result = Optional.of(get_enum());
////        break;
////      case INTERFACE:
////        result = Optional.of(get_interface());
////        break;
////      case HTTP_SERVICE:
////        result = Optional.of(getHttpService());
////        break;
////      case WEB_SERVICE:
////        result = Optional.of(getWebService());
////        break;
////      case WS_REFERENCE:
////        result = Optional.of(getWsReference());
////        break;
////      case XDTO_PACKAGE:
////        result = Optional.of(getXdtoPackage());
////        break;
////      default:
////        try {
////          String propertyName = type.getName();
////          result = Optional.of((DesignerMDO) getClass()
////            .getDeclaredField(
////              propertyName.substring(0, 1).toLowerCase(Locale.ENGLISH) + propertyName.substring(1))
////            .get(this));
////          break;
////        } catch (IllegalAccessException | NoSuchFieldException e) {
////          LOGGER.error("Can't find property for name", e);
////        }
////
//        result = Optional.empty();
////    }
//    return result;
//  }
//
//  private static Map<MDOType, Class<?>> computeTypesClasses() {
//    Map<MDOType, Class<?>> classes = new EnumMap<>(MDOType.class);
//
//    classes.put(MDOType.ACCOUNTING_REGISTER, AccountingRegister.class);
//    classes.put(MDOType.ACCUMULATION_REGISTER, AccumulationRegister.class);
//    classes.put(MDOType.BUSINESS_PROCESS, BusinessProcess.class);
//    classes.put(MDOType.CALCULATION_REGISTER, CalculationRegister.class);
//    classes.put(MDOType.CATALOG, Catalog.class);
//    classes.put(MDOType.CHART_OF_ACCOUNTS, ChartOfAccounts.class);
//    classes.put(MDOType.CHART_OF_CALCULATION_TYPES, ChartOfCalculationTypes.class);
//    classes.put(MDOType.CHART_OF_CHARACTERISTIC_TYPES, ChartOfCharacteristicTypes.class);
//    classes.put(MDOType.COMMAND_GROUP, CommandGroup.class);
//    classes.put(MDOType.COMMON_ATTRIBUTE, CommonAttribute.class);
//    classes.put(MDOType.COMMON_COMMAND, CommonCommand.class);
//    classes.put(MDOType.COMMON_FORM, CommonForm.class);
//    classes.put(MDOType.COMMON_MODULE, CommonModule.class);
//    classes.put(MDOType.COMMON_PICTURE, CommonPicture.class);
//    classes.put(MDOType.COMMON_TEMPLATE, CommonTemplate.class);
//    classes.put(MDOType.CONFIGURATION, MDOConfiguration.class);
//    classes.put(MDOType.CONSTANT, Constant.class);
//    classes.put(MDOType.DATA_PROCESSOR, DataProcessor.class);
//    classes.put(MDOType.DEFINED_TYPE, DefinedType.class);
//    classes.put(MDOType.DOCUMENT_JOURNAL, DocumentJournal.class);
//    classes.put(MDOType.DOCUMENT_NUMERATOR, DocumentNumerator.class);
//    classes.put(MDOType.DOCUMENT, Document.class);
//    classes.put(MDOType.ENUM, MDOEnum.class);
//    classes.put(MDOType.EVENT_SUBSCRIPTION, EventSubscription.class);
//    classes.put(MDOType.EXCHANGE_PLAN, ExchangePlan.class);
//    classes.put(MDOType.FILTER_CRITERION, FilterCriterion.class);
//    classes.put(MDOType.FUNCTIONAL_OPTION, FunctionalOption.class);
//    classes.put(MDOType.FUNCTIONAL_OPTIONS_PARAMETER, FunctionalOptionsParameter.class);
//    classes.put(MDOType.HTTP_SERVICE, HTTPService.class);
//    classes.put(MDOType.INFORMATION_REGISTER, InformationRegister.class);
//    classes.put(MDOType.INTERFACE, MDOInterface.class);
//    classes.put(MDOType.LANGUAGE, Language.class);
//    classes.put(MDOType.REPORT, Report.class);
//    classes.put(MDOType.ROLE, Role.class);
//    classes.put(MDOType.SCHEDULED_JOB, ScheduledJob.class);
//    classes.put(MDOType.SEQUENCE, Sequence.class);
//    classes.put(MDOType.SESSION_PARAMETER, SessionParameter.class);
//    classes.put(MDOType.SETTINGS_STORAGE, SettingsStorage.class);
//    classes.put(MDOType.STYLE_ITEM, StyleItem.class);
//    classes.put(MDOType.STYLE, Style.class);
//    classes.put(MDOType.SUBSYSTEM, Subsystem.class);
//    classes.put(MDOType.TASK, Task.class);
//    classes.put(MDOType.WEB_SERVICE, WebService.class);
//    classes.put(MDOType.WS_REFERENCE, WSReference.class);
//    classes.put(MDOType.XDTO_PACKAGE, XDTOPackage.class);
//    classes.put(MDOType.FORM, Form.class);
//    classes.put(MDOType.COMMAND, Command.class);
//    classes.put(MDOType.TEMPLATE, Template.class);
//    classes.put(MDOType.RECALCULATION, Recalculation.class);
//
//    return classes;
//  }
//}
