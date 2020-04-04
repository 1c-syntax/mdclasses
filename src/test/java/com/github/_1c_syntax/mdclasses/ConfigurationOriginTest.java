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
import com.github._1c_syntax.mdclasses.metadata.Configuration;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.additional.UseMode;
import com.github._1c_syntax.utils.Absolute;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationOriginTest {

  @Test
  void testBuilder() {

    File srcPath = new File("src/test/resources/metadata/original");
    Configuration configuration = Configuration.create(srcPath.toPath());

    assertThat(configuration).isNotNull();

    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.DESIGNER);
    assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.RUSSIAN);
    assertThat(configuration.getModalityUseMode()).isEqualTo(UseMode.DONT_USE);
    assertThat(configuration.getSynchronousExtensionAndAddInCallUseMode()).isEqualTo(UseMode.USE);
    assertThat(configuration.getSynchronousPlatformExtensionAndAddInCallUseMode()).isEqualTo(UseMode.DONT_USE);
    assertThat(CompatibilityMode.compareTo(configuration.getCompatibilityMode(), new CompatibilityMode(3, 10))).isEqualTo(0);
    assertThat(configuration.getModulesByType()).hasSize(22);

    File file = new File("src/test/resources/metadata/original/Documents/ПоступлениеТоваровУслуг/Ext/ManagerModule.bsl");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.ManagerModule);

    file = new File("src/test/resources/metadata/original/CommonModules/ПростойОбщийМодуль/Ext/Module.bsl");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.CommonModule);

    file = new File("src/test/resources/metadata/original/CommonForms/Форма/Ext/Form/Module.bsl");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.FormModule);

    CommonModule commonModule = (CommonModule) configuration.getChildren().stream().filter(mdObject ->
      mdObject instanceof CommonModule && mdObject.getName().equals("ПростойОбщийМодуль"))
      .findFirst().get();
    assertThat(commonModule).isNotNull();
    assertThat(commonModule.getName()).isEqualTo("ПростойОбщийМодуль");

    assertThat(configuration.getChildren().stream().filter(mdObject ->
      mdObject instanceof CommonModule)).hasSize(8);
  }

  @Test
  void testErrorBuild() {

    Path srcPath = Paths.get("src/test/resources/metadata");
    Configuration configuration = Configuration.create(srcPath);

    assertThat(configuration).isNotNull();
  }

}
