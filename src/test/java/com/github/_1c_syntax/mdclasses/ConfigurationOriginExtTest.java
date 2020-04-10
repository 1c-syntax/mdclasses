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
import com.github._1c_syntax.mdclasses.metadata.additional.*;
import com.github._1c_syntax.utils.Absolute;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationOriginExtTest {

  @Test
  void testBuilder() {

    File srcPath = new File("src/test/resources/metadata/original_ext");
    Configuration configuration = Configuration.create(srcPath.toPath());

    assertThat(configuration).isNotNull();

    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.DESIGNER);
    assertThat(configuration.getScriptVariant()).isEqualTo(ScriptVariant.RUSSIAN);
    assertThat(configuration.getModulesByType()).hasSize(6);

    File file = new File("src/test/resources/metadata/original_ext/CommonModules/ПростойОбщийМодуль1/Ext/Module.bsl");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.CommonModule);

    file = new File("src/test/resources/metadata/original_ext/CommonForms/Форма1/Ext/Form/Module.bsl");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.FormModule);

    CommonModule commonModule1 = (CommonModule) configuration.getChildren().stream().filter(mdObject ->
            mdObject instanceof CommonModule && mdObject.getName().equals("ПростойОбщийМодуль1"))
            .findFirst().get();
    assertThat(commonModule1).isNotNull();
    assertThat(commonModule1.getName()).isEqualTo("ПростойОбщийМодуль1");

    assertThat(configuration.getChildren().stream().filter(mdObject ->
      mdObject instanceof CommonModule)).hasSize(9);
  }

  @Test
  void testErrorBuild() {

    Path srcPath = Paths.get("src/test/resources/metadata");
    Configuration configuration = Configuration.create(srcPath);

    assertThat(configuration).isNotNull();
  }

}
