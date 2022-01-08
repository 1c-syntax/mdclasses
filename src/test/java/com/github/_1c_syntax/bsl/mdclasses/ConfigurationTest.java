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
package com.github._1c_syntax.bsl.mdclasses;

import com.github._1c_syntax.bsl.test_utils.AbstractMDClassTest;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static com.github._1c_syntax.bsl.test_utils.Assertions.assertThat;

class ConfigurationTest extends AbstractMDClassTest<Configuration> {
  ConfigurationTest() {
    super(Configuration.class);
  }

  @ParameterizedTest()
  @CsvSource(
    {
      "designer/mdclasses, 65, 128",
      "designer/mdclasses_3_18, 24, 38",
      "designer/mdclasses_ordinary, 1, 1",
      "designer/mdclasses_ext, 84, 147",
//      "designer/ssl_3_1, 1, 1" // очень большой для фикстуры
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var pack = argumentsAccessor.getString(0);
    var mdc = MDTestUtils.getMDClass("src/test/resources/ext/" + pack + "/src/cf", false);
    var current = MDTestUtils.createJson(mdc, false);
    var fixture = MDTestUtils.getFixture("src/test/resources/fixtures/" + pack + "/configuration.json");
    assertThat(current, true).isEqual(fixture);
    assertThat(mdc.getChildren()).hasSize(argumentsAccessor.getInteger(1));
    assertThat(mdc.getPlainChildren()).hasSize(argumentsAccessor.getInteger(2));
  }
}
