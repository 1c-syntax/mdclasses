/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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
package com.github._1c_syntax.bsl.mdclasses;

import com.github._1c_syntax.bsl.mdo.AccountingRegister;
import com.github._1c_syntax.bsl.mdo.AccumulationRegister;
import com.github._1c_syntax.bsl.mdo.Bot;
import com.github._1c_syntax.bsl.mdo.BusinessProcess;
import com.github._1c_syntax.bsl.mdo.CalculationRegister;
import com.github._1c_syntax.bsl.mdo.Catalog;
import com.github._1c_syntax.bsl.mdo.ChartOfAccounts;
import com.github._1c_syntax.bsl.mdo.ChartOfCalculationTypes;
import com.github._1c_syntax.bsl.mdo.ChartOfCharacteristicTypes;
import com.github._1c_syntax.bsl.mdo.CommandGroup;
import com.github._1c_syntax.bsl.mdo.CommonAttribute;
import com.github._1c_syntax.bsl.mdo.CommonCommand;
import com.github._1c_syntax.bsl.mdo.CommonForm;
import com.github._1c_syntax.bsl.mdo.CommonModule;
import com.github._1c_syntax.bsl.mdo.CommonPicture;
import com.github._1c_syntax.bsl.mdo.CommonTemplate;
import com.github._1c_syntax.bsl.mdo.Constant;
import com.github._1c_syntax.bsl.mdo.DataProcessor;
import com.github._1c_syntax.bsl.mdo.DefinedType;
import com.github._1c_syntax.bsl.mdo.Document;
import com.github._1c_syntax.bsl.mdo.DocumentJournal;
import com.github._1c_syntax.bsl.mdo.DocumentNumerator;
import com.github._1c_syntax.bsl.mdo.Enum;
import com.github._1c_syntax.bsl.mdo.EventSubscription;
import com.github._1c_syntax.bsl.mdo.ExchangePlan;
import com.github._1c_syntax.bsl.mdo.ExternalDataSource;
import com.github._1c_syntax.bsl.mdo.FilterCriterion;
import com.github._1c_syntax.bsl.mdo.FunctionalOption;
import com.github._1c_syntax.bsl.mdo.FunctionalOptionsParameter;
import com.github._1c_syntax.bsl.mdo.HTTPService;
import com.github._1c_syntax.bsl.mdo.InformationRegister;
import com.github._1c_syntax.bsl.mdo.IntegrationService;
import com.github._1c_syntax.bsl.mdo.Interface;
import com.github._1c_syntax.bsl.mdo.Language;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.PaletteColor;
import com.github._1c_syntax.bsl.mdo.Report;
import com.github._1c_syntax.bsl.mdo.Role;
import com.github._1c_syntax.bsl.mdo.ScheduledJob;
import com.github._1c_syntax.bsl.mdo.Sequence;
import com.github._1c_syntax.bsl.mdo.SessionParameter;
import com.github._1c_syntax.bsl.mdo.SettingsStorage;
import com.github._1c_syntax.bsl.mdo.Style;
import com.github._1c_syntax.bsl.mdo.StyleItem;
import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.mdo.Task;
import com.github._1c_syntax.bsl.mdo.WSReference;
import com.github._1c_syntax.bsl.mdo.WebService;
import com.github._1c_syntax.bsl.mdo.XDTOPackage;
import com.github._1c_syntax.bsl.mdo.support.ScriptVariant;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.ModuleType;
import com.github._1c_syntax.utils.Lazy;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Singular;
import lombok.Value;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Проект решения, включающий в себя конфигурацию, расширения и внешние обработки и отчеты
 */
@Value
@Builder
public class Project implements ConfigurationTree {
  /**
   * Основная конфигурация
   */
  @Default
  Configuration configuration = Configuration.EMPTY;

  /**
   * Набор расширений конфигурации
   */
  @Singular
  Map<String, ConfigurationExtension> extensions;

