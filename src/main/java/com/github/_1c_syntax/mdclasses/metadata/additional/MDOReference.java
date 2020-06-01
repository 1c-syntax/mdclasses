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
package com.github._1c_syntax.mdclasses.metadata.additional;

import com.github._1c_syntax.mdclasses.mdo.MDOAttribute;
import com.github._1c_syntax.mdclasses.mdo.MDObjectBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Класс-ссылка на объект в формате ВидОбъектаМетаднных.ИмяОбъекта
 */
@Data
@EqualsAndHashCode(of = {"mdoRef"})
@ToString(of = {"mdoRef"})
public class MDOReference {

  /**
   * Тип объекта метаданных
   */
  @NonNull
  MDOType type;

  /**
   * Строковое представление ссылки
   */
  @NonNull
  String mdoRef;

  public MDOReference(MDObjectBase mdo) {
    type = mdo.getType();
    mdoRef = getType().getName() + "." + mdo.getName();
  }

  /**
   * Создает ссылку для дочерних объектов
   *
   * @param mdo    - Объект метаданных
   * @param parent - Родительский объект
   */
  public MDOReference(MDObjectBase mdo, MDObjectBase parent) {
    this(mdo);
    if (parent.getMdoReference() != null) {
      mdoRef = parent.getMdoReference().getMdoRef() + "." + mdoRef;
    }
  }

  /**
   * Создает ссылку для атрибутов объекта
   *
   * @param mdo    - Объект-атрибут
   * @param parent - Родительский объект
   */
  public MDOReference(MDOAttribute mdo, MDObjectBase parent) {
    type = mdo.getType();
    mdoRef = mdo.getAttributeType().getClassName() + "." + mdo.getName();
    if (parent.getMdoReference() != null) {
      mdoRef = parent.getMdoReference().getMdoRef() + "." + mdoRef;
    }
  }
}
