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
package com.github._1c_syntax.bsl.reader.edt;

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.ExternalReport;
import com.github._1c_syntax.bsl.mdclasses.ExternalSource;
import com.github._1c_syntax.bsl.mdclasses.MDCReadSettings;
import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.mdo.Language;
import com.github._1c_syntax.bsl.mdo.children.AccountingFlag;
import com.github._1c_syntax.bsl.mdo.children.Dimension;
import com.github._1c_syntax.bsl.mdo.children.DocumentJournalColumn;
import com.github._1c_syntax.bsl.mdo.children.EnumValue;
import com.github._1c_syntax.bsl.mdo.children.ExtDimensionAccountingFlag;
import com.github._1c_syntax.bsl.mdo.children.ExternalDataSourceTable;
import com.github._1c_syntax.bsl.mdo.children.ExternalDataSourceTableField;
import com.github._1c_syntax.bsl.mdo.children.HTTPServiceMethod;
import com.github._1c_syntax.bsl.mdo.children.HTTPServiceURLTemplate;
import com.github._1c_syntax.bsl.mdo.children.IntegrationServiceChannel;
import com.github._1c_syntax.bsl.mdo.children.ObjectAttribute;
import com.github._1c_syntax.bsl.mdo.children.ObjectCommand;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdo.children.ObjectTabularSection;
import com.github._1c_syntax.bsl.mdo.children.ObjectTemplate;
import com.github._1c_syntax.bsl.mdo.children.Recalculation;
import com.github._1c_syntax.bsl.mdo.children.Resource;
import com.github._1c_syntax.bsl.mdo.children.StandardAttribute;
import com.github._1c_syntax.bsl.mdo.children.TaskAddressingAttribute;
import com.github._1c_syntax.bsl.mdo.children.WebServiceOperation;
import com.github._1c_syntax.bsl.mdo.children.WebServiceOperationParameter;
import com.github._1c_syntax.bsl.mdo.storage.EmptyFormData;
import com.github._1c_syntax.bsl.mdo.storage.FormData;
import com.github._1c_syntax.bsl.mdo.storage.ManagedFormData;
import com.github._1c_syntax.bsl.reader.MDReader;
import com.github._1c_syntax.bsl.reader.common.context.AbstractReaderContext;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.edt.converter.EDTConverter;
import com.github._1c_syntax.bsl.reader.edt.converter.Unmarshaller;
import com.github._1c_syntax.bsl.supconf.ParseSupportData;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ModuleType;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.core.ClassLoaderReference;
import com.thoughtworks.xstream.core.util.CompositeClassLoader;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
public class EDTReader implements MDReader {

  /**
   * Путь к файлу описания конфигурации
   */
  public static final String CONFIGURATION_MDO_PATH = Paths.get("src", "Configuration", "Configuration.mdo")
    .toString();

  /**
   * Имя корневого файла конфигурации
   */
  public static final String CONFIGURATION_MDO_FILE_NAME = "Configuration.mdo";

  @Getter
  private final ExtendXStream xstream;

  @Getter
  private final Path rootPath;

  @Getter
  private final MDCReadSettings readSettings;

  public EDTReader(Path path, MDCReadSettings readSettings) {
    this.xstream = createXMLMapper();
    var normalizedPath = path.toAbsolutePath();
    var file = normalizedPath.toFile();
    if (file.isFile() && CONFIGURATION_MDO_FILE_NAME.equals(file.getName())) { // передали сам файл, а не каталог
      var configurationDir = normalizedPath.getParent();
      if (configurationDir == null) {
        throw new IllegalArgumentException(
          "Не удалось определить каталог Configuration для файла " + normalizedPath);
      }
      var srcDir = configurationDir.getParent();
      var projectRoot = srcDir != null ? srcDir.getParent() : null;
      if (srcDir == null || projectRoot == null) {
        throw new IllegalArgumentException(
          "Не удалось определить корень проекта EDT для файла " + normalizedPath);
      }
      this.rootPath = projectRoot;
    } else {
      this.rootPath = path;
    }
    this.readSettings = readSettings;

    if (!readSettings.isSkipSupport()) {
      var pcbin = parentConfigurationsPath();
      if (pcbin.toFile().exists()) {
        ParseSupportData.read(pcbin);
      }
    }
  }

  @Override
  public ConfigurationSource getConfigurationSource() {
    return ConfigurationSource.EDT;
  }

  @Override
  public MDClass readConfiguration() {
    var mdc = Optional.ofNullable((MDClass) read(
      mdoPath(rootPath, MDOType.CONFIGURATION, MDOType.CONFIGURATION.nameEn())
    ));
    return mdc.orElse(Configuration.EMPTY);
  }

  @Override
  public ExternalSource readExternalSource() {
    var value = read(rootPath);
    if (value instanceof ExternalSource externalSource) {
      return externalSource;
    } else {
      return ExternalReport.EMPTY;
    }
  }

