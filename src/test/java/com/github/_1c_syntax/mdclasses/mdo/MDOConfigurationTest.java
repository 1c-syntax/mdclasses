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

import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.additional.UseMode;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MDOConfigurationTest extends AbstractMDOTest {
  MDOConfigurationTest() {
    super(MDOType.CONFIGURATION);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("Configuration/Configuration.mdo");
    checkBaseField(mdo, MDOConfiguration.class, "Конфигурация",
      "46c7c1d0-b04d-4295-9b04-ae3207c18d29");
    checkNoChildren(mdo);
    checkModules(((MDObjectBSL) mdo).getModules(), 3, "Configuration",
      ModuleType.SessionModule, ModuleType.ExternalConnectionModule, ModuleType.ManagedApplicationModule);

    var configuration = (MDOConfiguration) mdo;
    assertThat(CompatibilityMode.compareTo(
      configuration.getCompatibilityMode(), new CompatibilityMode(3, 10)))
      .isEqualTo(0);
    assertThat(CompatibilityMode.compareTo(
      configuration.getConfigurationExtensionCompatibilityMode(), new CompatibilityMode(3, 10)))
      .isEqualTo(0);
    assertThat(configuration.getDataLockControlMode()).isEqualTo("Managed");
    assertThat(configuration.getDefaultLanguage().isRight()).isTrue();
    assertThat(configuration.getDefaultLanguage().get().getName()).isEqualTo("Русский");
    assertThat(configuration.getDefaultRunMode()).isEqualTo("ManagedApplication");
    assertThat(configuration.getModalityUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getObjectAutonumerationMode()).isEqualTo("NotAutoFree");
    assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.RUSSIAN);
    assertThat(configuration.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE_WITH_WARNINGS);
    assertThat(configuration.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.DONT_USE);

    assertThat(configuration.getChildren()).hasSize(52)
      .allMatch(Either::isRight);
    checkChildCount(configuration, MDOType.ACCOUNTING_REGISTER, 1);
    checkChildCount(configuration, MDOType.ACCUMULATION_REGISTER, 1);
    checkChildCount(configuration, MDOType.BUSINESS_PROCESS, 1);
    checkChildCount(configuration, MDOType.CALCULATION_REGISTER, 1);
    checkChildCount(configuration, MDOType.CATALOG, 1);
    checkChildCount(configuration, MDOType.CHART_OF_ACCOUNTS, 1);
    checkChildCount(configuration, MDOType.CHART_OF_CALCULATION_TYPES, 1);
    checkChildCount(configuration, MDOType.CHART_OF_CHARACTERISTIC_TYPES, 1);
    checkChildCount(configuration, MDOType.COMMAND_GROUP, 1);
    checkChildCount(configuration, MDOType.COMMON_ATTRIBUTE, 1);
    checkChildCount(configuration, MDOType.COMMON_COMMAND, 1);
    checkChildCount(configuration, MDOType.COMMON_FORM, 1);
    checkChildCount(configuration, MDOType.COMMON_MODULE, 6);
    checkChildCount(configuration, MDOType.COMMON_PICTURE, 1);
    checkChildCount(configuration, MDOType.COMMON_TEMPLATE, 1);
    checkChildCount(configuration, MDOType.CONSTANT, 1);
    checkChildCount(configuration, MDOType.DATA_PROCESSOR, 1);
    checkChildCount(configuration, MDOType.DEFINED_TYPE, 1);
    checkChildCount(configuration, MDOType.DOCUMENT_JOURNAL, 1);
    checkChildCount(configuration, MDOType.DOCUMENT_NUMERATOR, 1);
    checkChildCount(configuration, MDOType.DOCUMENT, 1);
    checkChildCount(configuration, MDOType.ENUM, 1);
    checkChildCount(configuration, MDOType.EVENT_SUBSCRIPTION, 1);
    checkChildCount(configuration, MDOType.EXCHANGE_PLAN, 1);
    checkChildCount(configuration, MDOType.FILTER_CRITERION, 1);
    checkChildCount(configuration, MDOType.FUNCTIONAL_OPTION, 1);
    checkChildCount(configuration, MDOType.FUNCTIONAL_OPTIONS_PARAMETER, 1);
    checkChildCount(configuration, MDOType.HTTP_SERVICE, 1);
    checkChildCount(configuration, MDOType.INFORMATION_REGISTER, 2);
    checkChildCount(configuration, MDOType.LANGUAGE, 3);
    checkChildCount(configuration, MDOType.REPORT, 1);
    checkChildCount(configuration, MDOType.ROLE, 1);
    checkChildCount(configuration, MDOType.SCHEDULED_JOB, 1);
    checkChildCount(configuration, MDOType.SEQUENCE, 1);
    checkChildCount(configuration, MDOType.SESSION_PARAMETER, 1);
    checkChildCount(configuration, MDOType.SETTINGS_STORAGE, 1);
    checkChildCount(configuration, MDOType.STYLE_ITEM, 1);
    checkChildCount(configuration, MDOType.STYLE, 1);
    checkChildCount(configuration, MDOType.SUBSYSTEM, 2);
    checkChildCount(configuration, MDOType.TASK, 1);
    checkChildCount(configuration, MDOType.WEB_SERVICE, 1);
    checkChildCount(configuration, MDOType.WS_REFERENCE, 1);
    checkChildCount(configuration, MDOType.XDTO_PACKAGE, 1);

    checkSubsystems(configuration, 5);
  }

  @Test
  void testEDTEng() {
    var mdo = getMDObjectEDTEn("Configuration/Configuration.mdo");
    checkBaseField(mdo, MDOConfiguration.class, "Configuration",
      "04c0322d-92da-49ab-87e5-82c8dcd50888");
    checkNoChildren(mdo);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);

    var configuration = (MDOConfiguration) mdo;
    assertThat(CompatibilityMode.compareTo(
      configuration.getCompatibilityMode(), new CompatibilityMode(3, 14)))
      .isEqualTo(0);
    assertThat(CompatibilityMode.compareTo(
      configuration.getConfigurationExtensionCompatibilityMode(), new CompatibilityMode(3, 14)))
      .isEqualTo(0);
    assertThat(configuration.getDataLockControlMode()).isEqualTo("Managed");
    assertThat(configuration.getDefaultLanguage().isRight()).isTrue();
    assertThat(configuration.getDefaultLanguage().get().getName()).isEqualTo("English");
    assertThat(configuration.getDefaultRunMode()).isEqualTo("ManagedApplication");
    assertThat(configuration.getModalityUseMode()).isEqualTo(UseMode.DONT_USE);
    assertThat(configuration.getObjectAutonumerationMode()).isEqualTo("NotAutoFree");
    assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.ENGLISH);
    assertThat(configuration.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.DONT_USE);

    assertThat(configuration.getChildren()).hasSize(3)
      .allMatch(Either::isRight);
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("Configuration.xml");
    checkBaseField(mdo, MDOConfiguration.class, "Конфигурация",
      "46c7c1d0-b04d-4295-9b04-ae3207c18d29");
    checkNoChildren(mdo);
    checkModules(((MDObjectBSL) mdo).getModules(), 3, "Ext",
      ModuleType.SessionModule, ModuleType.ExternalConnectionModule, ModuleType.ManagedApplicationModule);

    var configuration = (MDOConfiguration) mdo;
    assertThat(CompatibilityMode.compareTo(
      configuration.getCompatibilityMode(), new CompatibilityMode(3, 10)))
      .isEqualTo(0);
    assertThat(CompatibilityMode.compareTo(
      configuration.getConfigurationExtensionCompatibilityMode(), new CompatibilityMode(3, 10)))
      .isEqualTo(0);
    assertThat(configuration.getDataLockControlMode()).isEqualTo("Managed");
    assertThat(configuration.getDefaultLanguage().isRight()).isTrue();
    assertThat(configuration.getDefaultLanguage().get().getName()).isEqualTo("Русский");
    assertThat(configuration.getDefaultRunMode()).isEqualTo("ManagedApplication");
    assertThat(configuration.getModalityUseMode()).isEqualTo(UseMode.DONT_USE);
    assertThat(configuration.getObjectAutonumerationMode()).isEqualTo("NotAutoFree");
    assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.RUSSIAN);
    assertThat(configuration.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.DONT_USE);

    assertThat(configuration.getChildren()).hasSize(52)
      .allMatch(Either::isRight);
    checkChildCount(configuration, MDOType.ACCOUNTING_REGISTER, 1);
    checkChildCount(configuration, MDOType.ACCUMULATION_REGISTER, 1);
    checkChildCount(configuration, MDOType.BUSINESS_PROCESS, 1);
    checkChildCount(configuration, MDOType.CALCULATION_REGISTER, 1);
    checkChildCount(configuration, MDOType.CATALOG, 1);
    checkChildCount(configuration, MDOType.CHART_OF_ACCOUNTS, 1);
    checkChildCount(configuration, MDOType.CHART_OF_CALCULATION_TYPES, 1);
    checkChildCount(configuration, MDOType.CHART_OF_CHARACTERISTIC_TYPES, 1);
    checkChildCount(configuration, MDOType.COMMAND_GROUP, 1);
    checkChildCount(configuration, MDOType.COMMON_ATTRIBUTE, 1);
    checkChildCount(configuration, MDOType.COMMON_COMMAND, 1);
    checkChildCount(configuration, MDOType.COMMON_FORM, 1);
    checkChildCount(configuration, MDOType.COMMON_MODULE, 6);
    checkChildCount(configuration, MDOType.COMMON_PICTURE, 1);
    checkChildCount(configuration, MDOType.COMMON_TEMPLATE, 1);
    checkChildCount(configuration, MDOType.CONSTANT, 1);
    checkChildCount(configuration, MDOType.DATA_PROCESSOR, 1);
    checkChildCount(configuration, MDOType.DEFINED_TYPE, 1);
    checkChildCount(configuration, MDOType.DOCUMENT_JOURNAL, 1);
    checkChildCount(configuration, MDOType.DOCUMENT_NUMERATOR, 1);
    checkChildCount(configuration, MDOType.DOCUMENT, 1);
    checkChildCount(configuration, MDOType.ENUM, 1);
    checkChildCount(configuration, MDOType.EVENT_SUBSCRIPTION, 1);
    checkChildCount(configuration, MDOType.EXCHANGE_PLAN, 1);
    checkChildCount(configuration, MDOType.FILTER_CRITERION, 1);
    checkChildCount(configuration, MDOType.FUNCTIONAL_OPTION, 1);
    checkChildCount(configuration, MDOType.FUNCTIONAL_OPTIONS_PARAMETER, 1);
    checkChildCount(configuration, MDOType.HTTP_SERVICE, 1);
    checkChildCount(configuration, MDOType.INFORMATION_REGISTER, 2);
    checkChildCount(configuration, MDOType.INTERFACE, 1);
    checkChildCount(configuration, MDOType.LANGUAGE, 3);
    checkChildCount(configuration, MDOType.REPORT, 1);
    checkChildCount(configuration, MDOType.ROLE, 1);
    checkChildCount(configuration, MDOType.SCHEDULED_JOB, 1);
    checkChildCount(configuration, MDOType.SEQUENCE, 1);
    checkChildCount(configuration, MDOType.SESSION_PARAMETER, 1);
    checkChildCount(configuration, MDOType.SETTINGS_STORAGE, 1);
    checkChildCount(configuration, MDOType.STYLE_ITEM, 1);
    checkChildCount(configuration, MDOType.STYLE, 1);
    checkChildCount(configuration, MDOType.SUBSYSTEM, 1);
    checkChildCount(configuration, MDOType.TASK, 1);
    checkChildCount(configuration, MDOType.WEB_SERVICE, 1);
    checkChildCount(configuration, MDOType.WS_REFERENCE, 1);
    checkChildCount(configuration, MDOType.XDTO_PACKAGE, 1);

  }

  private void checkChildCount(MDOConfiguration configuration, MDOType type, int count) {
    assertThat(configuration.getChildren())
      .extracting(Either::get)
      .filteredOn(mdObjectBase -> mdObjectBase.getType() == type).hasSize(count);
  }

  private void checkSubsystems(MDOConfiguration configuration, int count) {
    Map<Subsystem, List<MDObjectBase>> subsystems = new HashMap<>();
    configuration.getChildren().stream().filter(Either::isRight).map(Either::get)
      .filter(mdo -> mdo.getType() == MDOType.SUBSYSTEM)
      .forEach(subsystem -> getAllSubsystems((Subsystem) subsystem, subsystems));

    assertThat(subsystems).hasSize(count);
    subsystems.forEach((subsystem, mdoList) -> {
      assertThat(subsystem.getChildren()).hasSize(mdoList.size());
      assertThat(subsystem.getChildren())
        .extracting(Either::get)
        .containsAll(mdoList);
      assertThat(mdoList).allMatch(mdo -> mdo.getIncludedSubsystems().contains(subsystem));
    });
  }

  private void getAllSubsystems(Subsystem subsystem, Map<Subsystem, List<MDObjectBase>> subsystems) {
    List<MDObjectBase> mdoList = new ArrayList<>();
    subsystem.getChildren().stream().filter(Either::isRight).map(Either::get)
      .forEach(mdo -> {
        mdoList.add(mdo);
        if (mdo instanceof Subsystem) {
          getAllSubsystems((Subsystem) mdo, subsystems);
        }
      });

    subsystems.put(subsystem, mdoList);
  }

}
