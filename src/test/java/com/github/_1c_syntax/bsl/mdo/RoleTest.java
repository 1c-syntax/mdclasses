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

class RoleTest {
  @ParameterizedTest
  @CsvSource(
    {
      "true, ssl_3_1, Roles.БазовыеПраваБСП",
      "false, ssl_3_1, Roles.БазовыеПраваБСП"
    }
  )
  void testSimple(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
  }

  @ParameterizedTest
  @CsvSource(
    {
      "true, mdclasses, Roles.Роль1",
      "false, mdclasses, Roles.Роль1"
    }
  )
  void test(ArgumentsAccessor argumentsAccessor) {
    var mdo = MDTestUtils.getMDWithSimpleTest(argumentsAccessor);
    var role = (Role) mdo;
    var roleData = role.getData();
    assertThat(roleData).isNotNull();

    var objectRights = roleData.getObjectRights();
    assertThat(objectRights).hasSize(3);

    var confRights = objectRights.get(0);
    assertThat(confRights.getName().getMdoRef()).isEqualTo("Configuration.Конфигурация");
    assertThat(confRights.getRights()).hasSize(18);

    var documentRights = objectRights.get(1);
    assertThat(documentRights.getName().getMdoRef()).isEqualTo("Document.Документ1");
    assertThat(documentRights.getRights()).hasSize(18);

    var catalogRights = objectRights.get(2);
    assertThat(catalogRights.getName().getMdoRef()).isEqualTo("Catalog.Справочник1");
    assertThat(catalogRights.getRights()).hasSize(16);
  }
}