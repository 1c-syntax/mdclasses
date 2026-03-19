/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.FilterCriterion;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.FilterCriterionChildObjects;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.FilterCriterionProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;

/**
 * Преобразует критерий отбора mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class FilterCriterionToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private FilterCriterionToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из критерия отбора.
   *
   * @param fc критерий отбора из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(FilterCriterion fc) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.FilterCriterion inner = FACTORY.createFilterCriterion();
    inner.setUuid(fc.getUuid() != null ? fc.getUuid() : "");
    inner.setProperties(buildProperties(fc));
    inner.setChildObjects(buildChildObjects(fc));
    root.setFilterCriterion(inner);
    return root;
  }

  private static FilterCriterionProperties buildProperties(FilterCriterion fc) {
    FilterCriterionProperties p = FACTORY.createFilterCriterionProperties();
    p.setName(fc.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(fc.getSynonym())));
    p.setComment(fc.getComment() != null ? fc.getComment() : "");
    return p;
  }

  private static FilterCriterionChildObjects buildChildObjects(FilterCriterion fc) {
    FilterCriterionChildObjects co = FACTORY.createFilterCriterionChildObjects();
    if (fc.getForms() != null) {
      for (ObjectForm form : fc.getForms()) {
        co.getForm().add(form.getName() != null ? form.getName() : "");
      }
    }
    return co;
  }
}
