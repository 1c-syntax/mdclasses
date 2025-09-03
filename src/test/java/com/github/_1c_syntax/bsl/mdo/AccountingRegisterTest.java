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

import static org.assertj.core.api.Assertions.assertThat;

class AccountingRegisterTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, AccountingRegisters.РегистрБухгалтерии1, _edt",
      "false, mdclasses, AccountingRegisters.РегистрБухгалтерии1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);

    var accountingRegister = (AccountingRegister) mdo;

    assertThat(accountingRegister.getSynonym().isEmpty()).isFalse();
    assertThat(accountingRegister.getSynonym().get("ru")).isEqualTo("Регистр бухгалтерии");
    assertThat(accountingRegister.getSynonym().get("en")).isEqualTo("Accounting register");
    assertThat(accountingRegister.getSynonym().get("by")).isEmpty();

    assertThat(accountingRegister.getDescription()).isEqualTo("Регистр бухгалтерии");
    assertThat(accountingRegister.getDescription("ru")).isEqualTo("Регистр бухгалтерии");
    assertThat(accountingRegister.getDescription("en")).isEqualTo("Accounting register");
    assertThat(accountingRegister.getDescription("by")).isNotEmpty().isNotEqualTo("РегистрБухгалтерии1");
  }
}
