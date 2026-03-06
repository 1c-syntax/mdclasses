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
import com.github._1c_syntax.bsl.mdo.Catalog;
import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты записи объектов метаданных в формате Конфигуратора (Designer .xml).
 */
class MDOWriterDesignerTest {

  private static final String META_DATA_OBJECT = "MetaDataObject";

  @Test
  void writeSubsystemDesignerXml(@TempDir Path tempDir) throws Exception {
    var subsystem = Subsystem.builder()
      .name("TestSubsystemDesigner")
      .uuid("3d00f7d6-e3b0-49cf-8093-e2e4f6ea2293")
      .synonym(MultiLanguageString.create("ru", "Подсистема для Конфигуратора"))
      .build();

    var outFile = tempDir.resolve("Subsystems").resolve("TestSubsystemDesigner.xml");
    MDClasses.writeObject(outFile, subsystem);

    assertThat(outFile).exists().isRegularFile();
    var content = Files.readString(outFile, StandardCharsets.UTF_8);
    assertThat(content).contains(META_DATA_OBJECT);
    assertThat(content).contains("<Subsystem uuid=\"3d00f7d6-e3b0-49cf-8093-e2e4f6ea2293\">");
    assertThat(content).contains("<Properties>");
    assertThat(content).contains("<Name>TestSubsystemDesigner</Name>");
    assertThat(content).contains("Подсистема для Конфигуратора");
    assertThat(content).contains("</Properties>");
    assertThat(content).doesNotContainPattern("<Subsystem[^>]*>\\s*<Subsystem");
  }

  @Test
  void writeSubsystemWithChildrenHasChildObjects(@TempDir Path tempDir) throws Exception {
    var child = Subsystem.builder().name("ChildSubsystem").build();
    var subsystem = Subsystem.builder()
      .name("ParentSubsystem")
      .uuid("3d00f7d6-e3b0-49cf-8093-e2e4f6ea2293")
      .subsystem(child)
      .build();

    var outFile = tempDir.resolve("Subsystems").resolve("ParentSubsystem.xml");
    MDClasses.writeObject(outFile, subsystem);

    var content = Files.readString(outFile, StandardCharsets.UTF_8);
    assertThat(content).contains("<ChildObjects>");
    assertThat(content).contains("<Subsystem>ChildSubsystem</Subsystem>");
    assertThat(content).contains("</ChildObjects>");
  }

  @Test
  void writeCatalogDesignerXml(@TempDir Path tempDir) throws Exception {
    var catalog = Catalog.builder()
      .name("TestCatalogDesigner")
      .uuid("eeef463d-d5e7-42f2-ae53-10279661f59d")
      .synonym(MultiLanguageString.create("ru", "Справочник для Конфигуратора"))
      .build();

    var outFile = tempDir.resolve("Catalogs").resolve("TestCatalogDesigner.xml");
    MDClasses.writeObject(outFile, catalog);

    assertThat(outFile).exists().isRegularFile();
    var content = Files.readString(outFile, StandardCharsets.UTF_8);
    assertThat(content).contains(META_DATA_OBJECT);
    assertThat(content).contains("<Catalog uuid=\"eeef463d-d5e7-42f2-ae53-10279661f59d\">");
    assertThat(content).contains("<Properties>");
    assertThat(content).contains("<Name>TestCatalogDesigner</Name>");
    assertThat(content).contains("Справочник для Конфигуратора");
    assertThat(content).doesNotContainPattern("<Catalog[^>]*>\\s*<Catalog");
  }

  @Test
  void writeConfigurationDesignerXml(@TempDir Path tempDir) throws Exception {
    var config = Configuration.builder()
      .name("TestConfigDesigner")
      .uuid("afc7a6ad-095f-4fdc-8ba5-8c692defb671")
      .build();

    var outFile = tempDir.resolve("Configuration.xml");
    MDClasses.writeObject(outFile, config);

    assertThat(outFile).exists().isRegularFile();
    var content = Files.readString(outFile, StandardCharsets.UTF_8);
    assertThat(content).contains(META_DATA_OBJECT);
    assertThat(content).contains("<Configuration uuid=\"afc7a6ad-095f-4fdc-8ba5-8c692defb671\">");
    assertThat(content).contains("<Name>TestConfigDesigner</Name>");
    assertThat(content).contains("ChildObjects");
    assertThat(content).doesNotContainPattern("<Configuration[^>]*>\\s*<Configuration");
  }
}
