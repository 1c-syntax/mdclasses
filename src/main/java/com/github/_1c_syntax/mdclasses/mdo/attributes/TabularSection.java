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
package com.github._1c_syntax.mdclasses.mdo.attributes;

import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.MDOHasChildren;
import com.github._1c_syntax.mdclasses.mdo.metadata.AttributeMetadata;
import com.github._1c_syntax.mdclasses.mdo.metadata.AttributeType;
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
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AttributeMetadata(
  type = AttributeType.TABULAR_SECTION,
  name = "TabularSection",
  fieldNameEDT = "tabularSections"
)
public class TabularSection extends AbstractMDOAttribute implements MDOHasChildren {

  /**
   * Реквизиты табличной части
   */
  @XStreamImplicit(itemFieldName = "attributes")
  private List<AbstractMDOAttribute> attributes = Collections.emptyList();

  /**
   * Закэшированные данные о дочерних элементах
   */
  private Set<AbstractMDObjectBase> children;

  public TabularSection(DesignerMDO designerMDO) {
    super(designerMDO);
    List<AbstractMDOAttribute> computedAttributes = new ArrayList<>();
    designerMDO.getChildObjects().getAttributes().forEach(
      designerMDOAttribute -> computedAttributes.add(new Attribute(designerMDOAttribute)));
    setAttributes(computedAttributes);
  }

  @Override
  public void supplement(AbstractMDObjectBase parent) {
    super.supplement(parent);
    attributes.forEach(child -> child.supplement(this));
  }

  @Override
  public Set<AbstractMDObjectBase> getChildren() {
    if (children == null) {
      children = new HashSet<>(attributes);
    }
    return Collections.unmodifiableSet(children);
  }
}
