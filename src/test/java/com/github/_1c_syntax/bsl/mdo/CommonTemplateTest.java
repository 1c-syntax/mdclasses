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
package com.github._1c_syntax.bsl.mdo;

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

class CommonTemplateTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, CommonTemplates.Active",
      "false, mdclasses, CommonTemplates.Active",
      "true, mdclasses, CommonTemplates.HTML",
      "false, mdclasses, CommonTemplates.HTML",
      "true, mdclasses, CommonTemplates.ВнешняяКомпонента",
      "false, mdclasses, CommonTemplates.ВнешняяКомпонента",
      "true, mdclasses, CommonTemplates.ГеографическаяСхема",
      "false, mdclasses, CommonTemplates.ГеографическаяСхема",
      "true, mdclasses, CommonTemplates.ГрафическаяСхема",
      "false, mdclasses, CommonTemplates.ГрафическаяСхема",
      "true, mdclasses, CommonTemplates.ДвоичныеДанные",
      "false, mdclasses, CommonTemplates.ДвоичныеДанные",
      "true, mdclasses, CommonTemplates.МакетОформления",
      "false, mdclasses, CommonTemplates.МакетОформления",
      "true, mdclasses, CommonTemplates.СКД",
      "false, mdclasses, CommonTemplates.СКД",
      "true, mdclasses, CommonTemplates.ТабличныйДокумент",
      "false, mdclasses, CommonTemplates.ТабличныйДокумент",
      "true, mdclasses, CommonTemplates.ТекстовыйДокумент",
      "false, mdclasses, CommonTemplates.ТекстовыйДокумент",
      "true, ssl_3_1, CommonTemplates.СтруктураПодчиненности",
      "false, ssl_3_1, CommonTemplates.СтруктураПодчиненности",
      "true, ssl_3_2, CommonTemplates.СтруктураПодчиненности",
      "false, ssl_3_2, CommonTemplates.СтруктураПодчиненности"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(CommonTemplate.class);

    var commonTemplate = (CommonTemplate) mdo;
    assertThat(commonTemplate).isNotNull();
    assertThat(commonTemplate.getTemplateType()).isNotNull();
    assertThat(commonTemplate.getData()).isNotNull();
  }

  @TestFactory
  Stream<DynamicTest> compareTemplateDataDesigner() {
    return discoverCommonTemplateDataRefs().stream()
      .map(fixRef -> createTemplateDataTest(fixRef, false));
  }

  @TestFactory
  Stream<DynamicTest> compareTemplateDataEDT() {
    return discoverCommonTemplateDataRefs().stream()
      .map(fixRef -> createTemplateDataTest(fixRef, true));
  }

  private static DynamicTest createTemplateDataTest(TemplateDataFixtureRef fixRef, boolean formatEDT) {
    var pack = fixRef.pack;
    var templateRef = fixRef.templateRef;
    var groupRef = fixRef.groupRef;
    var displayName = pack + "/" + templateRef + " (" + (formatEDT ? "EDT" : "Designer") + ")";

    return DynamicTest.dynamicTest(displayName, () -> {
      var fixtureContent = Files.readString(fixRef.fixturePath, StandardCharsets.UTF_8);
      var mdo = Fixtures.get(pack, groupRef, formatEDT);
      assertThat(mdo).isInstanceOf(CommonTemplate.class);
      var commonTemplate = (CommonTemplate) mdo;
      var data = commonTemplate.getData();
      var actualJson = Fixtures.asJson(data);
      Assertions.assertThat(actualJson, true).isEqual(fixtureContent);
    });
  }

  private static List<TemplateDataFixtureRef> discoverCommonTemplateDataRefs() {
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
                if (!templateRef.startsWith("CommonTemplate.")) return;
                var groupRef = toGroupRef(templateRef);
                if (groupRef != null) {
                  refs.add(new TemplateDataFixtureRef(packName, templateRef, groupRef, fixtureFile));
                }
              });
          } catch (IOException e) { throw new RuntimeException(e); }
        });
    } catch (IOException e) { throw new RuntimeException(e); }
    return refs;
  }

  private static String toGroupRef(String mdoRef) {
    var dotIndex = mdoRef.indexOf('.');
    if (dotIndex < 0) return null;
    var typeStr = mdoRef.substring(0, dotIndex);
    var name = mdoRef.substring(dotIndex + 1);
    var mdoTypeOpt = MDOType.fromValue(typeStr);
    return mdoTypeOpt.map(mdoType -> mdoType.groupName() + "." + name).orElse(null);
  }

  private record TemplateDataFixtureRef(String pack, String templateRef, String groupRef, Path fixturePath) {
  }
}