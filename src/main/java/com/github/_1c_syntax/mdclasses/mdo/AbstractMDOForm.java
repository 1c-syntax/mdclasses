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

import com.github._1c_syntax.bsl.mdo.form.FormAttribute;
import com.github._1c_syntax.bsl.mdo.form.FormCommand;
import com.github._1c_syntax.bsl.mdo.form.FormItemCreator;
import com.github._1c_syntax.bsl.mdo.form.FormData;
import com.github._1c_syntax.bsl.mdo.form.item.BaseFormItem;
import com.github._1c_syntax.bsl.mdo.support.FormType;
import com.github._1c_syntax.mdclasses.mdo.children.form.EdtFormData;
import com.github._1c_syntax.mdclasses.mdo.children.form.FormItem;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import com.github._1c_syntax.mdclasses.utils.MDOPathUtils;
import com.github._1c_syntax.mdclasses.utils.TransformationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Базовый класс объектов форм
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public abstract class AbstractMDOForm extends AbstractMDObjectBSL {

  /**
   * Тип формы
   */
  private FormType formType = FormType.MANAGED;

  /**
   * Данные формы. Читается из отдельного файла
   * и предоставляет информацию о:
   * + список элементов формы
   * + список обработчиков формы
   * + список реквизитов формы
   */
  private EdtFormData data;

  /**
   * Путь к файлу с данными формы
   */
  private Path formDataPath;

  protected AbstractMDOForm(DesignerMDO designerMDO) {
    super(designerMDO);
    formType = designerMDO.getProperties().getFormType();
  }

  @Override
  public void supplement() {
    super.supplement();
    computeFormData();
  }

  @Override
  public void supplement(AbstractMDObjectBase parent) {
    super.supplement(parent);
    computeFormData();
  }

  private void computeFormData() {
    formDataPath = MDOPathUtils.getFormDataPath(this);
    MDOFactory.readFormData(formDataPath).ifPresent(this::setData);
  }

  @Override
  public Object buildMDObject() {
    TransformationUtils.setValue(builder, "formType", formType);

    FormData formData;
    if (getData() != null) {

      var children = createNewChildren(getData(), getData().getChildren());
      List<BaseFormItem> plainChildren = new ArrayList<>();
      recursiveFillPlainChildren(children, plainChildren);

      var attributes = getData().getAttributes().stream()
        .map(AbstractMDOForm::newFormAttribute).collect(Collectors.toUnmodifiableList());

      var commands = getData().getCommands().stream()
        .map(formCommand -> {
          var builder = FormCommand.builder();
          builder.name(formCommand.getName());
          builder.id(formCommand.getId());
          builder.action(formCommand.getAction());
          return builder.build();
        }).collect(Collectors.toUnmodifiableList());

      var dataBuilder = FormData.builder()
        .children(children)
        .plainChildren(plainChildren)
        .attributes(attributes)
        .commands(commands);

      formData = dataBuilder.build();

    } else {
      formData = FormData.EMPTY;
    }

    TransformationUtils.setValue(builder, "data", formData);

    return super.buildMDObject();
  }

  private static List<BaseFormItem> createNewChildren(EdtFormData formData, List<FormItem> items) {
    List<BaseFormItem> newItems = new ArrayList<>();

    // создадим форму
    var item = FormItemCreator.createFormItem(formData);
    newItems.add(item);

    for (FormItem formItem : items) {
      item.getChildren().add(newItem(formItem));
    }

    return newItems;
  }

  private static void processChildItems(FormItem formItem, List<BaseFormItem> children) {
    if (formItem.getChildren().isEmpty()) {
      return;
    }

    formItem.getChildren().forEach(item -> {
      var newItem = AbstractMDOForm.newItem(item);
      children.add(newItem);
    });
  }

  private static BaseFormItem newItem(FormItem formItem) {
    var item = FormItemCreator.createByType(formItem, formItem.getType());

    if (formItem.getChildren().isEmpty()) {
      formItem.setChildren(Collections.emptyList());
    } else {
      processChildItems(formItem, item.getChildren());
    }

    return item;
  }

  private static void recursiveFillPlainChildren(List<BaseFormItem> children, List<BaseFormItem> plainChildren) {
    children.forEach(baseFormItem -> {
      plainChildren.add(baseFormItem);
      recursiveFillPlainChildren(baseFormItem.getChildren(), plainChildren);
    });
  }

  private static FormAttribute newFormAttribute(com.github._1c_syntax.mdclasses.mdo.children.form.FormAttribute formAttribute) {
    var builder = FormAttribute.builder();
    builder.name(formAttribute.getName());
    builder.id(formAttribute.getId());
    builder.valueTypes(formAttribute.getValueTypes());
    builder.main(formAttribute.isMain());

    var children = formAttribute.getChildren().stream()
      .map(AbstractMDOForm::newFormAttribute)
      .collect(Collectors.toList());
    builder.children(children);

    return builder.build();
  }

}
