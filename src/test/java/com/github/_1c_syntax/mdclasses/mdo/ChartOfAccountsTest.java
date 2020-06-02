/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2020
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
package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.AttributeType;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChartOfAccountsTest extends AbstractMDOTest {
  ChartOfAccountsTest() {
    super(MDOType.CHART_OF_ACCOUNTS);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("ChartsOfAccounts/ПланСчетов1/ПланСчетов1.mdo");
    checkBaseField(mdo, ChartOfAccounts.class, "ПланСчетов1",
      "2766f353-abd2-4e7f-9a95-53f05c83f5d4");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((MDObjectComplex) mdo).getAttributes(), 2, "ChartOfAccounts.ПланСчетов1",
      AttributeType.ACCOUNTING_FLAG, AttributeType.EXT_DIMENSION_ACCOUNTING_FLAG);
    checkModules(((MDObjectBSL) mdo).getModules(), 1, "ChartsOfAccounts/ПланСчетов1",
      ModuleType.ObjectModule);

  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("ChartsOfAccounts/ПланСчетов1.xml");
    checkBaseField(mdo, ChartOfAccounts.class, "ПланСчетов1",
      "2766f353-abd2-4e7f-9a95-53f05c83f5d4");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((MDObjectComplex) mdo).getAttributes()).hasSize(0);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }

}
