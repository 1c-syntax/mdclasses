/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2022
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
package com.github._1c_syntax.mdclasses.mdo.children.form;

import com.github._1c_syntax.mdclasses.unmarshal.converters.ExtInfoConverter;
import com.github._1c_syntax.mdclasses.unmarshal.converters.StringConverterIntern;
import com.github._1c_syntax.mdclasses.unmarshal.converters.ValueTypeConverter;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerAttribute;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerColumn;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString(of = {"id", "name"})
@NoArgsConstructor
public class FormAttribute {
  /**
   * Имя реквизита
   */
  @XStreamConverter(StringConverterIntern.class)
  private String name;
  /**
   * Идентификатор реквизита, в конфигураторе не отображается.
   */
  private int id;

  /**
   * Список типов значений
   */
  @XStreamConverter(value = ValueTypeConverter.class)
  @XStreamAlias("valueType")
  private List<String> valueTypes = Collections.emptyList();

  /**
   * Признак, что реквизит является основным для формы
   */
  private boolean main;
  /**
   * Подчиненные реквизиты, например колонки в табличной части
   */
  @XStreamAlias("columns")
  @XStreamImplicit
  private List<FormAttribute> children = new ArrayList<>();

  /**
   * Дополнительная информация о реквизите
   */
  @XStreamConverter(value = ExtInfoConverter.class)
  private ExtInfo extInfo;

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
    if (designerAttribute.getType() != null) {
      var list = designerAttribute.getType().getTypes().stream()
        .map(value -> value.replace("cfg:", "")) // TODO: чтение типов реквизитов
        .map(String::intern)
        .collect(Collectors.toList());
      setValueTypes(list);
    }
    ExtInfo newExtInfo;
    if (designerAttribute.getSetting() == null) {
      newExtInfo = new ExtInfo();
    } else {
      newExtInfo = new DynamicListExtInfo(designerAttribute.getSetting());
    }
    setExtInfo(newExtInfo);
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
