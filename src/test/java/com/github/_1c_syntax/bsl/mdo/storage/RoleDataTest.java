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
package com.github._1c_syntax.bsl.mdo.storage;

import com.github._1c_syntax.bsl.mdo.support.RoleRight;
import com.github._1c_syntax.bsl.types.MdoReference;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class RoleDataTest {

  @Test
  void testEmptyConstant() {
    assertThat(RoleData.EMPTY).isNotNull();
    assertThat(RoleData.EMPTY.objectRights()).isEmpty();
  }

  @Test
  void testBuilderWithEmptyList() {
    var roleData = RoleData.builder().build();

    assertThat(roleData).isNotNull();
    assertThat(roleData.objectRights()).isEmpty();
  }

  @Test
  void testObjectRight() {
    var objectRight = new RoleData.ObjectRight(MdoReference.create("Catalogs.TestObject"), Collections.emptyList());

    assertThat(objectRight).isNotNull();
    assertThat(objectRight.name().getMdoRef()).isEqualTo("Catalog.TestObject");
    assertThat(objectRight.rights()).isEmpty();
  }

  @Test
  void testRight() {
    var right = new RoleData.Right(RoleRight.READ, true);

    assertThat(right).isNotNull();
    assertThat(right.name()).isEqualTo(RoleRight.READ);
    assertThat(right.value()).isTrue();
  }
}
