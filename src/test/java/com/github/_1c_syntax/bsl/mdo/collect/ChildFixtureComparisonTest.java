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

import com.github._1c_syntax.bsl.mdo.ChildrenOwner;
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import com.github._1c_syntax.bsl.types.MDOType;
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
 * Тест, проверяющий все JSON-фикстуры дочерних объектов MDO.
 * <p>
 * Для каждого json-файла в {@code fixtures/<pack>/children/<ref>.json} загружается родительский MDO,
 * извлекается дочерний объект через {@code findChild}, сериализуется в JSON
 * и сверяется с сохранённой JSON-фикстурой.
 */
class ChildFixtureComparisonTest {

  @TestFactory
  Stream<DynamicTest> compareAllFixturesDesigner() {
    return discoverChildFixtureRefs().stream()
      .map(fixRef -> createTest(fixRef, false));
  }

  @TestFactory
  Stream<DynamicTest> compareAllFixturesEDT() {
    return discoverChildFixtureRefs().stream()
      .map(fixRef -> createTest(fixRef, true));
  }

  private static DynamicTest createTest(ChildFixtureReference fixRef, boolean formatEDT) {
    var pack = fixRef.pack;
    var childMdoRef = fixRef.childMdoRef;
    var parentMdoRef = fixRef.parentMdoRef;
    var displayName = pack + "/" + childMdoRef + " (" + (formatEDT ? "EDT" : "Designer") + ")";

    return DynamicTest.dynamicTest(displayName, () -> {
      var fixtureContent = Files.readString(fixRef.fixturePath, StandardCharsets.UTF_8);

      var parentObj = Fixtures.get(pack, parentMdoRef, formatEDT);
      assertThat(parentObj).isNotNull();

      var childOpt = ((ChildrenOwner) parentObj).findChild(childMdoRef);
      assertThat(childOpt).isPresent();

      var child = childOpt.get();
      var actualJson = Fixtures.asJson(child);
      Assertions.assertThat(actualJson, true).isEqual(fixtureContent);
    });
  }

  private static List<ChildFixtureReference> discoverChildFixtureRefs() {
    List<ChildFixtureReference> refs = new ArrayList<>();

    try (var packsStream = Files.list(Fixtures.FIXTURES_PATH)) {
      packsStream.filter(Files::isDirectory)
        .forEach(packDir -> {
          var packName = packDir.getFileName().toString();
          var childrenDir = packDir.resolve("children");
          if (!Files.isDirectory(childrenDir)) {
            return;
          }
          try (var filesStream = Files.list(childrenDir)) {
            filesStream
              .filter(path -> path.toString().endsWith(".json"))
              .forEach(fixtureFile -> {
                var childMdoRef = PathUtils.getBaseName(fixtureFile.getFileName());
                var parentMdoRef = parseParentRef(childMdoRef);
                if (parentMdoRef != null) {
                  refs.add(new ChildFixtureReference(packName, childMdoRef, parentMdoRef, fixtureFile));
                }
              });
          } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения каталога фикстур: " + childrenDir, e);
          }
        });
    } catch (IOException e) {
      throw new RuntimeException("Ошибка чтения каталога фикстур", e);
    }
    return refs;
  }

  /**
   * Из полной ссылки на дочерний объект извлекает ссылку на родителя
   * в формате "ГруппаТипа.ИмяОбъекта" (например, "Catalogs.Справочник1").
   */
  private static String parseParentRef(String childMdoRef) {
    var dotIndex = childMdoRef.indexOf('.');
    if (dotIndex < 0) {
      return null;
    }
    var typeStr = childMdoRef.substring(0, dotIndex);
    var rest = childMdoRef.substring(dotIndex + 1);
    var secondDot = rest.indexOf('.');
    if (secondDot < 0) {
      return null;
    }
    var parentName = rest.substring(0, secondDot);

    var mdoTypeOpt = MDOType.fromValue(typeStr);
    return mdoTypeOpt.map(mdoType -> mdoType.groupName() + "." + parentName).orElse(null);
  }

  private record ChildFixtureReference(String pack, String childMdoRef, String parentMdoRef, Path fixturePath) {
  }
}
