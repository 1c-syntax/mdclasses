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
package com.github._1c_syntax.mdclasses;

import com.github._1c_syntax.mdclasses.mdo.CommonModule;
import com.github._1c_syntax.mdclasses.mdo.Subsystem;
import com.github._1c_syntax.mdclasses.metadata.Configuration;
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

public class ConfigurationEDTTest {
  @Test
  void testBuilder() {

    File srcPath = new File("src/test/resources/metadata/edt");
    Configuration configuration = Configuration.create(srcPath.toPath());

    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);
    assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.RUSSIAN);
    assertThat(configuration.getModalityUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE_WITH_WARNINGS);
    assertThat(configuration.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.DONT_USE);
    assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(), new CompatibilityMode(3, 10))).isEqualTo(0);
    assertThat(configuration.getModulesByType()).hasSize(35);

    File file = new File("src/test/resources/metadata/edt/src/Constants/Константа1/ValueManagerModule.bsl");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.ValueManagerModule);

    file = new File("src/test/resources/metadata/edt/src/CommonModules/ПростойОбщийМодуль/Module.bsl");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.CommonModule);

    file = new File("src/test/resources/metadata/edt/src/Catalogs/Справочник1/Forms/ФормаЭлемента/Module.bsl");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.FormModule);

    file = new File("src/test/resources/metadata/edt/src/Catalogs/Справочник1/Commands/Команда1/CommandModule.bsl");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.CommandModule);

    file = new File("src/test/resources/metadata/edt/src/CommonForms/Форма/Module.bsl");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.FormModule);

    CommonModule commonModule = (CommonModule) configuration.getChildren().stream().filter(mdObject ->
      mdObject instanceof CommonModule && mdObject.getName().equals("ПростойОбщийМодуль"))
      .findFirst().get();
    assertThat(commonModule).isNotNull();
    assertThat(commonModule.getName()).isEqualTo("ПростойОбщийМодуль");

    assertThat(configuration.getChildren().stream().filter(mdObject ->
      mdObject instanceof CommonModule)).hasSize(6);

    Subsystem subsystem = (Subsystem) configuration.getChildrenByMdoRef().get("Subsystem.ПерваяПодсистема");
    assertThat(subsystem).isNotNull();
    assertThat(subsystem.getChildren()).isNotNull();
    assertThat(subsystem.getChildren()).hasSize(2);
    // дочерние все подсистемы
    assertThat(subsystem.getChildren().stream().allMatch(child -> child.get().getType() == MDOType.SUBSYSTEM)).isTrue();

    // проверим что у всех дочерних объектов дочерних подсистем подсситема в списке includedSubsystem
    subsystem.getChildren().forEach(child -> {
      Subsystem childSubsystem = (Subsystem) child.get();
      assertThat(childSubsystem.getChildren()).isNotNull();
      assertThat(childSubsystem.getChildren()).hasSize(2);
      childSubsystem.getChildren().forEach(childMDO -> {
        var mdo = childMDO.get();
        assertThat(mdo.getIncludedSubsystems()).isNotNull();
        assertThat(mdo.getIncludedSubsystems()).contains(childSubsystem);
      });
    });
  }

  @Test
  void testErrorBuild() {

    Path srcPath = Paths.get("src/test/resources/metadata");
    Configuration configuration = Configuration.create(srcPath);

    assertThat(configuration).isNotNull();

    File file = new File("src/test/resources/metadata/Module.os");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.Unknown);
  }

  @Test
  void testBuilderEn() {

    File srcPath = new File("src/test/resources/metadata/edt_en");
    Configuration configuration = Configuration.create(srcPath.toPath());

    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);
    assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.ENGLISH);
    assertThat(configuration.getModalityUseMode()).isEqualTo(UseMode.DONT_USE);
    assertThat(configuration.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.DONT_USE);
    assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(), new CompatibilityMode(3, 14))).isEqualTo(0);
    assertThat(configuration.getModulesByType()).hasSize(2);

    File file = new File("src/test/resources/metadata/edt_en/src/CommonModules/CommonModule/Module.bsl");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.CommonModule);

  }
}
