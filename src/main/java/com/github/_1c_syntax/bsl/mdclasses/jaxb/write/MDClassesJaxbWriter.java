/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.AccumulationRegister;
import com.github._1c_syntax.bsl.mdo.AccountingRegister;
import com.github._1c_syntax.bsl.mdo.BusinessProcess;
import com.github._1c_syntax.bsl.mdo.CalculationRegister;
import com.github._1c_syntax.bsl.mdo.Catalog;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdo.ChartOfAccounts;
import com.github._1c_syntax.bsl.mdo.CommandGroup;
import com.github._1c_syntax.bsl.mdo.ChartOfCalculationTypes;
import com.github._1c_syntax.bsl.mdo.ChartOfCharacteristicTypes;
import com.github._1c_syntax.bsl.mdo.CommonModule;
import com.github._1c_syntax.bsl.mdo.DataProcessor;
import com.github._1c_syntax.bsl.mdo.Document;
import com.github._1c_syntax.bsl.mdo.DocumentJournal;
import com.github._1c_syntax.bsl.mdo.ExchangePlan;
import com.github._1c_syntax.bsl.mdo.FilterCriterion;
import com.github._1c_syntax.bsl.mdo.InformationRegister;
import com.github._1c_syntax.bsl.mdo.Language;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.Report;
import com.github._1c_syntax.bsl.mdo.Role;
import com.github._1c_syntax.bsl.mdo.SettingsStorage;
import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.mdo.Task;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.mdclasses.Configuration;

import com.github._1c_syntax.bsl.mdclasses.jaxb.mdclasses.MetaDataObject;

import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Запись объектов метаданных конфигурации 1С в Designer XML через JAXB.
 */
public final class MDClassesJaxbWriter {

  private record TypeWriter(MDOType type, Function<Configuration, List<? extends MD>> getList) {}

  private static final List<TypeWriter> LIST_TYPE_WRITERS = List.of(
    new TypeWriter(MDOType.LANGUAGE, Configuration::getLanguages),
    new TypeWriter(MDOType.COMMAND_GROUP, Configuration::getCommandGroups),
    new TypeWriter(MDOType.ROLE, Configuration::getRoles),
    new TypeWriter(MDOType.REPORT, Configuration::getReports),
    new TypeWriter(MDOType.TASK, Configuration::getTasks),
    new TypeWriter(MDOType.FILTER_CRITERION, Configuration::getFilterCriteria),
    new TypeWriter(MDOType.DATA_PROCESSOR, Configuration::getDataProcessors),
    new TypeWriter(MDOType.INFORMATION_REGISTER, Configuration::getInformationRegisters),
    new TypeWriter(MDOType.ACCOUNTING_REGISTER, Configuration::getAccountingRegisters),
    new TypeWriter(MDOType.ACCUMULATION_REGISTER, Configuration::getAccumulationRegisters),
    new TypeWriter(MDOType.CALCULATION_REGISTER, Configuration::getCalculationRegisters),
    new TypeWriter(MDOType.DOCUMENT_JOURNAL, Configuration::getDocumentJournals),
    new TypeWriter(MDOType.EXCHANGE_PLAN, Configuration::getExchangePlans),
    new TypeWriter(MDOType.CHART_OF_ACCOUNTS, Configuration::getChartsOfAccounts),
    new TypeWriter(MDOType.CHART_OF_CHARACTERISTIC_TYPES, Configuration::getChartsOfCharacteristicTypes),
    new TypeWriter(MDOType.CHART_OF_CALCULATION_TYPES, Configuration::getChartsOfCalculationTypes),
    new TypeWriter(MDOType.SETTINGS_STORAGE, Configuration::getSettingsStorages),
    new TypeWriter(MDOType.BUSINESS_PROCESS, Configuration::getBusinessProcesses),
    new TypeWriter(MDOType.DOCUMENT, Configuration::getDocuments),
    new TypeWriter(MDOType.ENUM, Configuration::getEnums),
    new TypeWriter(MDOType.COMMON_MODULE, Configuration::getCommonModules)
  );

  private static final Map<MDOType, BiFunction<MD, Configuration, MetaDataObject>> CONVERTERS = new HashMap<>();

