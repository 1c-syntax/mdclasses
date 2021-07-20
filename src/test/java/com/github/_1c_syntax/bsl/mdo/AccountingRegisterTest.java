/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2021
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

import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

class AccountingRegisterTest extends AbstractMDObjectTest<AccountingRegister> {
  AccountingRegisterTest() {
    super(AccountingRegister.class);
  }

  @ParameterizedTest(name = "DESIGNER {index}: {0}")
  @CsvSource(
    {
      "РегистрБухгалтерии1,e5930f2f-15d9-48a1-ac69-379ad990b02a,Accounting register,Регистр бухгалтерии,AccountingRegister,РегистрБухгалтерии,2,0,0,0,0,0"
    }
  )
  void testDesigner(ArgumentsAccessor argumentsAccessor) {
    var mdo = getMDObject("AccountingRegisters/" + argumentsAccessor.getString(0));
    mdoTest(mdo, MDOType.ACCOUNTING_REGISTER, argumentsAccessor);
  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "РегистрБухгалтерии1,e5930f2f-15d9-48a1-ac69-379ad990b02a,Accounting register,Регистр бухгалтерии,AccountingRegister,РегистрБухгалтерии,2,0,1,1,1,1"
    }
  )
  void testEdt(ArgumentsAccessor argumentsAccessor) {
    var name = argumentsAccessor.getString(0);
    var mdo = getMDObjectEDT("AccountingRegisters/" + name + "/" + name);
    mdoTest(mdo, MDOType.ACCOUNTING_REGISTER, argumentsAccessor);

    checkAttributeField(mdo.getAttributes().get(0),
      "Измерение1", "902c08a9-e457-436a-b0fb-b996f0d9bb00",
      List.of("master"));
    checkAttributeField(mdo.getAttributes().get(1),
      "Ресурс1", "e88df8bd-bf97-41a4-88fc-09c84a51824b",
      List.of("passwordMode") // бывает только для строк, а здесь строк быть не может
    );

    checkChildField(mdo.getForms().get(0),
      "ФормаСписка", "ac1810eb-867a-4c1d-9ebd-2d44b5138c3d");

    checkChildField(mdo.getTemplates().get(0),
      "Макет", "853bafc9-fa4d-483f-86af-e9ee4bd07c5f");

    checkChildField(mdo.getCommands().get(0),
      "Команда", "69297dd8-66d9-4b38-a4df-532d046dfb35");
  }
}
