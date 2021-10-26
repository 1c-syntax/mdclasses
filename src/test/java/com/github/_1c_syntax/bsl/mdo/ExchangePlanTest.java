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

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.mdo.support.AutoRecordType;
import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ExchangePlanTest extends AbstractMDObjectTest<ExchangePlan> {
  ExchangePlanTest() {
    super(ExchangePlan.class);
  }

  @ParameterizedTest()
  @CsvSource(
    {
      "original, ExchangePlan.ПланОбмена1",
      "original_3_18, ExchangePlan.ПланОбмена1"
//      "EDT, AccumulationRegister.Бот1",
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.testAndGetMDO(argumentsAccessor);
  }


//  @ParameterizedTest(name = "DESIGNER {index}: {0}")
//  @CsvSource(
//    {
//      "ПланОбмена1,242cb07d-3d2b-4689-b590-d3ed23ac9d10,,,ExchangePlan,ПланОбмена,0,0,0,0,0,0"
//    }
//  )
//  void testDesigner(ArgumentsAccessor argumentsAccessor) {
//    var mdo = getMDObject("ExchangePlans/" + argumentsAccessor.getString(0));
//    mdoTest(mdo, MDOType.EXCHANGE_PLAN, argumentsAccessor);
//    assertThat(mdo.isDistributedInfoBase()).isTrue();
//    assertThat(mdo.isIncludeConfigurationExtensions()).isTrue();
//  }

//  @ParameterizedTest(name = "EDT {index}: {0}")
//  @CsvSource(
//    {
//      "ПланОбмена1,242cb07d-3d2b-4689-b590-d3ed23ac9d10,,,ExchangePlan,ПланОбмена,1,1,1,1,1,1"
//    }
//  )
//  void testEdt(ArgumentsAccessor argumentsAccessor) {
//    var name = argumentsAccessor.getString(0);
//    var mdo = getMDObjectEDT("ExchangePlans/" + name + "/" + name);
//    mdoTest(mdo, MDOType.EXCHANGE_PLAN, argumentsAccessor);
//
//    checkAttributeField(mdo.getAttributes().get(0),
//      "Реквизит", "dbc326e3-d65f-4a7b-a426-ed39be45cfdc");
//
//    checkChildField(mdo.getForms().get(0),
//      "ФормаУзла", "829f7375-1f16-4c24-b6b3-189f87c43778");
//
//    checkChildField(mdo.getTemplates().get(0),
//      "Макет", "a1126315-8ec0-4e8e-9327-3a8fff6cc716");
//
//    checkChildField(mdo.getCommands().get(0),
//      "Команда", "70ab6afa-2da0-463c-b1be-4b05968bb991");
//
//    checkChildField(mdo.getTabularSections().get(0),
//      "ТабличнаяЧасть", "3b60ae22-8c6b-43a7-b599-cfc2d5074a97");
//    assertThat(mdo.isDistributedInfoBase()).isFalse();
//    assertThat(mdo.isIncludeConfigurationExtensions()).isFalse();
//  }
//
//  @ParameterizedTest(name = "EDT {index}: {0}")
//  @CsvSource(
//    {
//      "ПланОбмена1,242cb07d-3d2b-4689-b590-d3ed23ac9d10,,,ExchangePlan,ПланОбмена,1,1,1,1,1,1"
//    }
//  )
//  void testContent(ArgumentsAccessor argumentsAccessor) {
//    var rootPath = Path.of("src/test/resources/metadata/edt");
//    var mdc = MDClasses.createConfiguration(rootPath);
//    assertThat(mdc)
//      .isNotNull()
//      .isInstanceOf(Configuration.class);
//    var configuration = (Configuration) mdc;
//    assertThat(configuration.getExchangePlans()).hasSize(1);
//    var exchangePlan = configuration.getExchangePlans().get(0);
//    mdoTest(exchangePlan, MDOType.EXCHANGE_PLAN, argumentsAccessor);
//    assertThat(exchangePlan.getContent()).hasSize(2);
//    assertThat(exchangePlan.getContent().stream()
//      .collect(Collectors.toMap(ExchangePlan.Item::getMdoReference, ExchangePlan.Item::getAutoRecord)))
//      .containsKey(MdoReference.find("Catalog.Справочник1").get())
//      .containsKey(MdoReference.find("Document.Документ1").get())
//      .containsValue(AutoRecordType.ALLOW)
//      .containsValue(AutoRecordType.DENY);
//  }
}
