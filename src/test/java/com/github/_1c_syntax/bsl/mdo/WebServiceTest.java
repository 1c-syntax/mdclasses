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

class WebServiceTest extends AbstractMDObjectTest<WebService> {
  WebServiceTest() {
    super(WebService.class);
  }

  @ParameterizedTest()
  @CsvSource(
    {
      "original, WebService.WebСервис1"
//      "EDT, AccumulationRegister.Бот1",
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.testAndGetMDO(argumentsAccessor);
  }

//  @ParameterizedTest(name = "DESIGNER {index}: {0}")
//  @CsvSource(
//    {
//      "WebСервис1,d7f9b06b-0799-486e-adff-c45a2d5b8101,,,WebService,WebСервис,0,0,0,0,0,0"
//    }
//  )
//  void testDesigner(ArgumentsAccessor argumentsAccessor) {
//    var mdo = getMDObject("WebServices/" + argumentsAccessor.getString(0));
//    mdoTest(mdo, MDOType.WEB_SERVICE, argumentsAccessor);
//    assertThat(mdo.getOperations()).hasSize(2);
//    var operation = mdo.getOperations().get(0);
//    assertThat(operation.getName()).isEqualTo("Операция1");
//    assertThat(operation.getType()).isEqualTo(MDOType.WS_OPERATION);
//    assertThat(operation.getHandler()).isEqualTo("Операция1");
//    assertThat(operation.getUuid()).isEqualTo("cd562f7f-8321-4e95-a845-e93bc5924c2a");
//    assertThat(operation.getObjectBelonging()).isEqualTo(ObjectBelonging.OWN);
//    assertThat(operation.getMetadataName()).isEqualTo("Operation");
//    assertThat(operation.getMetadataNameRu()).isEqualTo("Операция");
//    assertThat(operation.getSynonym().getContent()).isEmpty();
//    assertThat(operation.getMdoReference().getMdoRef()).isEqualTo("WebService.WebСервис1.Operation.Операция1");
//    assertThat(operation.getMdoReference().getMdoRefRu()).isEqualTo("WebСервис.WebСервис1.Операция.Операция1");
//  }
//
//  @ParameterizedTest(name = "EDT {index}: {0}")
//  @CsvSource(
//    {
//      "WebСервис1,d7f9b06b-0799-486e-adff-c45a2d5b8101,,,WebService,WebСервис,0,0,0,0,0,1"
//    }
//  )
//  void testEdt(ArgumentsAccessor argumentsAccessor) {
//    var name = argumentsAccessor.getString(0);
//    var mdo = getMDObjectEDT("WebServices/" + name + "/" + name);
//    mdoTest(mdo, MDOType.WEB_SERVICE, argumentsAccessor);
//    assertThat(mdo.getOperations()).hasSize(2);
//    var operation = mdo.getOperations().get(0);
//    assertThat(operation.getName()).isEqualTo("Операция");
//    assertThat(operation.getType()).isEqualTo(MDOType.WS_OPERATION);
//    assertThat(operation.getHandler()).isEqualTo("Операция1");
//    assertThat(operation.getUuid()).isEqualTo("21c0c4a9-5f3b-4147-be1e-500ed834fae4");
//    assertThat(operation.getObjectBelonging()).isEqualTo(ObjectBelonging.OWN);
//    assertThat(operation.getMetadataName()).isEqualTo("Operation");
//    assertThat(operation.getMetadataNameRu()).isEqualTo("Операция");
//    assertThat(operation.getSynonym().getContent())
//      .hasSize(1)
//      .contains(Map.entry("ru", "Операция"));
//    assertThat(operation.getMdoReference().getMdoRef()).isEqualTo("WebService.WebСервис1.Operation.Операция");
//    assertThat(operation.getMdoReference().getMdoRefRu()).isEqualTo("WebСервис.WebСервис1.Операция.Операция");
//  }
}
