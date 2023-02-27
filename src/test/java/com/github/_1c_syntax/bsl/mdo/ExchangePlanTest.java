/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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

import com.github._1c_syntax.bsl.mdo.support.AutoRecordType;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import com.github._1c_syntax.bsl.types.MdoReference;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ExchangePlanTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, ExchangePlans.ПланОбмена1, _edt",
      "false, mdclasses, ExchangePlans.ПланОбмена1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(ExchangePlan.class);

    var exchangePlan = (ExchangePlan) mdo;
    var mdo1 = MdoReference.create("Catalog.Справочник1");
    var mdo2 = MdoReference.create("Document.Документ1");
    var mdo3 = MdoReference.create("Catalog.Документ1");
    assertThat(exchangePlan.contains(mdo1)).isTrue();
    assertThat(exchangePlan.contains(mdo2)).isTrue();
    assertThat(exchangePlan.contains(mdo3)).isFalse();

    assertThat(exchangePlan.autoRecord(mdo1)).isEqualTo(AutoRecordType.ALLOW);
    assertThat(exchangePlan.autoRecord(mdo2)).isEqualTo(AutoRecordType.DENY);
    assertThat(exchangePlan.autoRecord(mdo3)).isEqualTo(AutoRecordType.DENY);
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_1, ExchangePlans.ОбновлениеИнформационнойБазы, _edt",
      "false, ssl_3_1, ExchangePlans.ОбновлениеИнформационнойБазы"
    }
  )
  void testSSL(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(ExchangePlan.class);

    var exchangePlan = (ExchangePlan) mdo;
    var mdo1 = MdoReference.create("InformationRegister.СостоянияРассылокОтчетов");
    var mdo2 = MdoReference.create("Catalog.ЭлектронноеПисьмоВходящееПрисоединенныеФайлы");
    var mdo3 = MdoReference.create("Catalog.Справочник1");
    assertThat(exchangePlan.contains(mdo1)).isTrue();
    assertThat(exchangePlan.contains(mdo2)).isTrue();
    assertThat(exchangePlan.contains(mdo3)).isFalse();

    assertThat(exchangePlan.autoRecord(mdo1)).isEqualTo(AutoRecordType.DENY);
    assertThat(exchangePlan.autoRecord(mdo2)).isEqualTo(AutoRecordType.DENY);
    assertThat(exchangePlan.autoRecord(mdo3)).isEqualTo(AutoRecordType.DENY);
  }
//
//    @Override
//    @Test
//    void testEDT() {
//      var mdo = getMDObjectEDT("ExchangePlans.ПланОбмена1");
//      checkBaseField(mdo, ExchangePlan.class, "ПланОбмена1",
//        "242cb07d-3d2b-4689-b590-d3ed23ac9d10");
//      checkForms(mdo);
//      checkTemplates(mdo);
//      checkCommands(mdo);
//      assertThat(((AbstractMDObjectComplex) mdo).getAttributes()).isEmpty();
//      checkModules(((AbstractMDObjectBSL) mdo).getModules(), 1, "ExchangePlans/ПланОбмена1",
//        ModuleType.ObjectModule);
//      var exchangePlan = (ExchangePlan) mdo;
////    assertThat(exchangePlan.isDistributedInfoBase()).isFalse();
////    assertThat(exchangePlan.isIncludeConfigurationExtensions()).isFalse();
////    assertThat(exchangePlan.getContent()).hasSize(2);
//    }
//
//    @Override
//    @Test
//    void testDesigner() {
//      var mdo = getMDObjectDesigner("ExchangePlans.ПланОбмена1");
//      checkBaseField(mdo, ExchangePlan.class, "ПланОбмена1",
//        "242cb07d-3d2b-4689-b590-d3ed23ac9d10");
//      checkForms(mdo);
//      checkTemplates(mdo);
//      checkCommands(mdo);
//      assertThat(((AbstractMDObjectComplex) mdo).getAttributes()).isEmpty();
//      assertThat(((AbstractMDObjectBSL) mdo).getModules()).isEmpty();
//      var exchangePlan = (ExchangePlan) mdo;
////    assertThat(exchangePlan.isDistributedInfoBase()).isTrue();
////    assertThat(exchangePlan.isIncludeConfigurationExtensions()).isTrue();
////    assertThat(exchangePlan.getContent()).hasSize(2);
//    }
//
//  }

}