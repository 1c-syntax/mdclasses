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
package com.github._1c_syntax.bsl.reader.designer.converter;

import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.github._1c_syntax.bsl.supconf.ParseSupportData;
import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.github._1c_syntax.bsl.types.MDOType;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.experimental.UtilityClass;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Выполняет базовое чтение файлов
 */
@UtilityClass
class Unmarshaller {

  private static final String PROPERTIES_NODE = "Properties";
  private static final String CHILD_OBJECTS_NODE = "ChildObjects";
  private static final String NAME_NODE = "Name";
  private static final String TEMPLATE_TYPE_NODE = "TemplateType";
  private static final String CP_MODE_NODE = "CompatibilityMode";
  private static final String CP_EXT_MODE_NODE = "ConfigurationExtensionCompatibilityMode";
  private static final String UUID_FIELD = "uuid";
  private static final String SUPPORT_VALIANT_FIELD = "SupportVariant";

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

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var name = reader.getNodeName();
      if (PROPERTIES_NODE.equals(name)) {
        readProperties(reader, context, readerContext);
      } else if (CHILD_OBJECTS_NODE.equals(name) && readerContext.getMdoType() == MDOType.CONFIGURATION) {
        readChildObjectsConfiguration(reader, readerContext);
      } else if (CHILD_OBJECTS_NODE.equals(name)) {
        readChildObjects(reader, context, readerContext);
      }
      reader.moveUp();
    }
  }

  private void readProperties(HierarchicalStreamReader reader,
                              UnmarshallingContext context,
                              TransformationUtils.Context readerContext) {
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var name = reader.getNodeName();
      Object value = null;
      var fieldClass = TransformationUtils.fieldType(readerContext.getBuilder(), name);

      // не стоит тратить время
      if (fieldClass == null) {
        reader.moveUp();
        continue;
      }

      if (fieldClass instanceof ParameterizedType) {
        if (reader.hasMoreChildren()) {
          value = readCollection(reader, context, readerContext, name);
        }
      } else {
        value = context.convertAnother(fieldClass, (Class<?>) fieldClass);
      }

      if (value instanceof TransformationUtils.Context trContext) {
        readerContext.addChild(name, trContext);
      } else if (value instanceof List
        && !((List<?>) value).isEmpty()
        && ((List<?>) value).get(0) instanceof TransformationUtils.Context) {

        ((List<?>) value).forEach(child -> readerContext.addChild(name, (TransformationUtils.Context) child));

      } else if (name.equals(NAME_NODE) && value instanceof String string) {
        readerContext.setName(string);
      } else if (name.equals(TEMPLATE_TYPE_NODE) && value instanceof TemplateType templateType) {
        readerContext.setTemplateType(templateType);
      } else if (name.equals(CP_MODE_NODE) && value instanceof CompatibilityMode compatibilityMode) {
        readerContext.setCompatibilityMode(compatibilityMode);
      } else if (name.equals(CP_EXT_MODE_NODE) && value instanceof CompatibilityMode compatibilityMode) {
        readerContext.setConfigurationExtensionCompatibilityMode(compatibilityMode);
      }

      readerContext.setValue(name, value);
      reader.moveUp();
    }
  }

  private void readChildObjects(HierarchicalStreamReader reader,
                                UnmarshallingContext context,
                                TransformationUtils.Context readerContext) {
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var name = reader.getNodeName();
      var fieldClass = readerContext.fieldType(name);

      // не стоит тратить время
      if (fieldClass == null) {
        reader.moveUp();
        continue;
      }

      var value = (TransformationUtils.Context) context.convertAnother(fieldClass, fieldClass);
      readerContext.addChild(name, value);
      reader.moveUp();
    }
  }

  private void readChildObjectsConfiguration(HierarchicalStreamReader reader,
                                             TransformationUtils.Context readerContext) {
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var name = reader.getNodeName();
      readerContext.addChildMetadata(name, reader.getValue());
      reader.moveUp();
    }
  }

  private List<Object> readCollection(HierarchicalStreamReader reader,
                                      UnmarshallingContext context,
                                      TransformationUtils.Context readerContext,
                                      String fieldName) {
    List<Object> result = new ArrayList<>();
    var valueClass = readerContext.fieldType(fieldName);

    // не стоит тратить время
    if (valueClass == null) {
      return result;
    }

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var value = context.convertAnother(valueClass, valueClass);
      result.add(value);
      reader.moveUp();
    }
    return result;
  }
}
