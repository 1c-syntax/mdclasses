/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2022
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

import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.reader.MDReader;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.XStreamUtils;
import com.github._1c_syntax.bsl.reader.designer.converter.DesignerConverter;
import com.github._1c_syntax.bsl.reader.designer.wrapper.DesignerChildObjects;
import com.github._1c_syntax.bsl.reader.designer.wrapper.DesignerContentItem;
import com.github._1c_syntax.bsl.reader.designer.wrapper.DesignerExchangePlanContent;
import com.github._1c_syntax.bsl.reader.designer.wrapper.DesignerFormWrapper;
import com.github._1c_syntax.bsl.reader.designer.wrapper.DesignerMDO;
import com.github._1c_syntax.bsl.reader.designer.wrapper.DesignerRootWrapper;
import com.github._1c_syntax.bsl.reader.designer.wrapper.form.DesignerAttributeType;
import com.github._1c_syntax.bsl.reader.designer.wrapper.form.DesignerChildItems;
import com.github._1c_syntax.bsl.reader.designer.wrapper.form.DesignerColumns;
import com.github._1c_syntax.bsl.reader.designer.wrapper.form.DesignerEvent;
import com.github._1c_syntax.bsl.reader.designer.wrapper.form.DesignerForm;
import com.github._1c_syntax.bsl.reader.designer.wrapper.form.DesignerFormCommand;
import com.github._1c_syntax.bsl.reader.designer.wrapper.form.DesignerFormCommands;
import com.github._1c_syntax.bsl.supconf.ParseSupportData;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.mdo.children.ExchangePlanItem;
import com.github._1c_syntax.mdclasses.mdo.children.form.FormData;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.QNameMap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.xml.namespace.QName;
import java.nio.file.Path;

@Slf4j
public class DesignerReader implements MDReader {
  @Getter(lazy = true)
  private static final ExtendXStream xstream = createXMLMapper();

  private static final String CHILDREN_FIELD_NAME = "children";

  private final Path rootPath;

  public DesignerReader(Path path, boolean skipSupport) {
    rootPath = path;
    if (!skipSupport) {
      ParseSupportData.readSimple(DesignerPaths.parentConfigurationsPath(rootPath));
    }
  }

//  @Override
//  public MDClass readConfiguration() {
//    var mdc = readConfiguration(
//      DesignerPaths.mdoPath(rootPath, MDOType.CONFIGURATION, MDOType.CONFIGURATION.getName())
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
        path = DesignerPaths.mdoPath(folder, type.get(), name);
      } else {
        path = DesignerPaths.mdoPath(folder, name);
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

    var qNameMap = new QNameMap();
    qNameMap.registerMapping(
      new QName("http://g5.1c.ru/v8/dt/form", "Form", "form"), FormData.class);
    qNameMap.registerMapping(
      new QName("http://v8.1c.ru/8.3/xcf/logform", "Form"), DesignerFormWrapper.class);

    var xStream = new ExtendXStream(qNameMap);

    // необходимо зарегистрировать все используемые классы
    registerClasses(xStream);
    // для каждого типа данных или поля необходимо зарегистрировать конвертер
    XStreamUtils.registerConverters(xStream,
      "com.github._1c_syntax.bsl.reader.designer.converter",
      DesignerConverter.class);

    return xStream;
  }

  private static void registerClasses(XStream xStream) {
    xStream.alias("MetaDataObject", DesignerRootWrapper.class);

    xStream.aliasField("AutoCommandBar", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("Button", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("ButtonGroup", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("CalendarField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("ChartField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("CheckBoxField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("ColumnGroup", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("Command", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("CommandBar", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("CommandBarButton", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("CommandBarHyperlink", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("ContextMenu", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("DendrogramField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("FormattedDocumentField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("GanttChartField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("GeographicalSchemaField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("GraphicalSchemaField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("HTMLDocumentField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("Hyperlink", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("InputField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("LabelDecoration", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("LabelField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("Navigator", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("Page", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("Pages", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("PeriodField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("PictureDecoration", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("PictureField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("PlannerField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("ProgressBarField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("RadioButtonField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("SpreadSheetDocumentField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("Table", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("TextDocumentField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("TrackBarField", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("UsualButton", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("UsualGroup", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("Popup", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("ViewStatusAddition", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("ExtendedTooltip", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("SearchStringAddition", DesignerChildItems.class, CHILDREN_FIELD_NAME);
    xStream.aliasField("SearchControlAddition", DesignerChildItems.class, CHILDREN_FIELD_NAME);

    xStream.aliasField("Metadata", ExchangePlanItem.class, "mdObject");

    xStream.processAnnotations(DesignerExchangePlanContent.class);
    xStream.processAnnotations(DesignerMDO.class);
    xStream.processAnnotations(DesignerChildObjects.class);
    xStream.processAnnotations(DesignerAttributeType.class);
    xStream.processAnnotations(DesignerColumns.class);
    xStream.processAnnotations(DesignerEvent.class);
    xStream.processAnnotations(DesignerForm.class);
    xStream.processAnnotations(DesignerFormCommand.class);
    xStream.processAnnotations(DesignerFormCommands.class);
    xStream.processAnnotations(DesignerContentItem.class);
    xStream.processAnnotations(DesignerExchangePlanContent.class);

  }
}
