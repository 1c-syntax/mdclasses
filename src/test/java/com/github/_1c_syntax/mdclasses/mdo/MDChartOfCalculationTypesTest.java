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

import com.github._1c_syntax.mdclasses.mdo.attributes.TabularSection;
import com.github._1c_syntax.mdclasses.mdo.metadata.AttributeType;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDChartOfCalculationTypesTest extends AbstractMDOTest {
  MDChartOfCalculationTypesTest() {
    super(MDOType.CHART_OF_CALCULATION_TYPES);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("ChartsOfCalculationTypes/ПланВидовРасчета1/ПланВидовРасчета1.mdo");
    checkBaseField(mdo, MDChartOfCalculationTypes.class, "ПланВидовРасчета1",
      "1755c534-9ccd-49c4-9f8b-2aa066424aaa");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((AbstractMDObjectComplex) mdo).getAttributes()).isEmpty();
    assertThat(((AbstractMDObjectBSL) mdo).getModules()).isEmpty();

  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("ChartsOfCalculationTypes/ПланВидовРасчета1.xml");
    checkBaseField(mdo, MDChartOfCalculationTypes.class, "ПланВидовРасчета1",
      "1755c534-9ccd-49c4-9f8b-2aa066424aaa");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    checkAttributes(((AbstractMDObjectComplex) mdo).getAttributes(), 3, mdo.getMdoReference(),
      AttributeType.ATTRIBUTE, AttributeType.TABULAR_SECTION);
    var tabularSection = (TabularSection) ((AbstractMDObjectComplex) mdo).getAttributes().stream()
      .filter(attribute -> attribute.getAttributeType() == AttributeType.TABULAR_SECTION)
      .findFirst().get();
    checkAttributes(tabularSection.getAttributes(), 1, tabularSection.getMdoReference(), AttributeType.ATTRIBUTE);

  }

}
