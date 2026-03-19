/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.Language;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.LanguageProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;

/**
 * Преобразует язык mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class LanguageToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private LanguageToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из языка.
   *
   * @param language язык из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(Language language) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.Language inner = FACTORY.createLanguage();
    inner.setUuid(language.getUuid() != null ? language.getUuid() : "");
    inner.setProperties(buildProperties(language));
    root.setLanguage(inner);
    return root;
  }

  private static LanguageProperties buildProperties(Language l) {
    LanguageProperties p = FACTORY.createLanguageProperties();
    p.setName(l.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(l.getSynonym())));
    p.setComment(l.getComment() != null ? l.getComment() : "");
    p.setObjectBelonging(JaxbWriteDefaults.objectBelongingNative());
    p.setLanguageCode(l.getLanguageCode() != null ? l.getLanguageCode() : "");
    return p;
  }
}
