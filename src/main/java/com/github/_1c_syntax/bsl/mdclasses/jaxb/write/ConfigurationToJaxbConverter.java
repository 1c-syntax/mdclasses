/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdclasses.CF;
import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.support.ApplicationRunMode;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.github._1c_syntax.bsl.mdo.support.InterfaceCompatibilityMode;
import com.github._1c_syntax.bsl.types.ScriptVariant;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ConfigurationChildObjects;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ConfigurationProperties;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;
import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.ObjectFactory;
import com.github._1c_syntax.bsl.mdclasses.jaxb.v8_2_managed_application_core.ClientRunMode;

import java.util.List;
import java.util.UUID;

/**
 * Преобразует корневую конфигурацию (CF) в JAXB-DTO для записи Designer XML.
 */
public final class ConfigurationToJaxbConverter {

  private static final ObjectFactory FACTORY = new ObjectFactory();

  private ConfigurationToJaxbConverter() {
  }

  /**
   * Собирает JAXB MetaDataObject из конфигурации mdclasses.
   *
   * @param configuration конфигурация
   * @return корневой элемент для маршаллинга в Designer XML
   */
  public static MetaDataObject toMetaDataObject(Configuration configuration) {
    CF cf = configuration;
    MetaDataObject root = FACTORY.createMetaDataObject();
    root.setVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.Configuration inner = FACTORY.createConfiguration();
    inner.setUuid(nonEmptyUuid(cf.getUuid()));
    inner.setFormatVersion(JaxbWriteDefaults.DEFAULT_FORMAT_VERSION);
    inner.setProperties(buildProperties(cf));
    inner.setChildObjects(buildChildObjects(cf));
    root.setConfiguration(inner);
    return root;
  }

  private static String nonEmptyUuid(String uuid) {
    String value = (uuid != null && !uuid.isBlank()) ? uuid : UUID.randomUUID().toString();
    return value.toLowerCase();
  }

  private static ConfigurationProperties buildProperties(CF c) {
    ConfigurationProperties p = FACTORY.createConfigurationProperties();
    p.setName(c.getName());
    p.setSynonym(JaxbWriteDefaults.localStringType(JaxbWriteUtils.contentForLocalString(c.getSynonym())));
    p.setComment(c.getComment() != null ? c.getComment() : "");
    p.setVendor(c.getVendor() != null ? c.getVendor() : "");
    p.setVersion(c.getVersion() != null ? c.getVersion() : "");
    ApplicationRunMode runMode = c.getDefaultRunMode();
    if (runMode == ApplicationRunMode.MANAGED_APPLICATION) {
      p.setDefaultRunMode(ClientRunMode.MANAGED_APPLICATION);
    } else if (runMode == ApplicationRunMode.ORDINARY_APPLICATION) {
      p.setDefaultRunMode(ClientRunMode.ORDINARY_APPLICATION);
    }
    ScriptVariant scriptVariant = c.getScriptVariant();
    if (scriptVariant != null) {
      p.setScriptVariant(safeScriptVariant(scriptVariant.name()));
    }
    if (c.getDefaultLanguage() != null && c.getDefaultLanguage().getMdoRef() != null && !c.getDefaultLanguage().getMdoRef().isEmpty()) {
      p.setDefaultLanguage(c.getDefaultLanguage().getMdoRef());
    }
    InterfaceCompatibilityMode ifMode = c.getInterfaceCompatibilityMode();
    if (ifMode != null && ifMode.fullName() != null) {
      p.setInterfaceCompatibilityMode(safeInterfaceCompatibilityMode(ifMode.fullName().getEn()));
    }
    CompatibilityMode compatMode = c.getCompatibilityMode();
    if (compatMode != null) {
      p.setCompatibilityMode(safeCompatibilityMode(compatMode.toString()));
    }
    CompatibilityMode extCompatMode = c.getConfigurationExtensionCompatibilityMode();
    if (extCompatMode != null) {
      p.setConfigurationExtensionCompatibilityMode(safeCompatibilityMode(extCompatMode.toString()));
    }
    return p;
  }

  private static com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.InterfaceCompatibilityMode safeInterfaceCompatibilityMode(String value) {
    if (value == null || value.isEmpty()) {
      return com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.InterfaceCompatibilityMode.VERSION_8_2;
    }
    try {
      return com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.InterfaceCompatibilityMode.fromValue(value);
    } catch (IllegalArgumentException e) {
      return com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.InterfaceCompatibilityMode.VERSION_8_2;
    }
  }

  private static com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.CompatibilityMode safeCompatibilityMode(String value) {
    if (value == null || value.isEmpty()) {
      return com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.CompatibilityMode.values()[0];
    }
    try {
      return com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.CompatibilityMode.fromValue(value);
    } catch (IllegalArgumentException e) {
      return com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.CompatibilityMode.values()[0];
    }
  }

  private static com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.ScriptVariant safeScriptVariant(String value) {
    if (value == null || value.isEmpty()) {
      return com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.ScriptVariant.values()[0];
    }
    try {
      return com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.ScriptVariant.valueOf(value);
    } catch (IllegalArgumentException e) {
      return com.github._1c_syntax.bsl.mdclasses.jaxb.v8_3_xcf_enums.ScriptVariant.values()[0];
    }
  }

