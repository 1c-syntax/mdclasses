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

import com.github._1c_syntax.mdclasses.mdo.children.XDTOPackageData;
import com.github._1c_syntax.mdclasses.mdo.metadata.Metadata;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import com.github._1c_syntax.mdclasses.utils.MDOPathUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.nio.file.Path;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Metadata(
  type = MDOType.XDTO_PACKAGE,
  name = "XDTOPackage",
  nameRu = "ПакетXDTO",
  groupName = "XDTOPackages",
  groupNameRu = "ПакетыXDTO"
)
public class MDXdtoPackage extends AbstractMDObjectBase {

  /**
   * Пространство имен xdto-пакета
   */
  private String namespace = "";

  /**
   * Путь к файлу с данными xsd-схемы
   */
  private Path packageDataPath;

  /**
   * Содержимое xsd-схемы пакета
   */
  private XDTOPackageData data;

  public MDXdtoPackage(DesignerMDO designerMDO) {
    super(designerMDO);
    namespace = designerMDO.getProperties().getNamespace();
  }

  @Override
  public void supplement() {
    super.supplement();
    packageDataPath = MDOPathUtils.getPackageDataPath(this);
    MDOFactory.readXDTOPackageData(packageDataPath).ifPresent(this::setData);
  }
}
