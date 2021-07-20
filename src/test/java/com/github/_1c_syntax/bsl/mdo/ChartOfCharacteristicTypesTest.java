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

class ChartOfCharacteristicTypesTest extends AbstractMDObjectTest<ChartOfCharacteristicTypes> {
  ChartOfCharacteristicTypesTest() {
    super(ChartOfCharacteristicTypes.class);
  }

  @ParameterizedTest(name = "DESIGNER {index}: {0}")
  @CsvSource(
    {
      "ПланВидовХарактеристик1,f53a24c3-f1dc-43b7-8dcf-eeb8c0b7f452,,,ChartOfCharacteristicTypes,ПланВидовХарактеристик,0,0,0,0,0,0"
    }
  )
  void testDesigner(ArgumentsAccessor argumentsAccessor) {
    var mdo = getMDObject("ChartsOfCharacteristicTypes/" + argumentsAccessor.getString(0));
    mdoTest(mdo, MDOType.CHART_OF_CHARACTERISTIC_TYPES, argumentsAccessor);
  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "ПланВидовХарактеристик1,f53a24c3-f1dc-43b7-8dcf-eeb8c0b7f452,,,ChartOfCharacteristicTypes,ПланВидовХарактеристик,1,1,1,1,1,2"
    }
  )
  void testEdt(ArgumentsAccessor argumentsAccessor) {
    var name = argumentsAccessor.getString(0);
    var mdo = getMDObjectEDT("ChartsOfCharacteristicTypes/" + name + "/" + name);
    mdoTest(mdo, MDOType.CHART_OF_CHARACTERISTIC_TYPES, argumentsAccessor);

    checkAttributeField(mdo.getAttributes().get(0),
      "Реквизит", "fc53db45-c10b-4d0d-b7db-b08b1eb600ce");

    checkChildField(mdo.getForms().get(0),
      "ФормаЭлемента", "8bd7f127-527c-43ea-9ded-9adc3f5a5abe");

    checkChildField(mdo.getTemplates().get(0),
      "Макет", "a55d9aed-5bac-4565-858a-b866e5dfdd7b");

    checkChildField(mdo.getCommands().get(0),
      "Команда", "c7d21185-c0bd-4f66-b8a6-eedb5ace3fa3");

    checkChildField(mdo.getTabularSections().get(0),
      "ТабличнаяЧасть", "9ec021ee-9660-488c-80e8-23b17962c218");
  }
}