  @Singular
  List<Subsystem> subsystems;
  @Singular
  List<CommonModule> commonModules;
  @Singular
  List<SessionParameter> sessionParameters;
  @Singular
  List<Role> roles;
  @Singular
  List<CommonAttribute> commonAttributes;
  @Singular
  List<ExchangePlan> exchangePlans;
  @Singular("filterCriterion")
  List<FilterCriterion> filterCriteria;
  @Singular
  List<EventSubscription> eventSubscriptions;
  @Singular
  List<ScheduledJob> scheduledJobs;
  @Singular
  List<Bot> bots;
  @Singular
  List<FunctionalOption> functionalOptions;
  @Singular
  List<FunctionalOptionsParameter> functionalOptionsParameters;
  @Singular
  List<DefinedType> definedTypes;
  @Singular
  List<SettingsStorage> settingsStorages;
  @Singular
  List<CommonForm> commonForms;
  @Singular
  List<CommonCommand> commonCommands;
  @Singular
  List<CommandGroup> commandGroups;
  @Singular
  List<CommonTemplate> commonTemplates;
  @Singular
  List<CommonPicture> commonPictures;
  @Singular("Interface")
  List<Interface> interfaces;
  @Singular
  List<XDTOPackage> xDTOPackages;
  @Singular
  List<WebService> webServices;
  @Singular
  List<HTTPService> httpServices;
  @Singular
  List<WSReference> wsReferences;
  @Singular
  List<IntegrationService> integrationServices;
  @Singular
  List<StyleItem> styleItems;
  @Singular
  List<PaletteColor> paletteColors;
  @Singular
  List<Style> styles;
  @Singular
  List<Language> languages;
  @Singular
  List<Constant> constants;
  @Singular
  List<Catalog> catalogs;
  @Singular
  List<Document> documents;
  @Singular
  List<DocumentNumerator> documentNumerators;
  @Singular
  List<Sequence> sequences;
  @Singular
  List<DocumentJournal> documentJournals;
  @Singular("Enum")
  List<Enum> enums;
  @Singular
  List<Report> reports;
  @Singular
  List<DataProcessor> dataProcessors;
  @Singular("chartOfCharacteristicTypes")
  List<ChartOfCharacteristicTypes> chartsOfCharacteristicTypes;
  @Singular("chartOfAccounts")
  List<ChartOfAccounts> chartsOfAccounts;
  @Singular("chartOfCalculationTypes")
  List<ChartOfCalculationTypes> chartsOfCalculationTypes;
  @Singular
  List<InformationRegister> informationRegisters;
  @Singular
  List<AccumulationRegister> accumulationRegisters;
  @Singular
  List<AccountingRegister> accountingRegisters;
  @Singular
  List<CalculationRegister> calculationRegisters;
  @Singular
  List<BusinessProcess> businessProcesses;
  @Singular
  List<Task> tasks;
  @Singular
  List<ExternalDataSource> externalDataSources;

  Lazy<List<MD>> children = new Lazy<>(this::computeChildren);
  Lazy<List<MD>> plainChildren = new Lazy<>(this::computePlainChildren);

  Lazy<Map<URI, ModuleType>> modulesByType = new Lazy<>(this::computeModulesByType);
  Lazy<Map<URI, Module>> modulesByURI = new Lazy<>(this::computeModulesByURI);
  Lazy<Map<URI, MD>> modulesByObject = new Lazy<>(this::computeModulesByObject);
  Lazy<Map<String, CommonModule>> commonModulesByName = new Lazy<>(this::computeCommonModulesByName);
  Lazy<Map<MdoReference, MD>> childrenByMdoRef = new Lazy<>(this::computeChildrenByMdoRef);

  Lazy<List<Module>> allModules = new Lazy<>(this::computeAllModules);

