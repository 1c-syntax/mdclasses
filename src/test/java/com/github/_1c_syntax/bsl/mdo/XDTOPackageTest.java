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

import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class XDTOPackageTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_1, XDTOPackages.ApdexExport_1_0_0_4",
      "false, ssl_3_1, XDTOPackages.ApdexExport_1_0_0_4"
    }
  )
  void testSimple(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, XDTOPackages.ПакетXDTO1",
      "false, mdclasses, XDTOPackages.ПакетXDTO1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);

    var xdto = (XDTOPackage) mdo;
    assertThat(xdto.getNamespace()).isEqualTo("http://v8.1c.ru/edi/edi_stnd/EnterpriseData/1.8");
    assertThat(xdto.getData()).isNotNull();
    assertThat(xdto.getData().getTargetNamespace()).isEqualTo("http://v8.1c.ru/edi/edi_stnd/EnterpriseData/1.8");
    assertThat(xdto.getData().getImports()).hasSize(2)
      .anyMatch("http://www.1c.ru/SSL/Exchange/Message"::equals)
      .anyMatch("http://www.1c.ru/SSL/Exchange/Message2"::equals);

    assertThat(xdto.getData().getValueTypes()).hasSize(278)
      .anyMatch(xdtoValueType -> xdtoValueType.getName().equals("ТипКоличество"))
      .anyMatch(xdtoValueType -> xdtoValueType.getBase().equals("xs:decimal"))
      .anyMatch(xdtoValueType -> xdtoValueType.getEnumerations().size() == 1)
      .anyMatch(xdtoValueType -> xdtoValueType.getVariety().equals("Atomic"))
    ;

    assertThat(xdto.getData().getProperties()).hasSize(1);
    var xdtoProperty = xdto.getData().getProperties().get(0);
    assertThat(xdtoProperty.getName()).isEqualTo("performance");
    assertThat(xdtoProperty.getType()).isEqualTo("d2p1:Performance");
    assertThat(xdtoProperty.getForm()).isEqualTo("Attribute");

    assertThat(xdto.getData().getObjectTypes()).hasSize(737)
      .anyMatch(xdtoObjectType -> xdtoObjectType.getName().equals("КлючевыеСвойстваМаркиНоменклатуры"))
      .anyMatch(xdtoObjectType -> xdtoObjectType.getBase().equals("d2p1:Object"))
      .anyMatch(xdtoValueType -> xdtoValueType.getProperties().size() == 5)
    ;

    var example = xdto.getData().getObjectTypes().get(732);
    assertThat(example.getBase()).isEmpty();
    assertThat(example.getName()).isEqualTo("КлючевыеСвойстваПринадлежностьПрайсЛистаКонтрагенту");
    assertThat(example.getProperties()).hasSize(4);

    var exampleProperty = example.getProperties().get(3);
    assertThat(exampleProperty.getName()).isEqualTo("status");
    assertThat(exampleProperty.getType()).isEqualTo("xs:int");
    assertThat(exampleProperty.getLowerBound()).isEqualTo(1);
    assertThat(exampleProperty.getUpperBound()).isEqualTo(-1);
    assertThat(exampleProperty.getForm()).isEmpty();
    assertThat(exampleProperty.isNillable()).isTrue();
  }
}