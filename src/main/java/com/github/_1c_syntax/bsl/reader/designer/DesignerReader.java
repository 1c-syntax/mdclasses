/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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

import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
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
import com.github._1c_syntax.bsl.reader.MDReader;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.designer.converter.DesignerConverter;
import com.github._1c_syntax.bsl.supconf.ParseSupportData;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.wrapper.DesignerRootWrapper;
import com.thoughtworks.xstream.XStream;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.Optional;

@Slf4j
public class DesignerReader implements MDReader {
  @Getter(lazy = true)
  private static final ExtendXStream xstream = createXMLMapper();

  @Getter
  private final Path rootPath;

  public DesignerReader(Path path, boolean skipSupport) {
    rootPath = path;
    if (!skipSupport) {
      ParseSupportData.readSimple(DesignerPaths.parentConfigurationsPath(rootPath));
    }
  }

  @Override
  public ConfigurationSource getConfigurationSource() {
    return ConfigurationSource.DESIGNER;
  }

  @Override
  public MDClass readConfiguration() {
    var mdc = Optional.of((MDClass) read(
      DesignerPaths.mdoPath(rootPath, MDOType.CONFIGURATION, MDOType.CONFIGURATION.getName())
    ));
    return mdc.orElse(MDClasses.createConfiguration());
  }

  @Override
  public MDClass readExternalSource() {
    var value = read(rootPath);
    if (value instanceof MDClass mdc) {
      return mdc;
    } else {
      return MDClasses.createExternalReport();
    }
  }

  @Override
  public Object read(String fullName) {
    return read(rootPath, fullName);
  }

  @Override
  public Object read(Path folder, String fullName) {
    var dotPosition = fullName.indexOf('.');
    var type = MDOType.fromValue(fullName.substring(0, dotPosition));
    var name = fullName.substring(dotPosition + 1);

    if (type.isPresent()) {
      Path path;
      if (rootPath.equals(folder)) {
        path = DesignerPaths.mdoPath(folder, type.get(), name);
      } else {
        path = DesignerPaths.mdoPath(folder, name);
      }
      return read(path);
    }
    return null;
  }

  @Override
  public ExtendXStream getEXStream() {
    return getXstream();
  }

  private static ExtendXStream createXMLMapper() {
    var xStream = new ExtendXStream();

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
}
