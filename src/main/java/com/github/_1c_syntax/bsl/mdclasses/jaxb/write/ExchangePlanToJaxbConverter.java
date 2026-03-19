/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.ExchangePlan;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ExchangePlanChildObjects;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ExchangePlanProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;

/**
 * Преобразует план обмена mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class ExchangePlanToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private ExchangePlanToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из плана обмена.
   *
   * @param ep план обмена из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(ExchangePlan ep) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ExchangePlan inner = FACTORY.createExchangePlan();
    inner.setUuid(ep.getUuid() != null ? ep.getUuid() : "");
    inner.setProperties(buildProperties(ep));
    inner.setChildObjects(buildChildObjects(ep));
    root.setExchangePlan(inner);
    return root;
  }

  private static ExchangePlanProperties buildProperties(ExchangePlan ep) {
    ExchangePlanProperties p = FACTORY.createExchangePlanProperties();
    p.setName(ep.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(ep.getSynonym())));
    p.setComment(ep.getComment() != null ? ep.getComment() : "");
    return p;
  }

  private static ExchangePlanChildObjects buildChildObjects(ExchangePlan ep) {
    ExchangePlanChildObjects co = FACTORY.createExchangePlanChildObjects();
    if (ep.getForms() != null) {
      for (ObjectForm form : ep.getForms()) {
        co.getForm().add(form.getName() != null ? form.getName() : "");
      }
    }
    return co;
  }
}
