/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2022
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
package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.mdclasses.mdo.support.ObjectRight;
import com.github._1c_syntax.mdclasses.mdo.support.RoleData;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RoleDataTest {

  private static final String SRC_EDT = "src/test/resources/metadata/edt";
  private static final String SRC_DESIGNER = "src/test/resources/metadata/original";

  @Test
  void testRoleDataEdt() {
    var mdo = MDClasses.readMDObject(Paths.get(SRC_EDT), "Roles.Роль1");
    assertThat(mdo).isPresent();
    var role = (MDRole) mdo.get();
    testRole(role);
  }

  @Test
  void testRoleDataDesigner() {
    var mdo = MDClasses.readMDObject(Paths.get(SRC_DESIGNER), "Roles.Роль1");
    assertThat(mdo).isPresent();
    MDRole role = (MDRole) mdo.get();
    testRole(role);
  }

  private void testRole(MDRole role) {

    RoleData roleData = role.getRoleData();
    assertThat(roleData).isNotNull();

    List<ObjectRight> objectRights = roleData.getObjectRights();
    assertThat(objectRights).hasSize(3);

    ObjectRight confRights = objectRights.get(0);
    assertThat(confRights.getName()).isEqualTo("Configuration.Конфигурация");
    assertThat(confRights.getRights()).hasSize(18);

    ObjectRight documentRights = objectRights.get(1);
    assertThat(documentRights.getName()).isEqualTo("Document.Документ1");
    assertThat(documentRights.getRights()).hasSize(18);

    ObjectRight catalogRights = objectRights.get(2);
    assertThat(catalogRights.getName()).isEqualTo("Catalog.Справочник1");
    assertThat(catalogRights.getRights()).hasSize(16);
  }
}
