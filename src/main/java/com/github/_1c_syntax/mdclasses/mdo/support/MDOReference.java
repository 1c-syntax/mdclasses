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
package com.github._1c_syntax.mdclasses.mdo.support;

import com.github._1c_syntax.mdclasses.mdo.AbstractMDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Класс-ссылка на объект в формате ВидОбъектаМетаданных.ИмяОбъекта
 */
@Data
@EqualsAndHashCode(of = {"mdoRef"})
@ToString(of = {"mdoRef"})
public class MDOReference {

  /**
   * Тип объекта метаданных
   */
  private MDOType type;

  /**
   * Строковое представление ссылки
   */
  private String mdoRef;

  /**
   * Строковое представление ссылки на русском языке
   */
  private String mdoRefRu;

  public MDOReference(AbstractMDO mdo) {
    type = mdo.getType();
    mdoRef = mdo.getMetadataName() + "." + mdo.getName();
    mdoRefRu = mdo.getMetadataNameRu() + "." + mdo.getName();
  }

  /**
   * Создает ссылку для дочерних объектов
   *
   * @param mdo    - Объект метаданных
   * @param parent - Родительский объект
   */
  public MDOReference(AbstractMDO mdo, AbstractMDO parent) {
    this(mdo);
    if (parent.getMdoReference() != null) {
      mdoRef = parent.getMdoReference().getMdoRef() + "." + mdoRef;
      mdoRefRu = parent.getMdoReference().getMdoRefRu() + "." + mdoRefRu;
    }
  }
}
