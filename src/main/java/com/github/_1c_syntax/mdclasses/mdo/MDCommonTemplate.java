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

import com.github._1c_syntax.bsl.mdo.Template;
import com.github._1c_syntax.bsl.mdo.storage.EmptyTemplateData;
import com.github._1c_syntax.bsl.mdo.storage.TemplateData;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.reader.MDOReader;
import com.github._1c_syntax.bsl.reader.designer.DesignerPaths;
import com.github._1c_syntax.bsl.reader.designer.wrapper.DesignerMDO;
import com.github._1c_syntax.bsl.reader.edt.EDTPaths;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.mdo.metadata.Metadata;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
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
@Slf4j
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

  public MDCommonTemplate(DesignerMDO designerMDO) {
    super(designerMDO);
    setTemplateType(designerMDO.getProperties().getTemplateType());
  }

  @Override
  public void supplement() {
    super.supplement();
    templateData = readTemplateData(path, name, getMdoType(), templateType);
  }

  @Nullable
  public Path getTemplateDataPath() {
    return templateData.getDataPath();
  }

  // TODO убрать копипасту
  private static TemplateData readTemplateData(@NonNull Path mdoPath,
                                               @NonNull String mdoName,
                                               @NonNull MDOType mdoType,
                                               @NonNull TemplateType templateType) {
    if (templateType == TemplateType.DATA_COMPOSITION_SCHEME) {
      var path = getTemplateDataPath(mdoPath, mdoName, mdoType);
      var data = MDOReader.read(path);
      if (data instanceof TemplateData) {
        return (TemplateData) data;
      } else if (data == null) {
        LOGGER.warn("Missing file " + path);
        return EmptyTemplateData.getEmpty();
      }
      throw new IllegalArgumentException("Wrong template data file " + path);
    }
    return EmptyTemplateData.getEmpty();
  }

  private static Path getTemplateDataPath(Path mdoPath, String mdoName, MDOType mdoType) {
    if (mdoPath.toString().endsWith(".xml")) {
      return DesignerPaths.templateDataPath(mdoPath, mdoName);
    } else {
      return EDTPaths.templateDataPath(mdoPath, mdoName, mdoType);
    }
  }

}
