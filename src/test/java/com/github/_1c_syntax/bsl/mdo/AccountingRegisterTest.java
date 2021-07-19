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

import com.github._1c_syntax.bsl.mdo.support.IndexingType;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountingRegisterTest extends AbstractMDObjectTest<AccountingRegister> {
  AccountingRegisterTest() {
    super(AccountingRegister.class);
  }

  @Test
  void test() {
    var mdo = getMDObject("AccountingRegisters/РегистрБухгалтерии1");
    checkBaseField(mdo, MDOType.ACCOUNTING_REGISTER,
      "РегистрБухгалтерии1", "e5930f2f-15d9-48a1-ac69-379ad990b02a",
      ObjectBelonging.OWN);

  }

  @Test
  void test2() {
    var mdo = getMDObjectEDT("AccountingRegisters/РегистрБухгалтерии1/РегистрБухгалтерии1");
    checkBaseField(mdo, MDOType.ACCOUNTING_REGISTER,
      "РегистрБухгалтерии1", "e5930f2f-15d9-48a1-ac69-379ad990b02a",
      ObjectBelonging.OWN);
    checkAttributeField(mdo.getAttributes().get(0), "Измерение1", "902c08a9-e457-436a-b0fb-b996f0d9bb00");
    checkAttributeField(mdo.getAttributes().get(1), "Ресурс1", "e88df8bd-bf97-41a4-88fc-09c84a51824b");
  }
}