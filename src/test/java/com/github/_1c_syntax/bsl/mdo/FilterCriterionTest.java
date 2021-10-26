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
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

class FilterCriterionTest extends AbstractMDObjectTest<FilterCriterion> {
  FilterCriterionTest() {
    super(FilterCriterion.class);
  }

  @ParameterizedTest()
  @CsvSource(
    {
      "original, FilterCriterion.КритерийОтбора1"
//      "EDT, AccumulationRegister.Бот1",
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.testAndGetMDO(argumentsAccessor);
  }

//  @ParameterizedTest(name = "DESIGNER {index}: {0}")
//  @CsvSource(
//    {
//      "КритерийОтбора1,6e9d3381-0607-43df-866d-14ee5d65a294,,,FilterCriterion,КритерийОтбора,0,0,0,0,0,0"
//    }
//  )
//  void testDesigner(ArgumentsAccessor argumentsAccessor) {
//    var mdo = getMDObject("FilterCriteria/" + argumentsAccessor.getString(0));
//    mdoTest(mdo, MDOType.FILTER_CRITERION, argumentsAccessor);
//  }
//
//  @ParameterizedTest(name = "EDT {index}: {0}")
//  @CsvSource(
//    {
//      "КритерийОтбора1,6e9d3381-0607-43df-866d-14ee5d65a294,,,FilterCriterion,КритерийОтбора,0,0,1,1,0,1"
//    }
//  )
//  void testEdt(ArgumentsAccessor argumentsAccessor) {
//    var name = argumentsAccessor.getString(0);
//    var mdo = getMDObjectEDT("FilterCriteria/" + name + "/" + name);
//    mdoTest(mdo, MDOType.FILTER_CRITERION, argumentsAccessor);
//
//    checkChildField(mdo.getForms().get(0),
//      "ФормаСписка", "37f491b3-9eb6-4e94-9825-de7c1a5e909e");
//
//    checkChildField(mdo.getCommands().get(0),
//      "Команда", "8e807c30-50d7-4eba-8127-b234812d5f16");
//  }
}
