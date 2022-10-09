/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2022
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

import com.github._1c_syntax.bsl.mdo.Form;
import com.github._1c_syntax.bsl.mdo.support.FormType;
import com.github._1c_syntax.bsl.reader.MDOReader;
import com.github._1c_syntax.bsl.reader.designer.DesignerPaths;
import com.github._1c_syntax.bsl.reader.designer.wrapper.DesignerMDO;
import com.github._1c_syntax.bsl.reader.edt.EDTPaths;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.mdo.children.form.FormData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.nio.file.Path;

/**
 * Базовый класс объектов форм
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Slf4j
public abstract class AbstractMDOForm extends AbstractMDObjectBSL implements Form {

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

  protected AbstractMDOForm(DesignerMDO designerMDO) {
    super(designerMDO);
    formType = designerMDO.getProperties().getFormType();
  }

  @Override
  public void supplement() {
    super.supplement();
    data = readFormData(path, name, getMdoType());
  }

  @Override
  public void supplement(AbstractMDObjectBase parent) {
    super.supplement(parent);
    data = readFormData(path, name, getMdoType());
  }

  @Nullable
  public Path getFormDataPath() {
    return data.getDataPath();
  }

  private static FormData readFormData(@NonNull Path mdoPath,
                                       @NonNull String mdoName,
                                       @NonNull MDOType mdoType) {
    var path = getFormDataPath(mdoPath, mdoName, mdoType);
    return readFormData(path);
  }

  public static FormData readFormData(Path path) {
    var data = MDOReader.read(path);
    if (data instanceof FormData) {
      ((FormData) data).setDataPath(path);
      ((FormData) data).fillPlainChildren(((FormData) data).getChildren());
      return (FormData) data;
    } else if (data == null) {
      LOGGER.warn("Missing file " + path);
      return null;
    }
    throw new IllegalArgumentException("Wrong form data file " + path);
  }

  private static Path getFormDataPath(Path mdoPath, String mdoName, MDOType mdoType) {
    if (mdoPath.toString().endsWith(".xml")) {
      return DesignerPaths.formDataPath(mdoPath, mdoName);
    } else {
      return EDTPaths.formDataPath(mdoPath, mdoType, mdoName);
    }
  }

}
