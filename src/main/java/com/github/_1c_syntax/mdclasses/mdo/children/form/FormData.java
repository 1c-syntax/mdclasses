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
package com.github._1c_syntax.mdclasses.mdo.children.form;

import com.github._1c_syntax.mdclasses.mdo.support.DataPath;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerChildItems;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerForm;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerFormItem;
import com.github._1c_syntax.mdclasses.utils.MDOUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class FormData {
  /**
   * "Плоский" список элементов
   */
  private List<FormItem> plainChildren = new ArrayList<>();
  /**
   * Дерево элементов
   */
  @XStreamImplicit
  private List<FormItem> children = new ArrayList<>();
  /**
   * Обработчики событий
   */
  @XStreamImplicit
  private List<FormHandlerItem> handlers = Collections.emptyList();
  /**
   * Список реквизитов
   */
  @XStreamImplicit
  private List<FormAttribute> attributes = Collections.emptyList();
  /**
   * Доступность формы
   */
  private boolean enabled = true;
  /**
   * Список команд
   */
  @XStreamAlias("formCommands")
  @XStreamImplicit
  private List<FormCommand> commands = Collections.emptyList();

  // TODO: реализовать командный интерфейс

  /**
   * Конструктор для создания данных формы на основании модели конфигуратора
   *
   * @param designerForm - модель данных формата конфигуратора
   */
  public FormData(DesignerForm designerForm) {
    // элементы формы
    fillChildrenItems(designerForm.getChildItems(), children);
    addDesignerFormItem(designerForm.getAutoCommandBar(), children);

    // события
    handlers = Optional.ofNullable(designerForm.getEvents())
      .map(designerEvents -> designerEvents.getChildren().stream()
        .map(FormHandlerItem::new)
        .collect(Collectors.toList()))
      .orElse(Collections.emptyList());

    // реквизиты
    attributes = Optional.ofNullable(designerForm.getAttributes())
      .map(designerAttributes -> designerAttributes.getChildren().stream()
        .map(FormAttribute::new)
        .collect(Collectors.toList()))
      .orElse(Collections.emptyList());

    // команды
    commands = Optional.ofNullable(designerForm.getCommands())
      .map(designerFormCommands -> designerFormCommands.getChildren().stream()
        .map(FormCommand::new)
        .collect(Collectors.toList()))
      .orElse(Collections.emptyList());

  }

  private static void fillChildrenItems(DesignerChildItems designerChildItems, List<FormItem> list) {
    if (designerChildItems == null) {
      return;
    }
    designerChildItems.getChildren().forEach(formItem -> list.add(newFormItem(formItem)));
  }

  private static FormItem newFormItem(DesignerFormItem designerFormItem) {
    var formItem = new FormItem();
    formItem.setName(designerFormItem.getName());
    formItem.setId(designerFormItem.getId());
    if (designerFormItem.getDataPath() != null) {
      var dataPath = new DataPath(designerFormItem.getDataPath());
      formItem.setDataPath(dataPath);
    }
    formItem.setType(designerFormItem.getType());

    if (designerFormItem.getType().equals(MDOUtils.TYPE_INPUT_FIELD)) {
      var extInfo = new InputFieldExtInfo();
      extInfo.setPasswordMode(designerFormItem.getPasswordMode());
      formItem.setExtInfo(extInfo);
    } else {
      formItem.setExtInfo(new ExtInfo());
    }

    addDesignerFormItem(designerFormItem.getContextMenu(), formItem.getChildren());
    addDesignerFormItem(designerFormItem.getExtendedTooltip(), formItem.getChildren());
    addDesignerFormItem(designerFormItem.getAutoCommandBar(), formItem.getChildren());
    addDesignerFormItem(designerFormItem.getSearchStringAddition(), formItem.getChildren());
    addDesignerFormItem(designerFormItem.getViewStatusAddition(), formItem.getChildren());
    if (designerFormItem.getChildItems() != null) {
      fillChildrenItems(designerFormItem.getChildItems(), formItem.getChildren());
    }

    if (designerFormItem.getEvents() != null) {
      List<FormHandlerItem> handlers = designerFormItem.getEvents().getChildren().stream()
        .map(FormHandlerItem::new)
        .collect(Collectors.toList());
      formItem.setHandlers(handlers);
    }

    return formItem;
  }

  private static void addDesignerFormItem(DesignerFormItem designerFormItem, List<FormItem> children) {
    if (designerFormItem != null) {
      children.add(newFormItem(designerFormItem));
    }
  }

  public void fillPlainChildren(List<FormItem> itemList) {
    itemList.forEach((FormItem formItem) -> {
      plainChildren.add(formItem);
      fillPlainChildren(formItem.getChildren());
    });
  }
}
