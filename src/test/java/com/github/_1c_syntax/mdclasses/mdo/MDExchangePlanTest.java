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
package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.mdo.support.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDExchangePlanTest extends AbstractMDOTest {
  MDExchangePlanTest() {
    super(MDOType.EXCHANGE_PLAN);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("ExchangePlans/ПланОбмена1/ПланОбмена1.mdo");
    checkBaseField(mdo, MDExchangePlan.class, "ПланОбмена1",
      "242cb07d-3d2b-4689-b590-d3ed23ac9d10");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((AbstractMDObjectComplex) mdo).getAttributes()).isEmpty();
    checkModules(((AbstractMDObjectBSL) mdo).getModules(), 1, "ExchangePlans/ПланОбмена1",
      ModuleType.ObjectModule);
    var exchangePlan = (MDExchangePlan) mdo;
    assertThat(exchangePlan.isDistributedInfoBase()).isFalse();
    assertThat(exchangePlan.isIncludeConfigurationExtensions()).isFalse();
    assertThat(exchangePlan.getContent()).hasSize(2);
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("ExchangePlans/ПланОбмена1.xml");
    checkBaseField(mdo, MDExchangePlan.class, "ПланОбмена1",
      "242cb07d-3d2b-4689-b590-d3ed23ac9d10");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((AbstractMDObjectComplex) mdo).getAttributes()).isEmpty();
    assertThat(((AbstractMDObjectBSL) mdo).getModules()).isEmpty();
    var exchangePlan = (MDExchangePlan) mdo;
    assertThat(exchangePlan.isDistributedInfoBase()).isTrue();
    assertThat(exchangePlan.isIncludeConfigurationExtensions()).isTrue();
    assertThat(exchangePlan.getContent()).hasSize(2);
  }

}
