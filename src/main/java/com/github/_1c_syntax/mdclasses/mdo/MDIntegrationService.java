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

import com.github._1c_syntax.mdclasses.mdo.children.IntegrationServiceChannel;
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
  type = MDOType.INTEGRATION_SERVICE,
  name = "IntegrationService",
  nameRu = "СервисИнтеграции",
  groupName = "IntegrationServices",
  groupNameRu = "СервисыИнтеграции"
)
public class MDIntegrationService extends AbstractMDObjectBSL implements MDOHasChildren {

  /**
   * Адрес внешнего сервиса интеграции
   */
  private String externalIntegrationServiceAddress = "";

  /**
   * Каналы сервиса интеграции
   */
  @XStreamImplicit(itemFieldName = "integrationServiceChannels")
  private List<IntegrationServiceChannel> integrationChannels = Collections.emptyList();

  /**
   * Закэшированные данные о дочерних элементах
   */
  private Set<AbstractMDObjectBase> children;

  public MDIntegrationService(DesignerMDO designerMDO) {
    super(designerMDO);
    var channels = new ArrayList<>(integrationChannels);
    designerMDO.getChildObjects().getIntegrationChannels().forEach((DesignerMDO channel) ->
      channels.add(new IntegrationServiceChannel(channel)));
    integrationChannels = channels;
    externalIntegrationServiceAddress = designerMDO.getProperties().getExternalIntegrationServiceAddress();
  }

  @Override
  public void supplement() {
    super.supplement();
    integrationChannels.forEach(child -> child.supplement(this));
  }

  @Override
  public Set<AbstractMDObjectBase> getChildren() {
    if (children == null) {
      children = new HashSet<>(integrationChannels);
    }
    return Collections.unmodifiableSet(children);
  }
}
