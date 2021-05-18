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

import com.github._1c_syntax.mdclasses.mdo.metadata.Metadata;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * ВНИМАНИЕ!
 * Для формата EDT хранится в файле Configuration.mdo, т.е. отдельного файла нет
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Metadata(
  type = MDOType.LANGUAGE,
  name = "Language",
  nameRu = "Язык",
  groupName = "Languages",
  groupNameRu = "Языки"
)
public class MDLanguage extends AbstractMDObjectBase {

  public static final MDLanguage ENGLISH = new MDLanguage("English", "en");
  public static final MDLanguage RUSSIAN = new MDLanguage("Русский", "ru");

  /**
   * Код языка
   */
  private String languageCode = "";

  public MDLanguage(DesignerMDO designerMDO) {
    super(designerMDO);
    languageCode = designerMDO.getProperties().getLanguageCode();
  }

  private MDLanguage(String name, String languageCode) {
    this.name = name;
    this.languageCode = languageCode;
  }
}
