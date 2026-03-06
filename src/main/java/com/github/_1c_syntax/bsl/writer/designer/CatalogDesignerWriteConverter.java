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

import com.github._1c_syntax.bsl.mdo.Catalog;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Конвертер записи справочника в формате Конфигуратора (Designer .xml).
 * MVP: Name, Synonym, Comment, базовые свойства (UseStandardCommands, LevelCount, CodeLength и т.д.).
 */
public class CatalogDesignerWriteConverter implements Converter {

  private static final String PROPERTIES = "Properties";
  private static final String NAME = "Name";
  private static final String SYNONYM = "Synonym";
  private static final String COMMENT = "Comment";
  private static final String V8_ITEM = "v8:item";
  private static final String V8_LANG = "v8:lang";
  private static final String V8_CONTENT = "v8:content";
  private static final String FALSE = "false";

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    var catalog = (Catalog) source;

    if (catalog.getUuid() != null && !catalog.getUuid().isEmpty()) {
      writer.addAttribute("uuid", catalog.getUuid());
    }
    writer.startNode(PROPERTIES);
    writeElement(writer, NAME, catalog.getName());
    writeSynonym(writer, catalog.getSynonym());
    writeElement(writer, COMMENT, catalog.getComment() != null ? catalog.getComment() : "");
    writeElement(writer, "Hierarchical", "true");
    writeElement(writer, "HierarchyType", "HierarchyFoldersAndItems");
    writeElement(writer, "LimitLevelCount", FALSE);
    writeElement(writer, "LevelCount", "2");
    writeElement(writer, "FoldersOnTop", "true");
    writeElement(writer, "UseStandardCommands", "true");
    writeElement(writer, "Owners", "");
    writeElement(writer, "SubordinationUse", "ToItems");
    writeElement(writer, "CodeLength", "9");
    writeElement(writer, "DescriptionLength", "25");
    writeElement(writer, "CodeType", "String");
    writeElement(writer, "CodeAllowedLength", "Variable");
    writeElement(writer, "CodeSeries", catalog.getCodeSeries() != null ? catalog.getCodeSeries().fullName().getEn() : "WholeCatalog");
    writeElement(writer, "CheckUnique", catalog.isCheckUnique() ? "true" : FALSE);
    writeElement(writer, "Autonumbering", FALSE);
    writeElement(writer, "DefaultPresentation", "AsDescription");
    writer.endNode(); // Properties
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
    throw new UnsupportedOperationException("CatalogDesignerWriteConverter is for writing only");
  }

  @Override
  public boolean canConvert(Class type) {
    return Catalog.class.isAssignableFrom(type);
  }
}
