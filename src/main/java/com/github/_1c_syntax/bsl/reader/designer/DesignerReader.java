/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2024
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
package com.github._1c_syntax.bsl.reader.designer;

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.ExternalReport;
import com.github._1c_syntax.bsl.mdclasses.ExternalSource;
import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.mdo.ExchangePlan;
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
import com.github._1c_syntax.bsl.mdo.children.TaskAddressingAttribute;
import com.github._1c_syntax.bsl.mdo.children.WebServiceOperation;
import com.github._1c_syntax.bsl.mdo.children.WebServiceOperationParameter;
import com.github._1c_syntax.bsl.mdo.storage.EmptyFormData;
import com.github._1c_syntax.bsl.mdo.storage.FormData;
import com.github._1c_syntax.bsl.mdo.storage.ManagedFormData;
import com.github._1c_syntax.bsl.reader.MDReader;
import com.github._1c_syntax.bsl.reader.common.context.AbstractReaderContext;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.designer.converter.DesignerConverter;
import com.github._1c_syntax.bsl.reader.designer.converter.DesignerRootWrapper;
import com.github._1c_syntax.bsl.reader.designer.converter.Unmarshaller;
import com.github._1c_syntax.bsl.supconf.ParseSupportData;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ModuleType;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.core.ClassLoaderReference;
import com.thoughtworks.xstream.core.util.CompositeClassLoader;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.xml.QNameMap;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
public class DesignerReader implements MDReader {

  /**
   * Путь к файлу описания конфигурации
   */
  public static final String CONFIGURATION_MDO_PATH = "Configuration.xml";

  @Getter
  private final ExtendXStream xstream;

  @Getter
  private final Path rootPath;

  public DesignerReader(Path path, boolean skipSupport) {
    xstream =  createXMLMapper();
    rootPath = path;
    if (!skipSupport) {
      ParseSupportData.readSimple(parentConfigurationsPath());
    }
  }

  @Override
  @NonNull
  public ConfigurationSource getConfigurationSource() {
    return ConfigurationSource.DESIGNER;
  }

  @Override
  @NonNull
  public MDClass readConfiguration() {
    var mdc = Optional.ofNullable((MDClass) read(
      mdoPath(rootPath, MDOType.CONFIGURATION, MDOType.CONFIGURATION.getName())
    ));
    return mdc.orElse(Configuration.EMPTY);
  }

  @Override
  @NonNull
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
    var formDataPath = Paths.get(currentPath.getParent().toString(), name, "Ext", "Form.xml");
    if (!formDataPath.toFile().exists()) {
      return EmptyFormData.getEmpty();
    }
    return (FormData) read(formDataPath);
  }

  @Override
  @NonNull
  public Path moduleFolder(Path mdoPath, MDOType mdoType) {
    if (mdoType == MDOType.COMMAND) {
      return childrenFolder(mdoPath, mdoType);
    } else if (!MDOType.valuesWithoutChildren().contains(mdoType)) {
      return mdoPath.getParent();
    } else {
      return mdoTypeFolderPath(mdoPath);
    }
  }

  @Override
  @NonNull
  public Path modulePath(Path folder, String name, ModuleType moduleType) {
    var subdirectory = "Ext";

    if (moduleType == ModuleType.FormModule) {
      subdirectory = Path.of(subdirectory, "Form").toString();
    }

    if (!ModuleType.byMDOType(MDOType.CONFIGURATION).contains(moduleType)) {
      subdirectory = Path.of(name, subdirectory).toString();
    }

    return Paths.get(folder.toString(), subdirectory, moduleType.getFileName());
  }

  @Override
  @NonNull
  public Path mdoTypeFolderPath(Path mdoPath) {
    return Paths.get(FilenameUtils.getFullPathNoEndSeparator(mdoPath.toString()));
  }

  @Override
  @NonNull
  public String subsystemsNodeName() {
    return "Subsystem";
  }

  @Override
  @NonNull
  public String configurationExtensionFilter() {
    return "(<ObjectBelonging>)";
  }

  @Override
  public void unmarshal(HierarchicalStreamReader reader,
                        UnmarshallingContext context,
                        AbstractReaderContext readerContext) {
    Unmarshaller.unmarshal(reader, context, readerContext);
  }

  private ExtendXStream createXMLMapper() {
    var qNameMap = new QNameMap();
    qNameMap.registerMapping(new QName("http://v8.1c.ru/8.3/xcf/logform", "Form"), ManagedFormData.class);

    var classLoaderReference = new ClassLoaderReference(new CompositeClassLoader());
    var mapper = ExtendXStream.buildMapper(classLoaderReference);

    var xStream = new ExtendXStream(this, qNameMap, classLoaderReference, mapper);
    // необходимо зарегистрировать все используемые классы
    registerClasses(xStream);
    // для каждого типа данных или поля необходимо зарегистрировать конвертер
    ExtendXStream.registerConverters(xStream,
      "com.github._1c_syntax.bsl.reader.designer.converter",
      DesignerConverter.class);

    return xStream;
  }

  private static void registerClasses(XStream xStream) {
    xStream.alias("MetaDataObject", DesignerRootWrapper.class);
    xStream.alias("AccountingFlag", AccountingFlag.class);
    xStream.alias("AddressingAttribute", TaskAddressingAttribute.class);
    xStream.alias("Attribute", ObjectAttribute.class);
    xStream.alias("Dimension", Dimension.class);
    xStream.alias("EnumValue", EnumValue.class);
    xStream.alias("ExchangePlanContent", ExchangePlan.RecordContent.class);
    xStream.alias("ExtDimensionAccountingFlag", ExtDimensionAccountingFlag.class);
    xStream.alias("Column", DocumentJournalColumn.class);
    xStream.alias("Command", ObjectCommand.class);
    xStream.alias("Field", ExternalDataSourceTableField.class);
    xStream.alias("Form", ObjectForm.class);
    xStream.alias("IntegrationServiceChannel", IntegrationServiceChannel.class);
    xStream.alias("Method", HTTPServiceMethod.class);
    xStream.alias("Operation", WebServiceOperation.class);
    xStream.alias("Parameter", WebServiceOperationParameter.class);
    xStream.alias("Recalculation", Recalculation.class);
    xStream.alias("Resource", Resource.class);
    xStream.alias("Table", ExternalDataSourceTable.class);
    xStream.alias("TabularSection", ObjectTabularSection.class);
    xStream.alias("Template", ObjectTemplate.class);
    xStream.alias("URLTemplate", HTTPServiceURLTemplate.class);
  }

  private Path parentConfigurationsPath() {
    return Paths.get(rootPath.toString(), "Ext", "ParentConfigurations.bin");
  }

  private static Path mdoPath(Path path, MDOType type, String name) {
    return mdoPath(Paths.get(path.toString(), type.getGroupName()), name);
  }

  private static Path mdoPath(Path folder, String name) {
    return Paths.get(folder.toString(), name + ".xml");
  }

  private static Path childrenFolder(Path path, MDOType type) {
    return Paths.get(path.getParent().toString(), FilenameUtils.getBaseName(path.toString()), type.getGroupName());
  }
}
