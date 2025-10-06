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

  /**
   * Unmarshals a subsystem node from the XML reader into either a reference, a reader context, or a built subsystem object.
   *
   * If the current node name equals mdReader.subsystemsNodeName() and the node has no attributes, returns a read result
   * for a child-subsystem reference whose name is START_MDOREF_NAME concatenated with the node text and whose path is
   * derived from the MD reader and current path. Otherwise performs full unmarshalling via an MDReaderContext and:
   * - returns the MDReaderContext itself when the local root path indicates a nested (child) subsystem, or
   * - returns readerContext.build() for a standalone subsystem.
   *
   * @param reader  the HierarchicalStreamReader positioned at the subsystem node
   * @param context the UnmarshallingContext used during unmarshalling
   * @return an object representing either a subsystem reference, an MDReaderContext for nested subsystems, or the built standalone subsystem object
   */
  @Override
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

  /**
   * Builds the filesystem path pointing to the subsystem folder for the given metadata node.
   *
   * @param mdReader    reader providing metadata layout helpers (used to obtain the MDO type folder path)
   * @param currentPath the current node path from which the base name is taken and appended
   * @return            a Path composed of the MDO type folder path for the current path, the current path's base name, and the subsystem group name
   */
  private static Path dataPath(MDReader mdReader, Path currentPath) {
    return Paths.get(
      mdReader.mdoTypeFolderPath(currentPath).toString(),
      FilenameUtils.getBaseName(currentPath.toString()),
      MDOType.SUBSYSTEM.groupName()
    );
  }
}