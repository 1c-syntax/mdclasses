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

import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.List;

/**
 * Конвертер записи конфигурации в формате Конфигуратора (Designer Configuration.xml).
 * MVP: Name, Synonym, Comment, uuid, ChildObjects (списки подсистем, справочников и т.д. по имени).
 */
public class ConfigurationDesignerWriteConverter implements Converter {

  private static final String PROPERTIES = "Properties";
  private static final String NAME = "Name";
  private static final String SYNONYM = "Synonym";
  private static final String COMMENT = "Comment";
  private static final String CHILD_OBJECTS = "ChildObjects";
  private static final String V8_ITEM = "v8:item";
  private static final String V8_LANG = "v8:lang";
  private static final String V8_CONTENT = "v8:content";

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    var config = (Configuration) source;

    if (config.getUuid() != null && !config.getUuid().isEmpty()) {
      writer.addAttribute("uuid", config.getUuid());
    }
    writer.startNode(PROPERTIES);
    writeElement(writer, NAME, config.getName());
    writeSynonym(writer, config.getSynonym());
    writeElement(writer, COMMENT, config.getComment() != null ? config.getComment() : "");
    writer.endNode(); // Properties

    writer.startNode(CHILD_OBJECTS);
    writeChildList(writer, config.getSubsystems());
    writeChildList(writer, config.getCatalogs());
    writeChildList(writer, config.getDocuments());
    writeChildList(writer, config.getEnums());
    writeChildList(writer, config.getReports());
    writeChildList(writer, config.getDataProcessors());
    writeChildList(writer, config.getCommonModules());
    writeChildList(writer, config.getRoles());
    writeChildList(writer, config.getInterfaces());
    writeChildList(writer, config.getConstants());
    writeChildList(writer, config.getCommonForms());
    writeChildList(writer, config.getCommonCommands());
    writeChildList(writer, config.getFilterCriteria());
    writeChildList(writer, config.getExchangePlans());
    writeChildList(writer, config.getSessionParameters());
    writeChildList(writer, config.getSettingsStorages());
    writeChildList(writer, config.getFunctionalOptions());
    writeChildList(writer, config.getDefinedTypes());
    writeChildList(writer, config.getCommonTemplates());
    writeChildList(writer, config.getCommonPictures());
    writeChildList(writer, config.getCommonAttributes());
    writeChildList(writer, config.getXDTOPackages());
    writeChildList(writer, config.getWebServices());
    writeChildList(writer, config.getHttpServices());
    writeChildList(writer, config.getWsReferences());
    writeChildList(writer, config.getEventSubscriptions());
    writeChildList(writer, config.getScheduledJobs());
    writeChildList(writer, config.getDocumentNumerators());
    writeChildList(writer, config.getSequences());
    writeChildList(writer, config.getDocumentJournals());
    writeChildList(writer, config.getInformationRegisters());
    writeChildList(writer, config.getAccumulationRegisters());
    writeChildList(writer, config.getChartsOfCharacteristicTypes());
    writeChildList(writer, config.getChartsOfAccounts());
    writeChildList(writer, config.getAccountingRegisters());
    writeChildList(writer, config.getChartsOfCalculationTypes());
    writeChildList(writer, config.getCalculationRegisters());
    writeChildList(writer, config.getBusinessProcesses());
    writeChildList(writer, config.getTasks());
    writeChildList(writer, config.getExternalDataSources());
    writeChildList(writer, config.getStyles());
    writeChildList(writer, config.getStyleItems());
    writeChildList(writer, config.getLanguages());
    writeChildList(writer, config.getCommandGroups());
    writer.endNode(); // ChildObjects
  }

  private static void writeChildList(HierarchicalStreamWriter writer, List<? extends MD> list) {
    if (list == null) {
      return;
    }
    for (var obj : list) {
      if (obj != null && obj.getName() != null) {
        var type = obj.getMdoType();
        if (type != null && type != MDOType.UNKNOWN) {
          writeElement(writer, type.nameEn(), obj.getName());
        }
      }
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
    throw new UnsupportedOperationException("ConfigurationDesignerWriteConverter is for writing only");
  }

  @Override
  public boolean canConvert(Class type) {
    return Configuration.class.isAssignableFrom(type);
  }
}
