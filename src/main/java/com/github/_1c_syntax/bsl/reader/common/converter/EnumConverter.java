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
package com.github._1c_syntax.bsl.reader.common.converter;

import com.github._1c_syntax.bsl.types.EnumWithName;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Класс-конвертер из строкового значения в элемент перечисления.
 * Для использования с конкретным перечислением достаточно зарегистрировать
 * 'EnumConverter<MyEnum>' в XStream (см. ExtendXStream).
 * Перечисление должно реализовывать EnumWithName и предоставлять статический метод valueByName(String).
 */
@Slf4j
public class EnumConverter<T extends Enum<T> & EnumWithName> extends AbstractSingleValueConverter {
  private final Class<T> enumClazz;
  private final Method valueByNameMethod;

  public EnumConverter(Class<T> clazz) {
    enumClazz = clazz;
    try {
      var methodFind = clazz.getDeclaredMethod("valueByName", String.class);
      if (!Modifier.isStatic(methodFind.getModifiers())) {
        throw new IllegalArgumentException("valueByName must be static: " + clazz.getName());
      }
      if (!enumClazz.isAssignableFrom(methodFind.getReturnType())) {
        throw new IllegalArgumentException("valueByName must return " + enumClazz.getName());
      }
      methodFind.setAccessible(true);
      valueByNameMethod = methodFind;
    } catch (NoSuchMethodException e) {
      throw new IllegalArgumentException("Not found valueByName(String) in " + clazz.getName(), e);
    }
  }

  @Override
  public Object fromString(String sourceString) {
    if (sourceString == null) {
      return null;
    }
    try {
      return valueByNameMethod.invoke(null, sourceString);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean canConvert(Class type) {
    return enumClazz.isAssignableFrom(type);
  }
}
