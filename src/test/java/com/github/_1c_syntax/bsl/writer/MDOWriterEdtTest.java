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
package com.github._1c_syntax.bsl.writer;

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.mdclasses.MDCReadSettings;
import com.github._1c_syntax.bsl.mdo.Catalog;
import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.reader.MDOReader;
import com.github._1c_syntax.bsl.reader.edt.EDTReader;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты записи объектов метаданных в формате EDT (.mdo).
 */
class MDOWriterEdtTest {

  @Test
  void writeSubsystemThenReadBack(@TempDir Path tempDir) throws Exception {
    var subsystem = Subsystem.builder()
      .name("TestSubsystem")
      .uuid("test-uuid-123")
      .synonym(MultiLanguageString.create("ru", "Тестовая подсистема"))
      .build();

    var outFile = tempDir.resolve("Subsystems").resolve("TestSubsystem").resolve("TestSubsystem.mdo");
    MDClasses.writeObject(outFile, subsystem);

    assertThat(outFile).exists();
    assertThat(outFile).isRegularFile();
    var content = Files.readString(outFile, StandardCharsets.UTF_8);
    assertThat(content).as("written file content").isNotEmpty();
    assertThat(content).contains("mdclass:Subsystem");
    assertThat(content).contains("<name>TestSubsystem</name>");
    assertThat(content).contains("test-uuid-123");
    assertThat(content).contains("Тестовая подсистема");
    assertThat(content).contains("<includeHelpInContents>");
    assertThat(content).contains("<includeInCommandInterface>");
    int namePos = content.indexOf("<name>TestSubsystem</name>");
    int includeHelpPos = content.indexOf("<includeHelpInContents>");
    int includeCmdPos = content.indexOf("<includeInCommandInterface>");
    assertThat(namePos).isLessThan(includeHelpPos);
    assertThat(includeHelpPos).isLessThan(includeCmdPos);

    var reader = new EDTReader(outFile, MDCReadSettings.SKIP_SUPPORT);
    var readBack = reader.read(outFile);
    assertThat(readBack).isNotNull().isInstanceOf(Subsystem.class);
    var readSubsystem = (Subsystem) readBack;
    assertThat(readSubsystem.getName()).isEqualTo(subsystem.getName());
    assertThat(readSubsystem.getUuid()).isEqualTo(subsystem.getUuid());
  }

  @Test
  void writeConfigurationThenReadBack(@TempDir Path tempDir) throws Exception {
    var config = Configuration.builder()
      .name("TestConfig")
      .uuid("test-config-uuid-001")
      .build();

    var configurationMdo = tempDir.resolve("src").resolve("Configuration").resolve("Configuration.mdo");
    MDClasses.writeObject(configurationMdo, config);

    assertThat(configurationMdo).exists().isRegularFile();
    var content = Files.readString(configurationMdo, StandardCharsets.UTF_8);
    assertThat(content).contains("mdclass:Configuration");
    assertThat(content).contains("<name>TestConfig</name>");
    assertThat(content).contains("test-config-uuid-001");

    var readBack = MDOReader.readConfiguration(configurationMdo, MDCReadSettings.SKIP_SUPPORT);
    assertThat(readBack).isNotNull().isInstanceOf(Configuration.class);
  }

  @Test
  void writeCatalogThenReadBack(@TempDir Path tempDir) throws Exception {
    var catalog = Catalog.builder()
      .name("TestCatalog")
      .uuid("catalog-uuid-001")
      .synonym(MultiLanguageString.create("ru", "Тестовый справочник"))
      .checkUnique(true)
      .build();

    var outFile = tempDir.resolve("Catalogs").resolve("TestCatalog").resolve("TestCatalog.mdo");
    MDClasses.writeObject(outFile, catalog);

    assertThat(outFile).exists().isRegularFile();
    var content = Files.readString(outFile, StandardCharsets.UTF_8);
    assertThat(content).contains("Catalog");
    assertThat(content).contains("<name>TestCatalog</name>");
    assertThat(content).contains("catalog-uuid-001");
    assertThat(content).contains("Тестовый справочник");

    var reader = new EDTReader(outFile, MDCReadSettings.SKIP_SUPPORT);
    var readBack = reader.read(outFile);
    assertThat(readBack).isNotNull().isInstanceOf(Catalog.class);
    var readCatalog = (Catalog) readBack;
    assertThat(readCatalog.getName()).isEqualTo(catalog.getName());
    assertThat(readCatalog.getUuid()).isEqualTo(catalog.getUuid());
  }

  @Test
  void writeObjectThrowsOnNullPath() {
    var subsystem = Subsystem.builder().name("Test").build();
    try {
      MDClasses.writeObject((Path) null, subsystem);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(IllegalArgumentException.class);
    }
  }

  @Test
  void writeObjectThrowsOnNullObject(@TempDir Path tempDir) {
    var path = tempDir.resolve("Test.mdo");
    try {
      MDClasses.writeObject(path, null);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(IllegalArgumentException.class);
    }
  }

  @Test
  void writeObjectThrowsOnUnsupportedFormat(@TempDir Path tempDir) {
    var subsystem = Subsystem.builder().name("Test").build();
    var path = tempDir.resolve("Test.txt");
    try {
      MDClasses.writeObject(path, subsystem);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(UnsupportedOperationException.class);
      assertThat(e.getMessage()).contains(".mdo").contains(".xml");
    }
  }
}
