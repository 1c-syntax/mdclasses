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
package com.github._1c_syntax.bsl.test_utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.file.PathUtils;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Генератор JSON-фикстур для объектов MDO верхнего уровня.
 * <p>
 * Обходит уже существующие json-фикстуры верхнего уровня (файлы {@code <pack>/<mdoRef>.json}),
 * перечитывает исходные MDO из формата конфигуратора и сохраняет их сериализацию поверх фикстуры.
 * Используется для пересборки фикстур после изменения модели MDO.
 * <p>
 * Запускается вручную как обычное java-приложение (метод {@link #main(String[])}) с classpath тестов,
 * например из IDE. В обычный прогон тестов не входит.
 */
@UtilityClass
public class GenerateMdoFixtures {

  private static final Path FIXTURES_BASE = Fixtures.FIXTURES_PATH;

  @SneakyThrows
  public static void main(String[] args) {
    try (var packsStream = Files.list(FIXTURES_BASE)) {
      packsStream.filter(Files::isDirectory).forEach(GenerateMdoFixtures::generateForPack);
    }
    System.out.println("Done. Regenerated top-level fixtures in " + FIXTURES_BASE);
  }

  @SneakyThrows
  private static void generateForPack(Path packDir) {
    var pack = packDir.getFileName().toString();
    try (var filesStream = Files.list(packDir)) {
      filesStream
        .filter(path -> path.toString().endsWith(".json"))
        .filter(path -> {
          var fileName = path.getFileName().toString();
          return fileName.startsWith("InformationRegisters.")
            || fileName.startsWith("CalculationRegisters.")
            || fileName.startsWith("ExternalDataSources.")
            || fileName.equals("Configuration.json");
        })
        .forEach(fixtureFile -> regenerate(pack, fixtureFile));
    }
  }

  private static void regenerate(String pack, Path fixtureFile) {
    var mdoRef = PathUtils.getBaseName(fixtureFile.getFileName());
    var mdo = Fixtures.get(pack, mdoRef, false);
    if (mdo == null) {
      System.out.println("SKIP " + pack + "/" + mdoRef + " — source is null");
      return;
    }
    Fixtures.write(mdo, fixtureFile);
    System.out.println("  " + pack + "/" + mdoRef + ".json");
  }
}
