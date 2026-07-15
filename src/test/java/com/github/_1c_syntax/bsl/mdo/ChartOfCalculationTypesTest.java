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
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ChartOfCalculationTypesTest {
  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, ChartOfCalculationTypes.ПланВидовРасчета1",
    "false, mdclasses, ChartOfCalculationTypes.ПланВидовРасчета1"
  })
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(ChartOfCalculationTypes.class);

    var chartOfCalculationTypes = (ChartOfCalculationTypes) mdo;
    assertThat(chartOfCalculationTypes).isNotNull();

    var attributes = chartOfCalculationTypes.getAttributes();
    var tabularSections = chartOfCalculationTypes.getTabularSections();
    var forms = chartOfCalculationTypes.getForms();
    var templates = chartOfCalculationTypes.getTemplates();
    var commands = chartOfCalculationTypes.getCommands();
    var predefinedValues = chartOfCalculationTypes.getPredefinedValues();

    // --- ModuleOwner ---
    assertThat(chartOfCalculationTypes.getModuleTypes())
      .hasSize(chartOfCalculationTypes.getModules().size());
    Assertions.assertThat(chartOfCalculationTypes.getAllModules(), false)
      .containsAll(chartOfCalculationTypes.getModules(), forms, commands);

    // --- ChildrenOwner ---
    Assertions.assertThat(chartOfCalculationTypes.getChildren(), true)
      .containsAll(attributes, tabularSections, forms, templates, commands);
    Assertions.assertThat(chartOfCalculationTypes.getPlainChildren(), true)
      .containsAllPlain(attributes, tabularSections, forms, templates, commands);

    // --- AttributeOwner ---
    Assertions.assertThat(chartOfCalculationTypes.getAllAttributes(), false)
      .containsAll(attributes);
    Assertions.assertThat(chartOfCalculationTypes.getStorageFields(), false)
      .containsAll(attributes, tabularSections);
    Assertions.assertThat(chartOfCalculationTypes.getPlainStorageFields(), false)
      .containsAllPlain(attributes, tabularSections);

    // --- PredefinedDataOwner ---
    assertThat(predefinedValues).isEmpty();
  }
}
