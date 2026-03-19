/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.Report;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ReportChildObjects;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ReportProperties;

/**
 * Преобразует отчёт mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class ReportToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private ReportToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из отчёта.
   *
   * @param report отчёт из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(Report report) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.Report inner = FACTORY.createReport();
    inner.setUuid(report.getUuid() != null ? report.getUuid() : "");
    inner.setProperties(buildProperties(report));
    inner.setChildObjects(buildChildObjects(report));
    root.setReport(inner);
    return root;
  }

  private static ReportProperties buildProperties(Report r) {
    ReportProperties p = FACTORY.createReportProperties();
    p.setName(r.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(r.getSynonym())));
    p.setComment(r.getComment() != null ? r.getComment() : "");
    return p;
  }

  private static ReportChildObjects buildChildObjects(Report r) {
    ReportChildObjects co = FACTORY.createReportChildObjects();
    if (r.getForms() != null) {
      for (ObjectForm form : r.getForms()) {
        co.getForm().add(form.getName() != null ? form.getName() : "");
      }
    }
    return co;
  }
}
