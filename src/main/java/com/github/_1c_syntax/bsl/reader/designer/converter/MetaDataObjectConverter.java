/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2024
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

import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.bsl.reader.designer.DesignerReader;
import com.github._1c_syntax.mdclasses.wrapper.DesignerRootWrapper;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * Конвертор для объектов в формате конфигуратора, минуя класс враппер
 */
@Slf4j
@DesignerConverter
public class MetaDataObjectConverter implements ReadConverter {

  @SneakyThrows
  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    reader.moveDown();
    var nodeName = reader.getNodeName();
    Class<?> realClass = DesignerReader.getXstream().getRealClass(nodeName);
    if (realClass == null) {
      LOGGER.error("Unexpected type `{}`, path: `{}`", nodeName, ExtendXStream.getCurrentPath(reader));
      throw new IllegalStateException("Unexpected type: " + nodeName);
    }

    return context.convertAnother(reader, realClass);
  }

  @Override
  public boolean canConvert(Class type) {
    return DesignerRootWrapper.class.isAssignableFrom(type);
  }
}
