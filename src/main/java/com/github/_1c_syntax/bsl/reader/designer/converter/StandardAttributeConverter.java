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

import com.github._1c_syntax.bsl.mdo.children.StandardAttribute;
import com.github._1c_syntax.bsl.reader.common.context.MDReaderContext;
import com.github._1c_syntax.bsl.reader.common.converter.AbstractReadConverter;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

/**
 * Конвертер для дочерних элементов (атрибуты, операции и т.д.)
 */
@DesignerConverter
public class StandardAttributeConverter extends AbstractReadConverter {

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var readerContext = new MDReaderContext(reader);
    var attributeName = reader.getAttribute("name");
    readerContext.setName(attributeName);
    readerContext.setValue("name", attributeName);
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var name = reader.getNodeName();
      var fieldClass = readerContext.fieldType(name);
      if (fieldClass != null) {
        var value = ExtendXStream.readValue(context, fieldClass);
        readerContext.setValue(name, value);
      }
      reader.moveUp();
    }
    return readerContext;
  }

  @Override
  public boolean canConvert(Class type) {
    return StandardAttribute.class.isAssignableFrom(type);
  }
}
