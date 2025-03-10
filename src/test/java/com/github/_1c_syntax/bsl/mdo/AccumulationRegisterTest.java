/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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

import static com.github._1c_syntax.bsl.test_utils.assertions.Assertions.assertThat;

class AccumulationRegisterTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, AccumulationRegisters.РегистрНакопления1",
      "false, mdclasses, AccumulationRegisters.РегистрНакопления1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(AccumulationRegister.class);
    var accumulationRegister = (AccumulationRegister) mdo;
    assertThat(accumulationRegister.getAttributes()).isEmpty();
    assertThat(accumulationRegister.getForms()).isEmpty();
    assertThat(accumulationRegister.getCommands()).isEmpty();
    assertThat(accumulationRegister.getModules()).isEmpty();
    assertThat(accumulationRegister.getResources()).hasSize(1);
    assertThat(accumulationRegister.getDimensions()).hasSize(1);
    assertThat(accumulationRegister.getChildren()).hasSize(2);
  }
}