  static {
    CONVERTERS.put(MDOType.SUBSYSTEM, (md, cfg) -> SubsystemToJaxbConverter.toMetaDataObject((Subsystem) md));
    CONVERTERS.put(MDOType.CATALOG, (md, cfg) ->
      CatalogToJaxbConverter.toMetaDataObject((Catalog) md, defaultCommandGroupRef(cfg)));
    CONVERTERS.put(MDOType.ENUM, (md, cfg) -> EnumToJaxbConverter.toMetaDataObject((com.github._1c_syntax.bsl.mdo.Enum) md));
    CONVERTERS.put(MDOType.COMMON_MODULE, (md, cfg) -> CommonModuleToJaxbConverter.toMetaDataObject((CommonModule) md));
    CONVERTERS.put(MDOType.DOCUMENT, (md, cfg) -> DocumentToJaxbConverter.toMetaDataObject((Document) md));
    CONVERTERS.put(MDOType.ROLE, (md, cfg) -> RoleToJaxbConverter.toMetaDataObject((Role) md));
    CONVERTERS.put(MDOType.LANGUAGE, (md, cfg) -> LanguageToJaxbConverter.toMetaDataObject((Language) md));
    CONVERTERS.put(MDOType.COMMAND_GROUP, (md, cfg) -> CommandGroupToJaxbConverter.toMetaDataObject((CommandGroup) md));
    CONVERTERS.put(MDOType.REPORT, (md, cfg) -> ReportToJaxbConverter.toMetaDataObject((Report) md));
    CONVERTERS.put(MDOType.TASK, (md, cfg) -> TaskToJaxbConverter.toMetaDataObject((Task) md));
    CONVERTERS.put(MDOType.FILTER_CRITERION, (md, cfg) -> FilterCriterionToJaxbConverter.toMetaDataObject((FilterCriterion) md));
    CONVERTERS.put(MDOType.DATA_PROCESSOR, (md, cfg) -> DataProcessorToJaxbConverter.toMetaDataObject((DataProcessor) md));
    CONVERTERS.put(MDOType.INFORMATION_REGISTER, (md, cfg) -> InformationRegisterToJaxbConverter.toMetaDataObject((InformationRegister) md));
    CONVERTERS.put(MDOType.ACCOUNTING_REGISTER, (md, cfg) -> AccountingRegisterToJaxbConverter.toMetaDataObject((AccountingRegister) md));
    CONVERTERS.put(MDOType.ACCUMULATION_REGISTER, (md, cfg) -> AccumulationRegisterToJaxbConverter.toMetaDataObject((AccumulationRegister) md));
    CONVERTERS.put(MDOType.CALCULATION_REGISTER, (md, cfg) -> CalculationRegisterToJaxbConverter.toMetaDataObject((CalculationRegister) md));
    CONVERTERS.put(MDOType.DOCUMENT_JOURNAL, (md, cfg) -> DocumentJournalToJaxbConverter.toMetaDataObject((DocumentJournal) md));
    CONVERTERS.put(MDOType.EXCHANGE_PLAN, (md, cfg) -> ExchangePlanToJaxbConverter.toMetaDataObject((ExchangePlan) md));
    CONVERTERS.put(MDOType.CHART_OF_ACCOUNTS, (md, cfg) -> ChartOfAccountsToJaxbConverter.toMetaDataObject((ChartOfAccounts) md));
    CONVERTERS.put(MDOType.CHART_OF_CHARACTERISTIC_TYPES, (md, cfg) ->
      ChartOfCharacteristicTypesToJaxbConverter.toMetaDataObject((ChartOfCharacteristicTypes) md));
    CONVERTERS.put(MDOType.CHART_OF_CALCULATION_TYPES, (md, cfg) ->
      ChartOfCalculationTypesToJaxbConverter.toMetaDataObject((ChartOfCalculationTypes) md));
    CONVERTERS.put(MDOType.SETTINGS_STORAGE, (md, cfg) -> SettingsStorageToJaxbConverter.toMetaDataObject((SettingsStorage) md));
    CONVERTERS.put(MDOType.BUSINESS_PROCESS, (md, cfg) -> BusinessProcessToJaxbConverter.toMetaDataObject((BusinessProcess) md));
  }

  private static String defaultCommandGroupRef(Configuration c) {
    if (c == null || c.getCommandGroups() == null || c.getCommandGroups().isEmpty()) {
      return null;
    }
    return c.getCommandGroups().get(0).getMdoRef();
  }

  private MDClassesJaxbWriter() {
  }

  /**
   * Записывает объект метаданных в XML-файл в формате Designer.
   *
   * @param path путь к .xml файлу
   * @param md   объект метаданных
   * @throws IllegalArgumentException если тип объекта не поддерживается
   * @throws JAXBException            при ошибке сериализации
   * @throws IOException              при ошибке записи файла
   */
  public static void writeObjectJaxb(Path path, MD md) throws JAXBException, IOException {
    writeObjectJaxb(path, md, null);
  }

  /**
   * Записывает объект метаданных в XML. Для справочников при передаче конфигурации
   * используется первая группа команд как группа по умолчанию для команд.
   *
   * @param path          путь к .xml файлу
   * @param md            объект метаданных
   * @param configuration конфигурация или null
   */
  public static void writeObjectJaxb(Path path, MD md, Configuration configuration) throws JAXBException, IOException {
    var converter = CONVERTERS.get(md.getMdoType());
    if (converter == null) {
      throw new IllegalArgumentException("JAXB writer does not support type: " + md.getMdoType());
    }
    DesignerJaxbWriter.write(path, converter.apply(md, configuration));
  }

  /**
   * Записывает корневую конфигурацию в Configuration.xml.
   *
   * @param path          путь к файлу (например Configuration.xml)
   * @param configuration конфигурация
   */
  public static void writeConfigurationJaxb(Path path, Configuration configuration)
    throws JAXBException, IOException {
    var jaxb = ConfigurationToJaxbConverter.toMetaDataObject(configuration);
    DesignerJaxbWriter.write(path, jaxb);
  }

