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
package com.github._1c_syntax.mdclasses.mdo.wrapper;

import com.github._1c_syntax.mdclasses.mdo.AccountingRegister;
import com.github._1c_syntax.mdclasses.mdo.AccumulationRegister;
import com.github._1c_syntax.mdclasses.mdo.BusinessProcess;
import com.github._1c_syntax.mdclasses.mdo.CalculationRegister;
import com.github._1c_syntax.mdclasses.mdo.Catalog;
import com.github._1c_syntax.mdclasses.mdo.ChartOfAccounts;
import com.github._1c_syntax.mdclasses.mdo.ChartOfCalculationTypes;
import com.github._1c_syntax.mdclasses.mdo.ChartOfCharacteristicTypes;
import com.github._1c_syntax.mdclasses.mdo.Command;
import com.github._1c_syntax.mdclasses.mdo.CommandGroup;
import com.github._1c_syntax.mdclasses.mdo.CommonAttribute;
import com.github._1c_syntax.mdclasses.mdo.CommonCommand;
import com.github._1c_syntax.mdclasses.mdo.CommonForm;
import com.github._1c_syntax.mdclasses.mdo.CommonModule;
import com.github._1c_syntax.mdclasses.mdo.CommonPicture;
import com.github._1c_syntax.mdclasses.mdo.CommonTemplate;
import com.github._1c_syntax.mdclasses.mdo.Constant;
import com.github._1c_syntax.mdclasses.mdo.DataProcessor;
import com.github._1c_syntax.mdclasses.mdo.DefinedType;
import com.github._1c_syntax.mdclasses.mdo.Document;
import com.github._1c_syntax.mdclasses.mdo.DocumentJournal;
import com.github._1c_syntax.mdclasses.mdo.DocumentNumerator;
import com.github._1c_syntax.mdclasses.mdo.EventSubscription;
import com.github._1c_syntax.mdclasses.mdo.ExchangePlan;
import com.github._1c_syntax.mdclasses.mdo.FilterCriterion;
import com.github._1c_syntax.mdclasses.mdo.Form;
import com.github._1c_syntax.mdclasses.mdo.FunctionalOption;
import com.github._1c_syntax.mdclasses.mdo.FunctionalOptionsParameter;
import com.github._1c_syntax.mdclasses.mdo.HTTPService;
import com.github._1c_syntax.mdclasses.mdo.InformationRegister;
import com.github._1c_syntax.mdclasses.mdo.Language;
import com.github._1c_syntax.mdclasses.mdo.MDOConfiguration;
import com.github._1c_syntax.mdclasses.mdo.MDOEnum;
import com.github._1c_syntax.mdclasses.mdo.MDOInterface;
import com.github._1c_syntax.mdclasses.mdo.MDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.Report;
import com.github._1c_syntax.mdclasses.mdo.Role;
import com.github._1c_syntax.mdclasses.mdo.ScheduledJob;
import com.github._1c_syntax.mdclasses.mdo.Sequence;
import com.github._1c_syntax.mdclasses.mdo.SessionParameter;
import com.github._1c_syntax.mdclasses.mdo.SettingsStorage;
import com.github._1c_syntax.mdclasses.mdo.Style;
import com.github._1c_syntax.mdclasses.mdo.StyleItem;
import com.github._1c_syntax.mdclasses.mdo.Subsystem;
import com.github._1c_syntax.mdclasses.mdo.Task;
import com.github._1c_syntax.mdclasses.mdo.Template;
import com.github._1c_syntax.mdclasses.mdo.WSReference;
import com.github._1c_syntax.mdclasses.mdo.WebService;
import com.github._1c_syntax.mdclasses.mdo.XDTOPackage;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * Класс-обертка для чтения xml описания объектов в формате конфигуратора
 */
@Data
@Slf4j
public class DesignerWrapper {

  private static final Map<MDOType, Class<?>> TYPE_CLASSES = computeTypesClasses();

