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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

class BusinessProcessTest extends AbstractMDObjectTest<BusinessProcess> {
  BusinessProcessTest() {
    super(BusinessProcess.class);
  }

  @ParameterizedTest()
  @CsvSource(
    {
      "original, BusinessProcess.БизнесПроцесс1"
//      "EDT, AccumulationRegister.Бот1",
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.testAndGetMDO(argumentsAccessor);
  }

//  @ParameterizedTest(name = "EDT {index}: {0}")
//  @CsvSource(
//    {
//      "БизнесПроцесс1,560a32ca-028d-4b88-b6f2-6b7212bf31f8,,,BusinessProcess,БизнесПроцесс,1,1,1,1,1,2"
//    }
//  )
//  void testEdt(ArgumentsAccessor argumentsAccessor) {
//    var name = argumentsAccessor.getString(0);
//    var mdo = getMDObjectEDT("BusinessProcesses/" + name + "/" + name);
//    mdoTest(mdo, MDOType.BUSINESS_PROCESS, argumentsAccessor);
//
//    checkAttributeField(mdo.getAttributes().get(0),
//      "Реквизит", "b3d2a544-baef-41a3-9485-c6b364fe87b7");
//
//    checkChildField(mdo.getForms().get(0),
//      "ФормаЭлемента", "4c6e3430-02e1-4490-8037-a6bc3ba208cf");
//
//    checkChildField(mdo.getTemplates().get(0),
//      "Макет", "b1568d42-f083-4a86-9500-5b630340199e");
//
//    checkChildField(mdo.getCommands().get(0),
//      "Команда", "61241c39-482a-41ad-90f7-6ecb1358bf84");
//
//    checkChildField(mdo.getTabularSections().get(0),
//      "ТабличнаяЧасть", "ad9aacc9-4b7f-43e3-8016-8b0b22cb6191");
//  }
}
