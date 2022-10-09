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

import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.reader.MDReader;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.XStreamUtils;
import com.github._1c_syntax.bsl.reader.edt.converter.EDTConverter;
import com.github._1c_syntax.bsl.supconf.ParseSupportData;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectComplex;
import com.github._1c_syntax.mdclasses.mdo.MDSubsystem;
import com.github._1c_syntax.mdclasses.mdo.attributes.AccountingFlag;
import com.github._1c_syntax.mdclasses.mdo.attributes.AddressingAttribute;
import com.github._1c_syntax.mdclasses.mdo.attributes.Attribute;
import com.github._1c_syntax.mdclasses.mdo.attributes.Column;
import com.github._1c_syntax.mdclasses.mdo.attributes.Dimension;
import com.github._1c_syntax.mdclasses.mdo.attributes.ExtDimensionAccountingFlag;
import com.github._1c_syntax.mdclasses.mdo.attributes.Recalculation;
import com.github._1c_syntax.mdclasses.mdo.attributes.Resource;
import com.github._1c_syntax.mdclasses.mdo.attributes.TabularSection;
import com.github._1c_syntax.mdclasses.mdo.children.form.DynamicListExtInfo;
import com.github._1c_syntax.mdclasses.mdo.children.form.FormData;
import com.github._1c_syntax.mdclasses.mdo.children.form.FormItem;
import com.github._1c_syntax.mdclasses.mdo.children.form.InputFieldExtInfo;
import com.github._1c_syntax.mdclasses.mdo.support.DataPath;
import com.github._1c_syntax.mdclasses.mdo.support.ValueType;
import com.thoughtworks.xstream.XStream;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class EDTReader implements MDReader {

  @Getter(lazy = true)
  private static final ExtendXStream xstream = createXMLMapper();

  private static final String ATTRIBUTE_FIELD_NAME = "attributes";
  private static final String CHILDREN_FIELD_NAME = "children";

  private final Path rootPath;

  public EDTReader(Path path, boolean skipSupport) {
    rootPath = path;
    if (!skipSupport) {
      ParseSupportData.readSimple(EDTPaths.parentConfigurationsPath(rootPath));
    }
  }

//  @Override
//  public MDClass readConfiguration() {
//    var mdc = readConfiguration(
//      EDTPaths.mdoPath(rootPath, MDOType.CONFIGURATION, MDOType.CONFIGURATION.getName())
//    );
//    return mdc.orElse(MDClasses.createConfiguration());
//  }

  @Override
  public MDObject read(String fullName) {
    return read(rootPath, fullName);
  }

  @Override
  public MDObject read(Path folder, String fullName) {
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
      var result = read(path);
      if (result instanceof MDObject) {
        return (MDObject) result;
      }
    }
    return null;
  }

  @Override
  public ExtendXStream getEXStream() {
    return getXstream();
  }


//  private static Optional<MDClass> readConfiguration(Path path) {
//    return Optional.ofNullable((MDClass) getXstream().fromXML(path.toFile()));
//  }

  private static ExtendXStream createXMLMapper() {
    var xStream = new ExtendXStream();

    // необходимо зарегистрировать все используемые классы
    registerClasses(xStream);
    // для каждого типа данных или поля необходимо зарегистрировать конвертер
    XStreamUtils.registerConverters(xStream,
      "com.github._1c_syntax.bsl.reader.edt.converter",
      EDTConverter.class);

    return xStream;
  }

  private static void registerClasses(XStream xStream) {
    xStream.aliasField("content", MDSubsystem.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("subsystems", MDSubsystem.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("addressingAttributes", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("dimensions", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField(ATTRIBUTE_FIELD_NAME, AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("resources", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("recalculations", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("tabularSections", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("extDimensionAccountingFlags", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("accountingFlags", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);
    xStream.aliasField("columns", AbstractMDObjectComplex.class, ATTRIBUTE_FIELD_NAME);

    xStream.alias("addressingAttributes", AddressingAttribute.class);
    xStream.alias("dimensions", Dimension.class);
    xStream.alias(ATTRIBUTE_FIELD_NAME, Attribute.class);
    xStream.alias("resources", Resource.class);
    xStream.alias("recalculations", Recalculation.class);
    xStream.alias("tabularSections", TabularSection.class);
    xStream.alias("extDimensionAccountingFlags", ExtDimensionAccountingFlag.class);
    xStream.alias("accountingFlags", AccountingFlag.class);
    xStream.alias("columns", Column.class);

    xStream.alias("Form", FormData.class);
    List<Class<?>> formClasses = new ArrayList<>();
    formClasses.add(FormData.class);
    formClasses.add(FormItem.class);

    formClasses.forEach((Class<?> aClass) -> {
      xStream.aliasField("items", aClass, CHILDREN_FIELD_NAME);
      xStream.aliasField("autoCommandBar", aClass, CHILDREN_FIELD_NAME);
      xStream.aliasField("extendedTooltip", aClass, CHILDREN_FIELD_NAME);
      xStream.aliasField("contextMenu", aClass, CHILDREN_FIELD_NAME);
      xStream.aliasField("viewStatusAddition", aClass, CHILDREN_FIELD_NAME);
      xStream.aliasField("searchControlAddition", aClass, CHILDREN_FIELD_NAME);
      xStream.aliasField("searchStringAddition", aClass, CHILDREN_FIELD_NAME);
    });

    xStream.processAnnotations(DataPath.class);
    xStream.processAnnotations(ValueType.class);
    xStream.processAnnotations(DynamicListExtInfo.class);
    xStream.processAnnotations(InputFieldExtInfo.class);

  }
}
