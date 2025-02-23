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

import com.github._1c_syntax.bsl.mdo.Role;
import com.github._1c_syntax.bsl.reader.common.converter.AbstractReadConverter;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import java.nio.file.Path;

@EDTConverter
public class RoleConverter extends AbstractReadConverter {

  private static final String DATA_FIELD = "data";

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var readerContext = super.read(reader, context);
    readerContext.setValue(DATA_FIELD, ExtendXStream.read(reader, dataPath(readerContext.getCurrentPath())));
    return readerContext.build();
  }

  @Override
  public boolean canConvert(Class type) {
    return Role.class.isAssignableFrom(type);
  }

  private static Path dataPath(Path path) {
    return Path.of(path.getParent().toString(), "Rights.rights");
  }
}
