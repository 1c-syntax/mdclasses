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

import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import com.github._1c_syntax.bsl.types.MdoReference;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class IntegrationServiceTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses_3_18, IntegrationServices.СервисИнтеграции1, _edt",
      "false, mdclasses_3_18, IntegrationServices.СервисИнтеграции1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    assertThat(mdo).isInstanceOf(IntegrationService.class);
    var is = (IntegrationService) mdo;
    assertThat(is.getUuid()).isEqualTo("94ed2401-fd3c-4e92-b34d-1cdad2d8ee42");
    assertThat(is.getName()).isEqualTo("СервисИнтеграции1");
    assertThat(is.getMdoReference()).isEqualTo(MdoReference.create("IntegrationServices.СервисИнтеграции1"));
    assertThat(is.getComment()).isEmpty();
    assertThat(is.getSynonym().isEmpty()).isTrue();
    assertThat(is.getSupportVariant()).isEqualTo(SupportVariant.NONE);
    assertThat(is.getModules())
      .hasSize(1)
      .isEqualTo(is.getAllModules());
    assertThat(is.getIntegrationServiceChannels())
      .hasSize(2)
      .isEqualTo(is.getChildren())
      .isEqualTo(is.getPlainChildren());
  }
}