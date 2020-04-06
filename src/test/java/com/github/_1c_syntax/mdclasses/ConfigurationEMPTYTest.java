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
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.utils.Absolute;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationEMPTYTest {
  @Test
  void testBuilder() {

    Configuration configuration = Configuration.create(null);

    assertThat(configuration).isNotNull();
    assertThat(configuration.getConfigurationSource()).isEqualTo(ConfigurationSource.EMPTY);

    File file = new File("src/test/resources/metadata/edt/src/Constants/Константа1/ManagerModule.bsl");
    assertThat(configuration.getModuleType(Absolute.uri(file))).isEqualTo(ModuleType.Unknown);

    Configuration configuration2 = Configuration.create();

    assertThat(configuration2).isNotNull();
    assertThat(configuration2.getConfigurationSource()).isEqualTo(ConfigurationSource.EMPTY);
    assertThat(configuration2.getChildren().stream().filter(mdObject ->
      mdObject instanceof CommonModule && mdObject.getName().equals("НесуществущийМодуль"))
      .findFirst().isPresent()).isFalse();
  }
}
