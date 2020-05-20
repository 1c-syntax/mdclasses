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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Value
@RequiredArgsConstructor
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaDataObject {

  @JsonProperty("AccountingRegister")
  protected AccountingRegister accountingRegister;
  @JsonProperty("AccumulationRegister")
  protected AccumulationRegister accumulationRegister;
  @JsonProperty("BusinessProcess")
  protected BusinessProcess businessProcess;
  @JsonProperty("CalculationRegister")
  protected CalculationRegister calculationRegister;
  @JsonProperty("Catalog")
  protected Catalog catalog;
  @JsonProperty("ChartOfAccounts")
  protected ChartOfAccounts chartOfAccounts;
  @JsonProperty("ChartOfCalculationTypes")
  protected ChartOfCalculationTypes chartOfCalculationTypes;
  @JsonProperty("ChartOfCharacteristicTypes")
  protected ChartOfCharacteristicTypes chartOfCharacteristicTypes;
  @JsonProperty("CommandGroup")
  protected CommandGroup commandGroup;
  @JsonProperty("CommonAttribute")
  protected CommonAttribute commonAttribute;
  @JsonProperty("CommonCommand")
  protected CommonCommand commonCommand;
  @JsonProperty("CommonForm")
  protected CommonForm commonForm;
  @JsonProperty("CommonModule")
  protected CommonModule commonModule;
  @JsonProperty("CommonPicture")
  protected CommonPicture commonPicture;
  @JsonProperty("CommonTemplate")
  protected CommonTemplate commonTemplate;
  @JsonProperty("Configuration")
  protected MDOConfiguration configuration;
  @JsonProperty("Constant")
  protected Constant constant;
  //  @JsonProperty("Cube")
//  protected Cube cube;
  @JsonProperty("DataProcessor")
  protected DataProcessor dataProcessor;
  @JsonProperty("DefinedType")
  protected DefinedType definedType;
  //  @JsonProperty("DimensionTable")
//  protected DimensionTable dimensionTable;
  @JsonProperty("Document")
  protected Document document;
  @JsonProperty("DocumentJournal")
  protected DocumentJournal documentJournal;
  @JsonProperty("DocumentNumerator")
  protected DocumentNumerator documentNumerator;
  @JsonProperty("Enum")
  protected MDOEnum _enum;
  @JsonProperty("EventSubscription")
  protected EventSubscription eventSubscription;
  @JsonProperty("ExchangePlan")
  protected ExchangePlan exchangePlan;
  //  @JsonProperty("ExternalDataProcessor")
//  protected ExternalDataProcessor externalDataProcessor;
//  @JsonProperty("ExternalDataSource")
//  protected ExternalDataSource externalDataSource;
//  @JsonProperty("ExternalReport")
//  protected ExternalReport externalReport;
  @JsonProperty("FilterCriterion")
  protected FilterCriterion filterCriterion;
  @JsonProperty("Form")
  protected Form form;
  @JsonProperty("FunctionalOption")
  protected FunctionalOption functionalOption;
  @JsonProperty("FunctionalOptionsParameter")
  protected FunctionalOptionsParameter functionalOptionsParameter;
  @JsonProperty("HTTPService")
  protected HTTPService httpService;
  @JsonProperty("InformationRegister")
  protected InformationRegister informationRegister;
  @JsonProperty("Interface")
  protected MDOInterface _interface;
  @JsonProperty("Language")
  protected Language language;
  //  @JsonProperty("Recalculation")
//  protected Recalculation recalculation;
  @JsonProperty("Report")
  protected Report report;
  @JsonProperty("Role")
  protected Role role;
  @JsonProperty("ScheduledJob")
  protected ScheduledJob scheduledJob;
  @JsonProperty("Sequence")
  protected Sequence sequence;
  @JsonProperty("SessionParameter")
  protected SessionParameter sessionParameter;
  @JsonProperty("SettingsStorage")
  protected SettingsStorage settingsStorage;
  @JsonProperty("Style")
  protected Style style;
  @JsonProperty("StyleItem")
  protected StyleItem styleItem;
  @JsonProperty("Subsystem")
  protected Subsystem subsystem;
  //  @JsonProperty("Table")
//  protected Table table;
  @JsonProperty("Task")
  protected Task task;
  @JsonProperty("Template")
  protected Template template;
  @JsonProperty("WSReference")
  protected WSReference wsReference;
  @JsonProperty("WebService")
  protected WebService webService;
  @JsonProperty("XDTOPackage")
  protected XDTOPackage xdtoPackage;

  public MDObjectBase getPropertyByType(MDOType type) {

    switch (type) {
      case CONFIGURATION:
        return getConfiguration();
      case ENUM:
        return get_enum();
      case INTERFACE:
        return get_interface();
      case HTTP_SERVICE:
        return getHttpService();
      case WEB_SERVICE:
        return getWebService();
      case WS_REFERENCE:
        return getWsReference();
      case XDTO_PACKAGE:
        return getXdtoPackage();
      default:
        String propertyName = type.getMDOClassName();
        try {
          return (MDObjectBase) getClass()
            .getDeclaredField(
              propertyName.substring(0, 1).toLowerCase() + propertyName.substring(1))
            .get(this);
        } catch (IllegalAccessException | NoSuchFieldException e) {
          LOGGER.error("Can't find property for name", e);
        }

        return null;
    }
  }
}
