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

class DataProcessorTest extends AbstractMDObjectTest<DataProcessor> {
  DataProcessorTest() {
    super(DataProcessor.class);
  }

  @ParameterizedTest()
  @CsvSource(
    {
      "original, DataProcessor.Обработка1"
//      "EDT, AccumulationRegister.Бот1",
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.testAndGetMDO(argumentsAccessor);
  }


//  @ParameterizedTest(name = "DESIGNER {index}: {0}")
//  @CsvSource(
//    {
//      "Обработка1,a7c57ba0-75d8-487d-b8ea-ae5083d8a503,,,DataProcessor,Обработка,0,0,1,0,0,0"
//    }
//  )
//  void testDesigner(ArgumentsAccessor argumentsAccessor) {
//    var mdo = getMDObject("DataProcessors/" + argumentsAccessor.getString(0));
//    mdoTest(mdo, MDOType.DATA_PROCESSOR, argumentsAccessor);
//  }
//
//  @ParameterizedTest(name = "EDT {index}: {0}")
//  @CsvSource(
//    {
//      "Обработка1,a7c57ba0-75d8-487d-b8ea-ae5083d8a503,,,DataProcessor,Обработка,1,1,1,1,1,1"
//    }
//  )
//  void testEdt(ArgumentsAccessor argumentsAccessor) {
//    var name = argumentsAccessor.getString(0);
//    var mdo = getMDObjectEDT("DataProcessors/" + name + "/" + name);
//    mdoTest(mdo, MDOType.DATA_PROCESSOR, argumentsAccessor);
//
//    checkAttributeField(mdo.getAttributes().get(0),
//      "Реквизит", "6dd3a446-52f1-4f34-9ecb-28911c8d0f1d");
//
//    checkChildField(mdo.getForms().get(0),
//      "Форма", "5a32de80-b66f-4d6d-be8b-a117f5f71480");
//
//    checkChildField(mdo.getTemplates().get(0),
//      "Макет", "23c07ed7-69c3-46de-a114-f2a8fa669ea5");
//
//    checkChildField(mdo.getCommands().get(0),
//      "Команда", "d2dca064-4048-4289-a518-d96da49380c3");
//
//    checkChildField(mdo.getTabularSections().get(0),
//      "ТабличнаяЧасть", "c28cea46-9113-474d-a1f2-115c12d9d7fb");
//  }
}
