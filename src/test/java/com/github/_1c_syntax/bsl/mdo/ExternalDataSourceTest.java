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

import com.github._1c_syntax.bsl.mdo.children.ExternalDataSourceCube;
import com.github._1c_syntax.bsl.mdo.children.ExternalDataSourceTable;
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class ExternalDataSourceTest {
  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, ExternalDataSources.ТекущаяСУБД",
    "false, mdclasses, ExternalDataSources.ТекущаяСУБД",
    "true, mdclasses_3_27, ExternalDataSources.ВнешнийИсточникДанных1",
    "false, mdclasses_3_27, ExternalDataSources.ВнешнийИсточникДанных1"
  })
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(ExternalDataSource.class);

    var externalDataSource = (ExternalDataSource) mdo;
    assertThat(externalDataSource).isNotNull();

    var tables = externalDataSource.getTables();
    var functions = externalDataSource.getFunctions();
    var cubes = externalDataSource.getCubes();

    // --- ChildrenOwner ---
    Assertions.assertThat(externalDataSource.getChildren(), true)
      .containsAll(tables, functions, cubes);
    Assertions.assertThat(externalDataSource.getPlainChildren(), true)
      .containsAllPlain(
        tables,
        functions,
        cubes,
        tables.stream().map(ExternalDataSourceTable::getPlainChildren).flatMap(Collection::stream).toList(),
        cubes.stream().map(ExternalDataSourceCube::getPlainChildren).flatMap(Collection::stream).toList()
      );
  }
}
