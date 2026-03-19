/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.CommonModule;
import com.github._1c_syntax.bsl.mdo.support.ReturnValueReuse;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.CommonModuleProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;

/**
 * Преобразует модель общего модуля mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class CommonModuleToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private CommonModuleToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из общего модуля mdclasses.
   *
   * @param commonModule общий модуль из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(CommonModule commonModule) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.CommonModule inner = FACTORY.createCommonModule();
    inner.setUuid(commonModule.getUuid() != null ? commonModule.getUuid() : "");
    inner.setProperties(buildProperties(commonModule));
    root.setCommonModule(inner);
    return root;
  }

  private static CommonModuleProperties buildProperties(CommonModule c) {
    CommonModuleProperties p = FACTORY.createCommonModuleProperties();
    p.setName(c.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(c.getSynonym())));
    p.setComment(c.getComment() != null ? c.getComment() : "");
    p.setGlobal(c.isGlobal());
    p.setServer(c.isServer());
    p.setClientManagedApplication(c.isClientManagedApplication());
    p.setClientOrdinaryApplication(c.isClientOrdinaryApplication());
    p.setExternalConnection(c.isExternalConnection());
    p.setServerCall(c.isServerCall());
    p.setPrivileged(c.isPrivileged());
    ReturnValueReuse rvr = c.getReturnValuesReuse();
    if (rvr != null && rvr != ReturnValueReuse.UNKNOWN && rvr.fullName() != null) {
      p.setReturnValuesReuse(com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.ReturnValuesReuse.fromValue(rvr.fullName().getEn()));
    }
    return p;
  }
}
