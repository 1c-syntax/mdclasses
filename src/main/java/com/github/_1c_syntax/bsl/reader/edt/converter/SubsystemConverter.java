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

import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.reader.MDOReader;
import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.bsl.reader.edt.EDTPaths;
import com.github._1c_syntax.bsl.reader.edt.EDTReader;
import com.github._1c_syntax.bsl.types.MDOType;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Paths;

import static java.util.Objects.requireNonNull;

@EDTConverter
public class SubsystemConverter implements ReadConverter {

  private static final String START_MDOREF_NAME = MDOType.SUBSYSTEM.getName() + ".";
  private static final String SUBSYSTEMS_NODE = "subsystems";

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var name = reader.getNodeName();
    var currentPath = ExtendXStream.getCurrentPath(reader);

    if (SUBSYSTEMS_NODE.equals(name) && reader.getAttributeCount() == 0) { // дочерняя подсистема строкой

      var rootFolder = EDTPaths.mdoTypeFolderPathByMDOPath(currentPath).toString();
      var currentName = FilenameUtils.getBaseName(currentPath.toString());
      var folder = Paths.get(rootFolder, currentName, MDOType.SUBSYSTEM.getGroupName());
      return MDOReader.readMDObject(folder, START_MDOREF_NAME + reader.getValue());

    } else { // самостоятельная подсистема

      var realClass = EDTReader.getXstream().getRealClass(name);
      var readerContext = new TransformationUtils.Context(reader.getNodeName(), realClass, currentPath);
      Unmarshaller.unmarshal(reader, context, readerContext);

      var prjPath = MDOReader.getReader(ExtendXStream.getCurrentPath(reader)).getRootPath();
      requireNonNull(prjPath);
      var localRootPath = currentPath.toString().replace(prjPath.toString(), "");

      // определим это самостоятельная или дочерняя
      // у дочерней будет несколько вложенных папок подсистемы
      if (localRootPath.split(MDOType.SUBSYSTEM.getName()).length > 2) {
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
}
