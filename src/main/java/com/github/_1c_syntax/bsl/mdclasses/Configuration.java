/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.Module;
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
import com.github._1c_syntax.bsl.mdo.support.ApplicationRunMode;
import com.github._1c_syntax.bsl.mdo.support.DataLockControlMode;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.mdo.support.ScriptVariant;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.mdo.support.UsePurposes;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MdoReference;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;

import java.util.Collections;
import java.util.List;

/**
 * Корневой класс конфигурации 1с
 */
@Value
@Builder
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
@NonNull
public class Configuration implements CF {

  /**
   * Пустая конфигурация
   */
  public static final Configuration EMPTY = createEmptyConfiguration();

  /*
   * CF
   */

  @Default
  ConfigurationSource configurationSource = ConfigurationSource.EMPTY;
  @Default
  String uuid = "";
  @Default
  String name = "";
  @Default
  MultiLanguageString synonym = MultiLanguageString.EMPTY;
  @Default
  MdoReference mdoReference = MdoReference.EMPTY;
  @Default
  ObjectBelonging objectBelonging = ObjectBelonging.OWN;
  @Default
  SupportVariant supportVariant = SupportVariant.NONE;
  @Default
  String comment = "";
  @Default
  MdoReference defaultLanguage = MdoReference.EMPTY;
  @Default
  ScriptVariant scriptVariant = ScriptVariant.ENGLISH;
  @Default
  CompatibilityMode compatibilityMode = new CompatibilityMode();
  @Default
  CompatibilityMode configurationExtensionCompatibilityMode = new CompatibilityMode();
  @Default
  ApplicationRunMode defaultRunMode = ApplicationRunMode.AUTO;
  @Default
  List<Module> modules = Collections.emptyList();
  @Default
  String vendor = "";
  @Default
  String version = "";
  @Singular("addUsePurposes")
  List<UsePurposes> usePurposes;

  @Singular
  List<Subsystem> subsystems;
  @Singular
  List<CommonModule> commonModules;
  @Singular
  List<SessionParameter> sessionParameters;
  @Singular
  List<Role> roles;
  @Singular
  List<CommonAttribute> commonAttributes;
  @Singular
  List<ExchangePlan> exchangePlans;
  @Singular("filterCriterion")
  List<FilterCriterion> filterCriteria;
  @Singular
  List<EventSubscription> eventSubscriptions;
  @Singular
  List<ScheduledJob> scheduledJobs;
  @Singular
  List<Bot> bots;
  @Singular
  List<FunctionalOption> functionalOptions;
  @Singular
  List<FunctionalOptionsParameter> functionalOptionsParameters;
  @Singular
  List<DefinedType> definedTypes;
  @Singular
  List<SettingsStorage> settingsStorages;
  @Singular
  List<CommonForm> commonForms;
  @Singular
  List<CommonCommand> commonCommands;
  @Singular
  List<CommandGroup> commandGroups;
  @Singular
  List<CommonTemplate> commonTemplates;
  @Singular
  List<CommonPicture> commonPictures;
  @Singular
  List<Interface> interfaces;
  @Singular
  List<XDTOPackage> xDTOPackages;
  @Singular
  List<WebService> webServices;
  @Singular
  List<HTTPService> httpServices;
  @Singular
  List<WSReference> wsReferences;
  @Singular
  List<IntegrationService> integrationServices;
  @Singular
  List<StyleItem> styleItems;
  @Singular
  List<Style> styles;
  @Singular
  List<Language> languages;
  @Singular
  List<Constant> constants;
  @Singular
  List<Catalog> catalogs;
  @Singular
  List<Document> documents;
  @Singular
  List<DocumentNumerator> documentNumerators;
  @Singular
  List<Sequence> sequences;
  @Singular
  List<DocumentJournal> documentJournals;
  @Singular
  List<Enum> enums;
  @Singular
  List<Report> reports;
  @Singular
  List<DataProcessor> dataProcessors;
  @Singular("chartOfCharacteristicTypes")
  List<ChartOfCharacteristicTypes> chartsOfCharacteristicTypes;
  @Singular("chartOfAccounts")
  List<ChartOfAccounts> chartsOfAccounts;
  @Singular("chartOfCalculationTypes")
  List<ChartOfCalculationTypes> chartsOfCalculationTypes;
  @Singular
  List<InformationRegister> informationRegisters;
  @Singular
  List<AccumulationRegister> accumulationRegisters;
  @Singular
  List<AccountingRegister> accountingRegisters;
  @Singular
  List<CalculationRegister> calculationRegisters;
  @Singular
  List<BusinessProcess> businessProcesses;
  @Singular
  List<Task> tasks;
  @Singular
  List<ExternalDataSource> externalDataSources;

  @Singular
  List<MD> children;

  /*
   * Свое
   */

  /**
   * Режим управления блокировкой данных
   */
  @Default
  DataLockControlMode dataLockControlMode = DataLockControlMode.AUTOMATIC;

  /**
   * Режим автонумерации объектов
   */
  @Default
  String objectAutonumerationMode = "";

  /**
   * Режим использования модальных окон
   */
  @Default
  UseMode modalityUseMode = UseMode.USE;

  /**
   * Режим использования синхронных вызовов
   */
  @Default
  UseMode synchronousExtensionAndAddInCallUseMode = UseMode.USE_WITH_WARNINGS;

  /**
   * Режим использования синхронных вызовов для платформенных объектов и расширений
   */
  @Default
  UseMode synchronousPlatformExtensionAndAddInCallUseMode = UseMode.USE;

  /**
   * Использовать управляемые формы в обычном приложении
   */
  boolean useManagedFormInOrdinaryApplication;

  /**
   * Использовать обычные формы в управляемом приложении
   */
  boolean useOrdinaryFormInManagedApplication;

  /**
   * Информация о копирайте на разных языках
   */
  @Default
  MultiLanguageString copyrights = MultiLanguageString.EMPTY;

  /**
   * Детальная информация о конфигурации, на разных языках
   */
  @Default
  MultiLanguageString detailedInformation = MultiLanguageString.EMPTY;

  /**
   * Краткая информация о конфигурации, на разных языках
   */
  @Default
  MultiLanguageString briefInformation = MultiLanguageString.EMPTY;

  private static Configuration createEmptyConfiguration() {
    var emptyString = "empty";

    return Configuration.builder().configurationSource(ConfigurationSource.EMPTY).name(emptyString).uuid(emptyString).build();
  }
}