/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2020
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

import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.metadata.additional.AttributeType;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class TabularSection extends MDOAttribute {

  /**
   * Реквизиты табличной части
   */
  @NonNull
  @XStreamImplicit
  List<MDOAttribute> attributes = Collections.emptyList();

  public TabularSection(DesignerMDO designerMDO) {
    super(designerMDO);
    List<MDOAttribute> computedAttributes = new ArrayList<>();
    designerMDO.getChildObjects().getAttributes().forEach(
      designerMDOAttribute -> computedAttributes.add(new Attribute(designerMDOAttribute)));
    setAttributes(computedAttributes);
  }

  @Override
  public AttributeType getAttributeType() {
    return AttributeType.TABULAR_SECTION;
  }

}
