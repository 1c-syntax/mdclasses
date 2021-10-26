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

import com.github._1c_syntax.bsl.mdo.support.ReturnValueReuse;
import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ModuleType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CommonModuleTest extends AbstractMDObjectTest<CommonModule> {
  CommonModuleTest() {
    super(CommonModule.class);
  }

  @ParameterizedTest()
  @CsvSource(
    {
      "original, CommonModule.ГлобальныйОбщийМодуль",
      "original, CommonModule.ПростойОбщийМодуль",
      "original, CommonModule.ОбщийМодульПовтИспСеанс",
      "original_3_18, CommonModule.ОбщийМодуль1",
//      "EDT, AccumulationRegister.Бот1",
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.testAndGetMDO(argumentsAccessor);
  }


//  @ParameterizedTest(name = "DESIGNER {index}: {0}")
//  @CsvSource(
//    {
//      "ГлобальныйОбщийМодуль,9e9c021c-bdbd-4804-a53a-9442ba9eb18c,,Глобальный общий модуль,CommonModule,ОбщийМодуль," +
//        "0,0,0,0,0,1,true,true,true,false,true,true,false,Модуль/Ext/Module.bsl",
//      "ПростойОбщийМодуль,1be4af7e-334e-49fa-a9f9-d80c737ff954,,Простой общий модуль,CommonModule,ОбщийМодуль," +
//        "0,0,0,0,0,1,false,false,false,false,true,false,false,Модуль/Ext/Module.bsl",
//      "ОбщийМодульПовтИспСеанс,a09f73b1-0058-4f3b-a22b-799ef3c2a33d,,Общий модуль повт исп сеанс,CommonModule," +
//        "ОбщийМодуль,0,0,0,0,0,1,false,false,false,false,true,false,false,МодульПовтИспСеанс/Ext/Module.bsl"
//    }
//  )
//  void testDesigner(ArgumentsAccessor argumentsAccessor) {
//    var mdo = getMDObject("CommonModules/" + argumentsAccessor.getString(0));
//    mdoTest(mdo, MDOType.COMMON_MODULE, argumentsAccessor);
//
//    assertThat(mdo.isClientManagedApplication()).isEqualTo(argumentsAccessor.getBoolean(12));
//    assertThat(mdo.isClientOrdinaryApplication()).isEqualTo(argumentsAccessor.getBoolean(13));
//    assertThat(mdo.isGlobal()).isEqualTo(argumentsAccessor.getBoolean(14));
//    assertThat(mdo.isPrivileged()).isEqualTo(argumentsAccessor.getBoolean(15));
//    assertThat(mdo.isServer()).isEqualTo(argumentsAccessor.getBoolean(16));
//    assertThat(mdo.isExternalConnection()).isEqualTo(argumentsAccessor.getBoolean(17));
//    assertThat(mdo.isServerCall()).isEqualTo(argumentsAccessor.getBoolean(18));
//    assertThat(mdo.getModuleType()).isEqualTo(ModuleType.CommonModule);
//    assertThat(mdo.getUri().getPath())
//      .endsWith(argumentsAccessor.getString(19));
//  }
//
//  @ParameterizedTest(name = "EDT {index}: {0}")
//  @CsvSource(
//    {
//      "ОбщийМодульПолныйеПрава,5733cabc-1b65-48f8-9ab6-269f1b73a21c,,Общий модуль полныйе права,CommonModule,ОбщийМодуль,0,0,0,0,0,1"
//    }
//  )
//  void testEdt(ArgumentsAccessor argumentsAccessor) {
//    var name = argumentsAccessor.getString(0);
//    var mdo = getMDObjectEDT("CommonModules/" + name + "/" + name);
//    mdoTest(mdo, MDOType.COMMON_MODULE, argumentsAccessor);
//
//    assertThat(mdo.isClientManagedApplication()).isFalse();
//    assertThat(mdo.isClientOrdinaryApplication()).isFalse();
//    assertThat(mdo.isGlobal()).isFalse();
//    assertThat(mdo.isPrivileged()).isTrue();
//    assertThat(mdo.isServer()).isTrue();
//    assertThat(mdo.isExternalConnection()).isFalse();
//    assertThat(mdo.isServerCall()).isTrue();
//    assertThat(mdo.getModuleType()).isEqualTo(ModuleType.CommonModule);
//    assertThat(mdo.getUri().getPath())
//      .endsWith("еПрава/Module.bsl");
//    assertThat(mdo.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);
//  }
}
