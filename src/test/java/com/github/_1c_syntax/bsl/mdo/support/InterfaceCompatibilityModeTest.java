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
package com.github._1c_syntax.bsl.mdo.support;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InterfaceCompatibilityModeTest {

  @Test
  void getByName() {

    assertThat(InterfaceCompatibilityMode.valueByName("taxi")).isEqualTo(InterfaceCompatibilityMode.TAXI);
    assertThat(InterfaceCompatibilityMode.valueByName("TAXI")).isEqualTo(InterfaceCompatibilityMode.TAXI);
    assertThat(InterfaceCompatibilityMode.valueByName("Версия8_2"))
      .isEqualTo(InterfaceCompatibilityMode.VERSION_8_2);
    assertThat(InterfaceCompatibilityMode.valueByName("Version8_5EnableTaxi"))
      .isEqualTo(InterfaceCompatibilityMode.VERSION_8_5_ENABLE_TAXI);
  }
}