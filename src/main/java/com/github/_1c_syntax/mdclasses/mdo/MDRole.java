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

import com.github._1c_syntax.bsl.reader.MDOReader;
import com.github._1c_syntax.bsl.reader.designer.DesignerPaths;
import com.github._1c_syntax.bsl.reader.designer.wrapper.DesignerMDO;
import com.github._1c_syntax.bsl.reader.edt.EDTPaths;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.mdo.metadata.Metadata;
import com.github._1c_syntax.mdclasses.mdo.support.RoleData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.nio.file.Path;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Metadata(
  type = MDOType.ROLE,
  name = "Role",
  nameRu = "Роль",
  groupName = "Roles",
  groupNameRu = "Роли"
)
@Slf4j
public class MDRole extends AbstractMDObjectBase {

  private RoleData roleData;

  public MDRole(DesignerMDO designerMDO) {
    super(designerMDO);
  }

  @Override
  public void supplement() {
    super.supplement();
    roleData = readRoleData(path, name);
  }

  @Nullable
  public Path getRoleDataPath() {
    if (roleData != null) {
      return roleData.getDataPath();
    }
    return null;
  }

  private static RoleData readRoleData(@NonNull Path mdoPath,
                                       @NonNull String mdoName) {
    var path = getRoleDataPath(mdoPath, mdoName);
    var data = MDOReader.read(path);
    if (data instanceof RoleData) {
      ((RoleData) data).setDataPath(path);
      return (RoleData) data;
    } else if (data == null) {
      LOGGER.warn("Missing file " + path);
      return null;
    } else {
      throw new IllegalArgumentException("Wrong role data file " + path);
    }
  }

  private static Path getRoleDataPath(Path mdoPath, String mdoName) {
    if (mdoPath.toString().endsWith(".xml")) {
      return DesignerPaths.roleDataPath(mdoPath, mdoName);
    } else {
      return EDTPaths.roleDataPath(mdoPath);
    }
  }

}
