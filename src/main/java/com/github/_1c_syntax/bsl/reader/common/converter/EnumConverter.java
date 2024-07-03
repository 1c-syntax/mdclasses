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
package com.github._1c_syntax.bsl.reader.common.converter;

import com.github._1c_syntax.bsl.mdo.support.EnumWithValue;
import com.github._1c_syntax.bsl.mdo.support.UsePurposes;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс-конвертер из строкового значения в элемент перечисления.
 * Для каждого конкретного перечисления надо создать собственный класс, унаследованный от EnumWithValues.
 * Необходимо в конструкторе передать класс перечисления и зарегистрировать созданный класс конвертора в
 * *XStreamFactory.
 */
@Slf4j
public class EnumConverter<T extends Enum<T> & EnumWithValue> extends AbstractSingleValueConverter {

  private static final String URL_TEMPLATE =
    "https://github.com/1c-syntax/mdclasses/issues/new?labels=bug&title=%5BBUG%5D%20Unknown%20element%20%5B{}%20{}%5D";
  private static final String WARN_TEMPLATE = "Parsing error due to unknown element {}. Please, create issue using link "
    + URL_TEMPLATE;

  private final Class<T> enumClazz;

  public EnumConverter(Class<T> clazz) {
    enumClazz = clazz;
  }

  @Override
  public Object fromString(String sourceString) {
    Object result;
    if (UsePurposes.class.isAssignableFrom(enumClazz)) {
      result = fromValueStringUsePurposes(sourceString);
    } else {
      result = fromValue(enumClazz, sourceString);
    }

    if (result == null) {
      LOGGER.warn(WARN_TEMPLATE, sourceString, enumClazz.getName(), sourceString);
      return unknown(enumClazz);
    }
    return result;
  }

  private static <T extends Enum<T> & EnumWithValue> T fromValue(Class<T> clazz, String value) {
    for (T item : clazz.getEnumConstants()) {
      if (item.value().equals(value)) {
        return item;
      }
    }
    return null;
  }

  private static Object fromValueStringUsePurposes(String sourceString) {
    for (UsePurposes item : UsePurposes.class.getEnumConstants()) {
      if (item.valueVar1().equals(sourceString)
        || item.valueVar2().equals(sourceString)) {
        return item;
      }
    }
    return null;
  }

  private static <T extends Enum<T> & EnumWithValue> T unknown(Class<T> clazz) {
    for (T item : clazz.getEnumConstants()) {
      if (item.isUnknown()) {
        return item;
      }
    }
    throw new IllegalStateException("No unknown value found for enum " + clazz.getName());
  }

  @Override
  public boolean canConvert(Class type) {
    return enumClazz.isAssignableFrom(type);
  }
}
