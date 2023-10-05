/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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

import com.github._1c_syntax.bsl.mdo.Template;
import com.github._1c_syntax.bsl.mdo.storage.DataCompositionSchema;
import com.github._1c_syntax.bsl.mdo.storage.EmptyTemplateData;
import com.github._1c_syntax.bsl.mdo.storage.TemplateData;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.mdo.metadata.Metadata;
import com.github._1c_syntax.mdclasses.unmarshal.XStreamFactory;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.utils.MDOPathUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.nio.file.Files;
import java.nio.file.Path;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Metadata(
  type = MDOType.COMMON_TEMPLATE,
  name = "CommonTemplate",
  nameRu = "ОбщийМакет",
  groupName = "CommonTemplates",
  groupNameRu = "ОбщиеМакеты"
)
public class MDCommonTemplate extends AbstractMDObjectBase implements Template {
  /**
   * Тип макета. Например, `ТабличныйДокумент`.
   */
  @XStreamAlias("templateType")
  private TemplateType templateType = TemplateType.SPREADSHEET_DOCUMENT;

  /**
   * Содержимое макета. Например, Схема компоновки данных
   */
  private TemplateData templateData = EmptyTemplateData.getEmpty();

  /**
   * Путь к самому файлу макета
   */
  private Path templateDataPath;

  public MDCommonTemplate(DesignerMDO designerMDO) {
    super(designerMDO);
    setTemplateType(designerMDO.getProperties().getTemplateType());
  }

  @Override
  public void supplement() {
    super.supplement();
    templateDataPath = MDOPathUtils.getTemplateDataPath(this);
    if (templateType == TemplateType.DATA_COMPOSITION_SCHEME && Files.exists(templateDataPath)) {
      templateData = (DataCompositionSchema) XStreamFactory.fromXML(templateDataPath.toFile());
    }
  }
}
