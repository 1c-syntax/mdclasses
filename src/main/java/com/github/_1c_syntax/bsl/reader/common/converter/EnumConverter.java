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

/**
 * Класс-конвертер из строкового значения в элемент перечисления.
 * Для каждого конкретного перечисления надо создать собственный класс, унаследованный от EnumWithValues.
 * Необходимо в конструкторе передать класс перечисления и зарегистрировать созданный класс конвертора в
 * *XStreamFactory.
 */
@Slf4j
public class EnumConverter<T extends Enum<T> & EnumWithName> extends AbstractSingleValueConverter {

  private static final String URL_TEMPLATE =
    "https://github.com/1c-syntax/mdclasses/issues/new?labels=bug&title=%5BBUG%5D%20Unknown%20element%20%5B{}%20{}%5D";
  private static final String WARN_TEMPLATE =
    "Parsing error due to unknown element {}. Please, create issue using link " + URL_TEMPLATE;

  private final Class<T> enumClazz;
  private final Method valueByNameMethod;

  public EnumConverter(Class<T> clazz) {
    enumClazz = clazz;
    Method methodFind = null;
    for (var method : clazz.getDeclaredMethods()) {
      if (method.getName().equals("valueByName")) {
        methodFind = method;
        break;
      }
    }
    if (methodFind != null) {
      valueByNameMethod = methodFind;
    } else {
      throw new ClassCastException("Not found method valueByName");
    }
  }

  @Override
  public Object fromString(String sourceString) {
    try {
      return valueByNameMethod.invoke(enumClazz, sourceString);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean canConvert(Class type) {
    return enumClazz.isAssignableFrom(type);
  }
}
