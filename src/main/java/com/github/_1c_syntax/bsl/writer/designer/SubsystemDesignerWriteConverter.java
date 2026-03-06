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

import java.util.List;
import java.util.Set;

/**
 * Конвертер записи подсистемы в формате Конфигуратора (Designer .xml).
 * Выводит обёртку MetaDataObject и элемент Subsystem с Properties (Name, Synonym, Explanation, дочерние подсистемы).
 * Ограничение: элемент Content (состав подсистемы по ссылкам) не выводится — в Designer он в формате xr:Item.
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
  private static final Set<String> ALLOW_EMPTY_NODES = Set.of("Comment", "Explanation", "Picture", "Content");
  /** Preferred locale order for Explanation when Designer format only allows a single string. */
  private static final List<String> EXPLANATION_LOCALE_ORDER = List.of("ru", "en");

  /** Сериализует подсистему в Designer XML (Properties, ChildObjects). */
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
    writeElement(writer, "Explanation", explanationToString(subsystem.getExplanation()));
    writeElement(writer, "Picture", "");
    // Content: Designer uses <xr:Item xsi:type="xr:MDObjectRef">; not written here (namespace xr not in root)
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

  /**
   * Преобразует мультиязычное пояснение в одну строку для элемента Designer Explanation.
   * В формате Designer элемент Explanation — одно строковое значение, мультиязычность не поддерживается.
   * Используется подход (A): выбор по предпочтительному порядку локалей — сначала "ru", затем "en",
   * затем первая доступная запись. Остальные переводы не выводятся.
   */
  private static String explanationToString(MultiLanguageString explanation) {
    if (explanation == null || explanation.isEmpty()) {
      return "";
    }
    var content = explanation.getContent();
    if (content == null || content.isEmpty()) {
      return "";
    }
    for (var locale : EXPLANATION_LOCALE_ORDER) {
      var value = content.stream()
        .filter(e -> locale.equals(e.getLangKey()))
        .findFirst()
        .map(e -> e.getValue())
        .orElse(null);
      if (value != null && !value.isEmpty()) {
        return value;
      }
    }
    return content.iterator().next().getValue();
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
    if (text == null && !ALLOW_EMPTY_NODES.contains(nodeName)) {
      return;
    }
    writer.startNode(nodeName);
    writer.setValue(text != null ? text : "");
    writer.endNode();
  }

  /** Конвертер только для записи; чтение не поддерживается. */
  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    throw new UnsupportedOperationException("SubsystemDesignerWriteConverter is for writing only");
  }

  /** Поддерживается только тип {@link Subsystem}. */
  @Override
  public boolean canConvert(Class type) {
    return Subsystem.class.isAssignableFrom(type);
  }
}
