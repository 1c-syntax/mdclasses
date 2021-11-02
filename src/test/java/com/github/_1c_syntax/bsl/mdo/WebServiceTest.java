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

import com.github._1c_syntax.bsl.mdo.children.WebServiceOperation;
import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

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
    var value = (WebService) mdo;
    assertThat(value.getChildren()).hasSize(2);
    assertThat(value.getXdtoPackages()).isEmpty();

    assertThat(((WebServiceOperation) value.getChildren().get(1)).getChildren()).hasSize(2);
    assertThat(((WebServiceOperation) value.getChildren().get(0)).getHandler()).isNotBlank();

  }
}
