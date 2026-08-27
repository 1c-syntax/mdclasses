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
package com.github._1c_syntax.bsl.reader.edt.converter;

import com.github._1c_syntax.bsl.mdo.Language;
import com.github._1c_syntax.bsl.mdo.PredefinedDataOwner;
import com.github._1c_syntax.bsl.mdo.children.ExternalDataSourceTableField;
import com.github._1c_syntax.bsl.mdo.children.PredefinedValue;
import com.github._1c_syntax.bsl.mdo.children.StandardAttribute;
import com.github._1c_syntax.bsl.mdo.storage.form.FormAddition;
import com.github._1c_syntax.bsl.mdo.storage.form.FormCommand;
import com.github._1c_syntax.bsl.mdo.storage.form.FormContextMenu;
import com.github._1c_syntax.bsl.mdo.storage.form.FormElement;
import com.github._1c_syntax.bsl.mdo.storage.form.FormEventHandler;
import com.github._1c_syntax.bsl.mdo.storage.form.FormExtendedTooltip;
import com.github._1c_syntax.bsl.mdo.storage.form.FormGroup;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.reader.common.context.AbstractReaderContext;
import com.github._1c_syntax.bsl.reader.common.context.FormAttributeWrapper;
import com.github._1c_syntax.bsl.reader.common.context.FormElementReaderContext;
import com.github._1c_syntax.bsl.reader.common.context.MDCReaderContext;
import com.github._1c_syntax.bsl.reader.common.context.MDReaderContext;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import com.github._1c_syntax.bsl.types.ValueTypeDescription;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Выполняет базовое чтение файлов
 */
@UtilityClass
public class Unmarshaller {

  private static final String NAME_NODE = "name";
  private static final String TEMPLATE_TYPE_NODE = "templateType";
  private static final String CP_MODE_NODE = "compatibilityMode";
  private static final String CP_EXT_MODE_NODE = "configurationExtensionCompatibilityMode";

  private static final String CHILD_FILED = "child";
  private static final String STANDARD_ATTRIBUTES_NODE = "standardAttributes";
  private static final String PREDEFINED_NODE = "predefined";
  private static final String ITEMS_NODE = "items";
  private static final String PREDEFINED_VALUES_FIELD = "predefinedValues";
  private static final String USE_ALWAYS_NODE = "notDefaultUseAlwaysAttributes";
  private static final String USE_ALWAYS_FIELD = "useAlwaysFields";
  private static final String SEGMENTS_NODE = "segments";

  private static final Map<String, ClassField> FORM_ELEMENT_REMAPPING
    = Map.ofEntries(
    Map.entry("formCommands", new ClassField(FormCommand.class, "commands")),
    Map.entry("additionalColumns", new ClassField(FormAttributeWrapper.class, "columns")),
    Map.entry("items", new ClassField(FormElement.class, "elements")),
    Map.entry("contextMenu", new ClassField(FormContextMenu.class, "elements")),
    Map.entry("extendedTooltip", new ClassField(FormExtendedTooltip.class, "elements")),
    Map.entry("autoCommandBar", new ClassField(FormGroup.class, "elements")),
    Map.entry("searchControlAddition", new ClassField(FormAddition.class, "elements")),
    Map.entry("viewStatusAddition", new ClassField(FormAddition.class, "elements")),
    Map.entry("searchStringAddition", new ClassField(FormAddition.class, "elements")),
    Map.entry("handlers", new ClassField(FormEventHandler.class, "eventHandlers")),
    // поле ключа строки динамического списка EDT пишет в единственном числе,
    // а полей бывает несколько
    Map.entry("keyField", new ClassField(String.class, "keyFields"))
  );

  private static final Map<String, ClassField> ELEMENT_REMAPPING
    = Map.of(
    "tableFields", new ClassField(ExternalDataSourceTableField.class, "fields"),
    "valueType", new ClassField(ValueTypeDescription.class, "type"),
    "languages", new ClassField(Language.class, "language")
  );

