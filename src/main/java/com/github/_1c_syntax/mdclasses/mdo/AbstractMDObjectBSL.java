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

import com.github._1c_syntax.mdclasses.mdo.support.MDOModule;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.mdo.support.ModuleType;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.utils.MDOPathUtils;
import com.github._1c_syntax.mdclasses.utils.MDOUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Базовый класс объектов метаданных, имеющих модули с исходным кодом
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public abstract class AbstractMDObjectBSL extends AbstractMDObjectBase implements MDOHasModule {

  /**
   * Список модулей объекта
   */
  private List<MDOModule> modules = Collections.emptyList();

  protected AbstractMDObjectBSL(DesignerMDO designerMDO) {
    super(designerMDO);
  }

  @Override
  public void supplement() {
    super.supplement();
    MDOPathUtils.getMDOTypeFolderByMDOPath(path, getType()).ifPresent(this::computeAndSetModules);
  }

  @Override
  public void supplement(AbstractMDObjectBase parent) {
    super.supplement(parent);
    MDOPathUtils.getMDOTypeFolderByMDOPath(parent.getPath(), parent.getType())
      .flatMap(folder -> MDOPathUtils.getChildrenFolder(parent.getName(), folder, getType()))
      .ifPresent(this::computeAndSetModules);
  }

  private void computeAndSetModules(Path folder) {
    var moduleTypes = MDOUtils.getModuleTypesForMdoTypes().getOrDefault(getType(), Collections.emptySet());
    if (moduleTypes.isEmpty()) {
      return;
    }

    var configurationSource = MDOUtils.getConfigurationSourceByMDOPath(path);
    var mdoName = (getType() == MDOType.CONFIGURATION) ? "" : getName();
    List<MDOModule> mdoModules = new ArrayList<>();
    moduleTypes.forEach((ModuleType moduleType) ->
      MDOPathUtils.getModulePath(configurationSource, folder, mdoName, moduleType)
        .ifPresent((Path modulePath) -> {
          if (modulePath.toFile().exists()) {
            mdoModules.add(new MDOModule(moduleType, modulePath.toUri(), this));
          }
        }));
    setModules(mdoModules);
  }
}
