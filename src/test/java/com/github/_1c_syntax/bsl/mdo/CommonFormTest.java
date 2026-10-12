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
import com.github._1c_syntax.bsl.types.ModuleType;
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

class CommonFormTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, CommonForms.Форма",
      "false, mdclasses, CommonForms.Форма",
      "true, ssl_3_1, CommonForms.Вопрос",
      "false, ssl_3_1, CommonForms.Вопрос",
      "true, ssl_3_2, CommonForms.Вопрос",
      "false, ssl_3_2, CommonForms.Вопрос"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(CommonForm.class);

    var commonForm = (CommonForm) mdo;
    assertThat(commonForm).isNotNull();

    var modules = commonForm.getModules();
    Assertions.assertThat(commonForm.getAllModules(), false)
      .containsAll(modules);

    assertThat(commonForm.getModuleTypes())
      .hasSize(modules.size());

    assertThat(commonForm.getModuleTypes())
      .containsEntry(ModuleType.FormModule,
        modules.stream()
          .map(Module::getUri)
          .toList());
  }

  @TestFactory
  Stream<DynamicTest> compareFormDataDesigner() {
    return discoverCommonFormDataRefs().stream()
      .map(fixRef -> createFormDataTest(fixRef, false));
  }

  @TestFactory
  Stream<DynamicTest> compareFormDataEDT() {
    return discoverCommonFormDataRefs().stream()
      .map(fixRef -> createFormDataTest(fixRef, true));
  }

  private static DynamicTest createFormDataTest(FormDataFixtureRef fixRef, boolean formatEDT) {
    var pack = fixRef.pack;
    var formRef = fixRef.formRef;
    var groupRef = fixRef.groupRef;
    var displayName = pack + "/" + formRef + " (" + (formatEDT ? "EDT" : "Designer") + ")";

    return DynamicTest.dynamicTest(displayName, () -> {
      var fixtureContent = Files.readString(fixRef.fixturePath, StandardCharsets.UTF_8);
      var mdo = Fixtures.get(pack, groupRef, formatEDT);
      assertThat(mdo).isInstanceOf(CommonForm.class);
      var commonForm = (CommonForm) mdo;
      assertThat(commonForm).isNotNull();
      var data = commonForm.getData();
      var actualJson = Fixtures.asJson(data);
      Assertions.assertThat(actualJson, true).isEqual(fixtureContent);
    });
  }

  private static List<FormDataFixtureRef> discoverCommonFormDataRefs() {
    List<FormDataFixtureRef> refs = new ArrayList<>();
    try (var packsStream = Files.list(Fixtures.FIXTURES_PATH)) {
      packsStream.filter(Files::isDirectory)
        .forEach(packDir -> {
          var packName = packDir.getFileName().toString();
          var formdataDir = packDir.resolve("formdata");
          if (!Files.isDirectory(formdataDir)) return;
          try (var filesStream = Files.list(formdataDir)) {
            filesStream.filter(path -> path.toString().endsWith(".json"))
              .forEach(fixtureFile -> {
                var formRef = org.apache.commons.io.file.PathUtils
                  .getBaseName(fixtureFile.getFileName());
                if (!formRef.startsWith("CommonForm.")) return;
                var groupRef = toGroupRef(formRef);
                if (groupRef != null) {
                  refs.add(new FormDataFixtureRef(packName, formRef, groupRef, fixtureFile));
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

  private record FormDataFixtureRef(String pack, String formRef, String groupRef, Path fixturePath) {
  }
}
