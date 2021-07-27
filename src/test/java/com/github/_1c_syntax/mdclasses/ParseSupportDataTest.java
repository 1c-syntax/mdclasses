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
package com.github._1c_syntax.mdclasses;

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.support_configuration.ParseSupportData;
import com.github._1c_syntax.support_configuration.SupportVariant;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class ParseSupportDataTest {

  private static final String BASE_PATH = "src/test/resources/support/support";
  private final File parentConfigurationsBin = new File(BASE_PATH, "correct/ParentConfigurations.bin");

  @Test
  void testRead() {
    ParseSupportData parseSupportData = new ParseSupportData(parentConfigurationsBin.toPath());
    assertThat(parseSupportData.getSupportMap()).isNotEmpty();
  }

  @Test
  void testConfigurationSupportEDT() {

    final var PATH_TO_SUPPORT = "src/test/resources/support/edt";
    var srcPath = Paths.get(PATH_TO_SUPPORT).toAbsolutePath();
    var configuration = (Configuration) MDClasses.createConfiguration(srcPath);

    assertThat(configuration.getModulesBySupport()).isNotEmpty();

    var path = Paths.get(PATH_TO_SUPPORT, "src/Catalogs/ПервыйСправочник/ObjectModule.bsl");
    checkModuleSupport(configuration, path, SupportVariant.NOT_EDITABLE);

    path = Paths.get(PATH_TO_SUPPORT, "src/Configuration/SessionModule.bsl");
    checkModuleSupport(configuration, path, SupportVariant.EDITABLE_SUPPORT_ENABLED);

    path = Paths.get(PATH_TO_SUPPORT, "src/Documents/ПервыйДокумент/ObjectModule.bsl");
    checkModuleSupport(configuration, path, SupportVariant.NOT_SUPPORTED);

    path = Paths.get(PATH_TO_SUPPORT, "src/Catalogs/ПервыйСправочник/Forms/ФормаЭлемента/Module.bsl");
    checkModuleSupport(configuration, path, SupportVariant.NOT_EDITABLE);
  }

  @Test
  void testConfigurationSupportDesigner() {
    final var PATH_TO_SUPPORT = "src/test/resources/support/original";
    var srcPath = Paths.get(PATH_TO_SUPPORT).toAbsolutePath();
    var configuration = (Configuration) MDClasses.createConfiguration(srcPath);

    assertThat(configuration.getModulesBySupport()).isNotEmpty();

    var path = Paths.get(PATH_TO_SUPPORT, "Catalogs/ПервыйСправочник/Ext/ObjectModule.bsl");
    checkModuleSupport(configuration, path, SupportVariant.NOT_EDITABLE);

    path = Paths.get(PATH_TO_SUPPORT, "Ext/SessionModule.bsl");
    checkModuleSupport(configuration, path, SupportVariant.EDITABLE_SUPPORT_ENABLED);

    path = Paths.get(PATH_TO_SUPPORT, "Documents/ПервыйДокумент/Ext/ObjectModule.bsl");
    checkModuleSupport(configuration, path, SupportVariant.NOT_SUPPORTED);

    path = Paths.get(PATH_TO_SUPPORT, "Catalogs/ПервыйСправочник/Forms/ФормаЭлемента/Ext/Form/Module.bsl");
    checkModuleSupport(configuration, path, SupportVariant.NOT_EDITABLE);
  }

  @Test
  void testConfigurationFullSupportDesigner() {

    final var PATH_TO_SUPPORT = "src/test/resources/support/original-full-support";
    var srcPath = Paths.get(PATH_TO_SUPPORT).toAbsolutePath();
    var configuration = (Configuration) MDClasses.createConfiguration(srcPath);

    assertThat(configuration.getModulesBySupport()).isNotEmpty();

    var path = Paths.get(PATH_TO_SUPPORT, "Catalogs/ПервыйСправочник/Ext/ObjectModule.bsl");
    checkModuleSupport(configuration, path, SupportVariant.NOT_EDITABLE);

    path = Paths.get(PATH_TO_SUPPORT, "Documents/ПервыйДокумент/Ext/ObjectModule.bsl");
    checkModuleSupport(configuration, path, SupportVariant.NOT_EDITABLE);

    path = Paths.get(PATH_TO_SUPPORT, "Catalogs/ПервыйСправочник/Forms/ФормаЭлемента/Ext/Form/Module.bsl");
    checkModuleSupport(configuration, path, SupportVariant.NOT_EDITABLE);
  }

  @Test
  void testIncorrectSupportBin() {
    var path = Path.of(BASE_PATH, "incorrect/ParentConfigurations.bin");
    ParseSupportData parseSupportData = new ParseSupportData(path);
    assertThat(parseSupportData.getSupportMap()).isEmpty();
  }

  private void checkModuleSupport(Configuration configuration,
                                  Path path,
                                  SupportVariant supportVariant) {
    var module = configuration.getModuleByUri(path.toAbsolutePath().toUri());
    assertThat(module).isPresent();
    assertThat(module.get().getSupportVariant()).isEqualTo(supportVariant);
  }

}
