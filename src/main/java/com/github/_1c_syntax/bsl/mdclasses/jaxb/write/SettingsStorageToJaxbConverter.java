/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.SettingsStorage;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.SettingsStorageProperties;

/**
 * Преобразует хранилище настроек mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class SettingsStorageToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private SettingsStorageToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из хранилища настроек.
   *
   * @param ss хранилище настроек из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(SettingsStorage ss) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.SettingsStorage inner = FACTORY.createSettingsStorage();
    inner.setUuid(ss.getUuid() != null ? ss.getUuid() : "");
    inner.setProperties(buildProperties(ss));
    inner.setChildObjects(FACTORY.createSettingsStorageChildObjects());
    root.setSettingsStorage(inner);
    return root;
  }

  private static SettingsStorageProperties buildProperties(SettingsStorage ss) {
    SettingsStorageProperties p = FACTORY.createSettingsStorageProperties();
    p.setName(ss.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(ss.getSynonym())));
    p.setComment(ss.getComment() != null ? ss.getComment() : "");
    return p;
  }
}
