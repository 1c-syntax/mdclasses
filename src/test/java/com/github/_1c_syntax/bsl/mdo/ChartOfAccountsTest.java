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

import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.api.Test;

class ChartOfAccountsTest extends AbstractMDObjectTest<ChartOfAccounts> {
  ChartOfAccountsTest() {
    super(ChartOfAccounts.class);
  }

  @Test
  void test() {
    var mdo = getMDObject("ChartsOfAccounts/ПланСчетов1");
    checkBaseField(mdo, MDOType.CHART_OF_ACCOUNTS,
      "ПланСчетов1", "2766f353-abd2-4e7f-9a95-53f05c83f5d4",
      ObjectBelonging.OWN);
  }
}
