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

import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ExternalDataSourceTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, ExternalDataSources.ТекущаяСУБД, _edt",
      "false, mdclasses, ExternalDataSources.ТекущаяСУБД"
    }
  )
  void test27(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(ExternalDataSource.class);
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses_3_27, ExternalDataSources.ВнешнийИсточникДанных1, _edt",
      "false, mdclasses_3_27, ExternalDataSources.ВнешнийИсточникДанных1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(ExternalDataSource.class);

    var sxtSrc = (ExternalDataSource) mdo;
    assertThat(sxtSrc.getChildren()).hasSize(3);
    assertThat(sxtSrc.getPlainChildren()).hasSize(18);
    assertThat(sxtSrc.getFunctions()).hasSize(1);

    assertThat(sxtSrc.getTables()).hasSize(1);
    var table = sxtSrc.getTables().get(0);
    assertThat(table.getChildren()).hasSize(5);
    assertThat(table.getPlainChildren()).hasSize(5);
    assertThat(table.getFields()).hasSize(3);
    assertThat(table.getAllAttributes()).hasSize(3);

    assertThat(sxtSrc.getCubes()).hasSize(1);
    var cube = sxtSrc.getCubes().get(0);
    assertThat(cube.getChildren()).hasSize(6);
    assertThat(cube.getPlainChildren()).hasSize(10);
    assertThat(cube.getResources()).hasSize(2);
    assertThat(cube.getDimensions()).hasSize(2);
    assertThat(cube.getAllAttributes()).hasSize(4);

    assertThat(cube.getDimensionTables()).hasSize(1);
    var cubeTable = cube.getDimensionTables().get(0);
    assertThat(cubeTable.getChildren()).hasSize(4);
    assertThat(cubeTable.getPlainChildren()).hasSize(4);
    assertThat(cubeTable.getFields()).hasSize(3);
    assertThat(cubeTable.getAllAttributes()).hasSize(3);
  }
}
