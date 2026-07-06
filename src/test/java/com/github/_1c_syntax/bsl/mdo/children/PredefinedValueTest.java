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
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.types.MdoReference;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class PredefinedValueTest {

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, Catalogs.Справочник1, Catalog.Справочник1.Predefined.ПредопределенныйЭлемент",
    "false, mdclasses, Catalogs.Справочник1, Catalog.Справочник1.Predefined.ПредопределенныйЭлемент",
  })
  void test(ArgumentsAccessor argumentsAccessor) {
    var formatEDT = argumentsAccessor.getBoolean(0);
    var pack = argumentsAccessor.getString(1);
    var parentRef = argumentsAccessor.getString(2);
    var predefinedRef = argumentsAccessor.getString(3);

    var mdo = Fixtures.get(pack, parentRef, formatEDT);
    assertThat(mdo).isInstanceOf(Catalog.class);
    var catalog = (Catalog) mdo;
    assertThat(catalog).isNotNull();

    var childOpt = catalog.findChild(predefinedRef);
    assertThat(childOpt).isPresent();

    var predefined = (PredefinedValue) childOpt.get();
    assertThat(predefined.getName()).isEqualTo("ПредопределенныйЭлемент");
    assertThat(predefined.getCode()).isEqualTo("000000001");
    assertThat(predefined.getDescription()).isEqualTo("Предопределенный элемент");
    assertThat(predefined.isFolder()).isFalse();
    assertThat(predefined.getChildItems()).isEmpty();
    assertThat(predefined.getOwner()).isEqualTo(catalog.getMdoReference());
    assertThat(predefined.getMdoReference())
      .isEqualTo(MdoReference.create("Catalog.Справочник1.Predefined.ПредопределенныйЭлемент"));

    // ChildrenOwner
    assertThat(predefined.getChildren()).isEmpty();
  }
}
