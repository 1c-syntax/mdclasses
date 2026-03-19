/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.InformationRegister;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.InformationRegisterChildObjects;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.InformationRegisterProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;

/**
 * Преобразует регистр сведений mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class InformationRegisterToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private InformationRegisterToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из регистра сведений.
   *
   * @param ir регистр сведений из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(InformationRegister ir) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.InformationRegister inner = FACTORY.createInformationRegister();
    inner.setUuid(ir.getUuid() != null ? ir.getUuid() : "");
    inner.setProperties(buildProperties(ir));
    inner.setChildObjects(buildChildObjects(ir));
    root.setInformationRegister(inner);
    return root;
  }

  private static InformationRegisterProperties buildProperties(InformationRegister ir) {
    InformationRegisterProperties p = FACTORY.createInformationRegisterProperties();
    p.setName(ir.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(ir.getSynonym())));
    p.setComment(ir.getComment() != null ? ir.getComment() : "");
    return p;
  }

  private static InformationRegisterChildObjects buildChildObjects(InformationRegister ir) {
    InformationRegisterChildObjects co = FACTORY.createInformationRegisterChildObjects();
    if (ir.getForms() != null) {
      for (ObjectForm form : ir.getForms()) {
        co.getForm().add(form.getName() != null ? form.getName() : "");
      }
    }
    return co;
  }
}
