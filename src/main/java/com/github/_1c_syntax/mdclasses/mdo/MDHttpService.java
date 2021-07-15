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

import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.mdo.children.HTTPServiceURLTemplate;
import com.github._1c_syntax.mdclasses.mdo.metadata.Metadata;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.utils.TransformationUtils;
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
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Metadata(
  type = MDOType.HTTP_SERVICE,
  name = "HTTPService",
  nameRu = "HTTPСервис",
  groupName = "HTTPServices",
  groupNameRu = "HTTPСервисы"
)
public class MDHttpService extends AbstractMDObjectBSL implements MDOHasChildren {

  /**
   * Шаблоны URL HTTP-сервиса
   */
  @XStreamImplicit
  private List<HTTPServiceURLTemplate> urlTemplates = Collections.emptyList();

  /**
   * Закэшированные данные о дочерних элементах
   */
  private Set<AbstractMDObjectBase> children;

  public MDHttpService(DesignerMDO designerMDO) {
    super(designerMDO);
    var templates = new ArrayList<>(urlTemplates);
    designerMDO.getChildObjects().getHttpUrlTemplates().forEach((DesignerMDO urlTemplate) ->
      templates.add(new HTTPServiceURLTemplate(urlTemplate))
    );
    urlTemplates = templates;
  }

  @Override
  public void supplement() {
    super.supplement();
    urlTemplates.forEach(child -> child.supplement(this));
  }

  @Override
  public Set<AbstractMDObjectBase> getChildren() {
    if (children == null) {
      children = new HashSet<>(urlTemplates);
      urlTemplates.forEach(mdo -> children.addAll(mdo.getChildren()));
    }
    return Collections.unmodifiableSet(children);
  }

  @Override
  public Object buildMDObject() {
    builder = super.buildMDObject();
    TransformationUtils.setValue(builder, "urlTemplates",
      urlTemplates.stream().map(HTTPServiceURLTemplate::buildMDObject)
        .map(MDClasses::build)
        .collect(Collectors.toList()));
    return builder;
  }
}
