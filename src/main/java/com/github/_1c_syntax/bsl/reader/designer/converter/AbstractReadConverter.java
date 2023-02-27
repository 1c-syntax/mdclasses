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

import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.bsl.reader.designer.DesignerReader;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import java.nio.file.Path;

public abstract class AbstractReadConverter implements ReadConverter {

  protected String name;
  protected Class<?> realClass;
  protected Path currentPath;

  protected TransformationUtils.Context read(HierarchicalStreamReader reader, UnmarshallingContext context) {
    name = reader.getNodeName();
    realClass = DesignerReader.getXstream().getRealClass(name);
    currentPath = ExtendXStream.getCurrentPath(reader);
    var readerContext = new TransformationUtils.Context(reader.getNodeName(), realClass, currentPath);
    Unmarshaller.unmarshal(reader, context, readerContext);
    return readerContext;
  }
}
