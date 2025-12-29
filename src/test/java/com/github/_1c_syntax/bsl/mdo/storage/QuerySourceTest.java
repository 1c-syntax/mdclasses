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
package com.github._1c_syntax.bsl.mdo.storage;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuerySourceTest {

  @Test
  void testEmptyConstant() {
    assertThat(QuerySource.EMPTY).isNotNull();
    assertThat(QuerySource.EMPTY.line()).isZero();
    assertThat(QuerySource.EMPTY.column()).isZero();
    assertThat(QuerySource.EMPTY.textQuery()).isEmpty();
  }

  @Test
  void testRecordCreation() {
    var querySource = new QuerySource(10, 5, "SELECT * FROM Table");

    assertThat(querySource.line()).isEqualTo(10);
    assertThat(querySource.column()).isEqualTo(5);
    assertThat(querySource.textQuery()).isEqualTo("SELECT * FROM Table");
  }

  @Test
  void testRecordWithEmptyQuery() {
    var querySource = new QuerySource(0, 0, "");

    assertThat(querySource).isNotNull();
    assertThat(querySource.textQuery()).isEmpty();
  }

  @Test
  void testRecordEquality() {
    var querySource1 = new QuerySource(1, 2, "query");
    var querySource2 = new QuerySource(1, 2, "query");

    assertThat(querySource1).isEqualTo(querySource2);
  }
}
