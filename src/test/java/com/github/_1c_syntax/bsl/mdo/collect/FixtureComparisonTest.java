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
package com.github._1c_syntax.bsl.mdo.collect;

import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import org.apache.commons.io.file.PathUtils;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест, проверяющий ВСЕ фикстуры - корректность прочитанных данных сохраненным json примерам
 * <p>
 * Для каждого json-файла-фикстуры выполняется загрука соответствующих MDO из исходных файлов → сериализация в JSON →
 * сверка с сохраненной JSON-фикстурой
 */
class FixtureComparisonTest {

  @TestFactory
  Stream<DynamicTest> compareAllFixturesDesigner() {
    return discoverFixtureRefs().stream()
      .map(fixRef -> createTest(fixRef, false));
  }

  @TestFactory
  Stream<DynamicTest> compareAllFixturesEDT() {
    return discoverFixtureRefs().stream()
      .map(fixRef -> createTest(fixRef, true));
  }

  private static DynamicTest createTest(FixtureReference fixRef, boolean formatEDT) {
    var pack = fixRef.pack;
    var mdoRef = fixRef.mdoRef;
    var displayName = pack + "/" + mdoRef + " (" + (formatEDT ? "EDT" : "Designer") + ")";

    return DynamicTest.dynamicTest(displayName, () -> {
      var fixtureContent = Files.readString(fixRef.fixturePath, StandardCharsets.UTF_8);

      var mdoObj = Fixtures.get(pack, mdoRef, formatEDT);
      assertThat(mdoObj).isNotNull();
      var actualJson = Fixtures.asJson(mdoObj);
      Assertions.assertThat(actualJson, true).isEqual(fixtureContent);
    });
  }

  /**
   * Сканирует каталог с фикстурами для формирования тестов
   */
  private static List<FixtureReference> discoverFixtureRefs() {
    List<FixtureReference> fixtureRefs = new ArrayList<>();

    try (var packsStream = Files.list(Fixtures.FIXTURES_PATH)) {
      // анализируем только каталоги, они соответствуют именам каталогов с исходниками
      packsStream.filter(Files::isDirectory)
        .forEach((Path packDir) -> {
          var packName = packDir.getFileName().toString();

          // получаем сами файлы фикстур
          try (var filesStream = Files.list(packDir)) {
            filesStream
              .filter(path -> path.toString().endsWith(".json"))
              .forEach((Path fixtureFile) -> {
                // вычленяем Ссылку на mdo - это имя файла
                var mdoRef = PathUtils.getBaseName(fixtureFile.getFileName());
                fixtureRefs.add(new FixtureReference(packName, mdoRef, fixtureFile));
              });
          } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения каталога фикстур: " + packDir, e);
          }
        });
    } catch (IOException e) {
      throw new RuntimeException("Ошибка чтения каталога фикстур", e);
    }
    return fixtureRefs;
  }

  private record FixtureReference(String pack, String mdoRef, Path fixturePath) {
  }
}
