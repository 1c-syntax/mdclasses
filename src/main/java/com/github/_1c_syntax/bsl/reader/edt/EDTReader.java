/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2022
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

import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
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
import com.github._1c_syntax.bsl.mdo.children.TaskAddressingAttribute;
import com.github._1c_syntax.bsl.mdo.children.WebServiceOperation;
import com.github._1c_syntax.bsl.mdo.children.WebServiceOperationParameter;
import com.github._1c_syntax.bsl.reader.MDReader;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.edt.converter.EDTConverter;
import com.github._1c_syntax.bsl.supconf.ParseSupportData;
import com.github._1c_syntax.bsl.types.MDOType;
import com.thoughtworks.xstream.XStream;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.Optional;

@Slf4j
public class EDTReader implements MDReader {

  @Getter(lazy = true)
  private static final ExtendXStream xstream = createXMLMapper();

  @Getter
  private final Path rootPath;

  public EDTReader(Path path, boolean skipSupport) {
    rootPath = path;
    if (!skipSupport) {
      ParseSupportData.readSimple(EDTPaths.parentConfigurationsPath(rootPath));
    }
  }

  @Override
  public MDClass readConfiguration() {
    var mdc = Optional.of((MDClass) read(
      EDTPaths.mdoPath(rootPath, MDOType.CONFIGURATION, MDOType.CONFIGURATION.getName())
    ));
    return mdc.orElse(MDClasses.createConfiguration());
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
        path = EDTPaths.mdoPath(folder, type.get(), name);
      } else {
        path = EDTPaths.mdoPath(folder, name);
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
  }
}
