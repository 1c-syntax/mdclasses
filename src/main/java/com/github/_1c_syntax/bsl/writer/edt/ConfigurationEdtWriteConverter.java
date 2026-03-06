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

import com.github._1c_syntax.bsl.mdo.Language;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.List;

/**
 * Конвертер записи конфигурации в формате EDT (Configuration.mdo).
 */
public class ConfigurationEdtWriteConverter implements Converter {

  private static final String MDCLASS_NS = "http://g5.1c.ru/v8/dt/metadata/mdclass";
  private static final String NAME = "name";
  private static final String KEY = "key";
  private static final String VALUE = "value";
  private static final String SYNONYM = "synonym";
  private static final String LANGUAGE_CODE = "languageCode";

  /** Сериализует конфигурацию в EDT Configuration.mdo (name, synonym, режимы, списки ссылок и т.д.). */
  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    var config = (Configuration) source;

    writer.addAttribute("xmlns:mdclass", MDCLASS_NS);
    if (config.getUuid() != null && !config.getUuid().isEmpty()) {
      writer.addAttribute("uuid", config.getUuid());
    }

    writeElement(writer, NAME, config.getName());
    writeSynonym(writer, config.getSynonym());
    writeElement(writer, "configurationExtensionCompatibilityMode", compatibilityModeString(config.getConfigurationExtensionCompatibilityMode()));
    writeElement(writer, "defaultRunMode", config.getDefaultRunMode() != null ? config.getDefaultRunMode().fullName().getEn() : null);
    if (config.getUsePurposes() != null) {
      for (var p : config.getUsePurposes()) {
        writeElement(writer, "usePurposes", p != null ? p.fullName().getEn() : null);
      }
    }
    writeElement(writer, "scriptVariant", config.getScriptVariant() != null ? config.getScriptVariant().nameEn() : null);
    writeElement(writer, "useManagedFormInOrdinaryApplication", config.isUseManagedFormInOrdinaryApplication() ? "true" : null);
    writeElement(writer, "useOrdinaryFormInManagedApplication", config.isUseOrdinaryFormInManagedApplication() ? "true" : null);
    writeMdoRef(writer, "defaultLanguage", config.getDefaultLanguage());
    writeMultiLang(writer, "briefInformation", config.getBriefInformation());
    writeMultiLang(writer, "detailedInformation", config.getDetailedInformation());
    writeMultiLang(writer, "copyright", config.getCopyrights());
    writeElement(writer, "objectAutonumerationMode", nullToEmpty(config.getObjectAutonumerationMode()));
    writeElement(writer, "synchronousPlatformExtensionAndAddInCallUseMode",
      config.getSynchronousPlatformExtensionAndAddInCallUseMode() != null
        ? config.getSynchronousPlatformExtensionAndAddInCallUseMode().fullName().getEn() : null);
    writeElement(writer, "compatibilityMode", compatibilityModeString(config.getCompatibilityMode()));

    if (config.getLanguages() != null) {
      for (var lang : config.getLanguages()) {
        writeLanguage(writer, lang);
      }
    }

