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
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import io.vavr.control.Either;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Subsystem extends MDObjectBase {

  /**
   * Дочерние объекты подсистемы, включает в себя как дочерние подсистемы, так и и другие объекты,
   * включенные в подсистему
   * Для объектов, которые не удалось прочитать (при загрузке конфигурации) хранит только строки
   */
  @XStreamImplicit
  private List<Either<String, MDObjectBase>> children = Collections.emptyList();

  /**
   * Признак "Включать в командный интерфейс"
   */
  private boolean includeInCommandInterface;

  public Subsystem(DesignerMDO designerMDO) {
    super(designerMDO);
    List<Either<String, MDObjectBase>> newChildren = new ArrayList<>();
    designerMDO.getProperties().getContent()
      .getItems().forEach(item -> newChildren.add(Either.left(item)));
    includeInCommandInterface = designerMDO.getProperties().isIncludeInCommandInterface();
    newChildren.addAll(designerMDO.getChildObjects().getChildren());
    setChildren(newChildren);
  }

  @Override
  public MDOType getType() {
    return MDOType.SUBSYSTEM;
  }

}
