/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * MDClasses is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * MDClasses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with MDClasses.
 */
package com.github._1c_syntax.bsl.writer.edt;

import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Конвертер записи подсистемы в формате EDT (.mdo).
 */
public class SubsystemEdtWriteConverter implements Converter {

  private static final String MDCLASS_NS = "http://g5.1c.ru/v8/dt/metadata/mdclass";
  private static final String NAME = "name";
  private static final String SYNONYM = "synonym";
  private static final String KEY = "key";
  private static final String VALUE = "value";
  private static final String SUBSYSTEMS = "subsystems";
  private static final String INCLUDE_IN_COMMAND_INTERFACE = "includeInCommandInterface";
  private static final String INCLUDE_HELP_IN_CONTENTS = "includeHelpInContents";

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    var subsystem = (Subsystem) source;

    writer.addAttribute("xmlns:mdclass", MDCLASS_NS);
    if (subsystem.getUuid() != null && !subsystem.getUuid().isEmpty()) {
      writer.addAttribute("uuid", subsystem.getUuid());
    }

    writeElement(writer, NAME, subsystem.getName());
    writeSynonym(writer, subsystem.getSynonym());
    writeElement(writer, INCLUDE_HELP_IN_CONTENTS, subsystem.isIncludeHelpInContents() ? "true" : "false");
    writeElement(writer, INCLUDE_IN_COMMAND_INTERFACE, subsystem.isIncludeInCommandInterface() ? "true" : "false");

    var children = subsystem.getSubsystems();
    if (children != null) {
      for (var child : children) {
        writeElement(writer, SUBSYSTEMS, child.getName());
      }
    }
  }

  private static void writeSynonym(HierarchicalStreamWriter writer, MultiLanguageString synonym) {
    if (synonym == null || synonym.isEmpty()) {
      return;
    }
    writer.startNode(SYNONYM);
    for (var entry : synonym.getContent()) {
      writeElement(writer, KEY, entry.getLangKey());
      writeElement(writer, VALUE, entry.getValue());
    }
    writer.endNode();
  }

  private static void writeElement(HierarchicalStreamWriter writer, String nodeName, String text) {
    if (text == null) {
      return;
    }
    writer.startNode(nodeName);
    writer.setValue(escapeXml(text));
    writer.endNode();
  }

  private static String escapeXml(String s) {
    if (s == null) {
      return "";
    }
    return s.replace("&", "&amp;")
      .replace("<", "&lt;")
      .replace(">", "&gt;")
      .replace("\"", "&quot;")
      .replace("'", "&apos;");
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    throw new UnsupportedOperationException("SubsystemEdtWriteConverter is for writing only");
  }

  @Override
  public boolean canConvert(Class type) {
    return Subsystem.class.isAssignableFrom(type);
  }
}
