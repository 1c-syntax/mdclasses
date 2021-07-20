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

class CatalogTest extends AbstractMDObjectTest<Catalog> {
  CatalogTest() {
    super(Catalog.class);
  }

  @ParameterizedTest(name = "DESIGNER {index}: {0}")
  @CsvSource(
    {
      "Справочник1,eeef463d-d5e7-42f2-ae53-10279661f59d,,,Catalog,Справочник,3,1,4,1,2,0"
    }
  )
  void testDesigner(ArgumentsAccessor argumentsAccessor) {
    var mdo = getMDObject("Catalogs/" + argumentsAccessor.getString(0));
    mdoTest(mdo, MDOType.CATALOG, argumentsAccessor);
  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "Справочник1,eeef463d-d5e7-42f2-ae53-10279661f59d,,,Catalog,Справочник,3,1,3,1,1,2"
    }
  )
  void testEdt(ArgumentsAccessor argumentsAccessor) {
    var name = argumentsAccessor.getString(0);
    var mdo = getMDObjectEDT("Catalogs/" + name + "/" + name);
    mdoTest(mdo, MDOType.CATALOG, argumentsAccessor);

    checkAttributeField(mdo.getAttributes().get(0),
      "Реквизит1", "f4b6bb58-962f-4811-809c-84514f07f16d");

    checkChildField(mdo.getForms().get(0),
      "ФормаЭлемента", "175b035e-ee35-4fdf-a8b4-c30ce49dee61");

    checkChildField(mdo.getTemplates().get(0),
      "Макет", "42def71b-52cc-4e34-8a08-82f0dfcd47bf");

    checkChildField(mdo.getCommands().get(0),
      "Команда1", "342ec3c7-82d4-42bb-a5ff-8a756f110744");

    checkChildField(mdo.getTabularSections().get(0),
      "ТабличнаяЧасть1", "451202c5-b1af-4bce-a705-0b5570071588");
  }
}
