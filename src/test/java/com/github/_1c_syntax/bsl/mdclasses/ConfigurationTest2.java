/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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

import com.github._1c_syntax.bsl.mdo.Form;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ConfigurationTest2 {

  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses_3_25, _edt",
      "false, mdclasses_3_25"
    }
  )
  void test8325(ArgumentsAccessor argumentsAccessor) {
    var mdc = MDTestUtils.readConfiguration(argumentsAccessor, false);
    assertThat(mdc).isInstanceOf(Configuration.class);
    var cf = (Configuration) mdc;
    assertThat(cf.getSupportVariant()).isEqualTo(SupportVariant.NONE);
    assertThat(cf.getModules()).isEmpty();
    assertThat(cf.getAllModules())
      .hasSize(17)
      .allMatch(module -> module.getSupportVariant().equals(SupportVariant.NONE));

    // проверка состава дочерних
    checkChildrenMdclasses(cf);

    assertThat(cf.getPlainChildren())
      .hasSize(351)
      .allMatch(md -> md.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getAllModules().stream().filter(Module::isProtected)).isEmpty();

    assertThat(cf.getChildren().stream().filter(md -> md instanceof Form form && !form.getData().isEmpty()))
      .hasSize(cf.getCommonForms().size());
    assertThat(cf.getPlainChildren().stream().filter(md -> md instanceof Form form && !form.getData().isEmpty()))
      .hasSize(21);
    assertThat(cf.getPlainChildren().stream().filter(md -> md instanceof Form form && form.getData().isEmpty()))
      .isEmpty();
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses_3_27, _edt",
      "false, mdclasses_3_27"
    }
  )
  void test8327(ArgumentsAccessor argumentsAccessor) {
    var mdc = MDTestUtils.readConfiguration(argumentsAccessor, false);
    assertThat(mdc).isInstanceOf(Configuration.class);
    var cf = (Configuration) mdc;
    assertThat(cf.getSupportVariant()).isEqualTo(SupportVariant.NONE);
    assertThat(cf.getModules()).isEmpty();
    assertThat(cf.getAllModules())
      .hasSize(18)
      .allMatch(module -> module.getSupportVariant().equals(SupportVariant.NONE));

    // проверка состава дочерних
    checkChildrenMdclasses(cf);

    assertThat(cf.getWebSocketClients()).hasSize(1);

    assertThat(cf.getPlainChildren())
      .hasSize(352)
      .allMatch(md -> md.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getAllModules().stream().filter(Module::isProtected)).isEmpty();

    assertThat(cf.getChildren().stream().filter(md -> md instanceof Form form && !form.getData().isEmpty()))
      .hasSize(cf.getCommonForms().size());
    assertThat(cf.getPlainChildren().stream().filter(md -> md instanceof Form form && !form.getData().isEmpty()))
      .hasSize(21);
    assertThat(cf.getPlainChildren().stream().filter(md -> md instanceof Form form && form.getData().isEmpty()))
      .isEmpty();
  }

  private static void checkChildrenMdclasses(Configuration cf) {
    assertThat(cf.getSubsystems()).hasSize(4);
    assertThat(cf.getCommonModules()).hasSize(4);
    assertThat(cf.getSessionParameters()).hasSize(4);
    assertThat(cf.getRoles()).hasSize(2);
    assertThat(cf.getCommonAttributes()).hasSize(2);
    assertThat(cf.getExchangePlans()).hasSize(2);
    assertThat(cf.getFilterCriteria()).hasSize(1);
    assertThat(cf.getEventSubscriptions()).hasSize(1);
    assertThat(cf.getScheduledJobs()).hasSize(2);
    assertThat(cf.getBots()).hasSize(1);
    assertThat(cf.getFunctionalOptions()).hasSize(2);
    assertThat(cf.getFunctionalOptionsParameters()).hasSize(1);
    assertThat(cf.getDefinedTypes()).hasSize(1);
    assertThat(cf.getSettingsStorages()).hasSize(2);
    assertThat(cf.getCommonForms()).hasSize(9);
    assertThat(cf.getCommonCommands()).hasSize(1);
    assertThat(cf.getCommandGroups()).hasSize(1);
    assertThat(cf.getCommonTemplates()).hasSize(9);
    assertThat(cf.getCommonPictures()).hasSize(1);
    assertThat(cf.getInterfaces()).isEmpty();
    assertThat(cf.getXDTOPackages()).hasSize(1);
    assertThat(cf.getWebServices()).hasSize(1);
    assertThat(cf.getHttpServices()).hasSize(1);
    assertThat(cf.getWsReferences()).hasSize(1);
    assertThat(cf.getIntegrationServices()).hasSize(1);
    assertThat(cf.getStyleItems()).hasSize(3);
    assertThat(cf.getStyles()).hasSize(2);
    assertThat(cf.getLanguages()).hasSize(2);
    assertThat(cf.getConstants()).hasSize(4);
    assertThat(cf.getCatalogs()).hasSize(3);
    assertThat(cf.getDocuments()).hasSize(2);
    assertThat(cf.getDocumentNumerators()).hasSize(1);
    assertThat(cf.getSequences()).hasSize(1);
    assertThat(cf.getDocumentJournals()).hasSize(1);
    assertThat(cf.getEnums()).hasSize(1);
    assertThat(cf.getReports()).hasSize(1);
    assertThat(cf.getDataProcessors()).hasSize(1);
    assertThat(cf.getChartsOfCharacteristicTypes()).hasSize(1);
    assertThat(cf.getChartsOfAccounts()).hasSize(1);
    assertThat(cf.getChartsOfCalculationTypes()).hasSize(2);
    assertThat(cf.getInformationRegisters()).hasSize(2);
    assertThat(cf.getAccumulationRegisters()).hasSize(2);
    assertThat(cf.getAccountingRegisters()).hasSize(1);
    assertThat(cf.getCalculationRegisters()).hasSize(1);
    assertThat(cf.getBusinessProcesses()).hasSize(1);
    assertThat(cf.getTasks()).hasSize(1);
    assertThat(cf.getExternalDataSources()).hasSize(1);

    assertThat(cf.getChildren()).hasSize(
      cf.getSubsystems().size() + cf.getCommonModules().size() +
        cf.getSessionParameters().size() + cf.getRoles().size() +
        cf.getCommonAttributes().size() + cf.getExchangePlans().size() +
        cf.getFilterCriteria().size() + cf.getEventSubscriptions().size() +
        cf.getScheduledJobs().size() + cf.getBots().size() +
        cf.getFunctionalOptions().size() + cf.getFunctionalOptionsParameters().size() +
        cf.getDefinedTypes().size() + cf.getSettingsStorages().size() +
        cf.getCommonForms().size() + cf.getCommonCommands().size() +
        cf.getCommandGroups().size() + cf.getCommonTemplates().size() +
        cf.getCommonPictures().size() + cf.getInterfaces().size() +
        cf.getXDTOPackages().size() + cf.getWebServices().size() +
        cf.getHttpServices().size() + cf.getWsReferences().size() +
        cf.getIntegrationServices().size() + cf.getStyleItems().size() +
        cf.getStyles().size() + cf.getLanguages().size() +
        cf.getConstants().size() + cf.getCatalogs().size() +
        cf.getDocuments().size() + cf.getDocumentNumerators().size() +
        cf.getSequences().size() + cf.getDocumentJournals().size() +
        cf.getEnums().size() + cf.getReports().size() +
        cf.getDataProcessors().size() + cf.getChartsOfCharacteristicTypes().size() +
        cf.getChartsOfAccounts().size() + cf.getChartsOfCalculationTypes().size() +
        cf.getInformationRegisters().size() + cf.getAccumulationRegisters().size() +
        cf.getAccountingRegisters().size() + cf.getCalculationRegisters().size() +
        cf.getBusinessProcesses().size() + cf.getTasks().size() +
        cf.getExternalDataSources().size() +
        cf.getWebSocketClients().size());
  }
}
