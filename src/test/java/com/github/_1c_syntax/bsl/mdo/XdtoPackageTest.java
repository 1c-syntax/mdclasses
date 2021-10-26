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

class XdtoPackageTest extends AbstractMDObjectTest<XdtoPackage> {
  XdtoPackageTest() {
    super(XdtoPackage.class);
  }

  @ParameterizedTest()
  @CsvSource(
    {
      "original, XDTOPackage.ПакетXDTO1"
//      "EDT, AccumulationRegister.Бот1",
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.testAndGetMDO(argumentsAccessor);
  }

//  @ParameterizedTest(name = "DESIGNER {index}: {0}")
//  @CsvSource(
//    {
//      "ПакетXDTO1,b8a93cce-56e4-4507-b281-5c525a466a0f,,,XDTOPackage,ПакетXDTO,0,0,0,0,0,0"
//    }
//  )
//  void testDesigner(ArgumentsAccessor argumentsAccessor) {
//    var mdo = getMDObject("XDTOPackages/" + argumentsAccessor.getString(0));
//    mdoTest(mdo, MDOType.XDTO_PACKAGE, argumentsAccessor);
//    assertThat(mdo.getNamespace()).isEqualTo("http://v8.1c.ru/edi/edi_stnd/EnterpriseData/1.8");
//    assertThat(mdo.getData()).isNotNull();
//
//    var xdtoData = mdo.getData();
//    assertThat(xdtoData.getTargetNamespace()).isEqualTo("http://v8.1c.ru/edi/edi_stnd/EnterpriseData/1.8");
//    assertThat(xdtoData.getImports())
//      .hasSize(1)
//      .contains("http://www.1c.ru/SSL/Exchange/Message");
//    assertThat(xdtoData.getObjectTypes())
//      .hasSize(737);
//
//    var objectType = xdtoData.getObjectTypes().get(1);
//    assertThat(objectType.getName()).isEqualTo("СоставнойЛюбойОбъект");
//    assertThat(objectType.getBase()).isEmpty();
//    assertThat(objectType.getProperties())
//      .hasSize(194);
//
//    var property = objectType.getProperties().get(1);
//    assertThat(property.getName()).isEqualTo("АвизоПоМПЗВходящее");
//    assertThat(property.getForm()).isEmpty();
//    assertThat(property.getLowerBound()).isZero();
//    assertThat(property.getUpperBound()).isZero();
//    assertThat(property.getType()).isEqualTo("d3p1:КлючевыеСвойстваАвизоПоМПЗВходящее");
//    assertThat(property.isNillable()).isFalse();
//
//    assertThat(xdtoData.getProperties()).isEmpty();
//
//    var valueType = xdtoData.getValueTypes().get(0);
//    assertThat(valueType.getName()).isEqualTo("ТипДатаФНС");
//    assertThat(valueType.getBase()).isEqualTo("xs:string");
//    assertThat(valueType.getEnumerations()).isEmpty();
//    assertThat(valueType.getVariety()).isEqualTo("Atomic");
//
//  }
}
