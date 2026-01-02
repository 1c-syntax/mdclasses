/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
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

class HandlerTest {

  @Test
  void testEmptyConstant() {
    assertThat(Handler.EMPTY).isNotNull();
    assertThat(Handler.EMPTY.isEmpty()).isTrue();
    assertThat(Handler.EMPTY.getMethodPath()).isEmpty();
    assertThat(Handler.EMPTY.getModuleName()).isEmpty();
    assertThat(Handler.EMPTY.getMethodName()).isEmpty();
  }

  @Test
  void testNullPath() {
    var handler = new Handler(null);

    assertThat(handler.isEmpty()).isTrue();
    assertThat(handler.getMethodPath()).isEmpty();
    assertThat(handler.getModuleName()).isEmpty();
    assertThat(handler.getMethodName()).isEmpty();
  }

  @Test
  void testEmptyPath() {
    var handler = new Handler("");

    assertThat(handler.isEmpty()).isTrue();
    assertThat(handler.getMethodPath()).isEmpty();
  }

  @Test
  void testValidHandler() {
    var handler = new Handler("Configuration.CommonModule.MethodName");

    assertThat(handler.isEmpty()).isFalse();
    assertThat(handler.getMethodPath()).isEqualTo("Configuration.CommonModule.MethodName");
    assertThat(handler.getModuleName()).isEqualTo("CommonModule");
    assertThat(handler.getMethodName()).isEqualTo("MethodName");
  }

  @Test
  void testHandlerWithModuleOnly() {
    var handler = new Handler("Configuration.CommonModule");

    assertThat(handler.isEmpty()).isFalse();
    assertThat(handler.getModuleName()).isEqualTo("CommonModule");
    assertThat(handler.getMethodName()).isEmpty();
  }

  @Test
  void testHandlerWithShortPath() {
    var handler = new Handler("Module");

    assertThat(handler.isEmpty()).isFalse();
    assertThat(handler.getModuleName()).isEmpty();
    assertThat(handler.getMethodName()).isEmpty();
  }
}
