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

class ChartOfCalculationTypesTest extends AbstractMDObjectTest<ChartOfCalculationTypes> {
  ChartOfCalculationTypesTest() {
    super(ChartOfCalculationTypes.class);
  }

  @ParameterizedTest(name = "DESIGNER {index}: {0}")
  @CsvSource(
    {
      "ПланВидовРасчета1,1755c534-9ccd-49c4-9f8b-2aa066424aaa,,,ChartOfCalculationTypes,ПланВидовРасчета,2,1,0,0,0,0"
    }
  )
  void testDesigner(ArgumentsAccessor argumentsAccessor) {
    var mdo = getMDObject("ChartsOfCalculationTypes/" + argumentsAccessor.getString(0));
    mdoTest(mdo, MDOType.CHART_OF_CALCULATION_TYPES, argumentsAccessor);
  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "ПланВидовРасчета1,1755c534-9ccd-49c4-9f8b-2aa066424aaa,,,ChartOfCalculationTypes,ПланВидовРасчета,1,1,1,1,1,1"
    }
  )
  void testEdt(ArgumentsAccessor argumentsAccessor) {
    var name = argumentsAccessor.getString(0);
    var mdo = getMDObjectEDT("ChartsOfCalculationTypes/" + name + "/" + name);
    mdoTest(mdo, MDOType.CHART_OF_CALCULATION_TYPES, argumentsAccessor);

    checkAttributeField(mdo.getAttributes().get(0),
      "Реквизит", "3ef419e4-1b25-41e4-af8f-98b26c7f3287");

    checkChildField(mdo.getForms().get(0),
      "ФормаЭлемента", "580cd87b-3194-4e6b-9930-480c64aabef7");

    checkChildField(mdo.getTemplates().get(0),
      "Макет", "d3f68ce9-9ad3-4a69-9715-337aeca62f14");

    checkChildField(mdo.getCommands().get(0),
      "Команда", "0f28da06-421e-4ba1-8e6c-56ec5b933d32");

    checkChildField(mdo.getTabularSections().get(0),
      "ТабличнаяЧасть", "30f1cedf-4d10-455f-8091-d08880e21987");
  }
}
