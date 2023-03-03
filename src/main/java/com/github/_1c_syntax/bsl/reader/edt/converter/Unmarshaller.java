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
package com.github._1c_syntax.bsl.reader.edt.converter;

import com.github._1c_syntax.bsl.mdclasses.CF;
import com.github._1c_syntax.bsl.mdo.Language;
import com.github._1c_syntax.bsl.mdo.children.ExternalDataSourceTableField;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.github._1c_syntax.bsl.supconf.ParseSupportData;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.github._1c_syntax.bsl.types.MDOType;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.experimental.UtilityClass;

/**
 * Выполняет базовое чтение файлов
 */
@UtilityClass
class Unmarshaller {

  private static final String NAME_NODE = "name";
  private static final String TEMPLATE_TYPE_NODE = "templateType";
  private static final String CP_MODE_NODE = "compatibilityMode";
  private static final String CP_EXT_MODE_NODE = "configurationExtensionCompatibilityMode";
  private static final String UUID_FIELD = "uuid";
  private static final String LANGUAGE_NODE = "languages";
  private static final String LANGUAGE_METHOD_NAME = "language";
  private static final String TABLE_FIELDS_NODE = "tableFields";

  private static final String SUPPORT_VALIANT_FIELD = "SupportVariant";

  private static final String CHILD_FILED = "child";
  private static final String TABLE_FIELDS_FIELD = "fields";

  /**
   * Читает общую информацию из файла
   */
  public void unmarshal(HierarchicalStreamReader reader,
                        UnmarshallingContext context,
                        TransformationUtils.Context readerContext) {
    var uuid = reader.getAttribute(UUID_FIELD);
    readerContext.setValue(UUID_FIELD, uuid);

    var supportVariant = ParseSupportData.getSupportVariantByMDO(uuid, readerContext.getCurrentPath());
    readerContext.setValue(SUPPORT_VALIANT_FIELD, supportVariant);
    readerContext.setSupportVariant(supportVariant);

    if (CF.class.isAssignableFrom(readerContext.getRealClass())) {
      unmarshalMDC(reader, context, readerContext);
    } else {
      unmarshalMD(reader, context, readerContext);
    }
  }

  public void unmarshalMD(HierarchicalStreamReader reader,
                          UnmarshallingContext context,
                          TransformationUtils.Context readerContext) {

    Object lastValue = null;
    var lastName = "";
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var name = reader.getNodeName();
      var fieldClass = readerContext.fieldType(name);

      // не стоит тратить время
      if (fieldClass == null && TABLE_FIELDS_NODE.equals(name)) {
        name = TABLE_FIELDS_FIELD;
        fieldClass = ExternalDataSourceTableField.class;
      } else if (fieldClass == null) {
        reader.moveUp();
        continue;
      } else {
        // no-op
      }

      var value = context.convertAnother(fieldClass, fieldClass);

      if (value instanceof TransformationUtils.Context) {
        readerContext.addChild(name, (TransformationUtils.Context) value);
        lastValue = null;
        lastName = "";
      } else {
        readExtra(readerContext, name, value);

        if (isMultiLanguageString(lastValue, lastName, name, value)) {
          lastValue = readMultiLanguageString(readerContext, lastValue, name, value);
        } else {
          readerContext.setValue(name, value);
          lastValue = value;
        }
        lastName = name;
      }
      reader.moveUp();
    }
  }

  public void unmarshalMDC(HierarchicalStreamReader reader,
                           UnmarshallingContext context,
                           TransformationUtils.Context readerContext) {

    Object lastValue = null;
    var lastName = "";
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var name = reader.getNodeName();
      Class<?> fieldClass;
      var isChild = false;

      var mdoType = MDOType.fromValue(name);
      if (mdoType.isEmpty()) {
        fieldClass = readerContext.fieldType(name);
      } else if (LANGUAGE_NODE.equals(name)) {
        fieldClass = Language.class;
        name = LANGUAGE_METHOD_NAME;
      } else {
        fieldClass = String.class;
        isChild = true;
      }

      // не стоит тратить время
      if (fieldClass == null) {
        reader.moveUp();
        continue;
      }

      var value = context.convertAnother(fieldClass, fieldClass);

      if (value instanceof TransformationUtils.Context) {
        readerContext.addChild(name, (TransformationUtils.Context) value);
        lastValue = null;
        lastName = "";
      } else {
        readExtra(readerContext, name, value);

        if (isMultiLanguageString(lastValue, lastName, name, value)) {
          lastValue = readMultiLanguageString(readerContext, lastValue, name, value);
        } else if (isChildMD(isChild, value)) {
          readerContext.addChildMetadata((String) value);
          lastValue = value;
        } else {
          readerContext.setValue(name, value);
          lastValue = value;
        }
        lastName = name;
      }
      reader.moveUp();
    }
  }

  private static boolean isChildMD(boolean isChild, Object value) {
    return isChild && value instanceof String;
  }

  private static boolean isCPExtModeNode(String name, Object value) {
    return name.equals(CP_EXT_MODE_NODE) && value instanceof CompatibilityMode;
  }

  private static boolean isCPModeNode(String name, Object value) {
    return name.equals(CP_MODE_NODE) && value instanceof CompatibilityMode;
  }

  private static boolean isTemplateNode(String name, Object value) {
    return name.equals(TEMPLATE_TYPE_NODE) && value instanceof TemplateType;
  }

  private static boolean isNameNode(String name, Object value) {
    return isChildMD(name.equals(NAME_NODE), value);
  }

  private static boolean isMultiLanguageString(Object lastValue, String lastName, String name, Object value) {
    return lastName.equals(name) && lastValue instanceof MultiLanguageString && value instanceof MultiLanguageString;
  }

  private static MultiLanguageString readMultiLanguageString(TransformationUtils.Context readerContext,
                                                             Object lastValue,
                                                             String name,
                                                             Object value) {
    var newValue = new MultiLanguageString((MultiLanguageString) lastValue, (MultiLanguageString) value);
    readerContext.setValue(name, newValue);
    return newValue;
  }


  private static void readExtra(TransformationUtils.Context readerContext, String name, Object value) {
    if (isNameNode(name, value)) {
      readerContext.setName((String) value);
    } else if (isTemplateNode(name, value)) {
      readerContext.setTemplateType((TemplateType) value);
    } else if (isCPModeNode(name, value)) {
      readerContext.setCompatibilityMode((CompatibilityMode) value);
    } else if (isCPExtModeNode(name, value)) {
      readerContext.setConfigurationExtensionCompatibilityMode((CompatibilityMode) value);
    } else if (value instanceof Language) {
      readerContext.setValue(CHILD_FILED, value);
    } else {
      // no-op
    }
  }
}
