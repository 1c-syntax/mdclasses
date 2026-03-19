/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.children.EnumValue;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.EnumChildObjects;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.EnumProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.EnumValueProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;

/**
 * Преобразует модель перечисления mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class EnumToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private EnumToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из перечисления mdclasses.
   *
   * @param anEnum перечисление из модели mdclasses (mdo.Enum)
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(com.github._1c_syntax.bsl.mdo.Enum anEnum) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.Enum inner = FACTORY.createEnum();
    inner.setUuid(anEnum.getUuid() != null ? anEnum.getUuid() : "");
    inner.setProperties(buildProperties(anEnum));
    inner.setChildObjects(buildChildObjects(anEnum));
    root.setEnum(inner);
    return root;
  }

  private static EnumProperties buildProperties(com.github._1c_syntax.bsl.mdo.Enum e) {
    EnumProperties p = FACTORY.createEnumProperties();
    p.setName(e.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(e.getSynonym())));
    p.setComment(e.getComment() != null ? e.getComment() : "");
    p.setObjectBelonging(JaxbWriteDefaults.objectBelongingNative());
    p.setUseStandardCommands(false);
    p.setStandardAttributes(JaxbWriteDefaults.emptyStandardAttributeDescriptions());
    p.setCharacteristics(JaxbWriteDefaults.emptyCharacteristicsDescriptions());
    p.setQuickChoice(false);
    p.setChoiceMode(JaxbWriteDefaults.choiceModeFromForm());
    p.setDefaultListForm("");
    p.setDefaultChoiceForm("");
    p.setAuxiliaryListForm("");
    p.setAuxiliaryChoiceForm("");
    p.setManagerModule("");
    p.setListPresentation(JaxbWriteDefaults.localStringType(""));
    p.setExtendedListPresentation(JaxbWriteDefaults.localStringType(""));
    p.setExplanation(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(e.getExplanation())));
    p.setChoiceHistoryOnInput(JaxbWriteDefaults.choiceHistoryOnInputAuto());
    return p;
  }

  private static EnumChildObjects buildChildObjects(com.github._1c_syntax.bsl.mdo.Enum e) {
    EnumChildObjects co = FACTORY.createEnumChildObjects();
    if (e.getEnumValues() != null) {
      for (EnumValue ev : e.getEnumValues()) {
        com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.EnumValue jaxbEv = FACTORY.createEnumValue();
        jaxbEv.setUuid(ev.getUuid() != null ? ev.getUuid() : "");
        EnumValueProperties evProps = FACTORY.createEnumValueProperties();
        evProps.setName(ev.getName());
        evProps.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(ev.getSynonym())));
        evProps.setComment(ev.getComment() != null ? ev.getComment() : "");
        evProps.setObjectBelonging(JaxbWriteDefaults.objectBelongingNative());
        jaxbEv.setProperties(evProps);
        co.getEnumValue().add(jaxbEv);
      }
    }
    if (e.getForms() != null) {
      for (ObjectForm form : e.getForms()) {
        co.getForm().add(form.getName() != null ? form.getName() : "");
      }
    }
    return co;
  }
}
