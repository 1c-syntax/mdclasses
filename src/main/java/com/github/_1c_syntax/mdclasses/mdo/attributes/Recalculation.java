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
package com.github._1c_syntax.mdclasses.mdo.attributes;

import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import com.github._1c_syntax.bsl.reader.MDOReader;
import com.github._1c_syntax.bsl.reader.designer.wrapper.DesignerMDO;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.ModuleType;
import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.metadata.AttributeMetadata;
import com.github._1c_syntax.mdclasses.mdo.metadata.AttributeType;
import com.github._1c_syntax.mdclasses.mdo.support.MDOModule;
import com.github._1c_syntax.mdclasses.utils.MDOPathUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AttributeMetadata(
  type = AttributeType.RECALCULATION,
  name = "Recalculation",
  nameRu = "Перерасчет",
  fieldNameEDT = "recalculations"
)
public class Recalculation extends AbstractMDOAttribute implements ModuleOwner {

  List<MDOModule> modules = Collections.emptyList();

  public Recalculation(DesignerMDO designerMDO) {
    super(designerMDO);
  }

  @Override
  public void supplement(AbstractMDObjectBase parent) {
    super.supplement(parent);
    MDOPathUtils.getMDOTypeFolderByMDOPath(parent.getPath(), parent.getMdoType())
      .flatMap(folder -> MDOPathUtils.getChildrenFolder(parent.getName(), folder, getMdoType()))
      .ifPresent(this::computeAndSetModules);
  }

  private void computeAndSetModules(Path folder) {
    var moduleTypes = ModuleType.byMDOType(getMdoType());
    if (moduleTypes.isEmpty()) {
      return;
    }

    // для формата EDT отдельного файла нет
    var configurationSource = (path == null) ?
      ConfigurationSource.EDT
      : MDOReader.getConfigurationSourceByMDOPath(path);

    List<MDOModule> mdoModules = new ArrayList<>();
    moduleTypes.forEach((ModuleType moduleType) ->
      MDOPathUtils.getModulePath(configurationSource, folder, getName(), moduleType)
        .ifPresent((Path modulePath) -> {
          if (modulePath.toFile().exists()) {
            mdoModules.add(new MDOModule(moduleType, modulePath.toUri(), this));
          }
        }));
    setModules(mdoModules);
  }

}
