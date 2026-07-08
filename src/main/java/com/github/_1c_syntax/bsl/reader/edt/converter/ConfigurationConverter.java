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

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.ConfigurationExtension;
import com.github._1c_syntax.bsl.mdclasses.ConfigurationTree;
import com.github._1c_syntax.bsl.reader.common.context.MDCReaderContext;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendReaderWrapper;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

/**
 * Конвертер для конфигурации/расширения в формате EDT.
 * Определяет тип по атрибутам и namespace корневого элемента
 */
@EDTConverter
public class ConfigurationConverter implements ReadConverter {

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    Class<?> realClass = Configuration.class;

    if (hasExtensionMarker(reader)) {
      realClass = ConfigurationExtension.class;
    }

    var readerContext = new MDCReaderContext(realClass, reader);
    readerContext.getMdReader().unmarshal(reader, context, readerContext);
    return readerContext.build();
  }

  @Override
  public boolean canConvert(Class type) {
    return ConfigurationTree.class.isAssignableFrom(type);
  }

  /**
   * Проверяет EDT-формат: {@code xsi:type="mdclassExtension:ConfigurationExtension"}
   * или атрибут {@code extension="true"} в корневом элементе,
   * или наличие namespace {@code xmlns:mdclassExtension}.
   */
  private static boolean hasExtensionMarker(HierarchicalStreamReader reader) {
    var typeAttr = reader.getAttribute("xsi:type");
    if (typeAttr != null && typeAttr.contains("mdclassExtension:ConfigurationExtension")) {
      return true;
    }

    var extAttr = reader.getAttribute("extension");
    if ("true".equals(extAttr)) {
      return true;
    }

    // Проверка через namespace (некоторые форматы EDT не выставляют xsi:type на корне)
    if (reader instanceof ExtendReaderWrapper wrapper) {
      var xmlReader = wrapper.getXMLStreamReader();
      for (int i = 0; i < xmlReader.getNamespaceCount(); i++) {
        if ("mdclassExtension".equals(xmlReader.getNamespacePrefix(i))) {
          return true;
        }
      }
    }

    return false;
  }
}
