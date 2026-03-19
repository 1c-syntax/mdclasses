/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.SubsystemChildObjects;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.SubsystemProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_readable.MDListType;

/**
 * Преобразует модель подсистемы mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class SubsystemToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private SubsystemToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из подсистемы mdclasses.
   *
   * @param subsystem подсистема из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(Subsystem subsystem) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.Subsystem inner = FACTORY.createSubsystem();
    inner.setUuid(subsystem.getUuid() != null ? subsystem.getUuid() : "");
    inner.setProperties(buildProperties(subsystem));
    inner.setChildObjects(buildChildObjects(subsystem));
    root.setSubsystem(inner);
    return root;
  }

  private static SubsystemChildObjects buildChildObjects(Subsystem s) {
    SubsystemChildObjects co = new SubsystemChildObjects();
    if (s.getSubsystems() != null) {
      for (Subsystem child : s.getSubsystems()) {
        co.getSubsystem().add(child.getName());
      }
    }
    return co;
  }

  private static SubsystemProperties buildProperties(Subsystem s) {
    SubsystemProperties p = new SubsystemProperties();
    p.setName(s.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(s.getSynonym())));
    p.setComment(s.getComment() != null ? s.getComment() : "");
    p.setObjectBelonging(JaxbWriteDefaults.objectBelongingNative());
    p.setIncludeHelpInContents(s.isIncludeHelpInContents());
    p.setHelp("");
    p.setIncludeInCommandInterface(s.isIncludeInCommandInterface());
    p.setCommandInterface("");
    p.setExplanation(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(s.getExplanation())));
    p.setPicture(JaxbWriteDefaults.emptyPicture());
    MDListType content = JaxbWriteDefaults.emptyMDListType();
    if (s.getContent() != null && !s.getContent().isEmpty()) {
      for (MdoReference ref : s.getContent()) {
        content.getItem().add(shortNameFromMdoRef(ref.getMdoRef()));
      }
    }
    p.setContent(content);
    return p;
  }

  private static String shortNameFromMdoRef(String mdoRef) {
    if (mdoRef == null) {
      return "";
    }
    int dot = mdoRef.indexOf('.');
    return dot >= 0 ? mdoRef.substring(dot + 1) : mdoRef;
  }
}
