/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.CommandGroup;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.CommandGroupProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_2_managed_application_core.CommandGroupCategory;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_2_managed_application_logform.ButtonRepresentation;

/**
 * Преобразует группу команд mdclasses в JAXB-DTO для записи Designer XML.
 */
public final class CommandGroupToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private CommandGroupToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из группы команд.
   *
   * @param commandGroup группа команд из модели mdclasses
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(CommandGroup commandGroup) {
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.CommandGroup inner = FACTORY.createCommandGroup();
    inner.setUuid(commandGroup.getUuid() != null ? commandGroup.getUuid() : "");
    inner.setProperties(buildProperties(commandGroup));
    root.setCommandGroup(inner);
    return root;
  }

  private static CommandGroupProperties buildProperties(CommandGroup g) {
    CommandGroupProperties p = FACTORY.createCommandGroupProperties();
    p.setName(g.getName() != null ? g.getName() : "");
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(g.getSynonym())));
    p.setComment(g.getComment() != null ? g.getComment() : "");
    p.setObjectBelonging(JaxbWriteDefaults.objectBelongingNative());
    p.setRepresentation(ButtonRepresentation.AUTO);
    p.setToolTip(JaxbWriteDefaults.localStringType(""));
    p.setPicture(JaxbWriteDefaults.emptyPicture());
    p.setCategory(CommandGroupCategory.NAVIGATION_PANEL);
    return p;
  }
}
