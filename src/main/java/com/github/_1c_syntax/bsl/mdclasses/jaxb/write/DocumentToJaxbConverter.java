/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.Document;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.DocumentChildObjects;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.DocumentProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;

/**
 * Преобразует модель документа mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class DocumentToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private DocumentToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из документа mdclasses.
   *
   * @param document документ из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(Document document) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.Document inner = FACTORY.createDocument();
    inner.setUuid(document.getUuid() != null ? document.getUuid() : "");
    inner.setProperties(buildProperties(document));
    inner.setChildObjects(buildChildObjects(document));
    root.setDocument(inner);
    return root;
  }

  private static DocumentProperties buildProperties(Document d) {
    DocumentProperties p = FACTORY.createDocumentProperties();
    p.setName(d.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(d.getSynonym())));
    p.setComment(d.getComment() != null ? d.getComment() : "");
    return p;
  }

  private static DocumentChildObjects buildChildObjects(Document d) {
    DocumentChildObjects co = FACTORY.createDocumentChildObjects();
    if (d.getForms() != null) {
      for (ObjectForm form : d.getForms()) {
        co.getForm().add(form.getName() != null ? form.getName() : "");
      }
    }
    return co;
  }
}
