/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.ChartOfAccounts;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ChartOfAccountsChildObjects;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ChartOfAccountsProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;

/**
 * Преобразует план счетов mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class ChartOfAccountsToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private ChartOfAccountsToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из плана счетов.
   *
   * @param coa план счетов из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(ChartOfAccounts coa) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ChartOfAccounts inner = FACTORY.createChartOfAccounts();
    inner.setUuid(coa.getUuid() != null ? coa.getUuid() : "");
    inner.setProperties(buildProperties(coa));
    inner.setChildObjects(buildChildObjects(coa));
    root.setChartOfAccounts(inner);
    return root;
  }

  private static ChartOfAccountsProperties buildProperties(ChartOfAccounts coa) {
    ChartOfAccountsProperties p = FACTORY.createChartOfAccountsProperties();
    p.setName(coa.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(coa.getSynonym())));
    p.setComment(coa.getComment() != null ? coa.getComment() : "");
    return p;
  }

  private static ChartOfAccountsChildObjects buildChildObjects(ChartOfAccounts chart) {
    ChartOfAccountsChildObjects childObjects = FACTORY.createChartOfAccountsChildObjects();
    if (chart.getForms() != null) {
      for (ObjectForm form : chart.getForms()) {
        childObjects.getForm().add(form.getName() != null ? form.getName() : "");
      }
    }
    return childObjects;
  }
}
