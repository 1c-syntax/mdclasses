/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.CalculationRegister;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.CalculationRegisterChildObjects;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.CalculationRegisterProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;

/**
 * Преобразует регистр расчёта mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class CalculationRegisterToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private CalculationRegisterToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из регистра расчёта.
   *
   * @param cr регистр расчёта из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(CalculationRegister cr) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.CalculationRegister inner = FACTORY.createCalculationRegister();
    inner.setUuid(cr.getUuid() != null ? cr.getUuid() : "");
    inner.setProperties(buildProperties(cr));
    inner.setChildObjects(buildChildObjects(cr));
    root.setCalculationRegister(inner);
    return root;
  }

  private static CalculationRegisterProperties buildProperties(CalculationRegister cr) {
    CalculationRegisterProperties p = FACTORY.createCalculationRegisterProperties();
    p.setName(cr.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(cr.getSynonym())));
    p.setComment(cr.getComment() != null ? cr.getComment() : "");
    p.setObjectBelonging(JaxbWriteDefaults.objectBelongingNative());
    p.setUseStandardCommands(false);
    p.setDefaultListForm("");
    p.setAuxiliaryListForm("");
    p.setPeriodicity(JaxbWriteDefaults.calculationRegisterPeriodicityYear());
    p.setActionPeriod(false);
    p.setBasePeriod(false);
    p.setSchedule("");
    p.setScheduleValue("");
    p.setScheduleDate("");
    p.setChartOfCalculationTypes("");
    p.setRecordSetModule("");
    p.setManagerModule("");
    p.setIncludeHelpInContents(false);
    p.setHelp("");
    p.setStandardAttributes(JaxbWriteDefaults.emptyStandardAttributeDescriptions());
    p.setDataLockControlMode(JaxbWriteDefaults.defaultDataLockControlModeAutomatic());
    p.setFullTextSearch(JaxbWriteDefaults.fullTextSearchDontUse());
    p.setListPresentation(JaxbWriteDefaults.localStringType(""));
    p.setExtendedListPresentation(JaxbWriteDefaults.localStringType(""));
    p.setExplanation(JaxbWriteDefaults.localStringType(""));
    p.setAdditionalIndexes("");
    return p;
  }

  private static CalculationRegisterChildObjects buildChildObjects(CalculationRegister cr) {
    CalculationRegisterChildObjects co = FACTORY.createCalculationRegisterChildObjects();
    if (cr.getForms() != null) {
      for (ObjectForm form : cr.getForms()) {
        co.getForm().add(form.getName() != null ? form.getName() : "");
      }
    }
    return co;
  }
}
