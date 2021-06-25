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
package com.github._1c_syntax.mdclasses.metadata;

import com.github._1c_syntax.mdclasses.Configuration;
import com.github._1c_syntax.mdclasses.ConfigurationExtension;
import com.github._1c_syntax.mdclasses.common.CompatibilityMode;
import com.github._1c_syntax.mdclasses.common.ConfigurationSource;
import com.github._1c_syntax.mdclasses.mdo.AbstractMDOForm;
import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.MDCommonForm;
import com.github._1c_syntax.mdclasses.mdo.children.Form;
import com.github._1c_syntax.mdclasses.mdo.support.ApplicationRunMode;
import com.github._1c_syntax.mdclasses.mdo.support.ConfigurationExtensionPurpose;
import com.github._1c_syntax.mdclasses.mdo.support.DataLockControlMode;
import com.github._1c_syntax.mdclasses.mdo.support.FormType;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.mdo.support.ModuleType;
import com.github._1c_syntax.mdclasses.mdo.support.ObjectBelonging;
import com.github._1c_syntax.mdclasses.mdo.support.ScriptVariant;
import com.github._1c_syntax.mdclasses.mdo.support.UseMode;
import com.github._1c_syntax.utils.Absolute;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationTest {

  @Test
  void testEDT() {

    File srcPath = new File("src/test/resources/metadata/edt");
    Configuration configuration = Configuration.create(srcPath.toPath());
    assertThat(configuration).isNotInstanceOf(ConfigurationExtension.class);
    assertThat(configuration.getName()).isEqualTo("Конфигурация");
    assertThat(configuration.getUuid()).isEqualTo("46c7c1d0-b04d-4295-9b04-ae3207c18d29");
    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);
    assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(),
      new CompatibilityMode(3, 10))).isZero();
    assertThat(CompatibilityMode.compareTo(configuration.getConfigurationExtensionCompatibilityMode(),
      new CompatibilityMode(3, 10))).isZero();
    assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.RUSSIAN);
    assertThat(configuration.getDefaultRunMode()).isEqualTo(ApplicationRunMode.MANAGED_APPLICATION);
    assertThat(configuration.getDefaultLanguage().getName()).isEqualTo("Русский");
    assertThat(configuration.getDataLockControlMode()).isEqualTo(DataLockControlMode.AUTOMATIC);
    assertThat(configuration.getObjectAutonumerationMode()).isEqualTo("NotAutoFree");
    assertThat(configuration.getModalityUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE_WITH_WARNINGS);
    assertThat(configuration.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.DONT_USE);

    assertThat(configuration.isUseManagedFormInOrdinaryApplication()).isTrue();
    assertThat(configuration.isUseOrdinaryFormInManagedApplication()).isTrue();

    assertThat(configuration.getCopyrights()).hasSize(2)
      .anyMatch(copyright -> copyright.getContent().equals("Моя Программа")
        && copyright.getLanguage().equals("ru"))
      .anyMatch(copyright -> copyright.getContent().equals("My program")
        && copyright.getLanguage().equals("en"));

    assertThat(configuration.getBriefInformation()).hasSize(2)
            .anyMatch(briefInfo -> briefInfo.getLanguage().equals("ru")
                    && briefInfo.getContent().equals("Краткая информация"))
            .anyMatch(briefInfo -> briefInfo.getLanguage().equals("en")
                    && briefInfo.getContent().equals("Short info"));

    assertThat(configuration.getDetailedInformation()).hasSize(2)
            .anyMatch(briefInfo -> briefInfo.getLanguage().equals("ru")
                    && briefInfo.getContent().equals("Подробная информация"))
            .anyMatch(briefInfo -> briefInfo.getLanguage().equals("en")
                    && briefInfo.getContent().equals("Detailed info"));

    assertThat(configuration.getModulesByType()).hasSize(39);
    assertThat(configuration.getModulesBySupport()).isEmpty();
    assertThat(configuration.getModulesByObject()).hasSize(39);
    assertThat(configuration.getModules()).hasSize(39);
    assertThat(configuration.getCommonModules()).hasSize(6);
    assertThat(configuration.getLanguages()).hasSize(3);
    assertThat(configuration.getRoles()).hasSize(1);

    assertThat(configuration.getChildren()).hasSize(106);
    checkChildCount(configuration, MDOType.CONFIGURATION, 1);
    checkChildCount(configuration, MDOType.COMMAND, 3);
    checkChildCount(configuration, MDOType.FORM, 7);
    checkChildCount(configuration, MDOType.TEMPLATE, 2);
    checkChildCount(configuration, MDOType.ATTRIBUTE, 27);
    checkChildCount(configuration, MDOType.WS_OPERATION, 2);
    checkChildCount(configuration, MDOType.HTTP_SERVICE_URL_TEMPLATE, 1);
    checkChildCount(configuration, MDOType.HTTP_SERVICE_METHOD, 2);

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
    checkChildCount(configuration, MDOType.COMMON_TEMPLATE, 10);
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

    assertThat(configuration.getChildrenByMdoRef()).hasSize(105);

    assertThat(configuration.getCommonModule("ГлобальныйОбщийМодуль")).isPresent();
    assertThat(configuration.getCommonModule("НесуществующийМодуль")).isNotPresent();

    checkFillPath(configuration.getChildren());
    checkFormData(configuration.getChildren());

    var modulesByType = configuration.getModulesByMDORef("Document.ДОкумент1");
    assertThat(modulesByType).hasSize(2)
      .containsKey(ModuleType.ManagerModule)
      .containsKey(ModuleType.ObjectModule);

    modulesByType = configuration.getModulesByMDORef("WSReference.WSСсылка");
    assertThat(modulesByType).isEmpty();

    checkOrderedCommonModules(configuration);
  }

  @Test
  void testEDTEn() {

    File srcPath = new File("src/test/resources/metadata/edt_en");
    Configuration configuration = Configuration.create(srcPath.toPath());

    assertThat(configuration).isNotInstanceOf(ConfigurationExtension.class);
    assertThat(configuration.getName()).isEqualTo("Configuration");
    assertThat(configuration.getUuid()).isEqualTo("04c0322d-92da-49ab-87e5-82c8dcd50888");
    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);
    assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(),
      new CompatibilityMode(3, 14))).isZero();
    assertThat(CompatibilityMode.compareTo(configuration.getConfigurationExtensionCompatibilityMode(),
      new CompatibilityMode(3, 14))).isZero();
    assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.ENGLISH);
    assertThat(configuration.getDefaultRunMode()).isEqualTo(ApplicationRunMode.MANAGED_APPLICATION);
    assertThat(configuration.getDefaultLanguage().getName()).isEqualTo("English");
    assertThat(configuration.getDataLockControlMode()).isEqualTo(DataLockControlMode.AUTOMATIC_AND_MANAGED);
    assertThat(configuration.getObjectAutonumerationMode()).isEqualTo("NotAutoFree");
    assertThat(configuration.getModalityUseMode()).isEqualTo(UseMode.DONT_USE);
    assertThat(configuration.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.DONT_USE);

    assertThat(configuration.getModulesByType()).hasSize(2);
    assertThat(configuration.getModulesBySupport()).isEmpty();
    assertThat(configuration.getModulesByObject()).hasSize(2);
    assertThat(configuration.getModules()).hasSize(2);
    assertThat(configuration.getCommonModules()).hasSize(2);
    assertThat(configuration.getLanguages()).hasSize(1);
    assertThat(configuration.getRoles()).isEmpty();

    assertThat(configuration.getChildren()).hasSize(4);
    checkChildCount(configuration, MDOType.COMMON_MODULE, 2);
    checkChildCount(configuration, MDOType.LANGUAGE, 1);
    assertThat(configuration.getChildrenByMdoRef()).hasSize(4);

    assertThat(configuration.getCommonModule("CommonModule")).isPresent();
    assertThat(configuration.getCommonModule("CommonModule3")).isNotPresent();
  }

  @Test
  void testEDTExt() {

    File srcPath = new File("src/test/resources/metadata/edt_ext");
    Configuration configuration = Configuration.create(srcPath.toPath());

    assertThat(configuration).isInstanceOf(ConfigurationExtension.class);
    assertThat(configuration.getName()).isEqualTo("Расширение");
    assertThat(configuration.getUuid()).isEqualTo("6e50eb82-8de4-4aff-ba5b-6b441963a56a");
    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);
    assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(),
      new CompatibilityMode(3, 10))).isZero();
    assertThat(CompatibilityMode.compareTo(configuration.getConfigurationExtensionCompatibilityMode(),
      new CompatibilityMode(3, 14))).isZero();
    assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.RUSSIAN);
    assertThat(configuration.getDefaultRunMode()).isEqualTo(ApplicationRunMode.MANAGED_APPLICATION);
    assertThat(configuration.getDefaultLanguage().getName()).isEqualTo("Русский");
    assertThat(configuration.getDataLockControlMode()).isEqualTo(DataLockControlMode.MANAGED);
    assertThat(configuration.getObjectAutonumerationMode()).isEqualTo("NotAutoFree");
    assertThat(configuration.getModalityUseMode()).isEqualTo(UseMode.DONT_USE);
    assertThat(configuration.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.DONT_USE);
    assertThat(((ConfigurationExtension) configuration).getConfigurationExtensionPurpose()).isEqualTo(ConfigurationExtensionPurpose.PATCH);
    assertThat(((ConfigurationExtension) configuration).getNamePrefix()).isEqualTo("Расш_");

    assertThat(configuration.getModulesByType()).hasSize(9);
    assertThat(configuration.getModulesBySupport()).isEmpty();
    assertThat(configuration.getModulesByObject()).hasSize(9);
    assertThat(configuration.getCommonModules()).hasSize(9);
    assertThat(configuration.getLanguages()).hasSize(1);
    assertThat(configuration.getRoles()).hasSize(2);

    assertThat(configuration.getChildren()).hasSize(142);
    checkChildCount(configuration, MDOType.CONFIGURATION, 1);
    checkChildCount(configuration, MDOType.COMMAND, 2);
    checkChildCount(configuration, MDOType.FORM, 13);
    checkChildCount(configuration, MDOType.TEMPLATE, 2);
    checkChildCount(configuration, MDOType.ATTRIBUTE, 42);

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
    checkChildCount(configuration, MDOType.LANGUAGE, 1);
    checkChildCount(configuration, MDOType.REPORT, 2);
    checkChildCount(configuration, MDOType.ROLE, 2);
    checkChildCount(configuration, MDOType.SCHEDULED_JOB, 1);
    checkChildCount(configuration, MDOType.SEQUENCE, 1);
    checkChildCount(configuration, MDOType.SESSION_PARAMETER, 2);
    checkChildCount(configuration, MDOType.SETTINGS_STORAGE, 1);
    checkChildCount(configuration, MDOType.STYLE_ITEM, 2);
    checkChildCount(configuration, MDOType.SUBSYSTEM, 2);
    checkChildCount(configuration, MDOType.TASK, 1);
    checkChildCount(configuration, MDOType.WEB_SERVICE, 2);
    checkChildCount(configuration, MDOType.WS_REFERENCE, 2);
    checkChildCount(configuration, MDOType.XDTO_PACKAGE, 2);

    assertThat(configuration.getChildrenByMdoRef()).hasSize(142);

    assertThat(configuration.getCommonModule("ПростойОбщийМодуль1")).isPresent();
    assertThat(configuration.getCommonModule("НесуществующийМодуль")).isNotPresent();

    assertThat(configuration.getChildren())
      .filteredOn(mdObjectBase -> mdObjectBase.getObjectBelonging() == ObjectBelonging.ADOPTED)
      .hasSize(77);

    assertThat(configuration.getChildren())
      .filteredOn(mdObjectBase -> mdObjectBase.getObjectBelonging() == ObjectBelonging.OWN)
      .hasSize(65);
  }

  @Test
  void testDesigner() {

    File srcPath = new File("src/test/resources/metadata/original");
    Configuration configuration = Configuration.create(srcPath.toPath());

    assertThat(configuration).isNotInstanceOf(ConfigurationExtension.class);
    assertThat(CompatibilityMode.compareTo(
      configuration.getCompatibilityMode(), new CompatibilityMode(3, 10)))
      .isZero();
    assertThat(CompatibilityMode.compareTo(
      configuration.getConfigurationExtensionCompatibilityMode(), new CompatibilityMode(3, 10)))
      .isZero();
    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.DESIGNER);
    assertThat(configuration.getDataLockControlMode()).isEqualTo(DataLockControlMode.AUTOMATIC);
    assertThat(configuration.getDefaultLanguage().getName()).isEqualTo("Русский");
    assertThat(configuration.getDefaultRunMode()).isEqualTo(ApplicationRunMode.MANAGED_APPLICATION);
    assertThat(configuration.getModalityUseMode()).isEqualTo(UseMode.DONT_USE);
    assertThat(configuration.getObjectAutonumerationMode()).isEqualTo("NotAutoFree");
    assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.RUSSIAN);
    assertThat(configuration.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.DONT_USE);

    assertThat(configuration.isUseManagedFormInOrdinaryApplication()).isTrue();
    assertThat(configuration.isUseOrdinaryFormInManagedApplication()).isFalse();

    assertThat(configuration.getModulesByType()).hasSize(19);
    assertThat(configuration.getCopyrights()).hasSize(2)
      .anyMatch(copyright -> copyright.getContent().equals("Моя Программа")
        && copyright.getLanguage().equals("ru"))
      .anyMatch(copyright -> copyright.getContent().equals("My program")
        && copyright.getLanguage().equals("en"));

    assertThat(configuration.getBriefInformation()).hasSize(2)
            .anyMatch(briefInfo -> briefInfo.getLanguage().equals("ru")
                    && briefInfo.getContent().equals("Краткая информация"))
            .anyMatch(briefInfo -> briefInfo.getLanguage().equals("en")
                    && briefInfo.getContent().equals("Short info"));

    assertThat(configuration.getDetailedInformation()).hasSize(2)
            .anyMatch(briefInfo -> briefInfo.getLanguage().equals("ru")
                    && briefInfo.getContent().equals("Подробная информация"))
            .anyMatch(briefInfo -> briefInfo.getLanguage().equals("en")
                    && briefInfo.getContent().equals("Detailed info"));

    assertThat(configuration.getModulesBySupport()).isEmpty();
    assertThat(configuration.getModulesByObject()).hasSize(19);
    assertThat(configuration.getModules()).hasSize(19);
    assertThat(configuration.getCommonModules()).hasSize(6);
    assertThat(configuration.getLanguages()).hasSize(3);
    assertThat(configuration.getRoles()).hasSize(1);

    assertThat(configuration.getChildren()).hasSize(112);
    checkChildCount(configuration, MDOType.CONFIGURATION, 1);
    checkChildCount(configuration, MDOType.COMMAND, 1);
    checkChildCount(configuration, MDOType.FORM, 8);
    checkChildCount(configuration, MDOType.TEMPLATE, 2);
    checkChildCount(configuration, MDOType.ATTRIBUTE, 34);
    checkChildCount(configuration, MDOType.WS_OPERATION, 2);
    checkChildCount(configuration, MDOType.HTTP_SERVICE_URL_TEMPLATE, 1);
    checkChildCount(configuration, MDOType.HTTP_SERVICE_METHOD, 2);

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
    checkChildCount(configuration, MDOType.COMMON_TEMPLATE, 10);
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

    assertThat(configuration.getChildrenByMdoRef()).hasSize(111);

    assertThat(configuration.getCommonModule("ГлобальныйОбщийМодуль")).isPresent();
    assertThat(configuration.getCommonModule("ГлобальныйОбщийМодуль3")).isNotPresent();

    checkFillPath(configuration.getChildren());
    checkFormData(configuration.getChildren());

    var modulesByType = configuration.getModulesByMDORef("CommonModule.ГлобальныйОбщийМодуль");
    assertThat(modulesByType).hasSize(1)
      .containsKey(ModuleType.CommonModule);

    modulesByType = configuration.getModulesByMDORef("WSReference.WSСсылка");
    assertThat(modulesByType).isEmpty();

    modulesByType = configuration.getModulesByMDORef(configuration.getCommonModule("ГлобальныйОбщийМодуль")
      .get().getMdoReference());
    assertThat(modulesByType).hasSize(1)
      .containsKey(ModuleType.CommonModule);

    checkOrderedCommonModules(configuration);
  }

  @Test
  void testDesignerExt() {

    File srcPath = new File("src/test/resources/metadata/original_ext");
    Configuration configuration = Configuration.create(srcPath.toPath());

    assertThat(configuration).isInstanceOf(ConfigurationExtension.class);
    assertThat(CompatibilityMode.compareTo(
      configuration.getCompatibilityMode(), new CompatibilityMode(3, 16)))
      .isZero();
    assertThat(CompatibilityMode.compareTo(
      configuration.getConfigurationExtensionCompatibilityMode(), new CompatibilityMode(3, 16)))
      .isZero();
    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.DESIGNER);
    assertThat(configuration.getDataLockControlMode()).isEqualTo(DataLockControlMode.AUTOMATIC);
    assertThat(configuration.getDefaultLanguage().getName()).isEqualTo("Русский");
    assertThat(configuration.getDefaultRunMode()).isEqualTo(ApplicationRunMode.MANAGED_APPLICATION);
    assertThat(configuration.getModalityUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getObjectAutonumerationMode()).isEmpty();
    assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.RUSSIAN);
    assertThat(configuration.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE);
    assertThat(((ConfigurationExtension) configuration).getConfigurationExtensionPurpose()).isEqualTo(ConfigurationExtensionPurpose.CUSTOMIZATION);
    assertThat(((ConfigurationExtension) configuration).getNamePrefix()).isEqualTo("Расш_");

    assertThat(configuration.getModulesByType()).hasSize(9);
    assertThat(configuration.getModulesBySupport()).isEmpty();
    assertThat(configuration.getModules()).hasSize(9);
    assertThat(configuration.getRoles()).hasSize(2);

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

    assertThat(configuration.getChildren())
      .filteredOn(mdObjectBase -> mdObjectBase.getObjectBelonging() == ObjectBelonging.ADOPTED)
      .hasSize(78);

    assertThat(configuration.getChildren())
      .filteredOn(mdObjectBase -> mdObjectBase.getObjectBelonging() == ObjectBelonging.OWN)
      .hasSize(64);
  }

  @Test
  void testDesignerExt2() {

    File srcPath = new File("src/test/resources/metadata/original_ext2");
    Configuration configuration = Configuration.create(srcPath.toPath());
    assertThat(configuration).isInstanceOf(ConfigurationExtension.class);
    assertThat(CompatibilityMode.compareTo(
      configuration.getCompatibilityMode(), new CompatibilityMode(3, 10)))
      .isZero();
    assertThat(CompatibilityMode.compareTo(
      configuration.getConfigurationExtensionCompatibilityMode(), new CompatibilityMode(3, 10)))
      .isZero();
    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.DESIGNER);
    assertThat(configuration.getDataLockControlMode()).isEqualTo(DataLockControlMode.AUTOMATIC);
    assertThat(configuration.getDefaultLanguage().getName()).isEqualTo("Русский");
    assertThat(configuration.getDefaultRunMode()).isEqualTo(ApplicationRunMode.MANAGED_APPLICATION);
    assertThat(configuration.getModalityUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getObjectAutonumerationMode()).isEmpty();
    assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.RUSSIAN);
    assertThat(configuration.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE);
    assertThat(((ConfigurationExtension) configuration).getConfigurationExtensionPurpose()).isEqualTo(ConfigurationExtensionPurpose.ADD_ON);
    assertThat(((ConfigurationExtension) configuration).getNamePrefix()).isEqualTo("Расш1_");

    assertThat(configuration.getModulesByType()).hasSize(2);
    assertThat(configuration.getModulesBySupport()).isEmpty();
    assertThat(configuration.getModules()).hasSize(2);
    assertThat(configuration.getRoles()).isEmpty();

    assertThat(configuration.getChildren()).hasSize(5);
    checkChildCount(configuration, MDOType.CONFIGURATION, 1);

    checkChildCount(configuration, MDOType.COMMON_MODULE, 2);
    checkChildCount(configuration, MDOType.LANGUAGE, 1);
    checkChildCount(configuration, MDOType.SUBSYSTEM, 1);

    assertThat(configuration.getChildrenByMdoRef()).hasSize(5);

    assertThat(configuration.getCommonModule("ПростойОбщийМодуль")).isPresent();
    assertThat(configuration.getCommonModule("НесуществующийМодуль")).isNotPresent();

    assertThat(configuration.getChildren())
      .filteredOn(mdObjectBase -> mdObjectBase.getObjectBelonging() == ObjectBelonging.ADOPTED)
      .hasSize(4);

    assertThat(configuration.getChildren())
      .filteredOn(mdObjectBase -> mdObjectBase.getObjectBelonging() == ObjectBelonging.OWN)
      .hasSize(1);
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
    assertThat(configuration2.getRoles()).isEmpty();

    Configuration configuration3 = Configuration.createExtension();

    assertThat(configuration3).isNotNull();
    assertThat(configuration3.getConfigurationSource()).isEqualTo(ConfigurationSource.EMPTY);
    assertThat(configuration3.getChildren()).isEmpty();
    assertThat(configuration3.getRoles()).isEmpty();
    assertThat(((ConfigurationExtension) configuration3).getConfigurationExtensionPurpose())
      .isEqualTo(ConfigurationExtensionPurpose.UNDEFINED);
  }

  @Test
  void testErrors() {

    Path srcPath = Paths.get("src/test/resources/metadata");
    Configuration configuration = Configuration.create(srcPath);
    assertThat(configuration).isNotNull();

    File file = new File("src/test/resources/metadata/Module.os");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.UNKNOWN);
  }

  @Test
  void testOrdinaryMode() {
    File srcPath = new File("src/test/resources/metadata/original_ordinary");
    Configuration configuration = Configuration.create(srcPath.toPath());
    assertThat(configuration.getDefaultRunMode()).isEqualTo(ApplicationRunMode.ORDINARY_APPLICATION);
  }

  @Test
  void testBrokenLanguageIntoExtension() {
    var srcPath = new File("src/test/resources/metadata/edt_ext_broken");
    var configuration = Configuration.create(srcPath.toPath());
  }

  private void checkFillPath(Set<AbstractMDObjectBase> child) {
    var elements = child.parallelStream()
      .filter(mdObjectBase -> mdObjectBase instanceof Form || mdObjectBase instanceof MDCommonForm)
      .filter(mdObjectBase -> mdObjectBase.getPath() == null)
      .filter(mdObjectBase -> !mdObjectBase.getPath().toFile().exists())
      .collect(Collectors.toList());
    assertThat(elements).isEmpty();
  }

  private void checkFormData(Set<AbstractMDObjectBase> child) {
    var elements = child.parallelStream()
      .filter(mdObjectBase -> mdObjectBase instanceof Form || mdObjectBase instanceof MDCommonForm)
      .filter(mdObjectBase -> ((AbstractMDOForm) mdObjectBase).getFormType() == FormType.MANAGED)
      .filter(mdObjectBase -> ((AbstractMDOForm) mdObjectBase).getData() == null)
      .filter(mdObjectBase -> ((AbstractMDOForm) mdObjectBase).getData() == null)
      .collect(Collectors.toList());
    assertThat(elements).isEmpty();
  }

  private void checkChildCount(Configuration configuration, MDOType type, int count) {
    assertThat(configuration.getChildren())
      .filteredOn(mdObjectBase -> mdObjectBase.getType() == type).hasSize(count);
  }

  private static void checkOrderedCommonModules(Configuration configuration) {
    var orderedCommonModules = configuration.getOrderedTopMDObjects().get(MDOType.COMMON_MODULE);
    checkPosition(orderedCommonModules, "ПростойОбщийМодуль", 0);
    checkPosition(orderedCommonModules, "ГлобальныйОбщийМодуль", 1);
    checkPosition(orderedCommonModules, "ОбщийМодульВызовСервера", 2);
    checkPosition(orderedCommonModules, "ОбщийМодульПовтИспВызов", 3);
    checkPosition(orderedCommonModules, "ОбщийМодульПовтИспСеанс", 4);
    checkPosition(orderedCommonModules, "ОбщийМодульПолныйеПрава", 5);
  }

  private static void checkPosition(List<AbstractMDObjectBase> children, String name, int position) {
    var child = children.get(position);
    assertThat(child.getName()).isEqualTo(name);
  }

}
