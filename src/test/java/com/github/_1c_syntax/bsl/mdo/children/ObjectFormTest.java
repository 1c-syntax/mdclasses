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
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

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
}
