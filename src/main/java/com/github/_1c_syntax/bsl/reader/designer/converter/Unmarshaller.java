/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
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
package com.github._1c_syntax.bsl.reader.designer.converter;

import com.github._1c_syntax.bsl.mdo.children.StandardAttribute;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.reader.common.context.AbstractReaderContext;
import com.github._1c_syntax.bsl.reader.common.context.FormElementReaderContext;
import com.github._1c_syntax.bsl.reader.common.context.MDCReaderContext;
import com.github._1c_syntax.bsl.reader.common.context.MDReaderContext;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Выполняет базовое чтение файлов
 */
@UtilityClass
public class Unmarshaller {

  private static final String PROPERTIES_NODE = "Properties";
  private static final String CHILD_OBJECTS_NODE = "ChildObjects";
  private static final String CHILD_ITEMS_NODE = "ChildItems";
  private static final String ITEMS_NODE = "items";
  private static final String USE_PURPOSES_NODE = "UsePurposes";

  private static final String EVENTS_NODE = "Events";
  private static final String HANDLES_NODE = "Handlers";
  private static final String ATTRIBUTES_NODE = "Attributes";

  private static final String NAME_NODE = "Name";
  private static final String TEMPLATE_TYPE_NODE = "TemplateType";
  private static final String CP_MODE_NODE = "CompatibilityMode";
  private static final String CP_EXT_MODE_NODE = "ConfigurationExtensionCompatibilityMode";
  private static final String STANDARD_ATTRIBUTES_NODE = "StandardAttributes";
  private static final String STANDARD_ATTRIBUTE_NODE = "StandardAttribute";
  private static final String ATTRIBUTE_FIELD_NAME = "Attribute";

  /**
   * Читает информацию из файлов MD и MDC
   */
  public static void unmarshal(HierarchicalStreamReader reader,
                               UnmarshallingContext context,
                               AbstractReaderContext readerContext) {
    if (readerContext instanceof MDReaderContext mdReaderContext) {
      unmarshal(reader, context, mdReaderContext);
    } else {
      unmarshal(reader, context, (MDCReaderContext) readerContext);
    }
  }

  /**
   * Читает информацию из файлов форм
   */
  public void unmarshal(HierarchicalStreamReader reader,
                        UnmarshallingContext context,
                        FormElementReaderContext readerContext) {
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var name = reader.getNodeName();
      if (CHILD_ITEMS_NODE.equals(name)) {
        readItemNode(reader, context, readerContext, ITEMS_NODE);
      } else if (ATTRIBUTES_NODE.equals(name)) {
        readItemNode(reader, context, readerContext, ATTRIBUTES_NODE);
      } else if (EVENTS_NODE.equals(name)) {
        readItemNode(reader, context, readerContext, HANDLES_NODE);
      } else {
        readNode(reader.getNodeName(), context, readerContext);
      }
      reader.moveUp();
    }
  }

  /**
   * Читает информацию из файлов объектов метаданных и внешних отчетов и обработок
   */
  private void unmarshal(HierarchicalStreamReader reader,
                         UnmarshallingContext context,
                         MDReaderContext readerContext) {
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var name = reader.getNodeName();
      if (PROPERTIES_NODE.equals(name) || CHILD_OBJECTS_NODE.equals(name)) {
        readPropertiesNode(reader, context, readerContext);
      }
      reader.moveUp();
    }
  }

  /**
   * Читает информацию из файлов контейнеров конфигурации и расширений
   */
  private void unmarshal(HierarchicalStreamReader reader,
                         UnmarshallingContext context,
                         MDCReaderContext readerContext) {
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var name = reader.getNodeName();
      if (PROPERTIES_NODE.equals(name)) {
        readPropertiesNode(reader, context, readerContext);
      } else if (CHILD_OBJECTS_NODE.equals(name)) {
        readChildrenNames(reader, readerContext);
      }
      reader.moveUp();
    }
  }

  private void readItemNode(HierarchicalStreamReader reader,
                            UnmarshallingContext context,
                            AbstractReaderContext readerContext,
                            String nodeName) {
    var fieldClass = readerContext.fieldType(nodeName);
    if(fieldClass == null) {
      return;
    }
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      readerContext.setValue(nodeName, ExtendXStream.readValue(context, fieldClass));
      reader.moveUp();
    }
  }

  private void readNode(String name, UnmarshallingContext context, FormElementReaderContext readerContext) {
    var fieldClass = readerContext.fieldType(name);
    if (fieldClass == null) {
      return;
    }
    readerContext.setValue(name, ExtendXStream.readValue(context, fieldClass));
  }

  private void readPropertiesNode(HierarchicalStreamReader reader,
                                  UnmarshallingContext context,
                                  AbstractReaderContext readerContext) {
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var name = reader.getNodeName();
      if (USE_PURPOSES_NODE.equals(name)) {
        readItemNode(reader, context, readerContext, USE_PURPOSES_NODE);
      } else if (STANDARD_ATTRIBUTES_NODE.equals(name)) {
        readPropertiesNode(reader, context, readerContext);
      } else if (STANDARD_ATTRIBUTE_NODE.equals(name)) {
        readValue(reader, context, readerContext, StandardAttribute.class, ATTRIBUTE_FIELD_NAME);
      } else {
        var fieldClass = readerContext.fieldType(name);
        readValue(reader, context, readerContext, Objects.requireNonNullElse(fieldClass, String.class), name);
      }
      reader.moveUp();
    }
  }

  private void readChildrenNames(HierarchicalStreamReader reader, AbstractReaderContext readerContext) {
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var name = reader.getNodeName();
      readerContext.setValue(name, name + "." + reader.getValue());
      reader.moveUp();
    }
  }

  private void readValue(HierarchicalStreamReader reader,
                         UnmarshallingContext context,
                         AbstractReaderContext readerContext,
                         Class<?> fieldClass,
                         String name) {
    var value = readValue(reader, context, fieldClass);

    if (name.equals(NAME_NODE) && value instanceof String string) {
      readerContext.setName(string);
    }

    if (readerContext instanceof MDReaderContext mdReaderContext) {
      if (name.equals(TEMPLATE_TYPE_NODE) && value instanceof TemplateType templateType) {
        mdReaderContext.setTemplateType(templateType);
      }
    } else {
      var mdcReaderContext = (MDCReaderContext) readerContext;
      if (name.equals(CP_MODE_NODE) && value instanceof CompatibilityMode compatibilityMode) {
        mdcReaderContext.setCompatibilityMode(compatibilityMode);
      } else if (name.equals(CP_EXT_MODE_NODE) && value instanceof CompatibilityMode compatibilityMode) {
        mdcReaderContext.setConfigurationExtensionCompatibilityMode(compatibilityMode);
      }
    }
    readerContext.setValue(name, value);
  }

  private Object readValue(HierarchicalStreamReader reader,
                           UnmarshallingContext context,
                           Class<?> fieldClass) {
    try {
      return ExtendXStream.readValue(context, fieldClass);
    } catch (ConversionException e) {
      List<Object> result = new ArrayList<>();
      while (reader.hasMoreChildren()) {
        reader.moveDown();
        result.add(ExtendXStream.readValue(context, fieldClass));
        reader.moveUp();
      }
      return result;
    }
  }
}
