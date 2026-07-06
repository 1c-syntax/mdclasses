/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
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
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import com.github._1c_syntax.bsl.test_utils.assertions.ConfigurationCollectionAssert;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.ModuleType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ConfigurationTest {

  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, Configuration",
      "false, mdclasses, Configuration",
      "true, mdclasses_3_18, Configuration",
      "false, mdclasses_3_18, Configuration",
      "true, mdclasses_ext, Configuration",
      "false, mdclasses_ext, Configuration",
      "true, mdclasses_3_25, Configuration",
      "false, mdclasses_3_25, Configuration",
      "true, mdclasses_3_27, Configuration",
      "false, mdclasses_3_27, Configuration",
      "false, mdclasses_5_1, Configuration",
      "true, mdclasses_5_1, Configuration"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var packName = argumentsAccessor.getString(1);
    var cf = (CF) Fixtures.get(argumentsAccessor);
    assertThat(cf).isNotNull();
    ConfigurationCollectionAssert.assertThat(cf, false)
      .hasSize(packName)
      .containsAllChildren()
      .containsAllPlainChildren();
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_1, Configuration",
      "false, ssl_3_1, Configuration",
      "true, ssl_3_2, Configuration",
      "false, ssl_3_2, Configuration"
    }
  )
  void testFullSSL(ArgumentsAccessor argumentsAccessor) {
    var packName = argumentsAccessor.getString(1);
    var cf = (CF) Fixtures.get(argumentsAccessor);
    assertThat(cf).isNotNull();
    ConfigurationCollectionAssert.assertThat(cf, false)
      .hasSize(packName)
      .containsAllChildren()
      .containsAllPlainChildren();

    assertThat(cf.getChildren().stream().filter(md -> md instanceof Form form && !form.getData().isEmpty()))
      .hasSize(cf.getCommonForms().size());
    assertThat(cf.getPlainChildren().stream().filter(md -> md instanceof Form form && !form.getData().isEmpty()))
      .isNotEmpty();
    assertThat(cf.getPlainChildren().stream().filter(md -> md instanceof Form form && form.getData().isEmpty()))
      .isEmpty();

    Assertions.assertThat(cf.getAllModules(), false)
      .containsAll(cf.getAllModules(), cf.getPlainChildren());

    assertThat(cf.getModules().stream().filter(Module::isProtected)).isEmpty();
    assertThat(cf.getAllModules().stream().filter(Module::isProtected)).isEmpty();

    assertThat(cf.getModulesByType())
      .containsValue(ModuleType.FormModule)
      .containsValue(ModuleType.CommandModule)
      .containsValue(ModuleType.CommonModule)
      .containsValue(ModuleType.ManagerModule)
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
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_1",
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

    var formatEDT = argumentsAccessor.getBoolean(0);
    var packName = argumentsAccessor.getString(1);
    var mdc = Fixtures.getNoCache(packName, "Configuration", formatEDT, settings);
    assertThat(mdc).isInstanceOf(Configuration.class);

    var cf = (Configuration) mdc;
    assertThat(cf).isNotNull();
    assertThat(cf.getSupportVariant()).isEqualTo(SupportVariant.NONE);
    assertThat(cf.getModules())
      .hasSize(4)
      .allMatch(module -> module.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getAllModules())
      .hasSize(2197)
      .allMatch(module -> module.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getPlainChildren())
      .allMatch(md -> md.getSupportVariant().equals(SupportVariant.NONE));

    assertThat(cf.getRoles())
      .hasSize(107)
      .allMatch(role -> role.getData() == RoleData.EMPTY)
    ;

    assertThat(cf.getXDTOPackages())
      .hasSize(53)
      .allMatch(xdtoPackage -> xdtoPackage.getData() == XdtoPackageData.EMPTY)
    ;

    var forms = cf.getPlainChildren().stream()
      .filter(FormOwner.class::isInstance)
      .map(FormOwner.class::cast)
      .map(FormOwner::getForms)
      .flatMap(Collection::stream)
      .toList();

    assertThat(forms)
      .hasSize(769)
      .allMatch(form -> form.getData().getPlainItems().isEmpty());

    var templates = cf.getPlainChildren().stream()
      .filter(TemplateOwner.class::isInstance)
      .map(TemplateOwner.class::cast)
      .map(TemplateOwner::getTemplates)
      .flatMap(Collection::stream)
      .toList();

    assertThat(templates)
      .hasSize(129)
      .allMatch(template -> template.getData().isEmpty());
  }
}