  public static ProjectBuilder create(Configuration configuration) {
    if (configuration.isEmpty()) {
      return Project.builder();
    }

    var builder = Project.builder();
    builder.configuration(configuration);
    builder.subsystems(configuration.getSubsystems());
    builder.commonModules(configuration.getCommonModules());
    builder.sessionParameters(configuration.getSessionParameters());
    builder.roles(configuration.getRoles());
    builder.commonAttributes(configuration.getCommonAttributes());
    builder.exchangePlans(configuration.getExchangePlans());
    builder.filterCriteria(configuration.getFilterCriteria());
    builder.eventSubscriptions(configuration.getEventSubscriptions());
    builder.scheduledJobs(configuration.getScheduledJobs());
    builder.bots(configuration.getBots());
    builder.functionalOptions(configuration.getFunctionalOptions());
    builder.functionalOptionsParameters(configuration.getFunctionalOptionsParameters());
    builder.definedTypes(configuration.getDefinedTypes());
    builder.settingsStorages(configuration.getSettingsStorages());
    builder.commonForms(configuration.getCommonForms());
    builder.commonCommands(configuration.getCommonCommands());
    builder.commandGroups(configuration.getCommandGroups());
    builder.commonTemplates(configuration.getCommonTemplates());
    builder.commonPictures(configuration.getCommonPictures());
    builder.interfaces(configuration.getInterfaces());
    builder.xDTOPackages(configuration.getXDTOPackages());
    builder.webServices(configuration.getWebServices());
    builder.httpServices(configuration.getHttpServices());
    builder.wsReferences(configuration.getWsReferences());
    builder.integrationServices(configuration.getIntegrationServices());
    builder.styleItems(configuration.getStyleItems());
    builder.paletteColors(configuration.getPaletteColors());
    builder.styles(configuration.getStyles());
    builder.languages(configuration.getLanguages());
    builder.constants(configuration.getConstants());
    builder.catalogs(configuration.getCatalogs());
    builder.documents(configuration.getDocuments());
    builder.documentNumerators(configuration.getDocumentNumerators());
    builder.sequences(configuration.getSequences());
    builder.documentJournals(configuration.getDocumentJournals());
    builder.enums(configuration.getEnums());
    builder.reports(configuration.getReports());
    builder.dataProcessors(configuration.getDataProcessors());
    builder.chartsOfCharacteristicTypes(configuration.getChartsOfCharacteristicTypes());
    builder.chartsOfAccounts(configuration.getChartsOfAccounts());
    builder.chartsOfCalculationTypes(configuration.getChartsOfCalculationTypes());
    builder.informationRegisters(configuration.getInformationRegisters());
    builder.accumulationRegisters(configuration.getAccumulationRegisters());
    builder.accountingRegisters(configuration.getAccountingRegisters());
    builder.calculationRegisters(configuration.getCalculationRegisters());
    builder.businessProcesses(configuration.getBusinessProcesses());
    builder.tasks(configuration.getTasks());
    builder.externalDataSources(configuration.getExternalDataSources());
    return builder;
  }

