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
package com.github._1c_syntax.mdclasses.mdo.children;

import com.github._1c_syntax.bsl.mdo.children.ObjectTemplate;
import com.github._1c_syntax.bsl.mdo.support.TemplateData;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.MDOTemplate;
import com.github._1c_syntax.mdclasses.mdo.metadata.Metadata;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.utils.MDOPathUtils;
import com.github._1c_syntax.mdclasses.utils.TransformationUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.nio.file.Path;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Metadata(
  type = MDOType.TEMPLATE,
  name = "Template",
  nameRu = "Макет",
  groupName = "Templates",
  groupNameRu = "Макеты"
)
public class Template extends AbstractMDObjectBase implements MDOTemplate {
  /**
   * Тип макета. Например, `ТабличныйДокумент`.
   */
  @XStreamAlias("templateType")
  private TemplateType templateType = TemplateType.SPREADSHEET_DOCUMENT;
  /**
   * Содержимое макета. Например, Схема компоновки данных
   */
  private TemplateData<?> templateData;

  /**
   * Путь к самому файлу макета
   */
  private Path templateDataPath;

  public Template(DesignerMDO designerMDO) {
    super(designerMDO);
    setTemplateType(designerMDO.getProperties().getTemplateType());
  }

  @Override
  public void supplement(AbstractMDObjectBase parent) {
    super.supplement(parent);
    templateDataPath = MDOPathUtils.getTemplateDataPath(this);
    templateData = TemplateData.create(templateType, templateDataPath);
  }

  @Override
  public Object buildMDObject() {
    setBuilder(ObjectTemplate.builder());
    var builder = super.buildMDObject();
    TransformationUtils.setValue(builder, "templateType", templateType);
    TransformationUtils.setValue(builder, "templateData", templateData);
    TransformationUtils.setValue(builder, "templateDataPath", templateDataPath);
    return builder;
  }
}
