/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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
    assertThat(xdto.getData().targetNamespace()).isEqualTo("http://v8.1c.ru/edi/edi_stnd/EnterpriseData/1.8");
    assertThat(xdto.getData().imports()).hasSize(2)
      .anyMatch("http://www.1c.ru/SSL/Exchange/Message"::equals)
      .anyMatch("http://www.1c.ru/SSL/Exchange/Message2"::equals);

    assertThat(xdto.getData().valueTypes()).hasSize(278)
      .anyMatch(xdtoValueType -> xdtoValueType.name().equals("ТипКоличество"))
      .anyMatch(xdtoValueType -> xdtoValueType.base().equals("xs:decimal"))
      .anyMatch(xdtoValueType -> xdtoValueType.enumerations().size() == 1)
      .anyMatch(xdtoValueType -> xdtoValueType.variety().equals("Atomic"))
    ;

    assertThat(xdto.getData().properties()).hasSize(1);
    var xdtoProperty = xdto.getData().properties().get(0);
    assertThat(xdtoProperty.name()).isEqualTo("performance");
    assertThat(xdtoProperty.type()).isEqualTo("d2p1:Performance");
    assertThat(xdtoProperty.form()).isEqualTo("Attribute");

    assertThat(xdto.getData().objectTypes()).hasSize(737)
      .anyMatch(xdtoObjectType -> xdtoObjectType.name().equals("КлючевыеСвойстваМаркиНоменклатуры"))
      .anyMatch(xdtoObjectType -> xdtoObjectType.base().equals("d2p1:Object"))
      .anyMatch(xdtoValueType -> xdtoValueType.properties().size() == 5)
    ;

    var example = xdto.getData().objectTypes().get(732);
    assertThat(example.base()).isEmpty();
    assertThat(example.name()).isEqualTo("КлючевыеСвойстваПринадлежностьПрайсЛистаКонтрагенту");
    assertThat(example.properties()).hasSize(4);

    var exampleProperty = example.properties().get(3);
    assertThat(exampleProperty.name()).isEqualTo("status");
    assertThat(exampleProperty.type()).isEqualTo("xs:int");
    assertThat(exampleProperty.lowerBound()).isEqualTo(1);
    assertThat(exampleProperty.upperBound()).isEqualTo(-1);
    assertThat(exampleProperty.form()).isEmpty();
    assertThat(exampleProperty.nillable()).isTrue();
  }
}