  public static void addExtension(ProjectBuilder builder, ConfigurationExtension extension) {
    if (extension.isEmpty()) {
      return;
    }

    builder.extension(extension.getName(), extension);

    addChildren(builder.subsystems, extension.getSubsystems());
    addChildren(builder.commonModules, extension.getCommonModules());
    addChildren(builder.sessionParameters, extension.getSessionParameters());
    addChildren(builder.roles, extension.getRoles());
    addChildren(builder.commonAttributes, extension.getCommonAttributes());
    addChildren(builder.exchangePlans, extension.getExchangePlans());
    addChildren(builder.filterCriteria, extension.getFilterCriteria());
    addChildren(builder.eventSubscriptions, extension.getEventSubscriptions());
    addChildren(builder.scheduledJobs, extension.getScheduledJobs());
    addChildren(builder.bots, extension.getBots());
    addChildren(builder.functionalOptions, extension.getFunctionalOptions());
    addChildren(builder.functionalOptionsParameters, extension.getFunctionalOptionsParameters());
    addChildren(builder.definedTypes, extension.getDefinedTypes());
    addChildren(builder.settingsStorages, extension.getSettingsStorages());
    addChildren(builder.commonForms, extension.getCommonForms());
    addChildren(builder.commonCommands, extension.getCommonCommands());
    addChildren(builder.commandGroups, extension.getCommandGroups());
    addChildren(builder.commonTemplates, extension.getCommonTemplates());
    addChildren(builder.commonPictures, extension.getCommonPictures());
    addChildren(builder.interfaces, extension.getInterfaces());
    addChildren(builder.xDTOPackages, extension.getXDTOPackages());
    addChildren(builder.webServices, extension.getWebServices());
    addChildren(builder.httpServices, extension.getHttpServices());
    addChildren(builder.wsReferences, extension.getWsReferences());
    addChildren(builder.integrationServices, extension.getIntegrationServices());
    addChildren(builder.styleItems, extension.getStyleItems());
    addChildren(builder.paletteColors, extension.getPaletteColors());
    addChildren(builder.styles, extension.getStyles());
    addChildren(builder.languages, extension.getLanguages());
    addChildren(builder.constants, extension.getConstants());
    addChildren(builder.catalogs, extension.getCatalogs());
    addChildren(builder.documents, extension.getDocuments());
    addChildren(builder.documentNumerators, extension.getDocumentNumerators());
    addChildren(builder.sequences, extension.getSequences());
    addChildren(builder.documentJournals, extension.getDocumentJournals());
    addChildren(builder.enums, extension.getEnums());
    addChildren(builder.reports, extension.getReports());
    addChildren(builder.dataProcessors, extension.getDataProcessors());
    addChildren(builder.chartsOfCharacteristicTypes, extension.getChartsOfCharacteristicTypes());
    addChildren(builder.chartsOfAccounts, extension.getChartsOfAccounts());
    addChildren(builder.chartsOfCalculationTypes, extension.getChartsOfCalculationTypes());
    addChildren(builder.informationRegisters, extension.getInformationRegisters());
    addChildren(builder.accumulationRegisters, extension.getAccumulationRegisters());
    addChildren(builder.accountingRegisters, extension.getAccountingRegisters());
    addChildren(builder.calculationRegisters, extension.getCalculationRegisters());
    addChildren(builder.businessProcesses, extension.getBusinessProcesses());
    addChildren(builder.tasks, extension.getTasks());
    addChildren(builder.externalDataSources, extension.getExternalDataSources());
  }

  public List<Module> getAllModules() {
    return allModules.getOrCompute();
  }

  public List<MD> getChildren() {
    return children.getOrCompute();
  }

  public List<MD> getPlainChildren() {
    return plainChildren.getOrCompute();
  }

  public Map<URI, ModuleType> getModulesByType() {
    return modulesByType.getOrCompute();
  }

  public Map<URI, MD> getModulesByObject() {
    return modulesByObject.getOrCompute();
  }

  public Map<URI, Module> getModulesByURI() {
    return modulesByURI.getOrCompute();
  }

  public Map<String, CommonModule> getCommonModulesByName() {
    return commonModulesByName.getOrCompute();
  }

  public Map<MdoReference, MD> getChildrenByMdoRef() {
    return childrenByMdoRef.getOrCompute();
  }

  public ModuleType getModuleTypeByURI(URI uri) {
    return getModulesByType().getOrDefault(uri, ModuleType.UNKNOWN);
  }

  public Map<ModuleType, List<URI>> mdoModuleTypes(String mdoRef) {
    return mdoModuleTypes(MdoReference.create(mdoRef));
  }

  public Map<ModuleType, List<URI>> mdoModuleTypes(MdoReference mdoRef) {
    Map<ModuleType, List<URI>> result = new HashMap<>();
    computeModuleTypes(result, configuration, mdoRef);

    for (ConfigurationExtension ext : extensions.values()) {
      computeModuleTypes(result, ext, mdoRef);
    }

    return result;
  }

