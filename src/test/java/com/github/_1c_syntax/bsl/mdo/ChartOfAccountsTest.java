/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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

class ChartOfAccountsTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, ChartsOfAccounts.ПланСчетов1, _edt",
      "false, mdclasses, ChartsOfAccounts.ПланСчетов1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(ChartOfAccounts.class);
    var chartOfAccounts = (ChartOfAccounts) mdo;
    assertThat(chartOfAccounts.getAllAttributes()).hasSize(2);
    assertThat(chartOfAccounts.getChildren()).hasSize(2);
    assertThat(chartOfAccounts.getAttributes()).isEmpty();
    assertThat(chartOfAccounts.getAccountingFlags()).hasSize(1);
    assertThat(chartOfAccounts.getExtDimensionAccountingFlags()).hasSize(1);
  }
}