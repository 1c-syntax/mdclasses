/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2021
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
package com.github._1c_syntax.mdclasses.unmarshal.converters;

import com.github._1c_syntax.mdclasses.mdo.support.DataPath;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Конвертирует dataPath с учетом нескольких видов описания
 */
public class DataPathConverter implements Converter {

  private static final String PATHS_NODE = "paths";
  private static final String SEGMENTS_NODE = "segments";

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var segment = "";
    reader.moveDown();
    if (PATHS_NODE.equals(reader.getNodeName())) {
      reader.moveDown();
    }
    if (SEGMENTS_NODE.equals(reader.getNodeName())) {
      segment = reader.getValue();
      reader.moveUp();
    }
    if (PATHS_NODE.equals(reader.getNodeName())) {
      reader.moveUp();
    }
    return new DataPath(segment);
  }

  @Override
  public boolean canConvert(Class type) {
    return type == DataPath.class;
  }
}
