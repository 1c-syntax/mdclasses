/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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

import com.github._1c_syntax.bsl.mdo.children.ExternalDataSourceTable;
import com.github._1c_syntax.bsl.reader.common.converter.AbstractReadConverter;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.types.MDOType;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

/**
 * Конвертер для таблицы внешнего источника
 */
@EDTConverter
public class ExternalDataSourceTableConverter extends AbstractReadConverter {

  private static final int POSITION_CHILD_NAME = 3;
  private static final Pattern SPLITTER_PATTERN = Pattern.compile("\\.");

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    if (reader.getAttributeCount() == 0) {
      // здесь только имя после третьей точки
      var childName = SPLITTER_PATTERN.split(reader.getValue())[POSITION_CHILD_NAME];
      return ExtendXStream.read(reader, dataPath(ExtendXStream.getCurrentPath(reader), childName));
    }

    return super.read(reader, context);
  }

  @Override
  public boolean canConvert(Class type) {
    return ExternalDataSourceTable.class.isAssignableFrom(type);
  }

  private static Path dataPath(Path path, String childName) {
    return Paths.get(path.getParent().toString(), MDOType.EXTERNAL_DATA_SOURCE_TABLE.getGroupName(),
      childName, childName + ".mdo");
  }
}