  @XStreamAlias("AccountingRegister")
  protected DesignerMDO accountingRegister;
  @XStreamAlias("AccumulationRegister")
  protected DesignerMDO accumulationRegister;
  @XStreamAlias("BusinessProcess")
  protected DesignerMDO businessProcess;
  @XStreamAlias("CalculationRegister")
  protected DesignerMDO calculationRegister;
  @XStreamAlias("Catalog")
  protected DesignerMDO catalog;
  @XStreamAlias("ChartOfAccounts")
  protected DesignerMDO chartOfAccounts;
  @XStreamAlias("ChartOfCalculationTypes")
  protected DesignerMDO chartOfCalculationTypes;
  @XStreamAlias("ChartOfCharacteristicTypes")
  protected DesignerMDO chartOfCharacteristicTypes;
  @XStreamAlias("CommandGroup")
  protected DesignerMDO commandGroup;
  @XStreamAlias("CommonAttribute")
  protected DesignerMDO commonAttribute;
  @XStreamAlias("CommonCommand")
  protected DesignerMDO commonCommand;
  @XStreamAlias("CommonForm")
  protected DesignerMDO commonForm;
  @XStreamAlias("CommonModule")
  protected DesignerMDO commonModule;
  @XStreamAlias("CommonPicture")
  protected DesignerMDO commonPicture;
  @XStreamAlias("CommonTemplate")
  protected DesignerMDO commonTemplate;
  @XStreamAlias("Configuration")
  protected DesignerMDO configuration;
  @XStreamAlias("Constant")
  protected DesignerMDO constant;
  @XStreamAlias("Cube")
  protected DesignerMDO cube;
  @XStreamAlias("DataProcessor")
  protected DesignerMDO dataProcessor;
  @XStreamAlias("DefinedType")
  protected DesignerMDO definedType;
  @XStreamAlias("DimensionTable")
  protected DesignerMDO dimensionTable;
  @XStreamAlias("Document")
  protected DesignerMDO document;
  @XStreamAlias("DocumentJournal")
  protected DesignerMDO documentJournal;
  @XStreamAlias("DocumentNumerator")
  protected DesignerMDO documentNumerator;
  @XStreamAlias("Enum")
  protected DesignerMDO _enum;
  @XStreamAlias("EventSubscription")
  protected DesignerMDO eventSubscription;
  @XStreamAlias("ExchangePlan")
  protected DesignerMDO exchangePlan;
  @XStreamAlias("ExternalDataProcessor")
  protected DesignerMDO externalDataProcessor;
  @XStreamAlias("ExternalDataSource")
  protected DesignerMDO externalDataSource;
  @XStreamAlias("ExternalReport")
  protected DesignerMDO externalReport;
  @XStreamAlias("FilterCriterion")
  protected DesignerMDO filterCriterion;
  @XStreamAlias("Form")
  protected DesignerMDO form;
  @XStreamAlias("FunctionalOption")
  protected DesignerMDO functionalOption;
  @XStreamAlias("FunctionalOptionsParameter")
  protected DesignerMDO functionalOptionsParameter;
  @XStreamAlias("HTTPService")
  protected DesignerMDO httpService;
  @XStreamAlias("InformationRegister")
  protected DesignerMDO informationRegister;
  @XStreamAlias("Interface")
  protected DesignerMDO _interface;
  @XStreamAlias("Language")
  protected DesignerMDO language;
  @XStreamAlias("Recalculation")
  protected DesignerMDO recalculation;
  @XStreamAlias("Report")
  protected DesignerMDO report;
  @XStreamAlias("Role")
  protected DesignerMDO role;
  @XStreamAlias("ScheduledJob")
  protected DesignerMDO scheduledJob;
  @XStreamAlias("Sequence")
  protected DesignerMDO sequence;
  @XStreamAlias("SessionParameter")
  protected DesignerMDO sessionParameter;
  @XStreamAlias("SettingsStorage")
  protected DesignerMDO settingsStorage;
  @XStreamAlias("Style")
  protected DesignerMDO style;
  @XStreamAlias("StyleItem")
  protected DesignerMDO styleItem;
  @XStreamAlias("Subsystem")
  protected DesignerMDO subsystem;
  @XStreamAlias("Table")
  protected DesignerMDO table;
  @XStreamAlias("Task")
  protected DesignerMDO task;
  @XStreamAlias("Template")
  protected DesignerMDO template;
  @XStreamAlias("WSReference")
  protected DesignerMDO wsReference;
  @XStreamAlias("WebService")
  protected DesignerMDO webService;
  @XStreamAlias("XDTOPackage")
  protected DesignerMDO xdtoPackage;

  /**
   * @param type - Тип читаемого объекта, по нему определяется имя свойства
   * @return - Возвращает MDObjectBase объект
   */
  public Optional<MDObjectBase> getPropertyByType(MDOType type, Path mdoPath) {
    var value = getPropertyValue(type);
    if (value.isPresent()) {
      Class<?> clazz = TYPE_CLASSES.get(type);
      if (clazz != null) {
        var designerMDO = value.get();
        designerMDO.setMdoPath(mdoPath);
        try {
          return Optional.of((MDObjectBase) clazz.getConstructor(DesignerMDO.class)
            .newInstance(designerMDO));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
          | NoSuchMethodException e) {
          LOGGER.error("Can't read property for name", e);
        }
      } else {
        LOGGER.error("Unknown mdo type `{}`", type);
      }
    }
    return Optional.empty();
  }

