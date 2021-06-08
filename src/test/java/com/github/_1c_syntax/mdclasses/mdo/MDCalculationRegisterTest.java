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

import com.github._1c_syntax.mdclasses.mdo.attributes.Dimension;
import com.github._1c_syntax.mdclasses.mdo.attributes.Recalculation;
import com.github._1c_syntax.mdclasses.mdo.metadata.AttributeType;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDCalculationRegisterTest extends AbstractMDOTest {
  MDCalculationRegisterTest() {
    super(MDOType.CALCULATION_REGISTER);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("CalculationRegisters/РегистрРасчета1/РегистрРасчета1.mdo");
    checkBaseField(mdo, MDCalculationRegister.class, "РегистрРасчета1",
      "90587c7d-b950-4476-ac14-426e4a83d9c4");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((AbstractMDObjectComplex) mdo).getAttributes(), 3, mdo.getMdoReference(),
      AttributeType.DIMENSION, AttributeType.RESOURCE, AttributeType.RECALCULATION);
    assertThat(((AbstractMDObjectBSL) mdo).getModules()).isEmpty();

    var calcRegister = (MDCalculationRegister) mdo;
    var recalculation = calcRegister.getAttributes().stream()
      .filter(attribute -> attribute.getAttributeType() == AttributeType.RECALCULATION).findFirst();
    recalculation.ifPresent(attribute -> {
      assertThat(((Recalculation) attribute).getModules()).hasSize(1);
    });

	var dimension = (Dimension) ((AbstractMDObjectComplex) mdo).getAttributes().get(1);
    assertThat(dimension.isDenyIncompleteValues()).isTrue();
    assertThat(dimension.isMaster()).isFalse();
    assertThat(dimension.isUseInTotals()).isTrue();
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("CalculationRegisters/РегистрРасчета1.xml");
    checkBaseField(mdo, MDCalculationRegister.class, "РегистрРасчета1",
      "90587c7d-b950-4476-ac14-426e4a83d9c4");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((AbstractMDObjectComplex) mdo).getAttributes(), 4, mdo.getMdoReference(),
      AttributeType.DIMENSION, AttributeType.RESOURCE, AttributeType.RECALCULATION);
    assertThat(((AbstractMDObjectBSL) mdo).getModules()).isEmpty();
    var calcRegister = (MDCalculationRegister) mdo;
    var recalculation = calcRegister.getAttributes().stream()
      .filter(attribute -> attribute.getAttributeType() == AttributeType.RECALCULATION).findFirst();
    recalculation.ifPresent(attribute -> {
      assertThat(((Recalculation) attribute).getModules()).hasSize(1);
    });

	var dimension = (Dimension) ((AbstractMDObjectComplex) mdo).getAttributes().get(1);
    assertThat(dimension.isDenyIncompleteValues()).isFalse();
    assertThat(dimension.isMaster()).isFalse();
    assertThat(dimension.isUseInTotals()).isTrue();

  }

}
