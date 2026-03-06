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
package com.github._1c_syntax.bsl.writer.designer;

import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Конвертер записи подсистемы в формате Конфигуратора (Designer .xml).
 * Выводит обёртку MetaDataObject и элемент Subsystem с Properties (Name, Synonym, дочерние подсистемы).
 */
public class SubsystemDesignerWriteConverter implements Converter {

  private static final String SUBSYSTEM = "Subsystem";
  private static final String PROPERTIES = "Properties";
  private static final String NAME = "Name";
  private static final String SYNONYM = "Synonym";
  private static final String V8_ITEM = "v8:item";
  private static final String V8_LANG = "v8:lang";
  private static final String V8_CONTENT = "v8:content";
  private static final String FALSE = "false";

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    var subsystem = (Subsystem) source;

    if (subsystem.getUuid() != null && !subsystem.getUuid().isEmpty()) {
      writer.addAttribute("uuid", subsystem.getUuid());
    }
    writer.startNode(PROPERTIES);
    writeElement(writer, NAME, subsystem.getName());
    writeSynonym(writer, subsystem.getSynonym());
    writeElement(writer, "Comment", subsystem.getComment() != null ? subsystem.getComment() : "");
    writeElement(writer, "IncludeHelpInContents", subsystem.isIncludeHelpInContents() ? "true" : FALSE);
    writeElement(writer, "IncludeInCommandInterface", subsystem.isIncludeInCommandInterface() ? "true" : FALSE);
    writeElement(writer, "UseOneCommand", FALSE);
    writeElement(writer, "Explanation", "");
    writeElement(writer, "Picture", "");
    writeElement(writer, "Content", "");
    writer.endNode(); // Properties

    var children = subsystem.getSubsystems();
    if (children != null && !children.isEmpty()) {
      writer.startNode("ChildObjects");
      for (var child : children) {
        writeElement(writer, SUBSYSTEM, child.getName());
      }
      writer.endNode(); // ChildObjects
    }
  }

  private static void writeSynonym(HierarchicalStreamWriter writer, MultiLanguageString synonym) {
    if (synonym == null || synonym.isEmpty()) {
      return;
    }
    writer.startNode(SYNONYM);
    for (var entry : synonym.getContent()) {
      writer.startNode(V8_ITEM);
      writeElement(writer, V8_LANG, entry.getLangKey());
      writeElement(writer, V8_CONTENT, entry.getValue());
      writer.endNode();
    }
    writer.endNode();
  }

  private static void writeElement(HierarchicalStreamWriter writer, String nodeName, String text) {
    if (text == null && !nodeName.equals("Comment") && !nodeName.equals("Explanation") && !nodeName.equals("Picture") && !nodeName.equals("Content")) {
      return;
    }
    writer.startNode(nodeName);
    writer.setValue(escapeXml(text != null ? text : ""));
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
    throw new UnsupportedOperationException("SubsystemDesignerWriteConverter is for writing only");
  }

  @Override
  public boolean canConvert(Class type) {
    return Subsystem.class.isAssignableFrom(type);
  }
}
