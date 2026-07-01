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

import com.github._1c_syntax.bsl.mdo.ChartOfCalculationTypes;
import com.github._1c_syntax.bsl.mdo.support.DefaultFormKind;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import com.github._1c_syntax.bsl.types.MdoReference;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ChartOfCalculationTypesTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, ChartsOfCalculationTypes.ПланВидовРасчета1",
      "false, mdclasses, ChartsOfCalculationTypes.ПланВидовРасчета1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(ChartOfCalculationTypes.class);
    var cct = (ChartOfCalculationTypes) mdo;

    // FormOwner
    assertThat(cct.getDefaultFormMap()).hasSize(6);

    // Для форм, которых нет в фикстуре
    assertThat(cct.getDefaultFormLink(DefaultFormKind.OBJECT_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(cct.getDefaultForm(DefaultFormKind.OBJECT_FORM)).isEmpty();
    assertThat(cct.getDefaultFormLink(DefaultFormKind.AUX_OBJECT_FORM)).isEqualTo(MdoReference.EMPTY);
    assertThat(cct.getDefaultForm(DefaultFormKind.AUX_OBJECT_FORM)).isEmpty();
    assertThat(cct.getDefaultFormLink(DefaultFormKind.FOLDER_FORM)).isEqualTo(MdoReference.EMPTY);

    // getFormByLink с несуществующей ссылкой
    assertThat(cct.getFormByLink(MdoReference.create("ChartOfCalculationTypes.Unknown.Form.Unknown"))).isEmpty();
  }
}