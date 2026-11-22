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
import com.github._1c_syntax.bsl.mdo.ExternalDataSource;
import com.github._1c_syntax.bsl.mdo.support.TableDataType;
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ExternalDataSourceTableTest {

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, ExternalDataSources.ТекущаяСУБД, ExternalDataSource.ТекущаяСУБД.Table.ИнформацияОбОшибках",
    "false, mdclasses, ExternalDataSources.ТекущаяСУБД, ExternalDataSource.ТекущаяСУБД.Table.ИнформацияОбОшибках",
  })
  void test(ArgumentsAccessor argumentsAccessor) {
    var formatEDT = argumentsAccessor.getBoolean(0);
    var pack = argumentsAccessor.getString(1);
    var parentRef = argumentsAccessor.getString(2);
    var tableRef = argumentsAccessor.getString(3);

    var mdo = Fixtures.get(pack, parentRef, formatEDT);
    assertThat(mdo).isInstanceOf(ExternalDataSource.class);

    var childOpt = ((ChildrenOwner) mdo).findChild(tableRef);
    assertThat(childOpt).isPresent();

    var table = (ExternalDataSourceTable) childOpt.get();
    assertThat(table.getName()).isEqualTo("ИнформацияОбОшибках");
    assertThat(table.getOwner()).isNotNull();
    assertThat(table.getTableDataType()).isEqualTo(TableDataType.NONOBJECT_DATA);

    // --- ChildrenOwner ---
    Assertions.assertThat(table.getChildren(), true)
      .containsAll(table.getFields(), table.getForms(), table.getCommands(), table.getTemplates());

    // --- AttributeOwner ---
    Assertions.assertThat(table.getAllAttributes(), false)
      .containsAll(table.getFields());
    Assertions.assertThat(table.getStorageFields(), false)
      .containsAll(table.getFields());
    Assertions.assertThat(table.getPlainStorageFields(), true)
      .containsAllPlain(table.getFields());

    // --- ModuleOwner ---
    Assertions.assertThat(table.getAllModules(), false)
      .containsAll(table.getModules(), table.getForms(), table.getCommands());
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses_3_27, ExternalDataSources.ВнешнийИсточникДанных1, "
      + "ExternalDataSource.ВнешнийИсточникДанных1.Table.Таблица1",
    "false, mdclasses_3_27, ExternalDataSources.ВнешнийИсточникДанных1, "
      + "ExternalDataSource.ВнешнийИсточникДанных1.Table.Таблица1",
  })
  void objectTableDataType(ArgumentsAccessor argumentsAccessor) {
    // given
    var formatEDT = argumentsAccessor.getBoolean(0);
    var mdo = Fixtures.get(argumentsAccessor.getString(1), argumentsAccessor.getString(2), formatEDT);

    // when
    var childOpt = ((ChildrenOwner) mdo).findChild(argumentsAccessor.getString(3));

    // then
    assertThat(childOpt).isPresent();
    assertThat(((ExternalDataSourceTable) childOpt.get()).getTableDataType())
      .isEqualTo(TableDataType.OBJECT_DATA);
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses_3_27, ExternalDataSources.ВнешнийИсточникДанных1, "
      + "ExternalDataSource.ВнешнийИсточникДанных1.Cube.Куб1.DimensionTable.ddddd",
    "false, mdclasses_3_27, ExternalDataSources.ВнешнийИсточникДанных1, "
      + "ExternalDataSource.ВнешнийИсточникДанных1.Cube.Куб1.DimensionTable.ddddd",
  })
  void hierarchicalDimensionTable(ArgumentsAccessor argumentsAccessor) {
    // given
    var formatEDT = argumentsAccessor.getBoolean(0);
    var mdo = Fixtures.get(argumentsAccessor.getString(1), argumentsAccessor.getString(2), formatEDT);

    // when
    var childOpt = ((ChildrenOwner) mdo).findChild(argumentsAccessor.getString(3));

    // then
    assertThat(childOpt).isPresent();
    assertThat(((ExternalDataSourceCubeDimensionTable) childOpt.get()).isHierarchical()).isTrue();
  }
}
