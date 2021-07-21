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

import com.github._1c_syntax.bsl.test_utils.AbstractMDClassTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

class ConfigurationExtensionTest extends AbstractMDClassTest<ConfigurationExtension> {
  ConfigurationExtensionTest() {
    super(ConfigurationExtension.class);
  }

  @ParameterizedTest(name = "{index}: {1} (path {0})")
  @CsvSource(
    {
      "src/test/resources/metadata/original_ext,Расширение,6e50eb82-8de4-4aff-ba5b-6b441963a56a",
      "src/test/resources/metadata/edt_ext,Расширение,6e50eb82-8de4-4aff-ba5b-6b441963a56a",
      "src/test/resources/metadata/original_ext2,Расширение1,3a54b148-94d1-45dc-9f8a-30bb86d0bd5d"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdc = getMDClass(argumentsAccessor.getString(0));
    mdcTest(mdc, argumentsAccessor);
  }
//  @Test
//  void test() {
//    var mdc = getMDClass("src/test/resources/metadata/original_ext");
//    checkBaseField(mdc, "Расширение", "6e50eb82-8de4-4aff-ba5b-6b441963a56a");
//
//    assertThat(CompatibilityMode.compareTo(
//      mdc.getCompatibilityMode(), new CompatibilityMode(3, 16)))
//      .isZero();
//    assertThat(CompatibilityMode.compareTo(
//      mdc.getConfigurationExtensionCompatibilityMode(), new CompatibilityMode(3, 16)))
//      .isZero();
//    assertThat(mdc.getConfigurationSource()).isEqualTo(ConfigurationSource.DESIGNER);
//    assertThat(mdc.getDataLockControlMode()).isEqualTo(DataLockControlMode.AUTOMATIC);
//    assertThat(mdc.getDefaultLanguage().getName()).isEqualTo("Русский");
//    assertThat(mdc.getDefaultRunMode()).isEqualTo(ApplicationRunMode.MANAGED_APPLICATION);
//    assertThat(mdc.getModalityUseMode()).isEqualTo(UseMode.USE);
//    assertThat(mdc.getObjectAutonumerationMode()).isEmpty();
//    assertThat(mdc.getScriptVariant()).isEqualTo(ScriptVariant.RUSSIAN);
//    assertThat(mdc.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE);
//    assertThat(mdc.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE);
//    assertThat(mdc.getConfigurationExtensionPurpose()).isEqualTo(ConfigurationExtensionPurpose.CUSTOMIZATION);
//    assertThat(mdc.getNamePrefix()).isEqualTo("Расш_");
//
////    assertThat(mdc.getModulesByType()).hasSize(9);
////    assertThat(mdc.getModulesBySupport()).isEmpty();
////    assertThat(mdc.getModules()).hasSize(9);
////    assertThat(mdc.getRoles()).hasSize(2);
//
//    assertThat(mdc.getChildren()).hasSize(84);
////    assertThat(mdc.getPlainChildren()).hasSize(84);
////    checkChildCount(mdc, MDOType.COMMAND, 2);
////    checkChildCount(mdc, MDOType.FORM, 12);
////    checkChildCount(mdc, MDOType.TEMPLATE, 2);
////    checkChildCount(mdc, MDOType.ATTRIBUTE, 41);
//
//    checkChildCount(mdc, MDOType.ACCOUNTING_REGISTER, 2);
//    checkChildCount(mdc, MDOType.ACCUMULATION_REGISTER, 2);
//    checkChildCount(mdc, MDOType.BUSINESS_PROCESS, 1);
//    checkChildCount(mdc, MDOType.CALCULATION_REGISTER, 2);
//    checkChildCount(mdc, MDOType.CATALOG, 2);
//    checkChildCount(mdc, MDOType.CHART_OF_ACCOUNTS, 2);
//    checkChildCount(mdc, MDOType.CHART_OF_CALCULATION_TYPES, 2);
//    checkChildCount(mdc, MDOType.CHART_OF_CHARACTERISTIC_TYPES, 2);
//    checkChildCount(mdc, MDOType.COMMAND_GROUP, 2);
//    checkChildCount(mdc, MDOType.COMMON_ATTRIBUTE, 1);
//    checkChildCount(mdc, MDOType.COMMON_COMMAND, 2);
//    checkChildCount(mdc, MDOType.COMMON_FORM, 2);
//    checkChildCount(mdc, MDOType.COMMON_MODULE, 9);
//    checkChildCount(mdc, MDOType.COMMON_PICTURE, 2);
//    checkChildCount(mdc, MDOType.COMMON_TEMPLATE, 2);
//    checkChildCount(mdc, MDOType.CONSTANT, 2);
//    checkChildCount(mdc, MDOType.DATA_PROCESSOR, 2);
//    checkChildCount(mdc, MDOType.DEFINED_TYPE, 1);
//    checkChildCount(mdc, MDOType.DOCUMENT_JOURNAL, 1);
//    checkChildCount(mdc, MDOType.DOCUMENT_NUMERATOR, 1);
//    checkChildCount(mdc, MDOType.DOCUMENT, 2);
//    checkChildCount(mdc, MDOType.ENUM, 2);
//    checkChildCount(mdc, MDOType.EVENT_SUBSCRIPTION, 1);
//    checkChildCount(mdc, MDOType.EXCHANGE_PLAN, 2);
//    checkChildCount(mdc, MDOType.FILTER_CRITERION, 2);
//    checkChildCount(mdc, MDOType.FUNCTIONAL_OPTION, 2);
//    checkChildCount(mdc, MDOType.FUNCTIONAL_OPTIONS_PARAMETER, 2);
//    checkChildCount(mdc, MDOType.HTTP_SERVICE, 2);
//    checkChildCount(mdc, MDOType.INFORMATION_REGISTER, 4);
//    checkChildCount(mdc, MDOType.INTERFACE, 0);
//    checkChildCount(mdc, MDOType.LANGUAGE, 1);
//    checkChildCount(mdc, MDOType.REPORT, 2);
//    checkChildCount(mdc, MDOType.ROLE, 2);
//    checkChildCount(mdc, MDOType.SCHEDULED_JOB, 1);
//    checkChildCount(mdc, MDOType.SEQUENCE, 1);
//    checkChildCount(mdc, MDOType.SESSION_PARAMETER, 2);
//    checkChildCount(mdc, MDOType.SETTINGS_STORAGE, 1);
//    checkChildCount(mdc, MDOType.STYLE_ITEM, 2);
//    checkChildCount(mdc, MDOType.STYLE, 2);
//    checkChildCount(mdc, MDOType.SUBSYSTEM, 2);
//    checkChildCount(mdc, MDOType.TASK, 1);
//    checkChildCount(mdc, MDOType.WEB_SERVICE, 2);
//    checkChildCount(mdc, MDOType.WS_REFERENCE, 2);
//    checkChildCount(mdc, MDOType.XDTO_PACKAGE, 2);
//
////    assertThat(mdc.getChildrenByMdoRef()).hasSize(142);
////
////    assertThat(mdc.getCommonModule("ПростойОбщийМодуль")).isPresent();
////    assertThat(mdc.getCommonModule("НесуществующийМодуль")).isNotPresent();
//
//    assertThat(mdc.getChildren())
//      .filteredOn(mdObjectBase -> mdObjectBase.getObjectBelonging() == ObjectBelonging.ADOPTED)
//      .hasSize(48);
//
//    assertThat(mdc.getChildren())
//      .filteredOn(mdObjectBase -> mdObjectBase.getObjectBelonging() == ObjectBelonging.OWN)
//      .hasSize(36);
//  }


}