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

class AccountingRegisterTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, AccountingRegisters.РегистрБухгалтерии1",
      "false, mdclasses, AccountingRegisters.РегистрБухгалтерии1",
      "true, mdclasses_3_25, AccountingRegisters.РегистрБухгалтерии1",
      "false, mdclasses_3_25, AccountingRegisters.РегистрБухгалтерии1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = Fixtures.get(argumentsAccessor);
    assertThat(mdo).isInstanceOf(AccountingRegister.class);

    var accountingRegister = (AccountingRegister) mdo;
    assertThat(accountingRegister).isNotNull();

    // --- AccountingRegister ---
    assertThat(accountingRegister.getChartOfAccounts())
      .isEqualTo(MdoReference.create("ChartOfAccounts.ПланСчетов1"));

    // --- ModuleOwner ---
    assertThat(accountingRegister.getModuleTypes()).isEmpty();
    Assertions.assertThat(accountingRegister.getAllModules(), false)
      .containsAll(accountingRegister.getModules(), accountingRegister.getForms(), accountingRegister.getCommands());

    // --- ChildrenOwner ---
    Assertions.assertThat(accountingRegister.getChildren(), false)
      .containsAll(accountingRegister.getAttributes(),
        accountingRegister.getDimensions(),
        accountingRegister.getResources(),
        accountingRegister.getForms(),
        accountingRegister.getCommands(),
        accountingRegister.getTemplates()
      );
    Assertions.assertThat(accountingRegister.getPlainChildren(), true)
      .containsAll(accountingRegister.getChildren());

    // --- AttributeOwner ---
    Assertions.assertThat(accountingRegister.getAllAttributes(), false)
      .containsAll(accountingRegister.getAttributes(),
        accountingRegister.getDimensions(),
        accountingRegister.getResources()
      );
    Assertions.assertThat(accountingRegister.getStorageFields(), false)
      .containsAll(accountingRegister.getAttributes(),
        accountingRegister.getDimensions(),
        accountingRegister.getResources()
      );
    Assertions.assertThat(accountingRegister.getPlainStorageFields(), false)
      .containsAll(accountingRegister.getAttributes(),
        accountingRegister.getDimensions(),
        accountingRegister.getResources()
      );
  }
}
