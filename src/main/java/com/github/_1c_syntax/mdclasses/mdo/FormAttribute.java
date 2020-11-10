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

import com.github._1c_syntax.mdclasses.mdo.wrapper.form.DesignerAttribute;
import com.github._1c_syntax.mdclasses.mdo.wrapper.form.DesignerColumn;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(of = {"id", "name"})
@NoArgsConstructor
public class FormAttribute {
  /**
   * Имя реквизита
   */
  private String name;
  /**
   * Идентификатор реквизита, в конфигураторе не отображается.
   */
  private int id;
  // TODO: valueType
  /**
   * Признак, что реквизит является основным для формы
   */
  private boolean main = false;
  /**
   * Подчиненные реквизиты, например колонки в табличной части
   */
  @XStreamAlias("columns")
  @XStreamImplicit
  private List<FormAttribute> children = new ArrayList<>();

  /**
   * Конструктор модели для реквизита формата конфигуратора
   *
   * @param designerAttribute - модель формата конфигуратора
   */
  public FormAttribute(DesignerAttribute designerAttribute) {
    setName(designerAttribute.getName());
    setId(designerAttribute.getId());
    setMain(designerAttribute.isMain());
    if (designerAttribute.getDesignerColumns() != null) {
      designerAttribute.getDesignerColumns().getChildren()
        .forEach(designerColumn -> children.add(new FormAttribute(designerColumn)));
    }
  }

  /**
   * Конструктор модели для колонки реквизита формата конфигуратора
   *
   * @param designerColumn - модель формата конфигуратора
   */
  public FormAttribute(DesignerColumn designerColumn) {
    setName(designerColumn.getName());
    setId(designerColumn.getId());
  }
}
