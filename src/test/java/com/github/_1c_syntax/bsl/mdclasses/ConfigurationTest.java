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

import com.github._1c_syntax.bsl.mdo.support.ApplicationRunMode;
import com.github._1c_syntax.bsl.mdo.support.DataLockControlMode;
import com.github._1c_syntax.bsl.mdo.support.ScriptVariant;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.github._1c_syntax.bsl.test_utils.AbstractMDClassTest;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationTest extends AbstractMDClassTest<Configuration> {
  ConfigurationTest() {
    super(Configuration.class);
  }

  @Test
  void test() {
    var mdc = getMDClass("src/test/resources/metadata/original");
    checkBaseField(mdc, "Конфигурация", "46c7c1d0-b04d-4295-9b04-ae3207c18d29");
    assertThat(CompatibilityMode.compareTo(
      mdc.getCompatibilityMode(), new CompatibilityMode(3, 10)))
      .isZero();
    assertThat(CompatibilityMode.compareTo(
      mdc.getConfigurationExtensionCompatibilityMode(), new CompatibilityMode(3, 10)))
      .isZero();
    assertThat(mdc.getConfigurationSource()).isEqualTo(ConfigurationSource.DESIGNER);
    assertThat(mdc.getDataLockControlMode()).isEqualTo(DataLockControlMode.AUTOMATIC);
    assertThat(mdc.getDefaultLanguage().getName()).isEqualTo("Русский");
    assertThat(mdc.getDefaultRunMode()).isEqualTo(ApplicationRunMode.MANAGED_APPLICATION);
    assertThat(mdc.getModalityUseMode()).isEqualTo(UseMode.DONT_USE);
    assertThat(mdc.getObjectAutonumerationMode()).isEqualTo("NotAutoFree");
    assertThat(mdc.getScriptVariant()).isEqualTo(ScriptVariant.RUSSIAN);
    assertThat(mdc.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE);
    assertThat(mdc.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.DONT_USE);

    assertThat(mdc.isUseManagedFormInOrdinaryApplication()).isTrue();
    assertThat(mdc.isUseOrdinaryFormInManagedApplication()).isFalse();

    assertThat(mdc.getCopyrights().getContent()).hasSize(2);
    assertThat(mdc.getBriefInformation().getContent()).hasSize(2);
    assertThat(mdc.getDetailedInformation().getContent()).hasSize(2);

    //    assertThat(mdc.getModulesByType()).hasSize(19);

//    assertThat(mdc.getModulesBySupport()).isEmpty();
//    assertThat(mdc.getModulesByObject()).hasSize(19);
//    assertThat(mdc.getModules()).hasSize(19);
//    assertThat(mdc.getCommonModules()).hasSize(6);
//    assertThat(mdc.getLanguages()).hasSize(3);
//    assertThat(mdc.getRoles()).hasSize(1);

    assertThat(mdc.getChildren()).hasSize(61);
    assertThat(mdc.getAllChildren()).hasSize(61);
//    checkChildCount(mdc, MDOType.COMMAND, 1);
//    checkChildCount(mdc, MDOType.FORM, 8);
//    checkChildCount(mdc, MDOType.TEMPLATE, 2);
//    checkChildCount(mdc, MDOType.ATTRIBUTE, 34);
//    checkChildCount(mdc, MDOType.WS_OPERATION, 2);
//    checkChildCount(mdc, MDOType.HTTP_SERVICE_URL_TEMPLATE, 1);
//    checkChildCount(mdc, MDOType.HTTP_SERVICE_METHOD, 2);

    checkChildCount(mdc, MDOType.ACCOUNTING_REGISTER, 1);
    checkChildCount(mdc, MDOType.ACCUMULATION_REGISTER, 1);
    checkChildCount(mdc, MDOType.BUSINESS_PROCESS, 1);
    checkChildCount(mdc, MDOType.CALCULATION_REGISTER, 1);
    checkChildCount(mdc, MDOType.CATALOG, 1);
    checkChildCount(mdc, MDOType.CHART_OF_ACCOUNTS, 1);
    checkChildCount(mdc, MDOType.CHART_OF_CALCULATION_TYPES, 1);
    checkChildCount(mdc, MDOType.CHART_OF_CHARACTERISTIC_TYPES, 1);
    checkChildCount(mdc, MDOType.COMMAND_GROUP, 1);
    checkChildCount(mdc, MDOType.COMMON_ATTRIBUTE, 1);
    checkChildCount(mdc, MDOType.COMMON_COMMAND, 1);
    checkChildCount(mdc, MDOType.COMMON_FORM, 1);
    checkChildCount(mdc, MDOType.COMMON_MODULE, 6);
    checkChildCount(mdc, MDOType.COMMON_PICTURE, 1);
    checkChildCount(mdc, MDOType.COMMON_TEMPLATE, 10);
    checkChildCount(mdc, MDOType.CONSTANT, 1);
    checkChildCount(mdc, MDOType.DATA_PROCESSOR, 1);
    checkChildCount(mdc, MDOType.DEFINED_TYPE, 1);
    checkChildCount(mdc, MDOType.DOCUMENT_JOURNAL, 1);
    checkChildCount(mdc, MDOType.DOCUMENT_NUMERATOR, 1);
    checkChildCount(mdc, MDOType.DOCUMENT, 1);
    checkChildCount(mdc, MDOType.ENUM, 1);
    checkChildCount(mdc, MDOType.EVENT_SUBSCRIPTION, 1);
    checkChildCount(mdc, MDOType.EXCHANGE_PLAN, 1);
    checkChildCount(mdc, MDOType.FILTER_CRITERION, 1);
    checkChildCount(mdc, MDOType.FUNCTIONAL_OPTION, 1);
    checkChildCount(mdc, MDOType.FUNCTIONAL_OPTIONS_PARAMETER, 1);
    checkChildCount(mdc, MDOType.HTTP_SERVICE, 1);
    checkChildCount(mdc, MDOType.INFORMATION_REGISTER, 2);
    checkChildCount(mdc, MDOType.INTERFACE, 1);
    checkChildCount(mdc, MDOType.LANGUAGE, 3);
    checkChildCount(mdc, MDOType.REPORT, 1);
    checkChildCount(mdc, MDOType.ROLE, 1);
    checkChildCount(mdc, MDOType.SCHEDULED_JOB, 1);
    checkChildCount(mdc, MDOType.SEQUENCE, 1);
    checkChildCount(mdc, MDOType.SESSION_PARAMETER, 1);
    checkChildCount(mdc, MDOType.SETTINGS_STORAGE, 1);
    checkChildCount(mdc, MDOType.STYLE_ITEM, 1);
    checkChildCount(mdc, MDOType.STYLE, 1);
    checkChildCount(mdc, MDOType.SUBSYSTEM, 1);
    checkChildCount(mdc, MDOType.TASK, 1);
    checkChildCount(mdc, MDOType.WEB_SERVICE, 1);
    checkChildCount(mdc, MDOType.WS_REFERENCE, 1);
    checkChildCount(mdc, MDOType.XDTO_PACKAGE, 1);

//    assertThat(mdc.getChildrenByMdoRef()).hasSize(111);

//    assertThat(mdc.getCommonModule("ГлобальныйОбщийМодуль")).isPresent();
//    assertThat(mdc.getCommonModule("ГлобальныйОбщийМодуль3")).isNotPresent();

//    checkFillPath(mdc.getChildren());
//    checkFormData(mdc.getChildren());

//    var modulesByType = mdc.getModulesByMDORef("CommonModule.ГлобальныйОбщийМодуль");
//    assertThat(modulesByType).hasSize(1)
//      .containsKey(ModuleType.CommonModule);
//
//    modulesByType = mdc.getModulesByMDORef("WSReference.WSСсылка");
//    assertThat(modulesByType).isEmpty();
//
//    modulesByType = mdc.getModulesByMDORef(mdc.getCommonModule("ГлобальныйОбщийМодуль")
//      .get().getMdoReference());
//    assertThat(modulesByType).hasSize(1)
//      .containsKey(ModuleType.CommonModule);
//
//    checkOrderedCommonModules(mdc);
  }

}