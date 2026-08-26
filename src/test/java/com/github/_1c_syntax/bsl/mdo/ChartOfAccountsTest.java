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

import com.github._1c_syntax.bsl.mdo.support.AttributeKind;
import com.github._1c_syntax.bsl.mdo.support.DefaultPresentation;
import com.github._1c_syntax.bsl.test_utils.Fixtures;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ChartOfAccountsTest {
  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, ChartsOfAccounts.ПланСчетов1",
    "false, mdclasses, ChartsOfAccounts.ПланСчетов1"
  })
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(ChartOfAccounts.class);

    var chartOfAccounts = (ChartOfAccounts) mdo;
    assertThat(chartOfAccounts).isNotNull();

    var attributes = chartOfAccounts.getAttributes();
    var tabularSections = chartOfAccounts.getTabularSections();
    var forms = chartOfAccounts.getForms();
    var templates = chartOfAccounts.getTemplates();
    var commands = chartOfAccounts.getCommands();
    var accountingFlags = chartOfAccounts.getAccountingFlags();
    var extDimAccFlags = chartOfAccounts.getExtDimensionAccountingFlags();

    // --- ModuleOwner ---
    assertThat(chartOfAccounts.getModuleTypes())
      .hasSize(chartOfAccounts.getModules().size());
    Assertions.assertThat(chartOfAccounts.getAllModules(), false)
      .containsAll(chartOfAccounts.getModules(), forms, commands);

    // --- ChildrenOwner ---
    Assertions.assertThat(chartOfAccounts.getChildren(), true)
      .containsAll(attributes, tabularSections, forms, templates, commands, accountingFlags, extDimAccFlags);
    Assertions.assertThat(chartOfAccounts.getPlainChildren(), true)
      .containsAllPlain(attributes, tabularSections, forms, templates, commands, accountingFlags, extDimAccFlags);

    // --- AttributeOwner ---
    assertThat(attributes)
      .allMatch(a -> a.getKind() == AttributeKind.STANDARD);
    Assertions.assertThat(chartOfAccounts.getAllAttributes(), false)
      .containsAll(attributes, accountingFlags, extDimAccFlags);
    Assertions.assertThat(chartOfAccounts.getStorageFields(), false)
      .containsAll(attributes, accountingFlags, extDimAccFlags, tabularSections);
    Assertions.assertThat(chartOfAccounts.getPlainStorageFields(), false)
      .containsAllPlain(attributes, accountingFlags, extDimAccFlags, tabularSections);

    // --- PredefinedDataOwner ---
    assertThat(chartOfAccounts.getPredefinedValues()).isEmpty();
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses_3_27, ChartsOfAccounts.ПланСчетов1",
    "false, mdclasses_3_27, ChartsOfAccounts.ПланСчетов1"
  })
  void testIndexFields(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(ChartOfAccounts.class);

    var chartOfAccounts = (ChartOfAccounts) mdo;
    assertThat(chartOfAccounts).isNotNull();

    assertThat(chartOfAccounts.getCodeLength()).isEqualTo(9);
    assertThat(chartOfAccounts.getDescriptionLength()).isEqualTo(30);
    assertThat(chartOfAccounts.getOrderLength()).isZero();
    assertThat(chartOfAccounts.getDefaultPresentation()).isEqualTo(DefaultPresentation.AS_CODE);
  }

  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, ChartsOfAccounts.ПланСчетов1",
    "false, mdclasses, ChartsOfAccounts.ПланСчетов1"
  })
  void shouldReadMaxExtDimensionCount(ArgumentsAccessor argumentsAccessor) {
    // given
    var mdo = Fixtures.get(argumentsAccessor);

    // when
    var chartOfAccounts = (ChartOfAccounts) mdo;

    // then
    assertThat(chartOfAccounts).isNotNull();
    assertThat(chartOfAccounts.getMaxExtDimensionCount()).isEqualTo(3);
  }
}