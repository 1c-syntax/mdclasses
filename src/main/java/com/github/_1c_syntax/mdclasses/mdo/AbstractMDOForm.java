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

import com.github._1c_syntax.mdclasses.mdo.children.form.FormData;
import com.github._1c_syntax.mdclasses.mdo.support.FormType;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import com.github._1c_syntax.mdclasses.utils.MDOPathUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.nio.file.Path;

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
  private FormData data;

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
}
