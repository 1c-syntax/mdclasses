/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.ChartOfCharacteristicTypes;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ChartOfCharacteristicTypesChildObjects;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ChartOfCharacteristicTypesProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;

/**
 * Преобразует план видов характеристик mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class ChartOfCharacteristicTypesToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private ChartOfCharacteristicTypesToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из плана видов характеристик.
   *
   * @param chart план видов характеристик из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(ChartOfCharacteristicTypes chart) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ChartOfCharacteristicTypes inner =
      FACTORY.createChartOfCharacteristicTypes();
    inner.setUuid(chart.getUuid() != null ? chart.getUuid() : "");
    inner.setProperties(buildProperties(chart));
    inner.setChildObjects(buildChildObjects(chart));
    root.setChartOfCharacteristicTypes(inner);
    return root;
  }

  private static ChartOfCharacteristicTypesProperties buildProperties(ChartOfCharacteristicTypes chart) {
    ChartOfCharacteristicTypesProperties p = FACTORY.createChartOfCharacteristicTypesProperties();
    p.setName(chart.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(chart.getSynonym())));
    p.setComment(chart.getComment() != null ? chart.getComment() : "");
    return p;
  }

  private static ChartOfCharacteristicTypesChildObjects buildChildObjects(ChartOfCharacteristicTypes chart) {
    ChartOfCharacteristicTypesChildObjects childObjects = FACTORY.createChartOfCharacteristicTypesChildObjects();
    if (chart.getForms() != null) {
      for (ObjectForm form : chart.getForms()) {
        childObjects.getForm().add(form.getName() != null ? form.getName() : "");
      }
    }
    return childObjects;
  }
}
