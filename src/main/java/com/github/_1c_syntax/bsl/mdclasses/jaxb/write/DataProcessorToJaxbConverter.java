/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.DataProcessor;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.DataProcessorChildObjects;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.DataProcessorProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;

/**
 * Преобразует обработку mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class DataProcessorToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private DataProcessorToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из обработки.
   *
   * @param dp обработка из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(DataProcessor dp) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.DataProcessor inner = FACTORY.createDataProcessor();
    inner.setUuid(dp.getUuid() != null ? dp.getUuid() : "");
    inner.setProperties(buildProperties(dp));
    inner.setChildObjects(buildChildObjects(dp));
    root.setDataProcessor(inner);
    return root;
  }

  private static DataProcessorProperties buildProperties(DataProcessor dp) {
    DataProcessorProperties p = FACTORY.createDataProcessorProperties();
    p.setName(dp.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(dp.getSynonym())));
    p.setComment(dp.getComment() != null ? dp.getComment() : "");
    return p;
  }

  private static DataProcessorChildObjects buildChildObjects(DataProcessor dp) {
    DataProcessorChildObjects co = FACTORY.createDataProcessorChildObjects();
    if (dp.getForms() != null) {
      for (ObjectForm form : dp.getForms()) {
        co.getForm().add(form.getName() != null ? form.getName() : "");
      }
    }
    return co;
  }
}