  /**
   * Читают общую информацию из файла
   */
  public void unmarshal(HierarchicalStreamReader reader,
                        UnmarshallingContext context,
                        AbstractReaderContext readerContext) {
    readerContext.setLastValue(null);
    readerContext.setLastName("");

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var nodeName = reader.getNodeName();
      if (PREDEFINED_NODE.equals(nodeName)
        && readerContext instanceof MDReaderContext
        && PredefinedDataOwner.class.isAssignableFrom(readerContext.getRealClass())) {
        readPredefined(reader, context, readerContext);
      } else if (USE_ALWAYS_NODE.equals(nodeName)) {
        readUseAlwaysField(reader, context, readerContext);
      } else if ("extInfo".equals(nodeName) || "tablePath".equals(nodeName)) {
        while (reader.hasMoreChildren()) {
          reader.moveDown();
          readNode(reader.getNodeName(), context, readerContext);
          reader.moveUp();
        }
      } else {
        readNode(nodeName, context, readerContext);
      }
      reader.moveUp();
    }
  }

  /**
   * Читает одно поле, помеченное «использовать всегда»: путь к данным лежит
   * во вложенном {@code <segments>}, а сам узел повторяется по полю
   */
  private void readUseAlwaysField(HierarchicalStreamReader reader,
                                  UnmarshallingContext context,
                                  AbstractReaderContext readerContext) {
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if (SEGMENTS_NODE.equals(reader.getNodeName())) {
        readerContext.setValue(USE_ALWAYS_FIELD, ExtendXStream.readValue(context, String.class));
      }
      reader.moveUp();
    }
  }

  /**
   * Читает встроенные в файл объекта предопределенные данные (обертка {@code <predefined>})
   */
  private void readPredefined(HierarchicalStreamReader reader,
                              UnmarshallingContext context,
                              AbstractReaderContext readerContext) {
    List<PredefinedValue> items = new ArrayList<>();
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if (ITEMS_NODE.equals(reader.getNodeName())) {
        items.add(ExtendXStream.readValue(context, PredefinedValue.class));
      }
      reader.moveUp();
    }
    if (!items.isEmpty()) {
      readerContext.setValue(PREDEFINED_VALUES_FIELD, items);
    }
  }

  private void readNode(String inName, UnmarshallingContext context, AbstractReaderContext readerContext) {
    Class<?> fieldClass = null;
    var name = inName;
    if (readerContext instanceof MDCReaderContext && MDOType.fromValue(name).isPresent()) {
      var remap = ELEMENT_REMAPPING.get(name);
      if (remap != null) {
        fieldClass = remap.clazz;
        name = remap.field;
      } else {
        fieldClass = String.class;
      }
    } else if (readerContext instanceof MDReaderContext && STANDARD_ATTRIBUTES_NODE.equals(name)) {
      fieldClass = StandardAttribute.class;
      name = "attributes";
    } else if (readerContext instanceof FormElementReaderContext) {
      var remap = FORM_ELEMENT_REMAPPING.get(name);
      if (remap != null) {
        fieldClass = remap.clazz;
        name = remap.field;
      }
    }

    if (fieldClass == null) {
      fieldClass = readerContext.fieldType(name);
    }

    if (fieldClass == null) {
      var remap = ELEMENT_REMAPPING.get(name);
      if (remap != null) {
        fieldClass = remap.clazz;
        name = remap.field;
      }
    }

    Object value;
    if (fieldClass == null) {
      value = ExtendXStream.readValue(context, String.class);
    } else {
      value = ExtendXStream.readValue(context, fieldClass);
    }

    if (name.equals(NAME_NODE)) {
      readerContext.setName((String) value);
    }
    if (readerContext instanceof MDReaderContext mdReaderContext
      && TEMPLATE_TYPE_NODE.equals(name)
      && value instanceof TemplateType newValue) {
      mdReaderContext.setTemplateType(newValue);
    } else if (readerContext instanceof MDCReaderContext mdcReaderContext) {
      saveExtra(mdcReaderContext, name, value);
    }
    readerContext.setValue(name, transformMultiLanguageString(readerContext, name, value));
  }

  private Object transformMultiLanguageString(AbstractReaderContext readerContext, String name, Object value) {
    var newVal = value;
    if (value instanceof MultiLanguageString newValue
      && readerContext.getLastValue() instanceof MultiLanguageString lastValue
      && readerContext.getLastName().equals(name)) {
      newVal = MultiLanguageString.create(lastValue, newValue);
    }
    readerContext.setLastName(name);
    readerContext.setLastValue(newVal);
    return newVal;
  }

  private static void saveExtra(MDCReaderContext readerContext, String name, Object value) {
    if (CP_MODE_NODE.equals(name)) {
      readerContext.setCompatibilityMode((CompatibilityMode) value);
    } else if (CP_EXT_MODE_NODE.equals(name)) {
      readerContext.setConfigurationExtensionCompatibilityMode((CompatibilityMode) value);
    } else if (value instanceof Language) {
      readerContext.setValue(CHILD_FILED, value);
    } else {
      // no-op
    }
  }

  private record ClassField(Class<?> clazz, String field) {
  }
}
