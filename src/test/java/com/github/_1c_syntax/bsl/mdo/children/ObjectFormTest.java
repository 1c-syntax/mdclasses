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
import com.github._1c_syntax.bsl.mdo.Form;
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

class ObjectFormTest {

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, Catalogs.Справочник1, Catalog.Справочник1.Form.ФормаЭлемента",
    "false, mdclasses, Catalogs.Справочник1, Catalog.Справочник1.Form.ФормаЭлемента",
    "true, mdclasses, Catalogs.Справочник1, Catalog.Справочник1.Form.ФормаСписка",
    "false, mdclasses, Catalogs.Справочник1, Catalog.Справочник1.Form.ФормаСписка",
    "true, ssl_3_1, Catalogs.Заметки, Catalog.Заметки.Form.ФормаЭлемента",
    "false, ssl_3_1, Catalogs.Заметки, Catalog.Заметки.Form.ФормаЭлемента",
    "true, ssl_3_2, Catalogs.Заметки, Catalog.Заметки.Form.ФормаЭлемента",
    "false, ssl_3_2, Catalogs.Заметки, Catalog.Заметки.Form.ФормаЭлемента",
  })
  void test(ArgumentsAccessor argumentsAccessor) {
    var formatEDT = argumentsAccessor.getBoolean(0);
    var pack = argumentsAccessor.getString(1);
    var parentRef = argumentsAccessor.getString(2);
    var formRef = argumentsAccessor.getString(3);

    var mdo = Fixtures.get(pack, parentRef, formatEDT);
    assertThat(mdo).isNotNull();

    var childOpt = ((ChildrenOwner) mdo).findChild(formRef);
    assertThat(childOpt).isPresent();

    var form = (ObjectForm) childOpt.get();

    // --- ModuleOwner ---
    assertThat(form.getModules()).isNotNull();
    Assertions.assertThat(form.getAllModules(), false)
      .containsAll(form.getModules());
    assertThat(form.getModuleTypes()).isNotNull();
  }

  @TestFactory
  Stream<DynamicTest> compareFormDataDesigner() {
    return discoverObjectFormDataRefs().stream()
      .map(fixRef -> createFormDataTest(fixRef, false));
  }

  @TestFactory
  Stream<DynamicTest> compareFormDataEDT() {
    return discoverObjectFormDataRefs().stream()
      .map(fixRef -> createFormDataTest(fixRef, true));
  }

  private static DynamicTest createFormDataTest(FormDataFixtureRef fixRef, boolean formatEDT) {
    var pack = fixRef.pack;
    var formRef = fixRef.formRef;
    var parentRef = fixRef.parentRef;
    var displayName = pack + "/" + formRef + " (" + (formatEDT ? "EDT" : "Designer") + ")";

    return DynamicTest.dynamicTest(displayName, () -> {
      var fixtureContent = Files.readString(fixRef.fixturePath, StandardCharsets.UTF_8);
      var parentObj = Fixtures.get(pack, parentRef, formatEDT);
      assertThat(parentObj).isNotNull();
      var childOpt = ((ChildrenOwner) parentObj).findChild(formRef);
      assertThat(childOpt).isPresent();
      var form = (Form) childOpt.get();
      var data = form.getData();
      var actualJson = Fixtures.asJson(data);
      Assertions.assertThat(actualJson, true).isEqual(fixtureContent);
    });
  }

  private static List<FormDataFixtureRef> discoverObjectFormDataRefs() {
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
                var parentRef = parseParentRef(formRef);
                if (parentRef != null) {
                  refs.add(new FormDataFixtureRef(packName, formRef, parentRef, fixtureFile));
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

  private record FormDataFixtureRef(String pack, String formRef, String parentRef, Path fixturePath) {
  }
}
