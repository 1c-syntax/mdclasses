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
import java.net.URI;
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
    assertThat(configuration.getModulesByURI().size()).isEqualTo(22);
    assertThat(configuration.getCommonModules()).hasSize(8);

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

    assertThat(configuration.getCommonModule("пРостойобщийМодуль")).isPresent();
    assertThat(configuration.getCommonModule("ТряЛяЛя")).isNotPresent();

    URI uri = Paths.get("src/test/resources/metadata/original/CommonModules/ГлобальныйКлиент/Ext/Module.bsl").toUri();
    assertThat(configuration.getModulesByURI().get(uri).getName()).isEqualTo("ГлобальныйКлиент");

    assertThat(configuration.getChildren().stream().filter(mdObject ->
      mdObject instanceof CommonModule)).hasSize(8);

    Subsystem subsystem = (Subsystem) configuration.getChildrenByMdoRef().get("Subsystem.ПерваяПодсистема");
    assertThat(subsystem).isNotNull();
    assertThat(subsystem.getChildren()).isNotNull();
    assertThat(subsystem.getChildren()).hasSize(4);
    // 2 дочерних - это подсистемы
    assertThat(subsystem.getChildren().stream().filter(child -> child.get().getType() == MDOType.SUBSYSTEM)).hasSize(2);

    // проверим что у всех дочерних объектов дочерних подсистем подсситема в списке includedSubsystem
    subsystem.getChildren().forEach(child -> {
      var childMDO = child.get();
      if (childMDO.getType() == MDOType.SUBSYSTEM) {
        testSubsystem((Subsystem) childMDO);
      } else {
        assertThat(childMDO.getIncludedSubsystems()).isNotNull();
        assertThat(childMDO.getIncludedSubsystems()).contains(subsystem);
      }

    });
  }

  @Test
  void testErrorBuild() {

    Path srcPath = Paths.get("src/test/resources/metadata");
    Configuration configuration = Configuration.create(srcPath);

    assertThat(configuration).isNotNull();
  }

  private void testSubsystem(Subsystem subsystem) {
    assertThat(subsystem.getChildren()).isNotNull();
    assertThat(subsystem.getChildren()).hasSize(2);
    subsystem.getChildren().forEach(childMDO -> {
      var mdo = childMDO.get();
      assertThat(mdo.getIncludedSubsystems()).isNotNull();
      assertThat(mdo.getIncludedSubsystems()).contains(subsystem);
    });
  }

}
