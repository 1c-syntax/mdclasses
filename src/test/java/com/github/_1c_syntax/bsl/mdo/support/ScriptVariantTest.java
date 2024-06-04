/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2024
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
package com.github._1c_syntax.bsl.mdo.support;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ScriptVariantTest {
  @ParameterizedTest(name = "{index}: {0}")
  @CsvSource(
    {
      "RUSSIAN,русский,ru,RussIan",
      "ENGLISH,английский,en,engLish",
      "RUSSIAN,ру,рус,eng"
    }
  )
  void testValueByString(ArgumentsAccessor argumentsAccessor) {
    var element = ScriptVariant.valueOf(argumentsAccessor.getString(0));
    assertThat(ScriptVariant.valueByString(argumentsAccessor.getString(1))).isEqualTo(element);
    assertThat(ScriptVariant.valueByString(argumentsAccessor.getString(2))).isEqualTo(element);
    assertThat(ScriptVariant.valueByString(argumentsAccessor.getString(3))).isEqualTo(element);
  }
}
