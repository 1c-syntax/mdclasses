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

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExchangePlanTest extends AbstractMDOTest {
  ExchangePlanTest() {
    super(MDOType.EXCHANGE_PLAN);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("ExchangePlans/ПланОбмена1/ПланОбмена1.mdo");
    checkBaseField(mdo, ExchangePlan.class, "ПланОбмена1",
      "242cb07d-3d2b-4689-b590-d3ed23ac9d10");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((MDObjectComplex) mdo).getAttributes()).hasSize(0);
    checkModules(((MDObjectBSL) mdo).getModules(), 1, "ExchangePlans/ПланОбмена1",
      ModuleType.ObjectModule);

  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("ExchangePlans/ПланОбмена1.xml");
    checkBaseField(mdo, ExchangePlan.class, "ПланОбмена1",
      "242cb07d-3d2b-4689-b590-d3ed23ac9d10");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((MDObjectComplex) mdo).getAttributes()).hasSize(0);
    assertThat(((MDObjectBSL) mdo).getModules()).hasSize(0);
  }

}