  private void computeModuleTypes(Map<ModuleType, List<URI>> result, CF cf, MdoReference mdoRef) {
    for (Map.Entry<ModuleType, URI> entry : cf.mdoModuleTypes(mdoRef).entrySet()) {
      ModuleType moduleType = entry.getKey();
      URI uri = entry.getValue();
      var uris = result.getOrDefault(moduleType, new ArrayList<>());
      uris.add(uri);
      result.put(moduleType, uris);
    }
  }

  public Optional<MD> findChild(MdoReference ref) {
    return Optional.ofNullable(getChildrenByMdoRef().get(ref));
  }

  public Optional<MD> findChild(URI uri) {
    return Optional.ofNullable(getModulesByObject().get(uri));
  }

  public Optional<MD> findChild(Predicate<? super MD> predicate) {
    return getPlainChildren().stream().filter(predicate).findFirst();
  }

  public Optional<Module> getModuleByUri(URI uri) {
    return Optional.ofNullable(getModulesByURI().get(uri));
  }

  public List<Subsystem> includedSubsystems(MdoReference mdoReference, boolean addParentSubsystem) {
    List<Subsystem> list = new ArrayList<>(configuration.includedSubsystems(mdoReference, addParentSubsystem));
    extensions.values().forEach(ext -> list.addAll(ext.includedSubsystems(mdoReference, addParentSubsystem)));
    return list;
  }

  public List<Subsystem> includedSubsystems(MD md, boolean addParentSubsystem) {
    return includedSubsystems(md.getMdoReference(), addParentSubsystem);
  }

  public ScriptVariant getScriptVariant() {
    return configuration.getScriptVariant();
  }

  public boolean isEmpty() {
    return configuration.isEmpty() && extensions.isEmpty();
  }

  private static <T> void addChildren(List<T> destination, List<T> source) {
    destination.addAll(source);
  }

  private List<MD> computeChildren() {
    List<MD> list = new ArrayList<>(configuration.getChildren());
    extensions.values().forEach(ext -> list.addAll(ext.getChildren()));
    return Collections.unmodifiableList(list);
  }

  private List<MD> computePlainChildren() {
    List<MD> list = new ArrayList<>(configuration.getPlainChildren());
    extensions.values().forEach(ext -> list.addAll(ext.getPlainChildren()));
    return Collections.unmodifiableList(list);
  }

  private Map<URI, ModuleType> computeModulesByType() {
    Map<URI, ModuleType> map = new HashMap<>(configuration.getModulesByType());
    extensions.values().forEach(ext -> map.putAll(ext.getModulesByType()));
    return Collections.unmodifiableMap(map);
  }

  private Map<URI, MD> computeModulesByObject() {
    Map<URI, MD> map = new HashMap<>(configuration.getModulesByObject());
    extensions.values().forEach(ext -> map.putAll(ext.getModulesByObject()));
    return Collections.unmodifiableMap(map);
  }

  private List<Module> computeAllModules() {
    List<Module> list = new ArrayList<>(configuration.getAllModules());
    extensions.values().forEach(ext -> list.addAll(ext.getAllModules()));
    return Collections.unmodifiableList(list);
  }

  private Map<URI, Module> computeModulesByURI() {
    Map<URI, Module> map = new HashMap<>(configuration.getModulesByURI());
    extensions.values().forEach(ext -> map.putAll(ext.getModulesByURI()));
    return Collections.unmodifiableMap(map);
  }

  private Map<String, CommonModule> computeCommonModulesByName() {
    Map<String, CommonModule> map = new HashMap<>(configuration.getCommonModulesByName());
    extensions.values().forEach(ext -> map.putAll(ext.getCommonModulesByName()));
    return Collections.unmodifiableMap(map);
  }

  private Map<MdoReference, MD> computeChildrenByMdoRef() {
    Map<MdoReference, MD> map = new HashMap<>(configuration.getChildrenByMdoRef());
    extensions.values().forEach(ext -> map.putAll(ext.getChildrenByMdoRef()));
    return Collections.unmodifiableMap(map);
  }

}
