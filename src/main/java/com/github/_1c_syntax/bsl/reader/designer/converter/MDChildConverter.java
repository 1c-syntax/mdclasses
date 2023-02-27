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

import com.github._1c_syntax.bsl.mdo.MDChild;
import com.github._1c_syntax.bsl.mdo.children.ExternalDataSourceTable;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdo.children.ObjectTemplate;
import com.github._1c_syntax.bsl.mdo.children.Recalculation;
import com.github._1c_syntax.bsl.reader.MDOReader;
import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.bsl.reader.designer.DesignerPaths;
import com.github._1c_syntax.bsl.reader.designer.DesignerReader;
import com.github._1c_syntax.bsl.types.MDOType;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Конвертер для дочерних элементов (атрибуты, операции и т.д.)
 */
@DesignerConverter
public class MDChildConverter implements ReadConverter {

  private static final Map<Class<?>, MDOType> TYPES_BY_CLASSES = computeTypes();

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var name = reader.getNodeName();
    var realClass = DesignerReader.getXstream().getRealClass(name);
    var currentPath = ExtendXStream.getCurrentPath(reader);

    if (TYPES_BY_CLASSES.containsKey(realClass) && reader.getAttributeCount() == 0) {
      var mdoType = TYPES_BY_CLASSES.get(realClass);
      var childrenFolder = DesignerPaths.childrenFolder(currentPath, mdoType);
      var childName = context.convertAnother(String.class, String.class);
      var childPath = Paths.get(childrenFolder.toString(), childName + DesignerPaths.EXTENSION_DOT);
      return MDOReader.read(childPath);
    }

    var readerContext = new TransformationUtils.Context(name, realClass, currentPath);
    Unmarshaller.unmarshal(reader, context, readerContext);
    return readerContext;
  }

  @Override
  public boolean canConvert(Class type) {
    return
      !ObjectTemplate.class.isAssignableFrom(type)
        && MDChild.class.isAssignableFrom(type);
  }

  private static Map<Class<?>, MDOType> computeTypes() {
    Map<Class<?>, MDOType> types = new HashMap<>();
    types.put(ObjectForm.class, MDOType.FORM);
    types.put(Recalculation.class, MDOType.RECALCULATION);
    types.put(ExternalDataSourceTable.class, MDOType.EXTERNAL_DATA_SOURCE_TABLE);
    return types;
  }
}