  private Optional<DesignerMDO> getPropertyValue(MDOType type) {
    Optional<DesignerMDO> result;
    switch (type) {
      case CONFIGURATION:
        result = Optional.of(getConfiguration());
        break;
      case ENUM:
        result = Optional.of(get_enum());
        break;
      case INTERFACE:
        result = Optional.of(get_interface());
        break;
      case HTTP_SERVICE:
        result = Optional.of(getHttpService());
        break;
      case WEB_SERVICE:
        result = Optional.of(getWebService());
        break;
      case WS_REFERENCE:
        result = Optional.of(getWsReference());
        break;
      case XDTO_PACKAGE:
        result = Optional.of(getXdtoPackage());
        break;
      default:
        try {
          String propertyName = type.getName();
          result = Optional.of((DesignerMDO) getClass()
            .getDeclaredField(
              propertyName.substring(0, 1).toLowerCase(Locale.ENGLISH) + propertyName.substring(1))
            .get(this));
          break;
        } catch (IllegalAccessException | NoSuchFieldException e) {
          LOGGER.error("Can't find property for name", e);
        }

        result = Optional.empty();
    }
    return result;
  }

  private static Map<MDOType, Class<?>> computeTypesClasses() {
    Map<MDOType, Class<?>> classes = new EnumMap<>(MDOType.class);
    for (MDOType mdoType : MDOType.values()) {
      Class<?> clazz = null;
      switch (mdoType) {
        case ACCOUNTING_REGISTER:
          clazz = AccountingRegister.class;
          break;
        case ACCUMULATION_REGISTER:
          clazz = AccumulationRegister.class;
          break;
        case BUSINESS_PROCESS:
          clazz = BusinessProcess.class;
          break;
        case CALCULATION_REGISTER:
          clazz = CalculationRegister.class;
          break;
        case CATALOG:
          clazz = Catalog.class;
          break;
        case CHART_OF_ACCOUNTS:
          clazz = ChartOfAccounts.class;
          break;
        case CHART_OF_CALCULATION_TYPES:
          clazz = ChartOfCalculationTypes.class;
          break;
        case CHART_OF_CHARACTERISTIC_TYPES:
          clazz = ChartOfCharacteristicTypes.class;
          break;
        case COMMAND_GROUP:
          clazz = CommandGroup.class;
          break;
        case COMMON_ATTRIBUTE:
          clazz = CommonAttribute.class;
          break;
        case COMMON_COMMAND:
          clazz = CommonCommand.class;
          break;
        case COMMON_FORM:
          clazz = CommonForm.class;
          break;
        case COMMON_MODULE:
          clazz = CommonModule.class;
          break;
        case COMMON_PICTURE:
          clazz = CommonPicture.class;
          break;
        case COMMON_TEMPLATE:
          clazz = CommonTemplate.class;
          break;
        case CONFIGURATION:
          clazz = MDOConfiguration.class;
          break;
        case CONSTANT:
          clazz = Constant.class;
          break;
        case DATA_PROCESSOR:
          clazz = DataProcessor.class;
          break;
        case DEFINED_TYPE:
          clazz = DefinedType.class;
          break;
        case DOCUMENT_JOURNAL:
          clazz = DocumentJournal.class;
          break;
        case DOCUMENT_NUMERATOR:
          clazz = DocumentNumerator.class;
          break;
        case DOCUMENT:
          clazz = Document.class;
          break;
        case ENUM:
          clazz = MDOEnum.class;
          break;
        case EVENT_SUBSCRIPTION:
          clazz = EventSubscription.class;
          break;
        case EXCHANGE_PLAN:
          clazz = ExchangePlan.class;
          break;
        case FILTER_CRITERION:
          clazz = FilterCriterion.class;
          break;
        case FUNCTIONAL_OPTION:
          clazz = FunctionalOption.class;
          break;
        case FUNCTIONAL_OPTIONS_PARAMETER:
          clazz = FunctionalOptionsParameter.class;
          break;
        case HTTP_SERVICE:
          clazz = HTTPService.class;
          break;
        case INFORMATION_REGISTER:
          clazz = InformationRegister.class;
          break;
        case INTERFACE:
          clazz = MDOInterface.class;
          break;
        case LANGUAGE:
          clazz = Language.class;
          break;
        case REPORT:
          clazz = Report.class;
          break;
        case ROLE:
          clazz = Role.class;
          break;
        case SCHEDULED_JOB:
          clazz = ScheduledJob.class;
          break;
        case SEQUENCE:
          clazz = Sequence.class;
          break;
        case SESSION_PARAMETER:
          clazz = SessionParameter.class;
          break;
        case SETTINGS_STORAGE:
          clazz = SettingsStorage.class;
          break;
        case STYLE_ITEM:
          clazz = StyleItem.class;
          break;
        case STYLE:
          clazz = Style.class;
          break;
        case SUBSYSTEM:
          clazz = Subsystem.class;
          break;
        case TASK:
          clazz = Task.class;
          break;
        case WEB_SERVICE:
          clazz = WebService.class;
          break;
        case WS_REFERENCE:
          clazz = WSReference.class;
          break;
        case XDTO_PACKAGE:
          clazz = XDTOPackage.class;
          break;
        case FORM:
          clazz = Form.class;
          break;
        case COMMAND:
          clazz = Command.class;
          break;
        case TEMPLATE:
          clazz = Template.class;
          break;
        default:
          // noop
      }
      if (clazz != null) {
        classes.put(mdoType, clazz);
      }
    }
    return classes;
  }
}
