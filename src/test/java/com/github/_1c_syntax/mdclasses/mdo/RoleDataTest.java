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
package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.mdo.support.ObjectRight;
import com.github._1c_syntax.mdclasses.mdo.support.RightType;
import com.github._1c_syntax.mdclasses.mdo.support.RoleData;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RoleDataTest {

  private static final String SRC_EDT = "src/test/resources/metadata/edt/src";
  private static final String SRC_DESIGNER = "src/test/resources/metadata/original";

  @Test
  void testRoleDataEdt() {
    var mdo = MDOFactory.readMDObject(
      Paths.get(SRC_EDT, "Roles/Роль1/Роль1.mdo"));
    assertThat(mdo).isPresent();
    MDRole role = (MDRole) mdo.get();
    testRole(role);
  }

  @Test
  void testRoleDataDesigner() {
    var mdo = MDOFactory.readMDObject(
      Paths.get(SRC_DESIGNER, "Roles/Роль1.xml"));
    assertThat(mdo).isPresent();
    MDRole role = (MDRole) mdo.get();
    testRole(role);
  }


  private void testRole(MDRole role) {

    RoleData roleData = role.getRoleData();
    assertThat(roleData).isNotNull();

    List<ObjectRight> objectRights = roleData.getObjectRights();
    assertThat(objectRights).hasSize(3);

    ObjectRight confObjRights = objectRights.get(0);
    assertThat(confObjRights.getName()).isEqualTo("Configuration.Конфигурация");
    var confRights = confObjRights.getRights();
    assertThat(confRights).hasSize(18);
    assertThat(confRights.get(0).getRightType()).isEqualTo(RightType.ADMINISTRATION);
    assertThat(confRights.get(1).getRightType()).isEqualTo(RightType.DATA_ADMINISTRATION);
    assertThat(confRights.get(2).getRightType()).isEqualTo(RightType.UPDATE_DATABASE_CONFIGURATION);
    assertThat(confRights.get(3).getRightType()).isEqualTo(RightType.EXCLUSIVE_MODE);
    assertThat(confRights.get(4).getRightType()).isEqualTo(RightType.ACTIVE_USERS);
    assertThat(confRights.get(5).getRightType()).isEqualTo(RightType.EVENTLOG);
    assertThat(confRights.get(6).getRightType()).isEqualTo(RightType.THIN_CLIENT);
    assertThat(confRights.get(7).getRightType()).isEqualTo(RightType.WEB_CLIENT);
    assertThat(confRights.get(8).getRightType()).isEqualTo(RightType.THICK_CLIENT);
    assertThat(confRights.get(9).getRightType()).isEqualTo(RightType.EXTERNAL_CONNECTION);
    assertThat(confRights.get(10).getRightType()).isEqualTo(RightType.AUTOMATION);
    assertThat(confRights.get(11).getRightType()).isEqualTo(RightType.ALL_FUNCTIONS_MODE);
    assertThat(confRights.get(12).getRightType()).isEqualTo(RightType.COLLABORATION_SYSTEM_INFOBASE_REGISTRATION);
    assertThat(confRights.get(13).getRightType()).isEqualTo(RightType.SAVE_USER_DATA);
    assertThat(confRights.get(14).getRightType()).isEqualTo(RightType.CONFIGURATION_EXTENSIONS_ADMINISTRATION);
    assertThat(confRights.get(15).getRightType()).isEqualTo(RightType.INTERACTIVE_OPEN_EXT_DATA_PROCESSORS);
    assertThat(confRights.get(16).getRightType()).isEqualTo(RightType.INTERACTIVE_OPEN_EXT_REPORTS);
    assertThat(confRights.get(17).getRightType()).isEqualTo(RightType.OUTPUT);

    ObjectRight docObjRights = objectRights.get(1);
    assertThat(docObjRights.getName()).isEqualTo("Document.Документ1");
    var docRights = docObjRights.getRights();
    assertThat(docRights).hasSize(18);
    assertThat(docRights.get(0).getRightType()).isEqualTo(RightType.READ);
    assertThat(docRights.get(1).getRightType()).isEqualTo(RightType.INSERT);
    assertThat(docRights.get(2).getRightType()).isEqualTo(RightType.UPDATE);
    assertThat(docRights.get(3).getRightType()).isEqualTo(RightType.DELETE);
    assertThat(docRights.get(4).getRightType()).isEqualTo(RightType.POSTING);
    assertThat(docRights.get(5).getRightType()).isEqualTo(RightType.UNDO_POSTING);
    assertThat(docRights.get(6).getRightType()).isEqualTo(RightType.VIEW);
    assertThat(docRights.get(7).getRightType()).isEqualTo(RightType.INTERACTIVE_INSERT);
    assertThat(docRights.get(8).getRightType()).isEqualTo(RightType.EDIT);
    assertThat(docRights.get(9).getRightType()).isEqualTo(RightType.INTERACTIVE_DELETE);
    assertThat(docRights.get(10).getRightType()).isEqualTo(RightType.INTERACTIVE_SET_DELETION_MARK);
    assertThat(docRights.get(11).getRightType()).isEqualTo(RightType.INTERACTIVE_CLEAR_DELETION_MARK);
    assertThat(docRights.get(12).getRightType()).isEqualTo(RightType.INTERACTIVE_DELETE_MARKED);
    assertThat(docRights.get(13).getRightType()).isEqualTo(RightType.INTERACTIVE_POSTING);
    assertThat(docRights.get(14).getRightType()).isEqualTo(RightType.INTERACTIVE_POSTING_REGULAR);
    assertThat(docRights.get(15).getRightType()).isEqualTo(RightType.INTERACTIVE_UNDO_POSTING);
    assertThat(docRights.get(16).getRightType()).isEqualTo(RightType.INTERACTIVE_CHANGE_OF_POSTED);
    assertThat(docRights.get(17).getRightType()).isEqualTo(RightType.INPUT_BY_STRING);

    ObjectRight catalogObjRights = objectRights.get(2);
    assertThat(catalogObjRights.getName()).isEqualTo("Catalog.Справочник1");
    var catalogRights = catalogObjRights.getRights();
    assertThat(catalogRights).hasSize(16);
    assertThat(catalogRights.get(0).getRightType()).isEqualTo(RightType.READ);
    assertThat(catalogRights.get(1).getRightType()).isEqualTo(RightType.INSERT);
    assertThat(catalogRights.get(2).getRightType()).isEqualTo(RightType.UPDATE);
    assertThat(catalogRights.get(3).getRightType()).isEqualTo(RightType.DELETE);
    assertThat(catalogRights.get(4).getRightType()).isEqualTo(RightType.VIEW);
    assertThat(catalogRights.get(5).getRightType()).isEqualTo(RightType.INTERACTIVE_INSERT);
    assertThat(catalogRights.get(6).getRightType()).isEqualTo(RightType.EDIT);
    assertThat(catalogRights.get(7).getRightType()).isEqualTo(RightType.INTERACTIVE_DELETE);
    assertThat(catalogRights.get(8).getRightType()).isEqualTo(RightType.INTERACTIVE_SET_DELETION_MARK);
    assertThat(catalogRights.get(9).getRightType()).isEqualTo(RightType.INTERACTIVE_CLEAR_DELETION_MARK);
    assertThat(catalogRights.get(10).getRightType()).isEqualTo(RightType.INTERACTIVE_DELETE_MARKED);
    assertThat(catalogRights.get(11).getRightType()).isEqualTo(RightType.INPUT_BY_STRING);
    assertThat(catalogRights.get(12).getRightType()).isEqualTo(RightType.INTERACTIVE_DELETE_PREDEFINED_DATA);
    assertThat(catalogRights.get(13).getRightType()).isEqualTo(RightType.INTERACTIVE_SET_DELETION_MARK_PREDEFINED_DATA);
    assertThat(catalogRights.get(14).getRightType()).isEqualTo(RightType.INTERACTIVE_CLEAR_DELETION_MARK_PREDEFINED_DATA);
    assertThat(catalogRights.get(15).getRightType()).isEqualTo(RightType.INTERACTIVE_DELETE_MARKED_PREDEFINED_DATA);
  }
}
