/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.ChartOfCalculationTypes;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ChartOfCalculationTypesChildObjects;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ChartOfCalculationTypesProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;

/**
 * Преобразует план видов расчёта mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class ChartOfCalculationTypesToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private ChartOfCalculationTypesToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из плана видов расчёта.
   *
   * @param chart план видов расчёта из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(ChartOfCalculationTypes chart) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ChartOfCalculationTypes inner =
      FACTORY.createChartOfCalculationTypes();
    inner.setUuid(chart.getUuid() != null ? chart.getUuid() : "");
    inner.setProperties(buildProperties(chart));
    inner.setChildObjects(buildChildObjects(chart));
    root.setChartOfCalculationTypes(inner);
    return root;
  }

  private static ChartOfCalculationTypesProperties buildProperties(ChartOfCalculationTypes chart) {
    ChartOfCalculationTypesProperties p = FACTORY.createChartOfCalculationTypesProperties();
    p.setName(chart.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(chart.getSynonym())));
    p.setComment(chart.getComment() != null ? chart.getComment() : "");
    return p;
  }

  private static ChartOfCalculationTypesChildObjects buildChildObjects(ChartOfCalculationTypes chart) {
    ChartOfCalculationTypesChildObjects childObjects = FACTORY.createChartOfCalculationTypesChildObjects();
    if (chart.getForms() != null) {
      for (ObjectForm form : chart.getForms()) {
        childObjects.getForm().add(form.getName() != null ? form.getName() : "");
      }
    }
    return childObjects;
  }
}
