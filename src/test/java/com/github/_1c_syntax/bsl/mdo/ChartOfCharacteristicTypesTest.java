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
import com.github._1c_syntax.bsl.types.MdoReference;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ChartOfCharacteristicTypesTest {
  @ParameterizedTest
  @CsvSource({
    "true, ssl_3_1, ChartsOfCharacteristicTypes.ОбъектыАдресацииЗадач",
    "false, ssl_3_1, ChartsOfCharacteristicTypes.ОбъектыАдресацииЗадач",
    "true, ssl_3_2, ChartsOfCharacteristicTypes.ОбъектыАдресацииЗадач",
    "false, ssl_3_2, ChartsOfCharacteristicTypes.ОбъектыАдресацииЗадач"
  })
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(ChartOfCharacteristicTypes.class);

    var chartOfCharacteristicTypes = (ChartOfCharacteristicTypes) mdo;
    assertThat(chartOfCharacteristicTypes).isNotNull();

    var attributes = chartOfCharacteristicTypes.getAttributes();
    var tabularSections = chartOfCharacteristicTypes.getTabularSections();
    var forms = chartOfCharacteristicTypes.getForms();
    var templates = chartOfCharacteristicTypes.getTemplates();
    var commands = chartOfCharacteristicTypes.getCommands();
    var predefinedValues = chartOfCharacteristicTypes.getPredefinedValues();

    // --- ModuleOwner ---
    assertThat(chartOfCharacteristicTypes.getModuleTypes())
      .hasSize(chartOfCharacteristicTypes.getModules().size());
    Assertions.assertThat(chartOfCharacteristicTypes.getAllModules(), false)
      .containsAll(chartOfCharacteristicTypes.getModules(), forms, commands);

    // --- ChildrenOwner ---
    Assertions.assertThat(chartOfCharacteristicTypes.getChildren(), true)
      .containsAll(attributes, tabularSections, forms, templates, commands, predefinedValues);
    Assertions.assertThat(chartOfCharacteristicTypes.getPlainChildren(), true)
      .containsAllPlain(attributes, tabularSections, forms, templates, commands, predefinedValues);

    // --- AttributeOwner ---
    Assertions.assertThat(chartOfCharacteristicTypes.getAllAttributes(), false)
      .containsAll(attributes);
    Assertions.assertThat(chartOfCharacteristicTypes.getStorageFields(), false)
      .containsAll(attributes, tabularSections);
    Assertions.assertThat(chartOfCharacteristicTypes.getPlainStorageFields(), false)
      .containsAllPlain(attributes, tabularSections);

    // --- PredefinedDataOwner ---
    assertThat(chartOfCharacteristicTypes.getPredefinedValues()).hasSize(1);
    var predefinedValue = chartOfCharacteristicTypes.getPredefinedValues().getFirst();
    assertThat(predefinedValue.getName()).isEqualTo("ВсеОбъектыАдресации");
    assertThat(predefinedValue.getDescription()).isEqualTo("Все объекты адресации");
    assertThat(predefinedValue.getOwner()).isEqualTo(chartOfCharacteristicTypes.getMdoReference());
    assertThat(predefinedValue.getMdoReference())
      .isEqualTo(MdoReference.create(
        "ChartOfCharacteristicTypes.ОбъектыАдресацииЗадач.Predefined.ВсеОбъектыАдресации"));
    assertThat(chartOfCharacteristicTypes.getChildren()).contains(predefinedValue);
  }
}
