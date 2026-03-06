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

import com.github._1c_syntax.bsl.mdo.Catalog;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Конвертер записи справочника в формате EDT (.mdo).
 * MVP: name, uuid, synonym, checkUnique, codeSeries.
 * Часть свойств (useStandardCommands, levelCount, codeLength и т.д.) задаётся значениями по умолчанию,
 * так как в модели {@link Catalog} пока нет соответствующих полей; при их появлении нужно перейти на чтение из catalog.
 */
public class CatalogEdtWriteConverter implements Converter {

  private static final String MDCLASS_NS = "http://g5.1c.ru/v8/dt/metadata/mdclass";
  private static final String NAME = "name";
  private static final String KEY = "key";
  private static final String VALUE = "value";
  private static final String SYNONYM = "synonym";

  /** Сериализует справочник в EDT XML (name, synonym, checkUnique, codeSeries и др.). */
  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    var catalog = (Catalog) source;

    if (catalog.getExplanation() != null && !catalog.getExplanation().isEmpty()) {
      throw new IllegalStateException(
        "EDT Catalog write does not support non-empty explanation, catalog: " + catalog.getName());
    }
    if (catalog.getOwners() != null && !catalog.getOwners().isEmpty()) {
      throw new IllegalStateException(
        "EDT Catalog write does not support non-empty owners, catalog: " + catalog.getName());
    }

    writer.addAttribute("xmlns:mdclass", MDCLASS_NS);
    if (catalog.getUuid() != null && !catalog.getUuid().isEmpty()) {
      writer.addAttribute("uuid", catalog.getUuid());
    }

    writeElement(writer, NAME, catalog.getName());
    // Catalog model only has getCodeSeries(), isCheckUnique() for EDT catalog props; rest are defaults until model is extended
    writeElement(writer, "useStandardCommands", "true");
    writeElement(writer, "fullTextSearchOnInputByString", "DontUse");
    writeElement(writer, "createOnInput", "Use");
    writeElement(writer, "dataLockControlMode", "Managed");
    writeElement(writer, "fullTextSearch", "Use");
    writeElement(writer, "levelCount", "2");
    writeElement(writer, "foldersOnTop", "true");
    writeElement(writer, "codeLength", "9");
    writeElement(writer, "descriptionLength", "25");
    writeElement(writer, "codeType", "String");
    writeElement(writer, "codeAllowedLength", "Variable");
    writeElement(writer, "checkUnique", catalog.isCheckUnique() ? "true" : "false");
    writeElement(writer, "autonumbering", "false");
    writeElement(writer, "defaultPresentation", "AsDescription");
    if (catalog.getCodeSeries() != null) {
      writeElement(writer, "codeSeries", catalog.getCodeSeries().fullName().getEn());
    }
    writeElement(writer, "editType", "InDialog");
    writeElement(writer, "choiceMode", "BothWays");
    writeSynonym(writer, catalog.getSynonym());
  }

  private static void writeSynonym(HierarchicalStreamWriter writer, MultiLanguageString synonym) {
    if (synonym == null || synonym.isEmpty()) {
      return;
    }
    for (var entry : synonym.getContent()) {
      writer.startNode(SYNONYM);
      writeElement(writer, KEY, entry.getLangKey());
      writeElement(writer, VALUE, entry.getValue());
      writer.endNode();
    }
  }

  private static void writeElement(HierarchicalStreamWriter writer, String nodeName, String text) {
    if (text == null) {
      return;
    }
    writer.startNode(nodeName);
    writer.setValue(text);
    writer.endNode();
  }

  /** Конвертер только для записи; чтение не поддерживается. */
  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    throw new UnsupportedOperationException("CatalogEdtWriteConverter is for writing only");
  }

  /** Поддерживается только тип {@link Catalog}. */
  @Override
  public boolean canConvert(Class type) {
    return Catalog.class.isAssignableFrom(type);
  }
}
