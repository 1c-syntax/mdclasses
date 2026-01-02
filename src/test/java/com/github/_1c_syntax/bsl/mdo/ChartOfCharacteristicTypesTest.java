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

import com.github._1c_syntax.bsl.mdo.support.CodeSeries;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ChartOfCharacteristicTypesTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, ChartsOfCharacteristicTypes.ПланВидовХарактеристик1, _edt",
      "false, mdclasses, ChartsOfCharacteristicTypes.ПланВидовХарактеристик1",
      "true, ssl_3_1, ChartsOfCharacteristicTypes.ДополнительныеРеквизитыИСведения, _edt",
      "false, ssl_3_1, ChartsOfCharacteristicTypes.ДополнительныеРеквизитыИСведения"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
  }

  /**
   * Проверяет, что для плана видов характеристик "ПланВидовХарактеристик1" поле checkUnique установлено в true.
   * <p>
   * В формате Designer: в XML файле явно указано {@code <checkUnique>true</checkUnique>}.
   * В формате EDT: в XML файле явно указано {@code <checkUnique>true</checkUnique>}.
   *
   * @param argumentsAccessor параметры теста (формат, имя пакета, ссылка на MDO, постфикс фикстуры)
   */
  @ParameterizedTest
  @CsvSource({
    "true, mdclasses, ChartsOfCharacteristicTypes.ПланВидовХарактеристик1, _edt",
    "false, mdclasses, ChartsOfCharacteristicTypes.ПланВидовХарактеристик1"
  })
  void testCheckUniqueTrue(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo)
      .isInstanceOf(ChartOfCharacteristicTypes.class);

    var chartOfCharacteristicTypes = (ChartOfCharacteristicTypes) mdo;
    assertThat(chartOfCharacteristicTypes.isCheckUnique())
      .as("Поле checkUnique должно быть true для плана видов характеристик ПланВидовХарактеристик1")
      .isTrue();
    assertThat(chartOfCharacteristicTypes.getCodeSeries())
      .as("Поле codeSeries должно быть WHOLE_CATALOG для плана видов характеристик ПланВидовХарактеристик1")
      .isEqualTo(CodeSeries.WHOLE_CATALOG);
  }
}