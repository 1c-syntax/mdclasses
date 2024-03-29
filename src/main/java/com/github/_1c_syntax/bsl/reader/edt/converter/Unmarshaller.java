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
package com.github._1c_syntax.bsl.reader.edt.converter;

import com.github._1c_syntax.bsl.mdo.Language;
import com.github._1c_syntax.bsl.mdo.children.ExternalDataSourceTableField;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.reader.common.ReaderUtils;
import com.github._1c_syntax.bsl.reader.common.context.MDCReaderContext;
import com.github._1c_syntax.bsl.reader.common.context.MDReaderContext;
import com.github._1c_syntax.bsl.reader.common.context.ReaderContext;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.github._1c_syntax.bsl.types.MDOType;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.experimental.UtilityClass;

/**
 * Выполняет базовое чтение файлов
 */
@UtilityClass
public class Unmarshaller {

  private static final String NAME_NODE = "name";
  private static final String TEMPLATE_TYPE_NODE = "templateType";
  private static final String CP_MODE_NODE = "compatibilityMode";
  private static final String CP_EXT_MODE_NODE = "configurationExtensionCompatibilityMode";
  private static final String LANGUAGE_NODE = "languages";
  private static final String LANGUAGE_METHOD_NAME = "language";
  private static final String TABLE_FIELDS_NODE = "tableFields";

  private static final String CHILD_FILED = "child";
  private static final String TABLE_FIELDS_FIELD = "fields";

  /**
   * Читают общую информацию из файла
   */
  public void unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context, ReaderContext readerContext) {
    readerContext.setLastValue(null);
    readerContext.setLastName("");

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      readNode(reader.getNodeName(), context, readerContext);
      reader.moveUp();
    }
  }

  public void unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context, MDReaderContext readerContext) {
    unmarshal(reader, context, (ReaderContext) readerContext);
  }

  public void unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context, MDCReaderContext readerContext) {
    unmarshal(reader, context, (ReaderContext) readerContext);
  }

  private void readNode(String name, UnmarshallingContext context, ReaderContext readerContext) {

    Class<?> fieldClass = null;
    if (readerContext instanceof MDCReaderContext) {
      var mdoType = MDOType.fromValue(name);
      if (mdoType.isPresent()) {
        if (LANGUAGE_NODE.equals(name)) {
          fieldClass = Language.class;
          name = LANGUAGE_METHOD_NAME;
        } else {
          fieldClass = String.class;
        }
      }
    }

    if (fieldClass == null) {
      fieldClass = readerContext.fieldType(name);
    }

    if (fieldClass == null && TABLE_FIELDS_NODE.equals(name)) {
      name = TABLE_FIELDS_FIELD;
      fieldClass = ExternalDataSourceTableField.class;
    }

    if (fieldClass == null) {
      return;
    }

    var value = ReaderUtils.readValue(context, fieldClass);
    if (readerContext instanceof MDReaderContext mdReaderContext) {
      saveExtra(mdReaderContext, name, value);
    } else if (readerContext instanceof MDCReaderContext mdcReaderContext) {
      saveExtra(mdcReaderContext, name, value);
    }
    readerContext.setValue(name, transformMultiLanguageString(readerContext, name, value));
  }

  private Object transformMultiLanguageString(ReaderContext readerContext, String name, Object value) {
    var newVal = value;
    if (readerContext.getLastName().equals(name)
      && readerContext.getLastValue() instanceof MultiLanguageString lastValue
      && value instanceof MultiLanguageString newValue) {
      newVal = MultiLanguageString.create(lastValue, newValue);
    }
    readerContext.setLastName(name);
    readerContext.setLastValue(newVal);
    return newVal;
  }

  private static void saveExtra(MDReaderContext readerContext, String name, Object value) {
    if (name.equals(NAME_NODE) && value instanceof String string) {
      readerContext.setName(string);
    } else if (name.equals(TEMPLATE_TYPE_NODE) && value instanceof TemplateType templateType) {
      readerContext.setTemplateType(templateType);
    } else {
      // no-op
    }
  }

  private static void saveExtra(MDCReaderContext readerContext, String name, Object value) {
    if (name.equals(NAME_NODE) && value instanceof String string) {
      readerContext.setName(string);
    } else if (name.equals(CP_MODE_NODE) && value instanceof CompatibilityMode compatibilityMode) {
      readerContext.setCompatibilityMode(compatibilityMode);
    } else if (name.equals(CP_EXT_MODE_NODE) && value instanceof CompatibilityMode compatibilityMode) {
      readerContext.setConfigurationExtensionCompatibilityMode(compatibilityMode);
    } else if (value instanceof Language) {
      readerContext.setValue(CHILD_FILED, value);
    } else {
      // no-op
    }
  }
}
