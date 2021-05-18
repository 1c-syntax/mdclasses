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

import com.github._1c_syntax.mdclasses.unmarshal.XStreamFactory;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerRootWrapper;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Конвертор для объектов в формате конфигуратора, минуя класс враппер
 */
@Slf4j
public class DesignerMDOConverter implements Converter {
  private final Map<Class<?>, Constructor<?>> constructors = new HashMap<>();

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // no-op
  }

  @SneakyThrows
  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    reader.moveDown();
    var nodeName = reader.getNodeName();
    Class<?> realClass = XStreamFactory.getRealClass(nodeName);
    if (realClass == null) {
      throw new IllegalStateException("Unexpected type: " + nodeName);
    }
    var wrapperMDO = (DesignerMDO) context.convertAnother(reader, DesignerMDO.class);
    wrapperMDO.setMdoPath(XStreamFactory.getCurrentPath(reader));
    return getConstructor(realClass).newInstance(wrapperMDO);
  }

  @Override
  public boolean canConvert(Class type) {
    return type == DesignerRootWrapper.class;
  }

  private Constructor<?> getConstructor(Class<?> realClass) {
    return constructors.computeIfAbsent(realClass,
      (Class<?> realClazz) -> {
        try {
          return realClazz.getDeclaredConstructor(DesignerMDO.class);
        } catch (NoSuchMethodException e) {
          throw new IllegalStateException("No constructor found: " + realClass.getCanonicalName());
        }
      });
  }
}
