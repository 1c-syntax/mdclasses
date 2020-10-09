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

import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerChildItems;
import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerForm;
import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerFormItem;
import com.github._1c_syntax.mdclasses.metadata.additional.DataPath;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class FormData {
  // плоский список дочерних элементов
  private List<FormItem> plainChildren = new ArrayList<>();
  @XStreamImplicit
  private List<FormItem> children = new ArrayList<>();
  @XStreamImplicit
  private List<FormHandlerItem> handlers = Collections.emptyList();
  @XStreamImplicit
  private List<FormAttribute> attributes = Collections.emptyList();
  private String group;
  private String windowOpeningMode;
  private boolean autoTitle;
  private boolean autoUrl;
  private boolean autoFillCheck;
  private boolean allowFormCustomize;
  private boolean showTitle;
  private boolean showCloseButton;
  private boolean enabled;
  // TODO: реализовать командный интерфейс
  // commandInterface
  // -- navigationPanel
  // -- commandBar

  public FormData(DesignerForm designerForm) {
    fillChildrenItems(designerForm.getChildItems(), children);
    addDesignerFormItem(designerForm.getAutoCommandBar(), children);
    fillPlainChildren(children);
  }

  public void fillPlainChildren(List<FormItem> itemList) {
    itemList.forEach(formItem -> {
      plainChildren.add(formItem);
      fillPlainChildren(formItem.getChildren());
    });
  }

  private void fillChildrenItems(DesignerChildItems designerChildItems, List<FormItem> list) {
    if (designerChildItems == null) {
      return;
    }
    designerChildItems.getChildren().forEach(formItem -> list.add(newFormItem(formItem)));
  }

  private FormItem newFormItem(DesignerFormItem designerFormItem) {
    var formItem = new FormItem();
    formItem.setName(designerFormItem.getName());
    formItem.setId(designerFormItem.getId());
    if (designerFormItem.getDataPath() != null) {
      var dataPath = new DataPath(designerFormItem.getDataPath());
      formItem.setDataPath(dataPath);
    }

    addDesignerFormItem(designerFormItem.getContextMenu(), formItem.getChildren());
    addDesignerFormItem(designerFormItem.getExtendedTooltip(), formItem.getChildren());
    addDesignerFormItem(designerFormItem.getAutoCommandBar(), formItem.getChildren());

    if (designerFormItem.getChildItems() != null) {
      fillChildrenItems(designerFormItem.getChildItems(), formItem.getChildren());
    }
    return formItem;
  }

  private void addDesignerFormItem(DesignerFormItem designerFormItem, List<FormItem> children) {
    if (designerFormItem != null) {
      children.add(newFormItem(designerFormItem));
    }
  }

}
