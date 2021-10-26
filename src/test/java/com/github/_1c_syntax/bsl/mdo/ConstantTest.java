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

class ConstantTest extends AbstractMDObjectTest<Constant> {
  ConstantTest() {
    super(Constant.class);
  }

  @ParameterizedTest()
  @CsvSource(
    {
      "original, Constant.Константа1"
//      "EDT, AccumulationRegister.Бот1",
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.testAndGetMDO(argumentsAccessor);
  }

//
//  @ParameterizedTest(name = "DESIGNER {index}: {0}")
//  @CsvSource(
//    {
//      "Константа1,61e6a6f2-7057-4e93-96c3-7bd2559217f4,,,Constant,Константа,0,0,0,0,0,0"
//    }
//  )
//  void testDesigner(ArgumentsAccessor argumentsAccessor) {
//    var mdo = getMDObject("Constants/" + argumentsAccessor.getString(0));
//    mdoTest(mdo, MDOType.CONSTANT, argumentsAccessor);
//    assertThat(mdo.isPasswordMode()).isTrue();
//  }
//
//  @ParameterizedTest(name = "EDT {index}: {0}")
//  @CsvSource(
//    {
//      "Константа1,61e6a6f2-7057-4e93-96c3-7bd2559217f4,,,Constant,Константа,0,0,0,0,0,0"
//    }
//  )
//  void testEdt(ArgumentsAccessor argumentsAccessor) {
//    var name = argumentsAccessor.getString(0);
//    var mdo = getMDObjectEDT("Constants/" + name + "/" + name);
//    mdoTest(mdo, MDOType.CONSTANT, argumentsAccessor);
//    assertThat(mdo.isPasswordMode()).isFalse();
//    assertThat(mdo.getKind()).isEqualTo(AttributeKind.STANDARD);
//    assertThat(mdo.getIndexing()).isEqualTo(IndexingType.DONT_INDEX);
//  }
}
