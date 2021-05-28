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

import com.github._1c_syntax.mdclasses.mdo.children.WEBServiceOperation;
import com.github._1c_syntax.mdclasses.mdo.metadata.Metadata;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Metadata(
  type = MDOType.WEB_SERVICE,
  name = "WebService",
  nameRu = "WebСервис",
  groupName = "WebServices",
  groupNameRu = "WebСервисы"
)
public class MDWebService extends AbstractMDObjectBSL implements MDOHasChildren {

  /**
   * Закэшированные данные о дочерних элементах
   */
  private Set<AbstractMDObjectBase> children;

  /**
   * Операции веб-сервиса
   */
  @XStreamImplicit
  private List<WEBServiceOperation> operations = Collections.emptyList();

  public MDWebService(DesignerMDO designerMDO) {
    super(designerMDO);
    var wsOperations = new ArrayList<>(operations);
    designerMDO.getChildObjects().getOperations().forEach((DesignerMDO wsOperation) ->
      wsOperations.add(new WEBServiceOperation(wsOperation))
    );
    operations = wsOperations;
  }

  @Override
  public void supplement() {
    super.supplement();
    operations.forEach(child -> child.supplement(this));
  }

  @Override
  public Set<AbstractMDObjectBase> getChildren() {
    if (children == null) {
      children = new HashSet<>(operations);
    }
    return Collections.unmodifiableSet(children);
  }
}
