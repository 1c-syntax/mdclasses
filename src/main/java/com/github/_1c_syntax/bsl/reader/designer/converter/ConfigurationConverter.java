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

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.ConfigurationExtension;
import com.github._1c_syntax.bsl.mdclasses.ConfigurationTree;
import com.github._1c_syntax.bsl.reader.common.context.MDCReaderContext;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import java.io.IOException;
import java.nio.file.Files;

/**
 * Конвертер для конфигурации/расширения в формате Конфигуратора (Designer).
 * Определяет тип по наличию строки {@code ConfigurationExtensionPurpose} в файле
 */
@DesignerConverter
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
   * Проверяет наличие строки ConfigurationExtensionPurpose в файле
   */
  private static boolean hasExtensionMarker(HierarchicalStreamReader reader) {
    try (var lines = Files.lines(ExtendXStream.getCurrentPath(reader))) {
      return lines.anyMatch(line -> line.contains("ConfigurationExtensionPurpose"));
    } catch (IOException e) {
      return false;
    }
  }
}