  @Override
  @Nullable
  public Object read(Path folder, String fullName) {
    var dotPosition = fullName.indexOf('.');
    var type = MDOType.fromValue(fullName.substring(0, dotPosition));
    var name = fullName.substring(dotPosition + 1);

    if (type.isPresent()) {
      Path path;
      if (rootPath.equals(folder)) {
        path = mdoPath(folder, type.get(), name);
      } else {
        path = mdoPath(folder, name);
      }
      return read(path);
    }
    return null;
  }

  @Override
  @Nullable
  public FormData readFormData(Path currentPath, String name, MDOType mdoType) {
    Path formDataPath;
    var basePath = currentPath.getParent().toString();
    if (mdoType == MDOType.COMMON_FORM) {
      formDataPath = Path.of(basePath, "Form.form");
    } else {
      formDataPath = Path.of(basePath, MDOType.FORM.groupName(), name, "Form.form");
    }

    if (!formDataPath.toFile().exists()) {
      return EmptyFormData.EMPTY;
    }
    return (FormData) read(formDataPath);
  }

  @Override
  public Path moduleFolder(Path mdoPath, MDOType mdoType) {
    if (mdoType == MDOType.EXTERNAL_DATA_SOURCE_TABLE) {
      return mdoPath.getParent().getParent();
    } else if (!MDOType.valuesWithoutChildren().contains(mdoType)) {
      return Paths.get(mdoPath.getParent().toString(), mdoType.groupName());
    } else {
      return mdoTypeFolderPath(mdoPath);
    }
  }

  @Override
  public Path modulePath(Path folder, String name, ModuleType moduleType) {
    if (ModuleType.byMDOType(MDOType.CONFIGURATION).contains(moduleType)) {
      return Paths.get(folder.toString(), MDOType.CONFIGURATION.nameEn(), moduleType.getFileName());
    }
    return Paths.get(folder.toString(), name, moduleType.getFileName());
  }

  @Override
  public Path mdoTypeFolderPath(Path mdoPath) {
    return Paths.get(FilenameUtils.getFullPathNoEndSeparator(
      FilenameUtils.getFullPathNoEndSeparator(mdoPath.toString())));
  }

  @Override
  public String subsystemsNodeName() {
    return "subsystems";
  }

  @Override
  public String configurationExtensionFilter() {
    return "(<objectBelonging>)";
  }

  @Override
  public void unmarshal(HierarchicalStreamReader reader,
                        UnmarshallingContext context,
                        AbstractReaderContext readerContext) {
    Unmarshaller.unmarshal(reader, context, readerContext);
  }

  private ExtendXStream createXMLMapper() {
    var classLoaderReference = new ClassLoaderReference(new CompositeClassLoader());
    var mapper = ExtendXStream.buildMapper(classLoaderReference);
    var xStream = new ExtendXStream(this, classLoaderReference, mapper);

    // необходимо зарегистрировать все используемые классы
    registerClasses(xStream);
    // для каждого типа данных или поля необходимо зарегистрировать конвертер
    ExtendXStream.registerConverters(xStream,
      "com.github._1c_syntax.bsl.reader.edt.converter",
      EDTConverter.class);

    return xStream;
  }

  private static void registerClasses(XStream xStream) {
    xStream.alias("accountingFlags", AccountingFlag.class);
    xStream.alias("addressingAttributes", TaskAddressingAttribute.class);
    xStream.alias("attributes", ObjectAttribute.class);
    xStream.alias("columns", DocumentJournalColumn.class);
    xStream.alias("commands", ObjectCommand.class);
    xStream.alias("dimensions", Dimension.class);
    xStream.alias("enumValues", EnumValue.class);
    xStream.alias("extDimensionAccountingFlags", ExtDimensionAccountingFlag.class);
    xStream.alias("forms", ObjectForm.class);
    xStream.alias("integrationServiceChannels", IntegrationServiceChannel.class);
    xStream.alias("languages", Language.class);
    xStream.alias("methods", HTTPServiceMethod.class);
    xStream.alias("operations", WebServiceOperation.class);
    xStream.alias("parameters", WebServiceOperationParameter.class);
    xStream.alias("recalculations", Recalculation.class);
    xStream.alias("resources", Resource.class);
    xStream.alias("tables", ExternalDataSourceTable.class);
    xStream.alias("Table", ExternalDataSourceTable.class);
    xStream.alias("tableFields", ExternalDataSourceTableField.class);
    xStream.alias("tabularSections", ObjectTabularSection.class);
    xStream.alias("templates", ObjectTemplate.class);
    xStream.alias("urlTemplates", HTTPServiceURLTemplate.class);
    xStream.alias("Form", ManagedFormData.class);
    xStream.alias("standardAttributes", StandardAttribute.class);
  }

  private Path parentConfigurationsPath() {
    return Paths.get(rootPath.toString(), "src", MDOType.CONFIGURATION.nameEn(),
      "ParentConfigurations.bin");
  }

  private static Path mdoPath(Path rootPath, MDOType type, String name) {
    return mdoPath(Paths.get(rootPath.toString(), "src", type.groupName()), name);
  }

  private static Path mdoPath(Path folder, String name) {
    return Paths.get(folder.toString(), name, name + ".mdo");
  }
}
