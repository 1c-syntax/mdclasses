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

import com.github._1c_syntax.bsl.mdo.children.ExternalDataSourceCube;
import com.github._1c_syntax.bsl.mdo.children.ExternalDataSourceCubeDimensionTable;
import com.github._1c_syntax.bsl.mdo.children.ExternalDataSourceTable;
import com.github._1c_syntax.bsl.reader.common.converter.AbstractReadConverter;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.types.MDOType;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import org.jspecify.annotations.Nullable;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * Конвертер для таблицы внешнего источника
 */
@EDTConverter
public class ExternalDataSourceConverter extends AbstractReadConverter {

  private static final int POSITION_CHILD_NAME = 3;
  private static final int POSITION_CHILD_CHILD_NAME = 5;
  private static final Pattern SPLITTER_PATTERN = Pattern.compile("\\.");
  private static final Map<String, MDOType> TYPES_BY_CLASSES_3 = computeTypes3();
  private static final Map<String, MDOType> TYPES_BY_CLASSES_5 = computeTypes5();

  @Override
  @Nullable
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    if (reader.getAttributeCount() == 0) {
      var realClass = ExtendXStream.getRealClass(reader, reader.getNodeName());
      if (realClass == null) {
        throw new IllegalStateException("Could not resolve class for: " + reader.getNodeName());
      }
      int position;
      MDOType mdoType;
      var realClassName = realClass.getName();
      if (TYPES_BY_CLASSES_3.containsKey(realClassName)) {
        position = POSITION_CHILD_NAME;
        mdoType = TYPES_BY_CLASSES_3.get(realClassName);
      } else {
        position = POSITION_CHILD_CHILD_NAME;
        mdoType = TYPES_BY_CLASSES_5.get(realClassName);
      }

      // здесь только имя после третьей точки
      var childName = SPLITTER_PATTERN.split(reader.getValue())[position];
      return ExtendXStream.read(reader, dataPath(ExtendXStream.getCurrentPath(reader), mdoType, childName));
    }

    return super.read(reader, context);
  }

  @Override
  public boolean canConvert(Class type) {
    return ExternalDataSourceTable.class.isAssignableFrom(type)
      || ExternalDataSourceCube.class.isAssignableFrom(type)
      || ExternalDataSourceCubeDimensionTable.class.isAssignableFrom(type);
  }

  private static Map<String, MDOType> computeTypes3() {
    Map<String, MDOType> types = new ConcurrentHashMap<>();
    types.put(ExternalDataSourceTable.class.getName(), MDOType.EXTERNAL_DATA_SOURCE_TABLE);
    types.put(ExternalDataSourceCube.class.getName(), MDOType.EXTERNAL_DATA_SOURCE_CUBE);
    return types;
  }

  private static Map<String, MDOType> computeTypes5() {
    Map<String, MDOType> types = new ConcurrentHashMap<>();
    types.put(ExternalDataSourceCubeDimensionTable.class.getName(), MDOType.EXTERNAL_DATA_SOURCE_CUBE_DIMENSION_TABLE);
    return types;
  }

  private static Path dataPath(Path path, MDOType mdoType, String childName) {
    return Paths.get(path.getParent().toString(), mdoType.groupName(),
      childName, childName + ".mdo");
  }
}
