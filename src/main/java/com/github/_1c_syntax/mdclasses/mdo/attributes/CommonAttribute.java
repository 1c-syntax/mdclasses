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

import com.github._1c_syntax.mdclasses.mdo.MDCommonAttribute;
import com.github._1c_syntax.mdclasses.mdo.metadata.AttributeMetadata;
import com.github._1c_syntax.mdclasses.mdo.metadata.AttributeType;
import com.github._1c_syntax.mdclasses.mdo.support.AttributeKind;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@AttributeMetadata(
  type = AttributeType.COMMON_ATTRIBUTE,
  name = "",
  fieldNameEDT = ""
)
public class CommonAttribute extends AbstractMDOAttribute {
  MDCommonAttribute link;

  public CommonAttribute(MDCommonAttribute mdCommonAttribute) {
    this.link = mdCommonAttribute;
    this.comment = mdCommonAttribute.getComment();
    this.name = mdCommonAttribute.getName();
    this.mdoReference = mdCommonAttribute.getMdoReference();
    this.objectBelonging = mdCommonAttribute.getObjectBelonging();
    this.path = mdCommonAttribute.getPath();
    this.synonyms = mdCommonAttribute.getSynonyms();
    this.uuid = mdCommonAttribute.getUuid();
    setKind(AttributeKind.COMMON);
  }
}
