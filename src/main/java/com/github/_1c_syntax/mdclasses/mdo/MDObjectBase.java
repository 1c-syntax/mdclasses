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

import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerSynonym;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOReference;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ObjectBelonging;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Базовый класс всех объектов 1С. Необходимо гарантировать отсутствие null значений в полях
 */
@Data
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
@NoArgsConstructor
public class MDObjectBase implements MDOExtensions {

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
  protected List<MDOSynonym> synonyms = Collections.emptyList();

  /**
   * Строка с комментарием объекта
   */
  protected String comment = "";

  /**
   * MDO-Ссылка на объект
   */
  protected MDOReference mdoReference;

  /**
   * Список подсистем, в состав которых входит объект
   */
  protected List<Subsystem> includedSubsystems = Collections.emptyList();

  /**
   * Принадлежность объекта конфигурации (собственный или заимствованный)
   */
  protected ObjectBelonging objectBelonging = ObjectBelonging.OWN;

  /**
   * Используется для заполнения объекта на основании информации формата конфигуратора
   *
   * @param designerMDO - Служебный объект, содержащий данные в формате конфигуратора.
   */
  public MDObjectBase(DesignerMDO designerMDO) {
    uuid = designerMDO.getUuid();
    name = designerMDO.getProperties().getName();
    synonyms = getSynonymsFromDesignerMDO(designerMDO.getProperties().getSynonyms());
    comment = designerMDO.getProperties().getComment();
    objectBelonging = designerMDO.getProperties().getObjectBelonging();
  }

  @Override
  public MDOType getType() {
    return MDOType.UNKNOWN;
  }

  /**
   * Для добавления ссылки на подсистему, в которую включен объект
   *
   * @param subsystem - Подсистема, в которую включен объект
   */
  public void addIncludedSubsystem(Subsystem subsystem) {
    if (includedSubsystems.equals(Collections.emptyList())) {
      includedSubsystems = new ArrayList<>();
    }
    includedSubsystems.add(subsystem);
  }

  private List<MDOSynonym> getSynonymsFromDesignerMDO(List<DesignerSynonym> synonyms) {

    if (synonyms.isEmpty()) {
      return Collections.emptyList();
    }
    List<MDOSynonym> result = new ArrayList<>();
    for (DesignerSynonym synonym : synonyms) {
      MDOSynonym mdoSynonym = new MDOSynonym();
      mdoSynonym.setLanguage(synonym.getLanguage());
      mdoSynonym.setContent(synonym.getContent());

      result.add(mdoSynonym);
    }

    return result;
  }
}
