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

import com.github._1c_syntax.bsl.mdo.storage.AdditionalIndex;
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.ModuleType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CatalogTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, Catalogs.Справочник1",
      "false, mdclasses, Catalogs.Справочник1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(Catalog.class);

    var catalog = (Catalog) mdo;
    assertThat(catalog).isNotNull();

    // --- ModuleOwner ---
    assertThat(catalog.getModules())
      .hasSize(2)
      .anyMatch(Module::isProtected);
    Assertions.assertThat(catalog.getAllModules(), false)
      .containsAll(catalog.getModules(), catalog.getForms(), catalog.getCommands());
    assertThat(catalog.getModuleTypes())
      .hasSize(catalog.getModules().size())
      .containsKeys(ModuleType.ObjectModule, ModuleType.ManagerModule);

    // --- ChildrenOwner ---
    Assertions.assertThat(catalog.getChildren(), true)
      .containsAll(catalog.getAttributes(),
        catalog.getTabularSections(),
        catalog.getForms(),
        catalog.getTemplates(),
        catalog.getCommands(),
        catalog.getPredefinedValues());
    Assertions.assertThat(catalog.getPlainChildren(), true)
      .containsAllPlain(catalog.getAttributes(),
        catalog.getTabularSections(),
        catalog.getForms(),
        catalog.getTemplates(),
        catalog.getCommands(),
        catalog.getPredefinedValues());

    // --- AttributeOwner ---
    Assertions.assertThat(catalog.getAllAttributes(), false)
      .containsAll(catalog.getAttributes());
    Assertions.assertThat(catalog.getStorageFields(), false)
      .containsAll(catalog.getAttributes(),
        catalog.getTabularSections());
    Assertions.assertThat(catalog.getPlainStorageFields(), true)
      .containsAllPlain(catalog.getAttributes(),
        catalog.getTabularSections());

    // --- PredefinedDataOwner ---
    assertThat(catalog.getPredefinedValues()).hasSize(1);
    var predefinedValue = catalog.getPredefinedValues().getFirst();
    assertThat(predefinedValue).isNotNull();
    assertThat(predefinedValue.getName()).isEqualTo("ПредопределенныйЭлемент");
    assertThat(predefinedValue.getUuid()).isEqualTo("79adb5f1-7224-4404-98a7-d7ed155f6232");
    assertThat(predefinedValue.getCode()).isEqualTo("000000001");
    assertThat(predefinedValue.getDescription()).isEqualTo("Предопределенный элемент");
    assertThat(predefinedValue.isFolder()).isFalse();
    assertThat(predefinedValue.getChildItems()).isEmpty();
    assertThat(predefinedValue.getOwner()).isEqualTo(catalog.getMdoReference());
    assertThat(predefinedValue.getMdoReference())
      .isEqualTo(MdoReference.create("Catalog.Справочник1.Predefined.ПредопределенныйЭлемент"));
  }

  @ParameterizedTest
  @CsvSource({
    "true, ssl_3_1, Catalogs.ВерсииФайлов",
    "false, ssl_3_1, Catalogs.ВерсииФайлов",
    "true, ssl_3_2, Catalogs.ВерсииФайлов",
    "false, ssl_3_2, Catalogs.ВерсииФайлов",
    "true, ssl_3_1, Catalogs.Заметки",
    "false, ssl_3_1, Catalogs.Заметки",
    "true, ssl_3_2, Catalogs.Заметки",
    "false, ssl_3_2, Catalogs.Заметки"
  })
  void testSSL(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(Catalog.class);

    var catalog = (Catalog) mdo;
    assertThat(catalog).isNotNull();

    // --- ModuleOwner ---
    Assertions.assertThat(catalog.getAllModules(), false)
      .containsAll(catalog.getModules(), catalog.getForms(), catalog.getCommands());

    // --- ChildrenOwner ---
    Assertions.assertThat(catalog.getChildren(), true)
      .containsAll(catalog.getAttributes(),
        catalog.getTabularSections(),
        catalog.getForms(),
        catalog.getTemplates(),
        catalog.getCommands());
    Assertions.assertThat(catalog.getPlainChildren(), true)
      .containsAllPlain(catalog.getAttributes(),
        catalog.getTabularSections(),
        catalog.getForms(),
        catalog.getTemplates(),
        catalog.getCommands()
      );

    // --- AttributeOwner ---
    Assertions.assertThat(catalog.getAllAttributes(), false)
      .containsAll(catalog.getAttributes());
    Assertions.assertThat(catalog.getStorageFields(), false)
      .containsAll(catalog.getAttributes(),
        catalog.getTabularSections());
    Assertions.assertThat(catalog.getPlainStorageFields(), true)
      .containsAllPlain(catalog.getAttributes(),
        catalog.getTabularSections());

    // --- BasedOn ---
    assertThat(catalog.getBasedOn()).isEmpty();
  }

  @ParameterizedTest
  @CsvSource({
    "true, ssl_3_1, Catalogs.ШаблоныСообщений",
    "false, ssl_3_1, Catalogs.ШаблоныСообщений",
    "true, ssl_3_2, Catalogs.ШаблоныСообщений",
    "false, ssl_3_2, Catalogs.ШаблоныСообщений"
  })
  void testBasedOn(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(Catalog.class);

    var catalog = (Catalog) mdo;
    assertThat(catalog).isNotNull();

    assertThat(catalog.getBasedOn())
      .containsExactlyInAnyOrder(
        MdoReference.create("Document.ЭлектронноеПисьмоИсходящее")
      );
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses_3_27, Catalogs.Справочник1",
      "false, mdclasses_3_27, Catalogs.Справочник1"
    }
  )
  void testAdditionalIndexes(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(Catalog.class);

    var catalog = (Catalog) mdo;
    assertThat(catalog).isNotNull();

    assertThat(catalog.getAdditionalIndexes()).hasSize(2);

    var index1 = catalog.getAdditionalIndexes().get(0);
    assertThat(index1.getName()).isEqualTo("Индекс1");
    assertThat(index1.getUuid()).isEqualTo("00000000-0000-0000-0000-000000000000");
    assertThat(index1.getTable()).isEqualTo(MdoReference.create("Catalog.Справочник1"));
    assertThat(index1.getIndexedFields()).hasSize(2);
    assertThat(index1.getIndexedFields()).extracting(MdoReference::getMdoRef)
      .containsExactly("Catalog.Справочник1.StandardAttribute.Code", "Catalog.Справочник1.Attribute.Реквизит1");
    assertThat(index1.getAdditionalFields()).hasSize(2);
    assertThat(index1.getAdditionalFields()).extracting(MdoReference::getMdoRef)
      .containsExactly("Catalog.Справочник1.Attribute.Реквизит4", "Catalog.Справочник1.StandardAttribute.Predefined");

    var index2 = catalog.getAdditionalIndexes().get(1);
    assertThat(index2.getName()).isEqualTo("Индекс2");
    assertThat(index2.getUuid()).isEqualTo("4bf0a294-aae4-4965-b9f7-b1eaf3b4d021");
    assertThat(index2.getTable()).isEqualTo(MdoReference.create("Catalog.Справочник1"));
    assertThat(index2.getIndexedFields()).hasSize(2);
    assertThat(index2.getIndexedFields()).extracting(MdoReference::getMdoRef)
      .containsExactly("Catalog.Справочник1.StandardAttribute.Ref", "Catalog.Справочник1.StandardAttribute.Code");
    assertThat(index2.getAdditionalFields()).hasSize(1);
    assertThat(index2.getAdditionalFields()).extracting(MdoReference::getMdoRef)
      .containsExactly("Catalog.Справочник1.StandardAttribute.DeletionMark");
  }
}
