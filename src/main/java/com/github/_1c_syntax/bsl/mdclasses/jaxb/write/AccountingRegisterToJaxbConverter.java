/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.AccountingRegister;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.AccountingRegisterChildObjects;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.AccountingRegisterProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;

import java.math.BigDecimal;

/**
 * Преобразует регистр бухгалтерии mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class AccountingRegisterToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private AccountingRegisterToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из регистра бухгалтерии.
   *
   * @param ar регистр бухгалтерии из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(AccountingRegister ar) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.AccountingRegister inner = FACTORY.createAccountingRegister();
    inner.setUuid(ar.getUuid() != null ? ar.getUuid() : "");
    inner.setProperties(buildProperties(ar));
    inner.setChildObjects(buildChildObjects(ar));
    root.setAccountingRegister(inner);
    return root;
  }

  private static AccountingRegisterProperties buildProperties(AccountingRegister ar) {
    AccountingRegisterProperties p = FACTORY.createAccountingRegisterProperties();
    p.setName(ar.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(ar.getSynonym())));
    p.setComment(ar.getComment() != null ? ar.getComment() : "");
    p.setObjectBelonging(JaxbWriteDefaults.objectBelongingNative());
    p.setUseStandardCommands(false);
    p.setIncludeHelpInContents(false);
    p.setHelp("");
    p.setChartOfAccounts("");
    p.setCorrespondence(false);
    p.setPeriodAdjustmentLength(BigDecimal.ZERO);
    p.setDefaultListForm("");
    p.setAuxiliaryListForm("");
    p.setRecordSetModule("");
    p.setManagerModule("");
    p.setStandardAttributes(JaxbWriteDefaults.emptyStandardAttributeDescriptions());
    p.setDataLockControlMode(JaxbWriteDefaults.defaultDataLockControlModeAutomatic());
    p.setEnableTotalsSplitting(false);
    p.setFullTextSearch(JaxbWriteDefaults.fullTextSearchDontUse());
    p.setListPresentation(JaxbWriteDefaults.localStringType(""));
    p.setExtendedListPresentation(JaxbWriteDefaults.localStringType(""));
    p.setExplanation(JaxbWriteDefaults.localStringType(""));
    p.setAdditionalIndexes("");
    return p;
  }

  private static AccountingRegisterChildObjects buildChildObjects(AccountingRegister ar) {
    AccountingRegisterChildObjects co = FACTORY.createAccountingRegisterChildObjects();
    if (ar.getForms() != null) {
      for (ObjectForm form : ar.getForms()) {
        co.getForm().add(form.getName() != null ? form.getName() : "");
      }
    }
    return co;
  }
}
