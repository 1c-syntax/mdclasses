/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.Role;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.RoleProperties;

/**
 * Преобразует роль mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class RoleToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private RoleToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из роли.
   *
   * @param role роль из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(Role role) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.Role inner = FACTORY.createRole();
    inner.setUuid(role.getUuid() != null ? role.getUuid() : "");
    inner.setProperties(buildProperties(role));
    root.setRole(inner);
    return root;
  }

  private static RoleProperties buildProperties(Role r) {
    RoleProperties p = FACTORY.createRoleProperties();
    p.setName(r.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(r.getSynonym())));
    p.setComment(r.getComment() != null ? r.getComment() : "");
    p.setObjectBelonging(JaxbWriteDefaults.objectBelongingNative());
    p.setRights("");
    return p;
  }
}
