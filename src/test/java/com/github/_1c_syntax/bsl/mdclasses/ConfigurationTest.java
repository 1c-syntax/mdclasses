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

import com.github._1c_syntax.bsl.mdo.BusinessProcess;
import com.github._1c_syntax.bsl.mdo.Form;
import com.github._1c_syntax.bsl.mdo.FormOwner;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.TemplateOwner;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdo.storage.RoleData;
import com.github._1c_syntax.bsl.mdo.storage.XdtoPackageData;
import com.github._1c_syntax.bsl.mdo.support.DataLockControlMode;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.ModuleType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ConfigurationTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, _edt",
      "false, mdclasses",
      "true, mdclasses_3_18, _edt",
      "false, mdclasses_3_18",
      "true, mdclasses_ext, _edt",
      "false, mdclasses_ext",
      "true, mdclasses_3_24, _edt"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdc = MDTestUtils.getMDCWithSimpleTest(argumentsAccessor, false);
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_1, _edt",
      "false, ssl_3_1"
    }
  )
  void testFullSSL(ArgumentsAccessor argumentsAccessor) {
    var mdc = MDTestUtils.readConfiguration(argumentsAccessor, false);
    assertThat(mdc).isInstanceOf(Configuration.class);
    var cf = (Configuration) mdc;
    assertThat(cf.getSupportVariant()).isEqualTo(SupportVariant.NOT_EDITABLE);
    assertThat(cf.getModules())
      .hasSize(4)
      .allMatch(module -> module.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getAllModules())
      .hasSize(1320 + cf.getCommonModules().size())
      .allMatch(module -> module.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    // проверка состава дочерних
    checkChildrenSSL(cf);

    assertThat(cf.getPlainChildren())
      .hasSize(8038)
      .allMatch(md -> md.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getModulesByType())
      .hasSize(1792)
      .containsValue(ModuleType.FormModule)
    ;

    assertThat(cf.mdoModuleTypes("BusinessProcess.Задание.Form.ДействиеВыполнить"))
      .isNotEmpty()
      .hasSize(1)
    ;

    assertThat(cf.mdoModuleTypes(MdoReference.create("BusinessProcess.Задание.Form.ДействиеВыполнить")))
      .isNotEmpty()
      .hasSize(1)
    ;

    assertThat(cf.mdoModuleTypes("BusinessProcess.Задание.Form.ДействиеВыполнить2"))
      .isEmpty()
    ;

    assertThat(cf.getModulesByObject())
      .hasSize(1792)
      .containsValue(cf.findChild(MdoReference.create("BusinessProcess.Задание.Form.ДействиеВыполнить")).get())
    ;

    var mdoRef = MdoReference.create("BusinessProcess.Задание");
    var mdo = cf.findChild(mdoRef).get();

    assertThat(cf.includedSubsystems(mdoRef, false))
      .hasSize(1)
      .anyMatch(subsystem -> subsystem.getName().equals("БизнесПроцессыИЗадачи"))
    ;
    assertThat(cf.includedSubsystems(mdoRef, true))
      .hasSize(2)
      .anyMatch(subsystem -> subsystem.getName().equals("БизнесПроцессыИЗадачи"))
      .anyMatch(subsystem -> subsystem.getName().equals("СтандартныеПодсистемы"))
    ;
    assertThat(cf.includedSubsystems(mdo, true))
      .hasSize(2)
      .anyMatch(subsystem -> subsystem.getName().equals("БизнесПроцессыИЗадачи"))
      .anyMatch(subsystem -> subsystem.getName().equals("СтандартныеПодсистемы"))
    ;

    assertThat(((BusinessProcess) mdo).getModules())
      .hasSize(2)
      .anyMatch(module -> module.getModuleType() == ModuleType.ManagerModule);
    assertThat(((BusinessProcess) mdo).getAllModules())
      .hasSize(6)
      .anyMatch(module -> module.getModuleType() == ModuleType.ManagerModule)
      .anyMatch(module -> module.getModuleType() == ModuleType.FormModule);

    var module = ((BusinessProcess) mdo).getAllModules().stream()
      .filter(module1 -> module1.getModuleType() == ModuleType.FormModule)
      .findFirst().get();

    assertThat(cf.findChild(module.getUri()).get())
      .isNotEqualTo(mdo)
      .isInstanceOf(ObjectForm.class);

    var commonModule = cf.findCommonModule("АвтономнаяРабота").get();
    assertThat(cf.getModuleByUri(commonModule.getUri()))
      .isPresent()
    ;

    assertThat(cf.getModuleTypeByURI(commonModule.getUri()))
      .isNotNull()
      .isEqualTo(ModuleType.CommonModule)
    ;
    assertThat(cf.findChild(commonModule.getUri()))
      .contains(commonModule);

    assertThat(cf.getModules().stream().filter(Module::isProtected)).isEmpty();
    assertThat(cf.getAllModules().stream().filter(Module::isProtected)).isEmpty();

    assertThat(cf.getChildren().stream().filter(md -> md instanceof Form form && !form.getData().isEmpty()))
      .hasSize(cf.getCommonForms().size());
    assertThat(cf.getPlainChildren().stream().filter(md -> md instanceof Form form && !form.getData().isEmpty()))
      .hasSize(726);
    assertThat(cf.getPlainChildren().stream().filter(md -> md instanceof Form form && form.getData().isEmpty()))
      .isEmpty();

    assertThat(cf.getSynonym().isEmpty()).isFalse();
    assertThat(cf.getSynonym().get("ru")).isEqualTo("Библиотека стандартных подсистем, редакция 3.1");
    assertThat(cf.getDescription()).isEqualTo("Библиотека стандартных подсистем, редакция 3.1");
    assertThat(cf.getDescription("ru")).isEqualTo("Библиотека стандартных подсистем, редакция 3.1");
    assertThat(cf.getDescription("en")).isEqualTo("Библиотека стандартных подсистем, редакция 3.1");
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, _edt",
      "false, mdclasses"
    }
  )
  void testFullMdclasses(ArgumentsAccessor argumentsAccessor) {
    var mdc = MDTestUtils.readConfiguration(argumentsAccessor, false);
    assertThat(mdc).isInstanceOf(Configuration.class);
    var cf = (Configuration) mdc;
    assertThat(cf.getSupportVariant()).isEqualTo(SupportVariant.NONE);
    assertThat(cf.getModules())
      .hasSize(3)
      .allMatch(module -> module.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getAllModules())
      .hasSize(43 + cf.getCommonModules().size())
      .allMatch(module -> module.getSupportVariant().equals(SupportVariant.NONE));

    // проверка состава дочерних
    checkChildrenMdclasses(cf);

    assertThat(cf.getPlainChildren())
      .hasSize(223 + cf.getInterfaces().size() + cf.getStyles().size())
      .allMatch(md -> md.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getModules().stream().filter(Module::isProtected)).isEmpty();
    assertThat(cf.getAllModules().stream().filter(Module::isProtected)).hasSize(2);

    assertThat(cf.getChildren().stream().filter(md -> md instanceof Form form && !form.getData().isEmpty()))
      .hasSize(cf.getCommonForms().size());
    assertThat(cf.getPlainChildren().stream().filter(md -> md instanceof Form form && !form.getData().isEmpty()))
      .hasSize(11);
    assertThat(cf.getPlainChildren().stream().filter(md -> md instanceof Form form && form.getData().isEmpty()))
      .isEmpty();
  }

  @Test
  void testFullExt() {
    var configurationPath = Path.of("src/test/resources/ext/designer/mdclasses_ext/src/cf/Configuration.xml");
    var mdc = MDClasses.createConfiguration(configurationPath, MDCReadSettings.SKIP_SUPPORT);
    assertThat(mdc).isNotNull()
      .isInstanceOf(MDClass.class)
      .isInstanceOf(ConfigurationExtension.class);

    var cf = (ConfigurationExtension) mdc;
    assertThat(cf.isEmpty()).isFalse();
    assertThat(cf.getSupportVariant()).isEqualTo(SupportVariant.NONE);
    assertThat(cf.getModules()).isEmpty();
    assertThat(cf.getAllModules())
      .hasSize(7 + cf.getCommonModules().size())
      .allMatch(module -> module.getSupportVariant().equals(SupportVariant.NONE));

    // проверка состава дочерних
    checkChildrenExt(cf);

    // проверка порядок
    checkChildrenOrder(cf);

    assertThat(cf.getPlainChildren())
      .hasSize(330)
      .allMatch(md -> md.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getAllModules().stream().filter(Module::isProtected)).isEmpty();

    assertThat(cf.getChildren().stream().filter(md -> md instanceof Form form && !form.getData().isEmpty()))
      .hasSize(cf.getCommonForms().size());
    assertThat(cf.getPlainChildren().stream().filter(md -> md instanceof Form form && !form.getData().isEmpty()))
      .hasSize(cf.getCommonForms().size() + 12);
    assertThat(cf.getPlainChildren().stream().filter(md -> md instanceof Form form && form.getData().isEmpty()))
      .isEmpty();
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_1, _edt",
      "false, ssl_3_1"
    }
  )
  void testFullSSLSkipAll(ArgumentsAccessor argumentsAccessor) {
    var settings = MDCReadSettings.builder()
      .skipSupport(true)
      .skipRoleData(true)
      .skipFormElementItems(true)
      .skipXdtoPackage(true)
      .skipDataCompositionSchema(true)
      .build();

    var mdc = MDTestUtils.readConfiguration(argumentsAccessor, settings);
    assertThat(mdc).isInstanceOf(Configuration.class);
    var cf = (Configuration) mdc;
    assertThat(cf.getSupportVariant()).isEqualTo(SupportVariant.NONE);
    assertThat(cf.getModules())
      .hasSize(4)
      .allMatch(module -> module.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getAllModules())
      .hasSize(1320 + cf.getCommonModules().size())
      .allMatch(module -> module.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getPlainChildren())
      .hasSize(8038)
      .allMatch(md -> md.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getRoles())
      .hasSize(86)
      .allMatch(role -> role.getData() == RoleData.EMPTY)
    ;

    assertThat(cf.getXDTOPackages())
      .hasSize(38)
      .allMatch(xdtoPackage -> xdtoPackage.getData() == XdtoPackageData.EMPTY)
    ;

    var forms = cf.getPlainChildren().stream()
      .filter(FormOwner.class::isInstance)
      .map(FormOwner.class::cast)
      .map(FormOwner::getForms)
      .flatMap(Collection::stream)
      .toList();

    assertThat(forms)
      .hasSize(632)
      .allMatch(form -> form.getData().getPlainItems().isEmpty());

    var templates = cf.getPlainChildren().stream()
      .filter(TemplateOwner.class::isInstance)
      .map(TemplateOwner.class::cast)
      .map(TemplateOwner::getTemplates)
      .flatMap(Collection::stream)
      .toList();

    assertThat(templates)
      .hasSize(89)
      .allMatch(template -> template.getData().isEmpty());
  }

  @Test
  void testFullExtEdt() {
    var configurationPath = Path.of(
      "src/test/resources/ext/edt/mdclasses_ext/configuration/src/Configuration/Configuration.mdo");
    var mdc = MDClasses.createConfiguration(configurationPath, MDCReadSettings.SKIP_SUPPORT);
    assertThat(mdc).isNotNull()
      .isInstanceOf(MDClass.class)
      .isInstanceOf(ConfigurationExtension.class);

    var cf = (ConfigurationExtension) mdc;
    assertThat(cf.isEmpty()).isFalse();

    // проверка порядок
    checkChildrenOrder(cf);
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses_unknown, _edt"
    }
  )
  void unknownEnums(ArgumentsAccessor argumentsAccessor) {
    var mdc = MDTestUtils.getMDCWithSimpleTest(argumentsAccessor, false);
  }

  @ParameterizedTest
  @CsvSource(
    {
      "false, mdclasses_5_1",
      // "true, mdclasses_5_1, _edt" todo пока не поддерживается

    }
  )
  void test5_1(ArgumentsAccessor argumentsAccessor) {
    var mdc = MDTestUtils.getMDCWithSimpleTest(argumentsAccessor, false);
  }

  private static void checkChildrenSSL(Configuration cf) {
    assertThat(cf.getSubsystems())
      .hasSize(3)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getCommonModules())
      .hasSize(472)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getSessionParameters())
      .hasSize(57)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getRoles())
      .hasSize(86)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getCommonAttributes())
      .hasSize(6)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getExchangePlans())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getFilterCriteria())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getEventSubscriptions())
      .hasSize(88)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getScheduledJobs())
      .hasSize(38)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getBots()).isEmpty();

    assertThat(cf.getFunctionalOptions())
      .hasSize(65)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getFunctionalOptionsParameters())
      .hasSize(3)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getDefinedTypes())
      .hasSize(63)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getSettingsStorages())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getCommonForms())
      .hasSize(94)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getCommonCommands())
      .hasSize(58)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getCommandGroups())
      .hasSize(6)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getCommonTemplates())
      .hasSize(9)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getCommonPictures())
      .hasSize(395)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getInterfaces()).isEmpty();

    assertThat(cf.getXDTOPackages())
      .hasSize(38)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getWebServices())
      .hasSize(11)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getHttpServices()).isEmpty();

    assertThat(cf.getWsReferences()).isEmpty();

    assertThat(cf.getIntegrationServices()).isEmpty();

    assertThat(cf.getStyleItems())
      .hasSize(74)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getStyles()).isEmpty();

    assertThat(cf.getLanguages())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getConstants())
      .hasSize(135)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getCatalogs())
      .hasSize(64)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getDocuments())
      .hasSize(10)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getDocumentNumerators()).isEmpty();

    assertThat(cf.getSequences()).isEmpty();

    assertThat(cf.getDocumentJournals())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getEnums())
      .hasSize(80)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getReports())
      .hasSize(28)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getDataProcessors())
      .hasSize(68)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getChartsOfCharacteristicTypes())
      .hasSize(4)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getChartsOfAccounts()).isEmpty();

    assertThat(cf.getChartsOfCalculationTypes()).isEmpty();

    assertThat(cf.getInformationRegisters())
      .hasSize(154)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getAccumulationRegisters()).isEmpty();

    assertThat(cf.getAccountingRegisters()).isEmpty();

    assertThat(cf.getCalculationRegisters()).isEmpty();

    assertThat(cf.getBusinessProcesses())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getTasks())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NOT_EDITABLE));

    assertThat(cf.getExternalDataSources()).isEmpty();

    assertThat(cf.getDataLockControlMode()).isEqualTo(DataLockControlMode.MANAGED);
    assertThat(cf.getModalityUseMode()).isEqualTo(UseMode.USE_WITH_WARNINGS);
    assertThat(cf.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE_WITH_WARNINGS);
    assertThat(cf.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE);

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
        cf.getExternalDataSources().size());

  }

  private static void checkChildrenMdclasses(Configuration cf) {
    assertThat(cf.getSubsystems())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getCommonModules())
      .hasSize(6)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getSessionParameters())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getRoles())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getCommonAttributes())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getExchangePlans())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getFilterCriteria())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getEventSubscriptions())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getScheduledJobs())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getBots()).isEmpty();

    assertThat(cf.getFunctionalOptions())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getFunctionalOptionsParameters())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getDefinedTypes())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getSettingsStorages())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getCommonForms())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getCommonCommands())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getCommandGroups())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getCommonTemplates())
      .hasSize(10)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getCommonPictures())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    if (cf.getConfigurationSource() == ConfigurationSource.DESIGNER) {
      assertThat(cf.getInterfaces())
        .hasSize(2)
        .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));
    } else {
      assertThat(cf.getInterfaces()).isEmpty();
    }

    assertThat(cf.getXDTOPackages())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getWebServices())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getHttpServices())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getWsReferences())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getIntegrationServices()).isEmpty();

    assertThat(cf.getStyleItems())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    if (cf.getConfigurationSource() == ConfigurationSource.DESIGNER) {
      assertThat(cf.getStyles())
        .hasSize(1)
        .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));
    } else {
      assertThat(cf.getStyles()).isEmpty();
    }

    assertThat(cf.getLanguages())
      .hasSize(3)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getConstants())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getCatalogs())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getDocuments())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getDocumentNumerators())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getSequences())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getDocumentJournals())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getEnums())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getReports())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getDataProcessors())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getChartsOfCharacteristicTypes())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getChartsOfAccounts())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getChartsOfCalculationTypes())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getInformationRegisters())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getAccumulationRegisters())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getAccountingRegisters())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getCalculationRegisters())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getBusinessProcesses())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getTasks())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getExternalDataSources())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getDataLockControlMode()).isEqualTo(DataLockControlMode.AUTOMATIC);
    assertThat(cf.getModalityUseMode()).isEqualTo(UseMode.USE);
    assertThat(cf.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE_WITH_WARNINGS);
    assertThat(cf.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.DONT_USE);

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
        cf.getExternalDataSources().size());
  }

  private static void checkChildrenExt(ConfigurationExtension cf) {
    assertThat(cf.getSubsystems())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getCommonModules())
      .hasSize(9)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getSessionParameters())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getRoles())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getCommonAttributes())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getExchangePlans())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getFilterCriteria())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getEventSubscriptions())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getScheduledJobs())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getBots()).isEmpty();

    assertThat(cf.getFunctionalOptions())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getFunctionalOptionsParameters())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getDefinedTypes())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getSettingsStorages())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getCommonForms())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getCommonCommands())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getCommandGroups())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getCommonTemplates())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getCommonPictures())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getInterfaces()).isEmpty();

    assertThat(cf.getXDTOPackages())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getWebServices())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getHttpServices())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getWsReferences())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getIntegrationServices()).isEmpty();

    assertThat(cf.getStyleItems())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getStyles())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getLanguages())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getConstants())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getCatalogs())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getDocuments())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getDocumentNumerators())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getSequences())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getDocumentJournals())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getEnums())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getReports())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getDataProcessors())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getChartsOfCharacteristicTypes())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getChartsOfAccounts())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getChartsOfCalculationTypes())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getInformationRegisters())
      .hasSize(4)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getAccumulationRegisters())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getAccountingRegisters())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getCalculationRegisters())
      .hasSize(2)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getBusinessProcesses())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getTasks())
      .hasSize(1)
      .allMatch(mdo -> mdo.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getExternalDataSources()).isEmpty();

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
        cf.getExternalDataSources().size());
  }

  private static void checkChildrenOrder(ConfigurationExtension cf) {
    var ordered = List.of("Language.Русский",
      "Subsystem.ПерваяПодсистема",
      "Subsystem.ПерваяПодсистема1",
      "StyleItem.ЭлементСтиля1",
      "StyleItem.ЭлементСтиля2",
      "Style.Стиль1",
      "Style.Стиль2",
      "CommonPicture.ОбщаяКартинка1",
      "CommonPicture.ОбщаяКартинка2",
      "SessionParameter.ПараметрСеанса1",
      "SessionParameter.ПараметрСеанса2",
      "Role.Роль1",
      "Role.Роль2",
      "CommonTemplate.Макет",
      "CommonTemplate.Макет1",
      "FilterCriterion.КритерийОтбора1",
      "FilterCriterion.КритерийОтбора2",
      "CommonModule.ПростойОбщийМодуль",
      "CommonModule.ОбщийМодульВызовСервера",
      "CommonModule.ОбщийМодульПовтИспВызов",
      "CommonModule.ОбщийМодульПовтИспСеанс",
      "CommonModule.ОбщийМодульПолныйеПрава",
      "CommonModule.ПростойОбщийМодуль1",
      "CommonModule.ОбщийМодульВызовСервера1",
      "CommonModule.ОбщийМодульПовтИспВызов1",
      "CommonModule.ОбщийМодульПовтИспСеанс1",
      "CommonAttribute.ОбщийРеквизит1",
      "ExchangePlan.ПланОбмена1",
      "ExchangePlan.ПланОбмена2",
      "XDTOPackage.ПакетXDTO1",
      "XDTOPackage.ПакетXDTO2",
      "WebService.WebСервис1",
      "WebService.WebСервис2",
      "HTTPService.HTTPСервис1",
      "HTTPService.HTTPСервис2",
      "WSReference.WSСсылка1",
      "WSReference.WSСсылка2",
      "EventSubscription.ПодпискаНаСобытие1",
      "ScheduledJob.РегламентноеЗадание1",
      "SettingsStorage.ХранилищеНастроек1",
      "FunctionalOption.ФункциональнаяОпция1",
      "FunctionalOption.ФункциональнаяОпция2",
      "FunctionalOptionsParameter.ПараметрФункциональныхОпций1",
      "FunctionalOptionsParameter.ПараметрФункциональныхОпций2",
      "DefinedType.ОпределяемыйТип1",
      "CommonCommand.ОбщаяКоманда1",
      "CommonCommand.ОбщаяКоманда2",
      "CommandGroup.ГруппаКоманд1",
      "CommandGroup.ГруппаКоманд2",
      "Constant.Константа1",
      "Constant.Константа2",
      "CommonForm.Форма",
      "CommonForm.Форма1",
      "Catalog.Справочник1",
      "Catalog.Справочник2",
      "Document.Документ1",
      "Document.Документ2",
      "DocumentNumerator.НумераторДокументов1",
      "Sequence.Последовательность1",
      "DocumentJournal.ЖурналДокументов1",
      "Enum.Перечисление1",
      "Enum.Перечисление2",
      "Report.Отчет1",
      "Report.Отчет2",
      "DataProcessor.Обработка1",
      "DataProcessor.Обработка2",
      "InformationRegister.РегистрСведений1",
      "InformationRegister.РегистрСведений2",
      "InformationRegister.РегистрСведений3",
      "InformationRegister.РегистрСведений4",
      "AccumulationRegister.РегистрНакопления1",
      "AccumulationRegister.РегистрНакопления2",
      "ChartOfCharacteristicTypes.ПланВидовХарактеристик1",
      "ChartOfCharacteristicTypes.ПланВидовХарактеристик2",
      "ChartOfAccounts.ПланСчетов1",
      "ChartOfAccounts.ПланСчетов2",
      "AccountingRegister.РегистрБухгалтерии1",
      "AccountingRegister.РегистрБухгалтерии2",
      "ChartOfCalculationTypes.ПланВидовРасчета1",
      "ChartOfCalculationTypes.ПланВидовРасчета2",
      "CalculationRegister.РегистрРасчета2",
      "CalculationRegister.РегистрРасчета1",
      "BusinessProcess.БизнесПроцесс1",
      "Task.Задача1");

    assertThat(cf.getChildren()).hasSize(ordered.size());

    for (int i = 0; i < cf.getChildren().size(); i++) {
      var original = cf.getChildren().get(i);
      var fixture = ordered.get(i);
      assertThat(fixture).isEqualTo(original.getMdoReference().getMdoRef());
    }
  }
}
