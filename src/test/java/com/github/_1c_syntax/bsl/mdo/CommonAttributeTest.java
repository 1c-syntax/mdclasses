/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2024
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

import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import com.github._1c_syntax.bsl.types.MdoReference;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CommonAttributeTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, CommonAttributes.ОбщийРеквизит1",
      "false, mdclasses, CommonAttributes.ОбщийРеквизит1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(CommonAttribute.class);

    var commonAttribute = (CommonAttribute) mdo;

    var mdo1 = MdoReference.create("Catalog.Справочник1");
    var mdo2 = MdoReference.create("Catalog.Документ1");
    var mdo3 = MdoReference.create("Document.Документ1");

    assertThat(commonAttribute.contains(mdo1)).isTrue();
    assertThat(commonAttribute.contains(mdo2)).isFalse();
    assertThat(commonAttribute.contains(mdo3)).isTrue();

    assertThat(commonAttribute.useMode(mdo1)).isEqualTo(UseMode.DONT_USE);
    assertThat(commonAttribute.useMode(mdo2)).isEqualTo(UseMode.DONT_USE);
    assertThat(commonAttribute.useMode(mdo3)).isEqualTo(UseMode.USE);

  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_1, CommonAttributes.ОбластьДанныхВспомогательныеДанные",
      "false, ssl_3_1, CommonAttributes.ОбластьДанныхВспомогательныеДанные"
    }
  )
  void testSSL(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
  }

}