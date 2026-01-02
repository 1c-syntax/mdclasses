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
package com.github._1c_syntax.bsl.reader.common.converter;

import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.reader.MDReader;
import com.github._1c_syntax.bsl.reader.common.context.MDReaderContext;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.utils.CaseInsensitivePattern;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import org.apache.commons.io.FilenameUtils;
import org.jspecify.annotations.Nullable;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

/**
 * Общий конвертер для подсистем
 */
@CommonConverter
public class SubsystemConverter implements ReadConverter {

  private static final String START_MDOREF_NAME = MDOType.SUBSYSTEM.nameEn() + ".";
  private static final int COUNT_PARTS = 2;
  private static final Pattern NAME_SPLITTER_PATTERN = CaseInsensitivePattern.compile("[\\\\/]"
    + MDOType.SUBSYSTEM.groupName() + "[\\\\/]");

  @Override
  @Nullable
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var name = reader.getNodeName();
    var currentPath = ExtendXStream.getCurrentPath(reader);
    var mdReader = ExtendXStream.getCurrentMDReader(reader);

    if (mdReader.subsystemsNodeName().equals(name) && reader.getAttributeCount() == 0) { // дочерняя подсистема строкой
      return ExtendXStream.read(reader, dataPath(mdReader, currentPath), START_MDOREF_NAME + reader.getValue());
    } else { // самостоятельная подсистема
      var readerContext = new MDReaderContext(reader);
      readerContext.getMdReader().unmarshal(reader, context, readerContext);

      var prjPath = mdReader.getRootPath();
      requireNonNull(prjPath);
      var localRootPath = currentPath.toString().replace(prjPath.toString(), "");

      // определим это самостоятельная или дочерняя
      // у дочерней будет несколько вложенных папок подсистемы
      if (NAME_SPLITTER_PATTERN.split(localRootPath).length > COUNT_PARTS) {
        return readerContext;
      } else {
        return readerContext.build();
      }
    }
  }

  @Override
  public boolean canConvert(Class type) {
    return Subsystem.class.isAssignableFrom(type);
  }

  private static Path dataPath(MDReader mdReader, Path currentPath) {
    return Paths.get(
      mdReader.mdoTypeFolderPath(currentPath).toString(),
      FilenameUtils.getBaseName(currentPath.toString()),
      MDOType.SUBSYSTEM.groupName()
    );
  }
}
