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

import com.github._1c_syntax.bsl.mdo.Catalog;
import com.github._1c_syntax.bsl.mdo.support.AttributeKind;
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.ValueTypes;
import com.github._1c_syntax.bsl.types.value.PrimitiveValueType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectAttributeTest {
  @ParameterizedTest
  @CsvSource({
    "true, ssl_3_1, Catalogs.Заметки",
    "false, ssl_3_1, Catalogs.Заметки",
    "true, ssl_3_2, Catalogs.Заметки",
    "false, ssl_3_2, Catalogs.Заметки"
  })
  void test(ArgumentsAccessor argumentsAccessor) {
    var catalog = (Catalog) Fixtures.get(argumentsAccessor);
    assertThat(catalog).isNotNull();

    var child = catalog.findChild("Catalog.Заметки.Attribute.Автор");
    assertThat(child).isPresent();

    var attribute = (ObjectAttribute) child.get();
    assertThat(attribute.getSynonym().isEmpty()).isFalse();
    assertThat(attribute.getSynonym().get("ru")).isEqualTo("Автор");
    assertThat(attribute.getDescription()).isEqualTo("Автор");
    assertThat(attribute.getDescription("ru")).isEqualTo("Автор");
    assertThat(attribute.getDescription("en")).isEqualTo("Автор");
    assertThat(attribute.getValueType()).isNotNull();
    assertThat(attribute.getValueType()
      .contains(Objects.requireNonNull(ValueTypes.get("CatalogRef.Пользователи")))).isTrue();

    child = catalog.findChild("Catalog.Заметки.StandardAttribute.PredefinedDataName");
    assertThat(child).isPresent();
    var stdAttribute = (StandardAttribute) child.get();
    assertThat(stdAttribute.getName()).isEqualTo("PredefinedDataName");
    assertThat(stdAttribute.getFullName().getRu()).isEqualTo("ИмяПредопределенныхДанных");
    assertThat(stdAttribute.getMdoReference())
      .isEqualTo(MdoReference.create("Catalog.Заметки.StandardAttribute.PredefinedDataName"));
    assertThat(stdAttribute.getKind()).isEqualTo(AttributeKind.STANDARD);
    assertThat(stdAttribute.getSynonym().isEmpty()).isTrue();
    assertThat(stdAttribute.getDescription()).isEqualTo("ИмяПредопределенныхДанных");
    assertThat(stdAttribute.getDescription("ru")).isEqualTo("ИмяПредопределенныхДанных");
    assertThat(stdAttribute.getDescription("en")).isEqualTo("PredefinedDataName");
    assertThat(stdAttribute.getValueType()).isNotNull();
    assertThat(stdAttribute.getValueType().contains(PrimitiveValueType.STRING)).isTrue();
  }

}
