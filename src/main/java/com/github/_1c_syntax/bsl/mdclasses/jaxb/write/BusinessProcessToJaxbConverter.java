/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.BusinessProcess;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;

/**
 * Преобразует бизнес-процесс mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class BusinessProcessToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private BusinessProcessToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из бизнес-процесса.
   *
   * @param bp бизнес-процесс из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(BusinessProcess bp) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.BusinessProcess inner = FACTORY.createBusinessProcess();
    inner.setUuid(bp.getUuid() != null ? bp.getUuid() : "");
    inner.setProperties(buildProperties(bp));
    inner.setChildObjects(buildChildObjects(bp));
    root.setBusinessProcess(inner);
    return root;
  }

  private static com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.BusinessProcessProperties buildProperties(BusinessProcess bp) {
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.BusinessProcessProperties p =
      FACTORY.createBusinessProcessProperties();
    p.setName(bp.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(bp.getSynonym())));
    p.setComment(bp.getComment() != null ? bp.getComment() : "");
    return p;
  }

  private static com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.BusinessProcessChildObjects buildChildObjects(BusinessProcess bp) {
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.BusinessProcessChildObjects childObjects =
      FACTORY.createBusinessProcessChildObjects();
    if (bp.getForms() != null) {
      for (ObjectForm form : bp.getForms()) {
        childObjects.getForm().add(form.getName() != null ? form.getName() : "");
      }
    }
    return childObjects;
  }
}