  /**
   * Возвращает путь к XML-файлу объекта в дереве Designer.
   *
   * @param rootPath корень выгрузки
   * @param md      объект метаданных
   * @return путь к .xml файлу
   */
  public static Path getPathForObject(Path rootPath, MD md) {
    return DesignerPathResolver.pathForObject(rootPath, md);
  }

  /**
   * Возвращает путь к XML-файлу объекта с учётом родительской подсистемы (для вложенных подсистем).
   *
   * @param rootPath            корень выгрузки
   * @param md                  объект метаданных
   * @param parentSubsystemPath путь к .xml родительской подсистемы или null
   * @return путь к .xml файлу
   */
  public static Path getPathForObject(Path rootPath, MD md, Path parentSubsystemPath) {
    return DesignerPathResolver.pathForObject(rootPath, md, parentSubsystemPath);
  }

  /**
   * Записывает конфигурацию в папку в формате Designer (Configuration.xml и дочерние объекты).
   *
   * @param rootPath       корень выгрузки
   * @param configuration конфигурация для записи
   */
  public static void writeConfigurationToFolder(Path rootPath, Configuration configuration)
    throws JAXBException, IOException {
    writeConfigurationToFolder(rootPath, configuration, WriteOptions.defaults());
  }

  /**
   * Записывает конфигурацию в папку с заданными опциями.
   *
   * @param rootPath       корень выгрузки
   * @param configuration конфигурация для записи
   * @param options        опции (фильтр типов и т.д.) или null для записи всего
   */
  public static void writeConfigurationToFolder(Path rootPath, Configuration configuration,
    WriteOptions options) throws JAXBException, IOException {
    Path configPath = DesignerPathResolver.configurationPath(rootPath);
    Files.createDirectories(configPath.getParent());
    writeConfigurationJaxb(configPath, configuration);

    Set<MDOType> filter = options != null ? options.getTypeFilter() : null;

    writeSubsystemsIfAllowed(rootPath, configuration, filter);
    writeCatalogsIfAllowed(rootPath, configuration, filter);

    for (TypeWriter tw : LIST_TYPE_WRITERS) {
      if (shouldWriteType(filter, tw.type())) {
        writeList(rootPath, tw.getList().apply(configuration));
      }
    }
  }

  private static boolean shouldWriteType(Set<MDOType> filter, MDOType type) {
    return filter == null || filter.contains(type);
  }

  private static void writeSubsystemsIfAllowed(Path rootPath, Configuration configuration, Set<MDOType> filter)
    throws JAXBException, IOException {
    if (configuration.getSubsystems() == null || !shouldWriteType(filter, MDOType.SUBSYSTEM)) {
      return;
    }
    for (Subsystem s : configuration.getSubsystems()) {
      writeSubsystemRecursive(rootPath, s, null);
    }
  }

  private static void writeCatalogsIfAllowed(Path rootPath, Configuration configuration, Set<MDOType> filter)
    throws JAXBException, IOException {
    if (configuration.getCatalogs() == null || !shouldWriteType(filter, MDOType.CATALOG)) {
      return;
    }
    for (Catalog c : configuration.getCatalogs()) {
      Path path = DesignerPathResolver.pathForObject(rootPath, c);
      Files.createDirectories(path.getParent());
      writeObjectJaxb(path, c, configuration);
      writeCatalogForms(rootPath, c);
    }
  }

  private static void writeList(Path rootPath, List<? extends MD> list) throws JAXBException, IOException {
    if (list == null) {
      return;
    }
    for (MD md : list) {
      Path path = DesignerPathResolver.pathForObject(rootPath, md);
      Files.createDirectories(path.getParent());
      writeObjectJaxb(path, md);
    }
  }

  private static void writeCatalogForms(Path rootPath, Catalog catalog) throws JAXBException, IOException {
    if (catalog.getForms() == null) {
      return;
    }
    for (ObjectForm form : catalog.getForms()) {
      String formName = form.getName();
      if (formName == null || formName.isEmpty()) {
        continue;
      }
      Path formPath = DesignerPathResolver.pathForForm(rootPath, "Catalogs", catalog.getName(), formName);
      Files.createDirectories(formPath.getParent());
      var jaxb = FormToJaxbConverter.toMetaDataObject(form);
      DesignerJaxbWriter.write(formPath, jaxb);
    }
  }

  private static void writeSubsystemRecursive(Path rootPath, Subsystem subsystem, Path parentSubsystemPath)
    throws JAXBException, IOException {
    Path path = DesignerPathResolver.pathForObject(rootPath, subsystem, parentSubsystemPath);
    Files.createDirectories(path.getParent());
    writeObjectJaxb(path, subsystem);

    List<Subsystem> children = subsystem.getSubsystems();
    if (children != null && !children.isEmpty()) {
      for (Subsystem child : children) {
        writeSubsystemRecursive(rootPath, child, path);
      }
    }
  }
}
