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
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.Catalog;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.Role;
import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.MDCReadSettings;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.reader.MDOReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DesignerJaxbWriterTest {

  private static final Path DESIGNER_CF = Path.of("src/test/resources/ext/designer/mdclasses/src/cf");

  @Test
  void writeSubsystemRoundTrip(@TempDir Path tempDir) throws Exception {
    MD mdo = (MD) MDOReader.read(DESIGNER_CF, "Subsystems.ПерваяПодсистема", MDCReadSettings.DEFAULT);
    assertThat(mdo).isInstanceOf(Subsystem.class);

    Path out = tempDir.resolve("Subsystems.ПерваяПодсистема.xml");
    MDClassesJaxbWriter.writeObjectJaxb(out, mdo);

    assertThat(out).exists();
    String xml = Files.readString(out);
    assertThat(xml).contains("MetaDataObject");
    assertThat(xml).contains("Subsystem");
    assertThat(xml).contains("http://v8.1c.ru/8.3/MDClasses");
    assertThat(xml).contains("version=\"" + JaxbWriteDefaults.DEFAULT_FORMAT_VERSION + "\"");
    assertThat(xml).contains("ПерваяПодсистема");
  }

  @Test
  void writeCatalogRoundTrip(@TempDir Path tempDir) throws Exception {
    MD mdo = (MD) MDOReader.read(DESIGNER_CF, "Catalogs.Справочник1", MDCReadSettings.DEFAULT);
    assertThat(mdo).isInstanceOf(Catalog.class);

    Path out = tempDir.resolve("Catalogs.Справочник1.xml");
    MDClassesJaxbWriter.writeObjectJaxb(out, mdo);

    assertThat(out).exists();
    String xml = Files.readString(out);
    assertThat(xml).contains("MetaDataObject");
    assertThat(xml).contains("Catalog");
    assertThat(xml).contains("http://v8.1c.ru/8.3/MDClasses");
    assertThat(xml).contains("Справочник1");
  }

  @Test
  void writeConfigurationRoundTrip(@TempDir Path tempDir) throws Exception {
    var mdc = MDClasses.createConfiguration(DESIGNER_CF, MDCReadSettings.DEFAULT);
    assertThat(mdc).isInstanceOf(Configuration.class);
    Configuration cf = (Configuration) mdc;

    Path out = tempDir.resolve("Configuration.xml");
    MDClassesJaxbWriter.writeConfigurationJaxb(out, cf);

    assertThat(out).exists();
    String xml = Files.readString(out);
    assertThat(xml).contains("MetaDataObject");
    assertThat(xml).contains("Configuration");
    assertThat(xml).contains("http://v8.1c.ru/8.3/MDClasses");
  }

  @Test
  void writeUnsupportedTypeThrows() {
    // Use a type not yet supported by JAXB writer (e.g. Constant, SessionParameter)
    MD mdo = (MD) MDOReader.read(DESIGNER_CF, "Constants.Константа1", MDCReadSettings.DEFAULT);
    assertThat(mdo).isNotNull();

    assertThatThrownBy(() -> MDClassesJaxbWriter.writeObjectJaxb(Path.of("out.xml"), mdo))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("does not support type");
  }

  @Test
  void writeRoleRoundTrip(@TempDir Path tempDir) throws Exception {
    MD mdo = (MD) MDOReader.read(DESIGNER_CF, "Roles.Роль1", MDCReadSettings.DEFAULT);
    assertThat(mdo).isInstanceOf(Role.class);

    Path out = tempDir.resolve("Roles").resolve("Роль1.xml");
    Files.createDirectories(out.getParent());
    MDClassesJaxbWriter.writeObjectJaxb(out, mdo);

    assertThat(out).exists();
    String xml = Files.readString(out);
    assertThat(xml).contains("MetaDataObject");
    assertThat(xml).contains("Role");
    assertThat(xml).contains("Роль1");
  }

  @Test
  void writeConfigurationToFolderWithTypeFilter(@TempDir Path tempDir) throws Exception {
    Configuration cf = (Configuration) MDClasses.createConfiguration(DESIGNER_CF, MDCReadSettings.DEFAULT);
    WriteOptions options = WriteOptions.builder()
      .typeFilter(Set.of(MDOType.CATALOG, MDOType.DOCUMENT))
      .build();

    MDClassesJaxbWriter.writeConfigurationToFolder(tempDir, cf, options);

    assertThat(tempDir.resolve("Configuration.xml")).exists();
    assertThat(tempDir.resolve("Catalogs").resolve("Справочник1.xml")).exists();
    assertThat(tempDir.resolve("Documents").resolve("Документ1.xml")).exists();
    assertThat(tempDir.resolve("Subsystems")).doesNotExist();
  }

  @Test
  void writeConfigurationToFolderFull(@TempDir Path tempDir) throws Exception {
    Configuration cf = (Configuration) MDClasses.createConfiguration(DESIGNER_CF, MDCReadSettings.DEFAULT);
    MDClassesJaxbWriter.writeConfigurationToFolder(tempDir, cf);

    assertThat(tempDir.resolve("Configuration.xml")).exists();
    assertThat(tempDir.resolve("Subsystems")).isDirectory();
    assertThat(tempDir.resolve("Catalogs")).isDirectory();
    assertThat(tempDir.resolve("Documents")).isDirectory();
    assertThat(tempDir.resolve("Roles").resolve("Роль1.xml")).exists();
  }
}
