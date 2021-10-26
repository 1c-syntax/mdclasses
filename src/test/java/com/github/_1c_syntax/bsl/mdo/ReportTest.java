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

class ReportTest extends AbstractMDObjectTest<Report> {
  ReportTest() {
    super(Report.class);
  }

  @ParameterizedTest()
  @CsvSource(
    {
      "original, Report.Отчет1"
//      "EDT, AccumulationRegister.Бот1",
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.testAndGetMDO(argumentsAccessor);
  }

//    @ParameterizedTest(name = "EDT {index}: {0}")
//  @CsvSource(
//    {
//      "Отчет1,34d3754d-298c-4786-92f6-a487db249fc7,,,Report,Отчет,1,1,1,1,1,2"
//    }
//  )
//  void testEdt(ArgumentsAccessor argumentsAccessor) {
//    var name = argumentsAccessor.getString(0);
//    var mdo = getMDObjectEDT("Reports/" + name + "/" + name);
//    mdoTest(mdo, MDOType.REPORT, argumentsAccessor);
//
//    checkAttributeField(mdo.getAttributes().get(0),
//      "Реквизит", "4e960a3d-4380-4343-a532-13d3daccf577");
//
//    checkChildField(mdo.getForms().get(0),
//      "ФормаОтчета", "619f2b7f-f08e-4111-bb6e-d1ddd265935d");
//
//    checkChildField(mdo.getTemplates().get(0),
//      "МакетОтчета", "938a7553-1ef1-4d93-b071-0ef1c0740f5b");
//
//    checkChildField(mdo.getCommands().get(0),
//      "Команда", "63b3e264-c28f-415a-93d0-2c90362709c1");
//
//    checkChildField(mdo.getTabularSections().get(0),
//      "ТабличнаяЧасть", "c3018149-521a-4b16-8319-056bc022f3bb");
//  }
}
