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
import com.github._1c_syntax.mdclasses.metadata.additional.MDOReference;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Базовый класс всех объектов 1С. Необходимо гарантировать отсутствие null значений в полях
 */
@Data
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
@NoArgsConstructor
public class MDObjectBase implements MDOExtensions {

  /**
   * уникальный идентификатор объекта
   */
  @NonNull
  @XStreamAsAttribute
  String uuid = "";

  /**
   * Имя объекта
   */
  @NonNull
  String name = "";

  /**
   * Строка с комментарием объекта
   */
  @NonNull
  String comment = "";

  /**
   * MDO-Ссылка на объект
   */
  MDOReference mdoReference;

  /**
   * Список подсистем, в состав которых входит объект
   */
  @NonNull
  List<Subsystem> includedSubsystems = Collections.emptyList();

  /**
   * Используется для заполнения объекта на основании информации формата конфигуратора
   *
   * @param designerMDO - Служебный объект, содержащий данные в формате конфигуратора.
   */
  public MDObjectBase(DesignerMDO designerMDO) {
    uuid = designerMDO.getUuid();
    name = designerMDO.getProperties().getName();
    comment = designerMDO.getProperties().getComment();
  }

  @Override
  public @NonNull MDOType getType() {
    return MDOType.UNKNOWN;
  }

  /**
   * Для добавления ссылки на подсистему, в которую включен объект
   *
   * @param subsystem - Подсистема, в которую включен объект
   */
  public void addIncludedSubsystem(Subsystem subsystem) {
    if (includedSubsystems.equals(Collections.emptyList())) {
      includedSubsystems = new ArrayList<>();
    }
    includedSubsystems.add(subsystem);
  }
}
