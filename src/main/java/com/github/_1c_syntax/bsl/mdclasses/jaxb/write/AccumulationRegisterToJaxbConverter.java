/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.AccumulationRegister;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.AccumulationRegisterChildObjects;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.AccumulationRegisterProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;

/**
 * Преобразует регистр накопления mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class AccumulationRegisterToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private AccumulationRegisterToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из регистра накопления.
   *
   * @param ar регистр накопления из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(AccumulationRegister ar) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.AccumulationRegister inner = FACTORY.createAccumulationRegister();
    inner.setUuid(ar.getUuid() != null ? ar.getUuid() : "");
    inner.setProperties(buildProperties(ar));
    inner.setChildObjects(buildChildObjects(ar));
    root.setAccumulationRegister(inner);
    return root;
  }

  private static AccumulationRegisterProperties buildProperties(AccumulationRegister ar) {
    AccumulationRegisterProperties p = FACTORY.createAccumulationRegisterProperties();
    p.setName(ar.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(ar.getSynonym())));
    p.setComment(ar.getComment() != null ? ar.getComment() : "");
    p.setObjectBelonging(JaxbWriteDefaults.objectBelongingNative());
    p.setUseStandardCommands(false);
    p.setDefaultListForm("");
    p.setAuxiliaryListForm("");
    p.setRegisterType(JaxbWriteDefaults.accumulationRegisterTypeBalance());
    p.setIncludeHelpInContents(false);
    p.setHelp("");
    p.setRecordSetModule("");
    p.setManagerModule("");
    p.setStandardAttributes(JaxbWriteDefaults.emptyStandardAttributeDescriptions());
    p.setDataLockControlMode(JaxbWriteDefaults.defaultDataLockControlModeAutomatic());
    p.setFullTextSearch(JaxbWriteDefaults.fullTextSearchDontUse());
    p.setEnableTotalsSplitting(false);
    p.setAggregates("");
    p.setListPresentation(JaxbWriteDefaults.localStringType(""));
    p.setExtendedListPresentation(JaxbWriteDefaults.localStringType(""));
    p.setExplanation(JaxbWriteDefaults.localStringType(""));
    p.setAdditionalIndexes("");
    return p;
  }

  private static AccumulationRegisterChildObjects buildChildObjects(AccumulationRegister ar) {
    AccumulationRegisterChildObjects co = FACTORY.createAccumulationRegisterChildObjects();
    if (ar.getForms() != null) {
      for (ObjectForm form : ar.getForms()) {
        co.getForm().add(form.getName() != null ? form.getName() : "");
      }
    }
    return co;
  }
}
