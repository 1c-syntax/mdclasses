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

import com.github._1c_syntax.bsl.mdo.support.ContentStorage;
import com.github._1c_syntax.bsl.mdo.support.DataSeparation;
import com.github._1c_syntax.bsl.mdo.support.IndexingType;
import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.mdo.attributes.AbstractMDOAttribute;
import com.github._1c_syntax.mdclasses.mdo.attributes.CommonAttribute;
import com.github._1c_syntax.mdclasses.mdo.metadata.Metadata;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.utils.TransformationUtils;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Metadata(
  type = MDOType.COMMON_ATTRIBUTE,
  name = "CommonAttribute",
  nameRu = "ОбщийРеквизит",
  groupName = "CommonAttributes",
  groupNameRu = "ОбщиеРеквизиты"
)
public class MDCommonAttribute extends AbstractMDObjectBase {

  /**
   * Признак автоиспользования общего реквизита
   */
  private UseMode autoUse = UseMode.DONT_USE;
  /**
   * Признак использования общего реквизита как разделителя данных
   */
  private DataSeparation dataSeparation = DataSeparation.SEPARATE;

  /**
   * Список объектов, использующих общий реквизит
   */
  private List<AbstractMDObjectComplex> using = Collections.emptyList();

  /**
   * Объекты, использующие общий реквизит, и варианты использования
   */
  @Getter(AccessLevel.PRIVATE)
  @XStreamImplicit
  private List<UseContent> content = Collections.emptyList();

  /**
   * Ссылка на атрибут
   */
  @Setter(AccessLevel.NONE)
  private CommonAttribute commonAttribute;

  /**
   * Режим пароля. Только для общих реквизитов с типом с типом `Строка`
   */
  private boolean passwordMode;

  /**
   * Вариант индексирования реквизита
   */
  private IndexingType indexing = IndexingType.DONT_INDEX;

  public MDCommonAttribute(DesignerMDO designerMDO) {
    super(designerMDO);
    autoUse = designerMDO.getProperties().getAutoUse();
    dataSeparation = designerMDO.getProperties().getDataSeparation();
    var designerContent = new ArrayList<>(content);
    designerMDO.getProperties().getContent().getItems().forEach(
      metadataItem -> designerContent.add(new UseContent(metadataItem.getMetadata(), metadataItem.getUse()))
    );
    setContent(designerContent);
    setPasswordMode(designerMDO.getProperties().isPasswordMode());
    indexing = designerMDO.getProperties().getIndexing();
  }

  protected void linkUsing(Map<String, AbstractMDObjectBase> allMDO) {
    // todo переделать на заполнение в configuration
//    if (content.isEmpty()) {
//      return;
//    }
//    using = new ArrayList<>();
//    content.forEach((UseContent useContent) -> {
//      var mdo = allMDO.get(useContent.getMetadata());
//      if (mdo instanceof AbstractMDObjectComplex) {
//        var mdoComplex = (AbstractMDObjectComplex) mdo;
//        mdoComplex.addAttribute(getAttribute());
//        using.add(mdoComplex);
//      }
//    });
  }

  private AbstractMDOAttribute getAttribute() {
    if (commonAttribute == null) {
      commonAttribute = new CommonAttribute(this);
    }
    return commonAttribute;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  protected static class UseContent {
    private String metadata = "";
    private UseMode use = UseMode.USE;
  }

  @Override
  public Object buildMDObject() {
    builder = super.buildMDObject();
    TransformationUtils.setValue(builder, "autoUse", autoUse);
    TransformationUtils.setValue(builder, "dataSeparation", dataSeparation);
    TransformationUtils.setValue(builder, "passwordMode", passwordMode);
    TransformationUtils.setValue(builder, "indexing", indexing);
    TransformationUtils.setValue(builder, "using", new ContentStorage(using.stream()
      .map(AbstractMDO::getMdoReference)
      .map(mdoRef -> MdoReference.create(mdoRef.getType(), mdoRef.getMdoRef(), mdoRef.getMdoRefRu()))
      .collect(Collectors.toList())));

    return builder;
  }
}
