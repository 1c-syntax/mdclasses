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

import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class RoleTest extends AbstractMDObjectTest<Role> {
  RoleTest() {
    super(Role.class);
  }

  @ParameterizedTest(name = "DESIGNER {index}: {0}")
  @CsvSource(
    {
      "Роль1,ecad0539-4f9f-491b-b0f2-f8f42d9a7c41,,,Role,Роль,0,0,0,0,0,0"
    }
  )
  void testDesigner(ArgumentsAccessor argumentsAccessor) {
    var mdo = getMDObject("Roles/" + argumentsAccessor.getString(0));
    mdoTest(mdo, MDOType.ROLE, argumentsAccessor);
    assertThat(mdo.isIndependentRightsOfChildObjects()).isFalse();
    assertThat(mdo.isSetForAttributesByDefault()).isTrue();
    assertThat(mdo.isSetForNewObjects()).isFalse();
  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "Роль1,ecad0539-4f9f-491b-b0f2-f8f42d9a7c41,,,Role,Роль,0,0,0,0,0,0"
    }
  )
  void testEdt(ArgumentsAccessor argumentsAccessor) {
    var name = argumentsAccessor.getString(0);
    var mdo = getMDObjectEDT("Roles/" + name + "/" + name);
    mdoTest(mdo, MDOType.ROLE, argumentsAccessor);
    assertThat(mdo.isIndependentRightsOfChildObjects()).isTrue();
    assertThat(mdo.isSetForAttributesByDefault()).isTrue();
    assertThat(mdo.isSetForNewObjects()).isTrue();
    assertThat(mdo.getRights())
      .hasSize(44)
      .anyMatch(roleRight -> roleRight.getName().equals("Subsystem.ВтораяПодсистема")
        && roleRight.getRights().containsKey("View")
        && roleRight.getRights().containsValue(false));
  }
}
