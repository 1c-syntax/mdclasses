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
package com.github._1c_syntax.bsl.reader;

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.ExternalReport;
import com.github._1c_syntax.bsl.mdclasses.MDCReadSettings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class MDOReaderTest {

  @Test
  void testReadConfigurationWithValidPath() {
    var path = Paths.get("src/test/resources/ext/edt/ssl_3_1");
    var configuration = MDOReader.readConfiguration(path);

    assertThat(configuration)
      .isNotNull()
      .isInstanceOf(Configuration.class);
  }

  @Test
  void testReadConfigurationWithSettings() {
    var path = Paths.get("src/test/resources/ext/edt/ssl_3_1");
    var settings = MDCReadSettings.DEFAULT;
    var configuration = MDOReader.readConfiguration(path, settings);

    assertThat(configuration)
      .isNotNull()
      .isInstanceOf(Configuration.class);
  }

  @Test
  void testReadConfigurationWithNonExistentPath() {
    var path = Paths.get("src/test/resources/nonexistent");
    var configuration = MDOReader.readConfiguration(path);

    assertThat(configuration)
      .isNotNull()
      .isEqualTo(Configuration.EMPTY);
  }

  @ParameterizedTest
  @ValueSource(strings = {
    "src/test/resources/ext/edt/ssl_3_1",
    "src/test/resources/ext/designer/ssl_3_1/src/cf"
  })
  void testReadConfigurationDifferentSources(String pathString) {
    var path = Paths.get(pathString);
    var configuration = MDOReader.readConfiguration(path);

    assertThat(configuration)
      .isNotNull()
      .isInstanceOf(Configuration.class);
  }

  @Test
  void testReadWithNonExistentObject() {
    var path = Paths.get("src/test/resources/ext/edt/ssl_3_1");
    var result = MDOReader.read(path, "CommonModules.NonExistent");

    // Should return null for non-existent object
    assertThat(result).isNull();
  }

  @Test
  void testReadExternalSource() {
    var path = Paths.get("src/test/resources/ext/edt/external/ExternalDataProcessors/ТестоваяОбработка/ТестоваяОбработка.mdo");
    var externalSource = MDOReader.readExternalSource(path);

    assertThat(externalSource).isNotNull();
  }

  @Test
  void testReadExternalSourceWithSettings() {
    var path = Paths.get("src/test/resources/ext/edt/external/ExternalDataProcessors/ТестоваяОбработка/ТестоваяОбработка.mdo");
    var settings = MDCReadSettings.SKIP_SUPPORT;
    var externalSource = MDOReader.readExternalSource(path, settings);

    assertThat(externalSource).isNotNull();
  }

  @Test
  void testReadExternalSourceWithInvalidPath() {
    var path = Paths.get("src/test/resources/nonexistent.mdo");
    var externalSource = MDOReader.readExternalSource(path);

    assertThat(externalSource)
      .isNotNull()
      .isEqualTo(ExternalReport.EMPTY);
  }

  @Test
  void testReadConfigurationFromFile() {
    var path = Paths.get("src/test/resources/ext/edt/ssl_3_1/configuration/Configuration.mdo");
    var configuration = MDOReader.readConfiguration(path);

    assertThat(configuration)
      .isNotNull()
      .isInstanceOf(Configuration.class);
  }

  @Test
  void testReadConfigurationFromDesignerFile() {
    var path = Paths.get("src/test/resources/ext/designer/ssl_3_1/src/Configuration/Configuration.xml");
    var configuration = MDOReader.readConfiguration(path);

    assertThat(configuration)
      .isNotNull()
      .isInstanceOf(Configuration.class);
  }

  @Test
  void testReadConfigurationWithEmptyPath() {
    var path = Paths.get("");
    var configuration = MDOReader.readConfiguration(path);

    assertThat(configuration).isNotNull();
  }
}
