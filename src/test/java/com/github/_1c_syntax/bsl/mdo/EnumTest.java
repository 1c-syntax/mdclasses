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

class EnumTest extends AbstractMDObjectTest<Enum> {
  EnumTest() {
    super(Enum.class);
  }

  @ParameterizedTest(name = "DESIGNER {index}: {0}")
  @CsvSource(
    {
      "Перечисление1,f11f3441-4b64-4344-b1a0-0e4b3e466e03,,,Enum,Перечисление,0,0,0,0,0,0"
    }
  )
  void testDesigner(ArgumentsAccessor argumentsAccessor) {
    var mdo = getMDObject("Enums/" + argumentsAccessor.getString(0));
    mdoTest(mdo, MDOType.ENUM, argumentsAccessor);
  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "Перечисление1,f11f3441-4b64-4344-b1a0-0e4b3e466e03,,,Enum,Перечисление,0,0,1,1,1,1"
    }
  )
  void testEdt(ArgumentsAccessor argumentsAccessor) {
    var name = argumentsAccessor.getString(0);
    var mdo = getMDObjectEDT("Enums/" + name + "/" + name);
    mdoTest(mdo, MDOType.ENUM, argumentsAccessor);

    checkChildField(mdo.getForms().get(0),
      "ФормаСписка", "fec43f1c-ee8a-416d-9180-14b06ffaa6d6");

    checkChildField(mdo.getTemplates().get(0),
      "Макет", "d137ea0a-0b60-4226-939d-00d946c71c4e");

    checkChildField(mdo.getCommands().get(0),
      "Команда", "f325e376-febd-48f1-afc8-26626335125f");
  }
}