  private static String shortNameFromMdoRef(String mdoRef) {
    if (mdoRef == null) {
      return "";
    }
    int dot = mdoRef.indexOf('.');
    return dot >= 0 ? mdoRef.substring(dot + 1) : mdoRef;
  }

  private static ConfigurationChildObjects buildChildObjects(CF c) {
    ConfigurationChildObjects co = FACTORY.createConfigurationChildObjects();
    addChildNames(c.getLanguages(), co.getLanguage(), MD::getName);
    addChildNames(c.getSubsystems(), co.getSubsystem(), MD::getName);
    addChildNames(c.getStyleItems(), co.getStyleItem(), MD::getName);
    addChildNames(c.getStyles(), co.getStyle(), MD::getName);
    addChildNames(c.getCommonPictures(), co.getCommonPicture(), MD::getName);
    addChildNames(c.getInterfaces(), co.getInterface(), MD::getName);
    addChildNames(c.getSessionParameters(), co.getSessionParameter(), MD::getName);
    addChildNames(c.getRoles(), co.getRole(), MD::getName);
    addChildNames(c.getCommonTemplates(), co.getCommonTemplate(), MD::getName);
    addChildNames(c.getFilterCriteria(), co.getFilterCriterion(), MD::getName);
    addChildNames(c.getCommonModules(), co.getCommonModule(), MD::getName);
    addChildNames(c.getCommonAttributes(), co.getCommonAttribute(), MD::getName);
    addChildNames(c.getExchangePlans(), co.getExchangePlan(), md -> shortNameFromMdoRef(md.getMdoRef()));
    addChildNames(c.getXDTOPackages(), co.getXDTOPackage(), MD::getName);
    addChildNames(c.getWebServices(), co.getWebService(), MD::getName);
    addChildNames(c.getHttpServices(), co.getHTTPService(), MD::getName);
    addChildNames(c.getWsReferences(), co.getWSReference(), md -> shortNameFromMdoRef(md.getMdoRef()));
    addChildNames(c.getEventSubscriptions(), co.getEventSubscription(), MD::getName);
    addChildNames(c.getScheduledJobs(), co.getScheduledJob(), MD::getName);
    addChildNames(c.getSettingsStorages(), co.getSettingsStorage(), MD::getName);
    addChildNames(c.getFunctionalOptions(), co.getFunctionalOption(), MD::getName);
    addChildNames(c.getFunctionalOptionsParameters(), co.getFunctionalOptionsParameter(), MD::getName);
    addChildNames(c.getDefinedTypes(), co.getDefinedType(), MD::getName);
    addChildNames(c.getCommonCommands(), co.getCommonCommand(), MD::getName);
    addChildNames(c.getCommandGroups(), co.getCommandGroup(), g -> shortNameFromMdoRef(g.getMdoRef()));
    addChildNames(c.getConstants(), co.getConstant(), MD::getName);
    addChildNames(c.getCommonForms(), co.getCommonForm(), MD::getName);
    addChildNames(c.getCatalogs(), co.getCatalog(), MD::getName);
    addChildNames(c.getDocuments(), co.getDocument(), MD::getName);
    addChildNames(c.getDocumentNumerators(), co.getDocumentNumerator(), MD::getName);
    addChildNames(c.getSequences(), co.getSequence(), MD::getName);
    addChildNames(c.getDocumentJournals(), co.getDocumentJournal(), MD::getName);
    addChildNames(c.getEnums(), co.getEnum(), MD::getName);
    addChildNames(c.getReports(), co.getReport(), MD::getName);
    addChildNames(c.getDataProcessors(), co.getDataProcessor(), MD::getName);
    addChildNames(c.getInformationRegisters(), co.getInformationRegister(), MD::getName);
    addChildNames(c.getAccumulationRegisters(), co.getAccumulationRegister(), MD::getName);
    addChildNames(c.getChartsOfCharacteristicTypes(), co.getChartOfCharacteristicTypes(), MD::getName);
    addChildNames(c.getChartsOfAccounts(), co.getChartOfAccounts(), MD::getName);
    addChildNames(c.getAccountingRegisters(), co.getAccountingRegister(), MD::getName);
    addChildNames(c.getChartsOfCalculationTypes(), co.getChartOfCalculationTypes(), MD::getName);
    addChildNames(c.getCalculationRegisters(), co.getCalculationRegister(), MD::getName);
    addChildNames(c.getBusinessProcesses(), co.getBusinessProcess(), MD::getName);
    addChildNames(c.getTasks(), co.getTask(), MD::getName);
    addChildNames(c.getExternalDataSources(), co.getExternalDataSource(), MD::getName);
    addChildNames(c.getIntegrationServices(), co.getIntegrationService(), MD::getName);
    addChildNames(c.getBots(), co.getBot(), MD::getName);
    addChildNames(c.getWebSocketClients(), co.getWebSocketClient(), MD::getName);
    return co;
  }

  private static <T> void addChildNames(List<T> list, List<String> target, java.util.function.Function<T, String> nameFn) {
    if (list == null) {
      return;
    }
    for (T md : list) {
      String name = nameFn.apply(md);
      if (name != null) {
        target.add(name);
      }
    }
  }
}
