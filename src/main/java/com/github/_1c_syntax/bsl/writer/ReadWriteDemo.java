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

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Демонстрация чтения и записи метаданных: три типа объектов (Subsystem, Catalog, Configuration)
 * в двух форматах — EDT (.mdo) и Конфигуратор (Designer .xml). Артефакты раскладываются по каталогам:
 * build/read-write-demo-output/edt/ и build/read-write-demo-output/designer/.
 * Запуск: {@code ./gradlew runReadWriteDemo} или указать путь в аргументе.
 */
public final class ReadWriteDemo {

  private ReadWriteDemo() {
  }

  /**
   * Точка входа: создаёт примеры Subsystem, Catalog, Configuration и записывает их в EDT и Designer.
   *
   * @param args необязательный путь к каталогу вывода; по умолчанию build/read-write-demo-output
   */
  public static void main(String[] args) throws Exception {
    var baseDir = args.length > 0 ? Paths.get(args[0]) : Paths.get("build", "read-write-demo-output");
    var edtDir = baseDir.resolve("edt");
    var designerDir = baseDir.resolve("designer");
    var edtSrc = edtDir.resolve("src");
    var designerSrc = designerDir.resolve("src").resolve("cf");
    Files.createDirectories(edtSrc);
    Files.createDirectories(designerSrc);

    var subsystem = Subsystem.builder()
      .name("DemoSubsystem")
      .uuid("0421b67e-ed26-491d-ab98-ec59002ed4ce")
      .synonym(MultiLanguageString.create("ru", "Демо подсистема"))
      .build();
    var catalog = Catalog.builder()
      .name("DemoCatalog")
      .uuid("c6c26c3c-de7a-4ed4-944d-ada62cf1ab8f")
      .synonym(MultiLanguageString.create("ru", "Демо справочник"))
      .build();
    var config = Configuration.builder()
      .name("DemoConfiguration")
      .uuid("46c7c1d0-b04d-4295-9b04-ae3207c18d29")
      .build();

    var ok = true;

    // --- EDT (.mdo) — каталог edt/ ---
    System.out.println("=== EDT (edt/) ===");
    var subsystemMdo = edtSrc.resolve("Subsystems").resolve("DemoSubsystem").resolve("DemoSubsystem.mdo");
    MDClasses.writeObject(subsystemMdo, subsystem);
    System.out.println("Written: " + subsystemMdo.toAbsolutePath());
    var readSub = new EDTReader(subsystemMdo, MDCReadSettings.SKIP_SUPPORT).read(subsystemMdo);
    ok &= checkReadBack("Subsystem", readSub instanceof Subsystem s ? s.getName() : null, subsystem.getName());

    var catalogMdo = edtSrc.resolve("Catalogs").resolve("DemoCatalog").resolve("DemoCatalog.mdo");
    MDClasses.writeObject(catalogMdo, catalog);
    System.out.println("Written: " + catalogMdo.toAbsolutePath());
    var readCat = new EDTReader(catalogMdo, MDCReadSettings.SKIP_SUPPORT).read(catalogMdo);
    ok &= checkReadBack("Catalog", readCat instanceof Catalog c ? c.getName() : null, catalog.getName());

    var configMdo = edtSrc.resolve("Configuration").resolve("Configuration.mdo");
    MDClasses.writeObject(configMdo, config);
    System.out.println("Written: " + configMdo.toAbsolutePath());
    var readConfig = MDOReader.readConfiguration(configMdo, MDCReadSettings.SKIP_SUPPORT);
    if (readConfig != null && readConfig instanceof Configuration) {
      System.out.println("  Read back Configuration: " + readConfig.getClass().getSimpleName());
    } else {
      System.out.println("  ERROR: read back Configuration failed");
      ok = false;
    }

    // --- Конфигуратор (Designer .xml) — каталог designer/ ---
    System.out.println("=== Designer (designer/) ===");
    var subsystemXml = designerSrc.resolve("Subsystems").resolve("DemoSubsystem.xml");
    MDClasses.writeObject(subsystemXml, subsystem);
    System.out.println("Written: " + subsystemXml.toAbsolutePath());
    ok &= Files.exists(subsystemXml) && Files.size(subsystemXml) > 0;

    var catalogXml = designerSrc.resolve("Catalogs").resolve("DemoCatalog.xml");
    MDClasses.writeObject(catalogXml, catalog);
    System.out.println("Written: " + catalogXml.toAbsolutePath());
    ok &= Files.exists(catalogXml) && Files.size(catalogXml) > 0;

    var configXml = designerSrc.resolve("Configuration.xml");
    MDClasses.writeObject(configXml, config);
    System.out.println("Written: " + configXml.toAbsolutePath());
    ok &= Files.exists(configXml) && Files.size(configXml) > 0;

    if (ok) {
      System.out.println("OK: all objects written to edt/ and designer/ (Subsystem, Catalog, Configuration).");
    } else {
      System.exit(1);
    }
  }

  private static boolean checkReadBack(String type, String readName, String expectedName) {
    if (readName != null && readName.equals(expectedName)) {
      System.out.println("  Read back " + type + ": " + readName);
      return true;
    }
    System.out.println("  ERROR: read back " + type + " failed (expected " + expectedName + ", got " + readName + ")");
    return false;
  }
}
