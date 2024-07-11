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
import com.github._1c_syntax.bsl.mdo.storage.form.FormElementType;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.reader.common.context.AbstractReaderContext;
import com.github._1c_syntax.bsl.reader.common.context.FormElementReaderContext;
import com.github._1c_syntax.bsl.reader.common.context.MDCReaderContext;
import com.github._1c_syntax.bsl.reader.common.context.MDReaderContext;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
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
  public void unmarshal(HierarchicalStreamReader reader,
                        UnmarshallingContext context,
                        AbstractReaderContext readerContext) {
    readerContext.setLastValue(null);
    readerContext.setLastName("");

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      readNode(reader.getNodeName(), context, readerContext);
      reader.moveUp();
    }
  }

  private void readNode(String inName, UnmarshallingContext context, AbstractReaderContext readerContext) {
    Class<?> fieldClass = null;
    var name = inName;
    if (readerContext instanceof MDCReaderContext && MDOType.fromValue(name).isPresent()) {
      if (LANGUAGE_NODE.equals(name)) {
        fieldClass = Language.class;
        name = LANGUAGE_METHOD_NAME;
      } else {
        fieldClass = String.class;
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

    var value = ExtendXStream.readValue(context, fieldClass);
    if (name.equals(NAME_NODE)) {
      readerContext.setName((String) value);
    }
    if (readerContext instanceof MDReaderContext mdReaderContext && TEMPLATE_TYPE_NODE.equals(name)) {
      mdReaderContext.setTemplateType((TemplateType) value);
    } else if (readerContext instanceof MDCReaderContext mdcReaderContext) {
      saveExtra(mdcReaderContext, name, value);
    } else if (readerContext instanceof FormElementReaderContext formElementReaderContext
      && "type".equals(name)) {
      formElementReaderContext.setElementType((FormElementType) value);
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
}
