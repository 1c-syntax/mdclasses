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

import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.mdo.attributes.AbstractMDOAttribute;
import com.github._1c_syntax.mdclasses.mdo.support.LanguageContent;
import com.github._1c_syntax.mdclasses.mdo.support.MDOReference;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.utils.TransformationUtils;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * Базовый класс всех данных 1С. Необходимо гарантировать отсутствие null значений в полях
 */
@Data
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
@NoArgsConstructor
public abstract class AbstractMDO implements MDO {

  protected Object builder;

  /**
   * уникальный идентификатор объекта
   */
  @XStreamAsAttribute
  protected String uuid = "";

  /**
   * Имя объекта
   */
  protected String name = "";

  /**
   * Синонимы объекта
   */
  @XStreamImplicit(itemFieldName = "synonym")
  protected List<LanguageContent> synonyms = Collections.emptyList();

  /**
   * Строка с комментарием объекта
   */
  protected String comment = "";

  /**
   * MDO-Ссылка на объект
   */
  protected MDOReference mdoReference;

  /**
   * Принадлежность объекта конфигурации (собственный или заимствованный)
   */
  protected ObjectBelonging objectBelonging = ObjectBelonging.OWN;

  /**
   * Используется для заполнения объекта на основании информации формата конфигуратора
   *
   * @param designerMDO - Служебный объект, содержащий данные в формате конфигуратора.
   */
  protected AbstractMDO(DesignerMDO designerMDO) {
    uuid = designerMDO.getUuid();
    name = designerMDO.getProperties().getName();
    comment = designerMDO.getProperties().getComment();
    objectBelonging = designerMDO.getProperties().getObjectBelonging();

    synonyms = designerMDO.getProperties().getSynonyms().stream()
      .map(synonym -> new LanguageContent(synonym.getLanguage(), synonym.getContent()))
      .collect(Collectors.toList());
  }

  @SneakyThrows
  public Object buildMDObject() {
    requireNonNull(builder);

    TransformationUtils.setValue(builder, "uuid", uuid);
    TransformationUtils.setValue(builder, "name", name);
    TransformationUtils.setValue(builder, "objectBelonging", objectBelonging);
    TransformationUtils.setValue(builder, "synonyms", new MultiLanguageString(synonyms.stream()
      .collect(Collectors.toUnmodifiableMap(LanguageContent::getLanguage, LanguageContent::getContent))));
    TransformationUtils.setValue(builder, "metadataName", getMetadataName());

    TransformationUtils.setValue(builder, "metadataNameRu", getMetadataNameRu());
    if (!(this instanceof AbstractMDOAttribute)) {
      TransformationUtils.setValue(builder, "type", getType());
    }
    TransformationUtils.setValue(builder, "mdoReference", mdoReference.getRef());

    return TransformationUtils.build(builder);
  }

  /**
   * Возвращает тип метаданных
   */
  public abstract MDOType getType();

  /**
   * Возвращает имя метаданных объекта
   */
  public abstract String getMetadataName();

  /**
   * Возвращает имя метаданных объекта на русском языке
   */
  public abstract String getMetadataNameRu();


}
