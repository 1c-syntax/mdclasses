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
package com.github._1c_syntax.bsl.mdo.children;

import com.github._1c_syntax.bsl.mdo.ChildrenOwner;
import com.github._1c_syntax.bsl.mdo.Template;
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectTemplateTest {

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, Catalogs.Справочник1, Catalog.Справочник1.Template.Макет",
    "false, mdclasses, Catalogs.Справочник1, Catalog.Справочник1.Template.Макет",
  })
  void test(ArgumentsAccessor argumentsAccessor) {
    var formatEDT = argumentsAccessor.getBoolean(0);
    var pack = argumentsAccessor.getString(1);
    var parentRef = argumentsAccessor.getString(2);
    var templateRef = argumentsAccessor.getString(3);

    var mdo = Fixtures.get(pack, parentRef, formatEDT);
    assertThat(mdo).isNotNull();

    var childOpt = ((ChildrenOwner) mdo).findChild(templateRef);
    assertThat(childOpt).isPresent();

    var template = (ObjectTemplate) childOpt.get();

    assertThat(template.getTemplateType()).isNotNull();
    assertThat(template.getData()).isNotNull();
  }

  @TestFactory
  Stream<DynamicTest> compareTemplateDataDesigner() {
    return discoverObjectTemplateDataRefs().stream()
      .map(fixRef -> createTemplateDataTest(fixRef, false));
  }

  @TestFactory
  Stream<DynamicTest> compareTemplateDataEDT() {
    return discoverObjectTemplateDataRefs().stream()
      .map(fixRef -> createTemplateDataTest(fixRef, true));
  }

  private static DynamicTest createTemplateDataTest(TemplateDataFixtureRef fixRef, boolean formatEDT) {
    var pack = fixRef.pack;
    var templateRef = fixRef.templateRef;
    var parentRef = fixRef.parentRef;
    var displayName = pack + "/" + templateRef + " (" + (formatEDT ? "EDT" : "Designer") + ")";

    return DynamicTest.dynamicTest(displayName, () -> {
      var fixtureContent = Files.readString(fixRef.fixturePath, StandardCharsets.UTF_8);
      var parentObj = Fixtures.get(pack, parentRef, formatEDT);
      assertThat(parentObj).isNotNull();
      var childOpt = ((ChildrenOwner) parentObj).findChild(templateRef);
      assertThat(childOpt).isPresent();
      var template = (Template) childOpt.get();
      var data = template.getData();
      var actualJson = Fixtures.asJson(data);
      Assertions.assertThat(actualJson, true).isEqual(fixtureContent);
    });
  }

  private static List<TemplateDataFixtureRef> discoverObjectTemplateDataRefs() {
    List<TemplateDataFixtureRef> refs = new ArrayList<>();
    try (var packsStream = Files.list(Fixtures.FIXTURES_PATH)) {
      packsStream.filter(Files::isDirectory)
        .forEach(packDir -> {
          var packName = packDir.getFileName().toString();
          var templatedataDir = packDir.resolve("templatedata");
          if (!Files.isDirectory(templatedataDir)) return;
          try (var filesStream = Files.list(templatedataDir)) {
            filesStream.filter(path -> path.toString().endsWith(".json"))
              .forEach(fixtureFile -> {
                var templateRef = org.apache.commons.io.file.PathUtils
                  .getBaseName(fixtureFile.getFileName());
                var parentRef = parseParentRef(templateRef);
                if (parentRef != null) {
                  refs.add(new TemplateDataFixtureRef(packName, templateRef, parentRef, fixtureFile));
                }
              });
          } catch (IOException e) { throw new RuntimeException(e); }
        });
    } catch (IOException e) { throw new RuntimeException(e); }
    return refs;
  }

  private static String parseParentRef(String childMdoRef) {
    var dotIndex = childMdoRef.indexOf('.');
    if (dotIndex < 0) return null;
    var typeStr = childMdoRef.substring(0, dotIndex);
    var rest = childMdoRef.substring(dotIndex + 1);
    var secondDot = rest.indexOf('.');
    if (secondDot < 0) return null;
    var parentName = rest.substring(0, secondDot);
    var mdoTypeOpt = MDOType.fromValue(typeStr);
    return mdoTypeOpt.map(mdoType -> mdoType.groupName() + "." + parentName).orElse(null);
  }

  private record TemplateDataFixtureRef(String pack, String templateRef, String parentRef, Path fixturePath) {
  }
}
