/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.DocumentJournal;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.DocumentJournalChildObjects;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.DocumentJournalProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;

/**
 * Преобразует журнал документов mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class DocumentJournalToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private DocumentJournalToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из журнала документов.
   *
   * @param dj журнал документов из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(DocumentJournal dj) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.DocumentJournal inner = FACTORY.createDocumentJournal();
    inner.setUuid(dj.getUuid() != null ? dj.getUuid() : "");
    inner.setProperties(buildProperties(dj));
    inner.setChildObjects(buildChildObjects(dj));
    root.setDocumentJournal(inner);
    return root;
  }

  private static DocumentJournalProperties buildProperties(DocumentJournal dj) {
    DocumentJournalProperties p = FACTORY.createDocumentJournalProperties();
    p.setName(dj.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(dj.getSynonym())));
    p.setComment(dj.getComment() != null ? dj.getComment() : "");
    return p;
  }

  private static DocumentJournalChildObjects buildChildObjects(DocumentJournal dj) {
    DocumentJournalChildObjects co = FACTORY.createDocumentJournalChildObjects();
    if (dj.getForms() != null) {
      for (ObjectForm form : dj.getForms()) {
        co.getForm().add(form.getName() != null ? form.getName() : "");
      }
    }
    return co;
  }
}
