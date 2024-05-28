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
package com.github._1c_syntax.bsl.reader.common.converter;

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.ConfigurationExtension;
import com.github._1c_syntax.bsl.mdclasses.ConfigurationTree;
import com.github._1c_syntax.bsl.reader.common.context.MDCReaderContext;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Обобщенный конвертер для контейнеров конфигурации и расширения
 */
@CommonConverter
public class ConfigurationConverter implements ReadConverter {

  @SneakyThrows
  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    int count;
    var fileInputStream = new FileInputStream(ExtendXStream.getCurrentPath(reader).toFile());
    try (var scanner = new Scanner(fileInputStream, StandardCharsets.UTF_8)) {
      count = (int) scanner.findAll(ExtendXStream.getCurrentMDReader(reader).configurationExtensionFilter()).count();
    }

    Class<?> realClass = Configuration.class;
    if (count > 0) {
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
}
