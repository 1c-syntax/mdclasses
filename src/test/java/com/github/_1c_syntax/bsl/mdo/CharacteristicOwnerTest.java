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
import com.github._1c_syntax.bsl.types.MdoReference;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CharacteristicOwnerTest {

  @ParameterizedTest
  @CsvSource({
    "true, ssl_3_1, Documents.Встреча",
    "false, ssl_3_1, Documents.Встреча",
    "true, ssl_3_2, Documents.Встреча",
    "false, ssl_3_2, Documents.Встреча"
  })
  void testDocumentCharacteristics(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(Document.class);

    var document = (Document) mdo;
    assertThat(document).isNotNull();
    assertThat(document.getCharacteristics()).hasSize(2);

    var props = document.getCharacteristics().getFirst();
    assertThat(props.getCharacteristicTypes())
      .isEqualTo(MdoReference.create("Catalog.НаборыДополнительныхРеквизитовИСведений.TabularSection.ДополнительныеРеквизиты"));
    assertThat(props.getKeyField())
      .isEqualTo(MdoReference.create("Catalog.НаборыДополнительныхРеквизитовИСведений.TabularSection.ДополнительныеРеквизиты.Attribute.Свойство"));
    assertThat(props.getTypesFilterField())
      .isEqualTo(MdoReference.create("Catalog.НаборыДополнительныхРеквизитовИСведений.TabularSection.ДополнительныеРеквизиты.Attribute.ИмяПредопределенногоНабора"));
    assertThat(props.getCharacteristicValues())
      .isEqualTo(MdoReference.create("Document.Встреча.TabularSection.ДополнительныеРеквизиты"));
    assertThat(props.getObjectField())
      .isEqualTo(MdoReference.create("Document.Встреча.TabularSection.ДополнительныеРеквизиты.StandardAttribute.Ref"));
    assertThat(props.getTypeField())
      .isEqualTo(MdoReference.create("Document.Встреча.TabularSection.ДополнительныеРеквизиты.Attribute.Свойство"));
    assertThat(props.getValueField())
      .isEqualTo(MdoReference.create("Document.Встреча.TabularSection.ДополнительныеРеквизиты.Attribute.Значение"));

    var info = document.getCharacteristics().get(1);
    assertThat(info.getCharacteristicTypes())
      .isEqualTo(MdoReference.create("Catalog.НаборыДополнительныхРеквизитовИСведений.TabularSection.ДополнительныеСведения"));
    assertThat(info.getCharacteristicValues())
      .isEqualTo(MdoReference.create("InformationRegister.ДополнительныеСведения"));
    assertThat(info.getObjectField())
      .isEqualTo(MdoReference.create("InformationRegister.ДополнительныеСведения.Dimension.Объект"));
    assertThat(info.getTypeField())
      .isEqualTo(MdoReference.create("InformationRegister.ДополнительныеСведения.Dimension.Свойство"));
    assertThat(info.getValueField())
      .isEqualTo(MdoReference.create("InformationRegister.ДополнительныеСведения.Resource.Значение"));
  }

  @ParameterizedTest
  @CsvSource({
    "true, ssl_3_1, Catalogs.Пользователи",
    "false, ssl_3_1, Catalogs.Пользователи",
    "true, ssl_3_2, Catalogs.Пользователи",
    "false, ssl_3_2, Catalogs.Пользователи"
  })
  void testCatalogCharacteristics(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(Catalog.class);

    var catalog = (Catalog) mdo;
    assertThat(catalog).isNotNull();
    assertThat(catalog.getCharacteristics()).hasSize(3);

    var props = catalog.getCharacteristics().get(0);
    assertThat(props.getCharacteristicTypes())
      .isEqualTo(MdoReference.create("Catalog.НаборыДополнительныхРеквизитовИСведений.TabularSection.ДополнительныеРеквизиты"));

    var info = catalog.getCharacteristics().get(1);
    assertThat(info.getCharacteristicTypes())
      .isEqualTo(MdoReference.create("Catalog.НаборыДополнительныхРеквизитовИСведений.TabularSection.ДополнительныеСведения"));

    var contact = catalog.getCharacteristics().get(2);
    assertThat(contact.getCharacteristicTypes())
      .isEqualTo(MdoReference.create("Catalog.ВидыКонтактнойИнформации"));
  }

  @ParameterizedTest
  @CsvSource({
    "true, ssl_3_1, Documents.Анкета",
    "false, ssl_3_1, Documents.Анкета",
    "true, ssl_3_2, Documents.Анкета",
    "false, ssl_3_2, Documents.Анкета",
    "true, mdclasses, Documents.Документ1",
    "false, mdclasses, Documents.Документ1"
  })
  void testEmptyCharacteristics(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(Document.class);

    var document = (Document) mdo;
    assertThat(document).isNotNull();
    assertThat(document.getCharacteristics()).isEmpty();
  }
}
