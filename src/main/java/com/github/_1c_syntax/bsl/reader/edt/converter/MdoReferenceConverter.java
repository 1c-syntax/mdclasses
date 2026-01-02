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

import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import org.jspecify.annotations.Nullable;

/**
 * Используется для преобразования строковых представлений ссылки на объект
 */
@EDTConverter
public class MdoReferenceConverter implements ReadConverter {

  private static final String PICTURE_NODE_NAME = "picture";

  @Override
  @Nullable
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var nodeName = reader.getNodeName();
    var value = "";
    if (PICTURE_NODE_NAME.equals(nodeName) && reader.hasMoreChildren()) {
      reader.moveDown();
      value = reader.getValue();
      reader.moveUp();
    } else {
      value = reader.getValue();
    }

    if (value.isEmpty() || value.contains("-")) {
      return null;
    } else {
      return MdoReference.create(value);
    }
  }

  @Override
  public boolean canConvert(Class type) {
    return MdoReference.class.isAssignableFrom(type);
  }
}