    writeRefList(writer, "subsystems", config.getSubsystems());
    writeRefList(writer, "styleItems", config.getStyleItems());
    writeRefList(writer, "paletteColors", config.getPaletteColors());
    writeRefList(writer, "styles", config.getStyles());
    writeRefList(writer, "commonPictures", config.getCommonPictures());
    writeRefList(writer, "interfaces", config.getInterfaces());
    writeRefList(writer, "sessionParameters", config.getSessionParameters());
    writeRefList(writer, "roles", config.getRoles());
    writeRefList(writer, "commonTemplates", config.getCommonTemplates());
    writeRefList(writer, "filterCriteria", config.getFilterCriteria());
    writeRefList(writer, "commonModules", config.getCommonModules());
    writeRefList(writer, "commonAttributes", config.getCommonAttributes());
    writeRefList(writer, "exchangePlans", config.getExchangePlans());
    writeRefList(writer, "xDTOPackages", config.getXDTOPackages());
    writeRefList(writer, "webServices", config.getWebServices());
    writeRefList(writer, "webSocketClients", config.getWebSocketClients());
    writeRefList(writer, "httpServices", config.getHttpServices());
    writeRefList(writer, "wsReferences", config.getWsReferences());
    writeRefList(writer, "integrationServices", config.getIntegrationServices());
    writeRefList(writer, "eventSubscriptions", config.getEventSubscriptions());
    writeRefList(writer, "scheduledJobs", config.getScheduledJobs());
    writeRefList(writer, "bots", config.getBots());
    writeRefList(writer, "settingsStorages", config.getSettingsStorages());
    writeRefList(writer, "functionalOptions", config.getFunctionalOptions());
    writeRefList(writer, "functionalOptionsParameters", config.getFunctionalOptionsParameters());
    writeRefList(writer, "definedTypes", config.getDefinedTypes());
    writeRefList(writer, "commonCommands", config.getCommonCommands());
    writeRefList(writer, "commandGroups", config.getCommandGroups());
    writeRefList(writer, "constants", config.getConstants());
    writeRefList(writer, "commonForms", config.getCommonForms());
    writeRefList(writer, "catalogs", config.getCatalogs());
    writeRefList(writer, "documents", config.getDocuments());
    writeRefList(writer, "documentNumerators", config.getDocumentNumerators());
    writeRefList(writer, "sequences", config.getSequences());
    writeRefList(writer, "documentJournals", config.getDocumentJournals());
    writeRefList(writer, "enums", config.getEnums());
    writeRefList(writer, "reports", config.getReports());
    writeRefList(writer, "dataProcessors", config.getDataProcessors());
    writeRefList(writer, "informationRegisters", config.getInformationRegisters());
    writeRefList(writer, "accumulationRegisters", config.getAccumulationRegisters());
    writeRefList(writer, "chartsOfCharacteristicTypes", config.getChartsOfCharacteristicTypes());
    writeRefList(writer, "chartsOfAccounts", config.getChartsOfAccounts());
    writeRefList(writer, "accountingRegisters", config.getAccountingRegisters());
    writeRefList(writer, "chartsOfCalculationTypes", config.getChartsOfCalculationTypes());
    writeRefList(writer, "calculationRegisters", config.getCalculationRegisters());
    writeRefList(writer, "businessProcesses", config.getBusinessProcesses());
    writeRefList(writer, "tasks", config.getTasks());
    writeRefList(writer, "externalDataSources", config.getExternalDataSources());
  }

  private static String compatibilityModeString(CompatibilityMode mode) {
    if (mode == null) {
      return "";
    }
    return mode.toString();
  }

  private static void writeMdoRef(HierarchicalStreamWriter writer, String nodeName, MdoReference ref) {
    if (ref == null) {
      return;
    }
    String refStr = ref.getMdoRef();
    if (refStr != null && !refStr.isEmpty()) {
      writeElement(writer, nodeName, refStr);
    }
  }

  private static void writeRefList(HierarchicalStreamWriter writer, String nodeName, List<? extends MD> list) {
    if (list == null) {
      return;
    }
    for (var obj : list) {
      if (obj != null && obj.getName() != null) {
        var type = obj.getMdoType();
        if (type != MDOType.UNKNOWN) {
          writeElement(writer, nodeName, type.nameEn() + "." + obj.getName());
        }
      }
    }
  }

  private static void writeLanguage(HierarchicalStreamWriter writer, Language lang) {
    if (lang == null) {
      return;
    }
    writer.startNode("languages");
    if (lang.getUuid() != null && !lang.getUuid().isEmpty()) {
      writer.addAttribute("uuid", lang.getUuid());
    }
    writeElement(writer, NAME, lang.getName());
    writeSynonym(writer, lang.getSynonym());
    writeElement(writer, LANGUAGE_CODE, lang.getLanguageCode());
    writer.endNode();
  }

  private static void writeMultiLang(HierarchicalStreamWriter writer, String nodeName, MultiLanguageString multi) {
    if (multi == null || multi.isEmpty()) {
      return;
    }
    for (var entry : multi.getContent()) {
      writer.startNode(nodeName);
      writeElement(writer, KEY, entry.getLangKey());
      writeElement(writer, VALUE, entry.getValue());
      writer.endNode();
    }
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

  private static String nullToEmpty(String s) {
    return s != null ? s : "";
  }

  /** Конвертер только для записи; чтение не поддерживается. */
  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    throw new UnsupportedOperationException("ConfigurationEdtWriteConverter is for writing only");
  }

  /** Поддерживается только тип {@link Configuration}. */
  @Override
  public boolean canConvert(Class type) {
    return Configuration.class.isAssignableFrom(type);
  }
}
