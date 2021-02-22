/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2021
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
package com.github._1c_syntax.mdclasses.unmarshal;

import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerChildObjects;
import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerWrapper;
import com.github._1c_syntax.mdclasses.mdo.wrapper.form.DesignerAttribute;
import com.github._1c_syntax.mdclasses.mdo.wrapper.form.DesignerChildItems;
import com.github._1c_syntax.mdclasses.mdo.wrapper.form.DesignerForm;
import com.github._1c_syntax.mdclasses.mdo.wrapper.form.DesignerFormItem;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.unmarshal.annotation.TypeAlias;
import com.github._1c_syntax.mdclasses.unmarshal.converters.DesignerFormItemConverter;
import com.github._1c_syntax.mdclasses.unmarshal.converters.FormConverter;
import com.github._1c_syntax.mdclasses.unmarshal.converters.FormEventConverter;
import com.github._1c_syntax.mdclasses.unmarshal.converters.MetaDataObjectConverter;
import com.thoughtworks.xstream.XStream;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.atteo.classindex.ClassIndex;

import java.io.File;

/**
 * Класс для чтения XML файлов конфигурации (формат Конфигуратора)
 */
@Slf4j
@UtilityClass
public class DesignerXStreamFactory {
  private static final String WRAPPER_PREFIX = "WRAP";

  private final String CHILDREN_FIELD_NAME = "children";

  @Getter(lazy = true)
  private final XStream xstream = createXMLMapper();

  /**
   * Выполняет чтение объекта из XML файла
   */
  public Object fromXML(File file) {
    return XStreamFactory.fromXML(getXstream(), file);
  }

  public Class<?> getRealClass(String className) {
    return getXstream().getMapper().realClass(className);
  }

  public Class<?> getWrappedClass(String className) {
    return getXstream().getMapper().realClass(WRAPPER_PREFIX + className);
  }

  private XStream createXMLMapper() {
    var xStream = XStreamFactory.createXMLMapper();

    // для каждого типа данных или поля необходимо зарегистрировать конвертер
    addConverters(xStream);

    // обработка аннотаций
    processAnnotationsForMDO(xStream);

    // необходимо зарегистрировать все классы, имена которых в XML отличаются от имен самих классов
    addClassAliases(xStream);

    // все типы реквизитов добавляются в одно поле
    addFieldAliases(xStream);

    return xStream;
  }

  private void addConverters(XStream xStream) {
    xStream.registerConverter(new FormEventConverter());
    xStream.registerConverter(new DesignerFormItemConverter());
    xStream.registerConverter(new MetaDataObjectConverter());
    xStream.registerConverter(new FormConverter());
  }

  private void processAnnotationsForMDO(XStream xstream) {
    xstream.processAnnotations(DesignerWrapper.class);
    xstream.processAnnotations(DesignerMDO.class);
    xstream.processAnnotations(DesignerForm.class);
  }

  private void addFieldAliases(XStream xStream) {
    // дочерние элементы
    MDOType.valuesWithoutChildren().forEach((MDOType type) -> {
      xStream.aliasField(type.getName(), DesignerChildObjects.class, CHILDREN_FIELD_NAME);
    });

    addDesignerFormItemAliases(xStream);
    addDesignerFormCommonAliases(xStream);
  }

  private void addDesignerFormCommonAliases(XStream xStream) {
    xStream.aliasField("ChildItems", DesignerForm.class, "childItems");
    xStream.aliasField("ChildItems", DesignerFormItem.class, "childItems");
    xStream.aliasField("MainAttribute", DesignerAttribute.class, "main");
    xStream.aliasField("Columns", DesignerAttribute.class, "designerColumns");
    xStream.aliasField("DataPath", DesignerFormItem.class, "dataPath");
  }

  private void addDesignerFormItemAliases(XStream xStream) {
    // элементы формы
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

    // корень формы
    xStream.aliasField("AutoCommandBar", DesignerForm.class, "autoCommandBar");

    // элемент формы
    xStream.aliasField("ContextMenu", DesignerFormItem.class, "contextMenu");
    xStream.aliasField("ExtendedTooltip", DesignerFormItem.class, "extendedTooltip");
    xStream.aliasField("AutoCommandBar", DesignerFormItem.class, "autoCommandBar");
    xStream.aliasField("SearchStringAddition", DesignerFormItem.class, "searchStringAddition");
    xStream.aliasField("ViewStatusAddition", DesignerFormItem.class, "viewStatusAddition");
  }

  private void addClassAliases(XStream xStream) {
    ClassIndex.getAnnotated(TypeAlias.class).forEach(clazz -> {
      var annotation = clazz.getAnnotation(TypeAlias.class);
      var alias = (annotation.name().isEmpty()) ? annotation.designerName() : annotation.name();
      if (!alias.isEmpty()) {
        if (annotation.useDesignerWrapper()) {
          xStream.alias(WRAPPER_PREFIX + alias, DesignerMDO.class);
        }
        xStream.alias(alias, clazz);
      }
    });
//
//    xStream.alias("MetaDataObject", DesignerWrapper.class);
////    xStream.alias("Form", DesignerForm.class);
//    xStream.alias("Configuration", DesignerMDO.class);
//    xStream.alias("DesignerConfiguration", MDOConfiguration.class);
  }

}
