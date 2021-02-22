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

import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerXRItems;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.unmarshal.annotation.TypeAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@TypeAlias(edtName = "mdclass:Document", designerName = "Document", useDesignerWrapper = true)
public class Document extends MDObjectComplex {
  /**
   * Список связанных регистров (где документ является регистратором)
   */
  @XStreamImplicit
  private List<String> registerRecords = Collections.emptyList();

  public Document(DesignerMDO designerMDO) {
    super(designerMDO);
    // registerRecords
    Optional.ofNullable(designerMDO.getProperties().getRegisterRecords())
      .map(DesignerXRItems::getItems)
      .ifPresent(this::setRegisterRecords);
  }

  @Override
  public MDOType getType() {
    return MDOType.DOCUMENT;
  }

}
