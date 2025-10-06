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
package com.github._1c_syntax.bsl.reader.designer.converter;

import com.github._1c_syntax.bsl.mdo.MDChild;
import com.github._1c_syntax.bsl.mdo.children.ExternalDataSourceTable;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdo.children.ObjectTemplate;
import com.github._1c_syntax.bsl.mdo.children.Recalculation;
import com.github._1c_syntax.bsl.reader.common.converter.AbstractReadConverter;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.types.MDOType;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Конвертер для дочерних элементов (атрибуты, операции и т.д.)
 */
@DesignerConverter
public class MDChildConverter extends AbstractReadConverter {

  private static final Map<String, MDOType> TYPES_BY_CLASSES = computeTypes();

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var currentPath = ExtendXStream.getCurrentPath(reader);
    var realClassName = reader.getNodeName();
    var realClass = ExtendXStream.getRealClass(reader, realClassName);

    if (TYPES_BY_CLASSES.containsKey(realClass.getName()) && reader.getAttributeCount() == 0) {
      var mdoType = TYPES_BY_CLASSES.get(realClass.getName());
      var childName = ExtendXStream.readValue(context, String.class);
      return ExtendXStream.read(reader, childDataPath(currentPath, mdoType, childName));
    } else {
      return super.read(reader, context);
    }
  }

  @Override
  public boolean canConvert(Class type) {
    return
      !ObjectTemplate.class.isAssignableFrom(type)
        && MDChild.class.isAssignableFrom(type);
  }

  private static Map<String, MDOType> computeTypes() {
    Map<String, MDOType> types = new ConcurrentHashMap<>();
    types.put(ObjectForm.class.getName(), MDOType.FORM);
    types.put(Recalculation.class.getName(), MDOType.RECALCULATION);
    types.put(ExternalDataSourceTable.class.getName(), MDOType.EXTERNAL_DATA_SOURCE_TABLE);
    return types;
  }

  /**
   * Builds the filesystem path to a child MDO data XML file.
   *
   * @param path the original resource path for the parent MDO file
   * @param mdoType the MDO type whose group name is used as a path segment
   * @param childName the child object's base name (without extension)
   * @return the Path to the child data XML file in the form
   *         parent/<baseName>/<groupName>/<childName>.xml
   */
  private static Path childDataPath(Path path, MDOType mdoType, String childName) {
    return Paths.get(path.getParent().toString(),
      FilenameUtils.getBaseName(path.toString()),
      mdoType.groupName(),
      childName + ".xml");
  }
}