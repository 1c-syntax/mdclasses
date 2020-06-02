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
package com.github._1c_syntax.mdclasses.metadata;

import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.additional.UseMode;
import com.github._1c_syntax.utils.Absolute;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationTest {

  @Test
  void testEDT() {

    File srcPath = new File("src/test/resources/metadata/edt");
    Configuration configuration = Configuration.create(srcPath.toPath());

    assertThat(configuration.getName()).isEqualTo("Конфигурация");
    assertThat(configuration.getUuid()).isEqualTo("46c7c1d0-b04d-4295-9b04-ae3207c18d29");
    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);
    assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(),
      new CompatibilityMode(3, 10))).isEqualTo(0);
    assertThat(CompatibilityMode.compareTo(configuration.getConfigurationExtensionCompatibilityMode(),
      new CompatibilityMode(3, 10))).isEqualTo(0);
    assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.RUSSIAN);
    assertThat(configuration.getDefaultRunMode()).isEqualTo("ManagedApplication");
    assertThat(configuration.getDefaultLanguage()).isEqualTo("Language.Русский");
    assertThat(configuration.getDataLockControlMode()).isEqualTo("Managed");
    assertThat(configuration.getObjectAutonumerationMode()).isEqualTo("NotAutoFree");
    assertThat(configuration.getModalityUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE_WITH_WARNINGS);
    assertThat(configuration.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.DONT_USE);

    assertThat(configuration.getModulesByType()).hasSize(38);
    assertThat(configuration.getModulesBySupport()).hasSize(0);
    assertThat(configuration.getModulesByObject()).hasSize(38);
    assertThat(configuration.getCommonModules()).hasSize(6);

    assertThat(configuration.getChildren()).hasSize(90);
    checkChildCount(configuration, MDOType.CONFIGURATION, 1);
    checkChildCount(configuration, MDOType.COMMAND, 3);
    checkChildCount(configuration, MDOType.FORM, 7);
    checkChildCount(configuration, MDOType.TEMPLATE, 2);
    checkChildCount(configuration, MDOType.ATTRIBUTE, 26);

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
    checkChildCount(configuration, MDOType.LANGUAGE, 2);
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

    assertThat(configuration.getChildrenByMdoRef()).hasSize(90);

    assertThat(configuration.getCommonModule("ГлобальныйОбщийМодуль")).isPresent();
    assertThat(configuration.getCommonModule("НесуществующийМодуль")).isNotPresent();
  }

  @Test
  void testEDTEn() {

    File srcPath = new File("src/test/resources/metadata/edt_en");
    Configuration configuration = Configuration.create(srcPath.toPath());

    assertThat(configuration.getName()).isEqualTo("Configuration");
    assertThat(configuration.getUuid()).isEqualTo("04c0322d-92da-49ab-87e5-82c8dcd50888");
    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);
    assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(),
      new CompatibilityMode(3, 14))).isEqualTo(0);
    assertThat(CompatibilityMode.compareTo(configuration.getConfigurationExtensionCompatibilityMode(),
      new CompatibilityMode(3, 14))).isEqualTo(0);
    assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.ENGLISH);
    assertThat(configuration.getDefaultRunMode()).isEqualTo("ManagedApplication");
    assertThat(configuration.getDefaultLanguage()).isEqualTo("Language.English");
    assertThat(configuration.getDataLockControlMode()).isEqualTo("Managed");
    assertThat(configuration.getObjectAutonumerationMode()).isEqualTo("NotAutoFree");
    assertThat(configuration.getModalityUseMode()).isEqualTo(UseMode.DONT_USE);
    assertThat(configuration.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.DONT_USE);

    assertThat(configuration.getModulesByType()).hasSize(2);
    assertThat(configuration.getModulesBySupport()).hasSize(0);
    assertThat(configuration.getModulesByObject()).hasSize(2);
    assertThat(configuration.getCommonModules()).hasSize(2);

    assertThat(configuration.getChildren()).hasSize(4);
    checkChildCount(configuration, MDOType.COMMON_MODULE, 2);
    checkChildCount(configuration, MDOType.LANGUAGE, 1);
    assertThat(configuration.getChildrenByMdoRef()).hasSize(4);

    assertThat(configuration.getCommonModule("CommonModule")).isPresent();
    assertThat(configuration.getCommonModule("CommonModule3")).isNotPresent();
  }

  @Test
  void testDesigner() {

    File srcPath = new File("src/test/resources/metadata/original");
    Configuration configuration = Configuration.create(srcPath.toPath());

    assertThat(CompatibilityMode.compareTo(
      configuration.getCompatibilityMode(), new CompatibilityMode(3, 10)))
      .isEqualTo(0);
    assertThat(CompatibilityMode.compareTo(
      configuration.getConfigurationExtensionCompatibilityMode(), new CompatibilityMode(3, 10)))
      .isEqualTo(0);
    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.DESIGNER);
    assertThat(configuration.getDataLockControlMode()).isEqualTo("Managed");
    assertThat(configuration.getDefaultLanguage()).isEqualTo("Language.Русский");
    assertThat(configuration.getDefaultRunMode()).isEqualTo("ManagedApplication");
    assertThat(configuration.getModalityUseMode()).isEqualTo(UseMode.DONT_USE);
    assertThat(configuration.getObjectAutonumerationMode()).isEqualTo("NotAutoFree");
    assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.RUSSIAN);
    assertThat(configuration.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.DONT_USE);

    assertThat(configuration.getModulesByType()).hasSize(16);
    assertThat(configuration.getModulesBySupport()).hasSize(0);
    assertThat(configuration.getModulesByObject()).hasSize(16);
    assertThat(configuration.getCommonModules()).hasSize(6);

    assertThat(configuration.getChildren()).hasSize(87);
    checkChildCount(configuration, MDOType.CONFIGURATION, 1);
    checkChildCount(configuration, MDOType.COMMAND, 1);
    checkChildCount(configuration, MDOType.FORM, 7);
    checkChildCount(configuration, MDOType.TEMPLATE, 2);
    checkChildCount(configuration, MDOType.ATTRIBUTE, 26);

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
    checkChildCount(configuration, MDOType.LANGUAGE, 1);
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

    assertThat(configuration.getChildrenByMdoRef()).hasSize(87);

    assertThat(configuration.getCommonModule("ГлобальныйОбщийМодуль")).isPresent();
    assertThat(configuration.getCommonModule("ГлобальныйОбщийМодуль3")).isNotPresent();
  }

  @Test
  void testDesignerExt() {

    File srcPath = new File("src/test/resources/metadata/original_ext");
    Configuration configuration = Configuration.create(srcPath.toPath());
    assertThat(CompatibilityMode.compareTo(
      configuration.getCompatibilityMode(), new CompatibilityMode(3, 16)))
      .isEqualTo(0);
    assertThat(CompatibilityMode.compareTo(
      configuration.getConfigurationExtensionCompatibilityMode(), new CompatibilityMode(3, 16)))
      .isEqualTo(0);
    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.DESIGNER);
    assertThat(configuration.getDataLockControlMode()).isEmpty();
    assertThat(configuration.getDefaultLanguage()).isEqualTo("Language.Русский");
    assertThat(configuration.getDefaultRunMode()).isEqualTo("ManagedApplication");
    assertThat(configuration.getModalityUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getObjectAutonumerationMode()).isEmpty();
    assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.RUSSIAN);
    assertThat(configuration.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE);

    assertThat(configuration.getModulesByType()).hasSize(6);
    assertThat(configuration.getModulesBySupport()).hasSize(0);

    assertThat(configuration.getChildren()).hasSize(142);
    checkChildCount(configuration, MDOType.CONFIGURATION, 1);
    checkChildCount(configuration, MDOType.COMMAND, 2);
    checkChildCount(configuration, MDOType.FORM, 12);
    checkChildCount(configuration, MDOType.TEMPLATE, 2);
    checkChildCount(configuration, MDOType.ATTRIBUTE, 41);

    checkChildCount(configuration, MDOType.ACCOUNTING_REGISTER, 2);
    checkChildCount(configuration, MDOType.ACCUMULATION_REGISTER, 2);
    checkChildCount(configuration, MDOType.BUSINESS_PROCESS, 1);
    checkChildCount(configuration, MDOType.CALCULATION_REGISTER, 2);
    checkChildCount(configuration, MDOType.CATALOG, 2);
    checkChildCount(configuration, MDOType.CHART_OF_ACCOUNTS, 2);
    checkChildCount(configuration, MDOType.CHART_OF_CALCULATION_TYPES, 2);
    checkChildCount(configuration, MDOType.CHART_OF_CHARACTERISTIC_TYPES, 2);
    checkChildCount(configuration, MDOType.COMMAND_GROUP, 2);
    checkChildCount(configuration, MDOType.COMMON_ATTRIBUTE, 1);
    checkChildCount(configuration, MDOType.COMMON_COMMAND, 2);
    checkChildCount(configuration, MDOType.COMMON_FORM, 2);
    checkChildCount(configuration, MDOType.COMMON_MODULE, 9);
    checkChildCount(configuration, MDOType.COMMON_PICTURE, 2);
    checkChildCount(configuration, MDOType.COMMON_TEMPLATE, 2);
    checkChildCount(configuration, MDOType.CONSTANT, 2);
    checkChildCount(configuration, MDOType.DATA_PROCESSOR, 2);
    checkChildCount(configuration, MDOType.DEFINED_TYPE, 1);
    checkChildCount(configuration, MDOType.DOCUMENT_JOURNAL, 1);
    checkChildCount(configuration, MDOType.DOCUMENT_NUMERATOR, 1);
    checkChildCount(configuration, MDOType.DOCUMENT, 2);
    checkChildCount(configuration, MDOType.ENUM, 2);
    checkChildCount(configuration, MDOType.EVENT_SUBSCRIPTION, 1);
    checkChildCount(configuration, MDOType.EXCHANGE_PLAN, 2);
    checkChildCount(configuration, MDOType.FILTER_CRITERION, 2);
    checkChildCount(configuration, MDOType.FUNCTIONAL_OPTION, 2);
    checkChildCount(configuration, MDOType.FUNCTIONAL_OPTIONS_PARAMETER, 2);
    checkChildCount(configuration, MDOType.HTTP_SERVICE, 2);
    checkChildCount(configuration, MDOType.INFORMATION_REGISTER, 4);
    checkChildCount(configuration, MDOType.INTERFACE, 0);
    checkChildCount(configuration, MDOType.LANGUAGE, 1);
    checkChildCount(configuration, MDOType.REPORT, 2);
    checkChildCount(configuration, MDOType.ROLE, 2);
    checkChildCount(configuration, MDOType.SCHEDULED_JOB, 1);
    checkChildCount(configuration, MDOType.SEQUENCE, 1);
    checkChildCount(configuration, MDOType.SESSION_PARAMETER, 2);
    checkChildCount(configuration, MDOType.SETTINGS_STORAGE, 1);
    checkChildCount(configuration, MDOType.STYLE_ITEM, 2);
    checkChildCount(configuration, MDOType.STYLE, 2);
    checkChildCount(configuration, MDOType.SUBSYSTEM, 2);
    checkChildCount(configuration, MDOType.TASK, 1);
    checkChildCount(configuration, MDOType.WEB_SERVICE, 2);
    checkChildCount(configuration, MDOType.WS_REFERENCE, 2);
    checkChildCount(configuration, MDOType.XDTO_PACKAGE, 2);

    assertThat(configuration.getChildrenByMdoRef()).hasSize(142);

    assertThat(configuration.getCommonModule("ПростойОбщийМодуль")).isPresent();
    assertThat(configuration.getCommonModule("НесуществующийМодуль")).isNotPresent();
  }

  @Test
  void testEmpty() {

    Configuration configuration = Configuration.create(null);

    assertThat(configuration).isNotNull();
    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.EMPTY);
    assertThat(configuration.getRootPath()).isNotPresent();

    File file = new File("src/test/resources/metadata/edt/src/Constants/Константа1/ManagerModule.bsl");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.UNKNOWN);

    Configuration configuration2 = Configuration.create();

    assertThat(configuration2).isNotNull();
    assertThat(configuration2.getConfigurationSource()).isEqualTo(ConfigurationSource.EMPTY);
    assertThat(configuration2.getChildren()).isEmpty();
  }

  @Test
  void testErrors() {

    Path srcPath = Paths.get("src/test/resources/metadata");
    Configuration configuration = Configuration.create(srcPath);
    assertThat(configuration).isNotNull();

    File file = new File("src/test/resources/metadata/Module.os");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.UNKNOWN);
  }

  private void checkChildCount(Configuration configuration, MDOType type, int count) {
    assertThat(configuration.getChildren())
      .filteredOn(mdObjectBase -> mdObjectBase.getType() == type).hasSize(count);
  }

}
