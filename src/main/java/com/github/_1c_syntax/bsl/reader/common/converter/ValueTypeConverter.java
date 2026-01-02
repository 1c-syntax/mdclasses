/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
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

import com.github._1c_syntax.bsl.types.ValueType;
import com.github._1c_syntax.bsl.types.ValueTypeVariant;
import com.github._1c_syntax.bsl.types.ValueTypes;
import com.github._1c_syntax.bsl.types.value.CustomValueType;
import com.github._1c_syntax.bsl.types.value.MDOValueType;
import com.github._1c_syntax.bsl.types.value.PrimitiveValueType;
import com.github._1c_syntax.bsl.types.value.V8ValueType;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Конвертер типа значений. Прочитанные типы запоминаются в кеше.
 * Используется безрегистровое кеширование, т.е. типы String и STRING считаются одинаковыми
 */
@Slf4j
@CommonConverter
public class ValueTypeConverter extends AbstractSingleValueConverter {
  private static final String URL_TEMPLATE =
    "https://github.com/1c-syntax/mdclasses/issues/new?labels=bug&title=%5BBUG%5D%20Unknown%20valueType%20%5B{}%5D";
  private static final String WARN_TEMPLATE =
    "Parsing error due to unknown value type {}. Please, create issue using link " + URL_TEMPLATE;

  /**
   * Кеш прочитанных типов
   */
  private static final Map<String, ValueType> ALL_TYPES = builtinTypes();

  @Override
  public Object fromString(String string) {
    // сначала из кеша
    var type = getType(string);
    if (type != null) {
      return type;
    }

    // Имя может содержать префикс-неймспейс. Отрежем его и еще раз поищем
    // Т.о. типы с префиксами и без считаются одинаковыми. Например xs:string == String
    var trimString = string;
    var posColon = string.indexOf(":");
    if (posColon > 0) {
      trimString = string.substring(posColon + 1);
      type = getType(trimString);
      if (type != null) {
        putType(string, type);
        return type;
      }
    }

    // Попробуем найти тип в типах метаданных
    type = ValueTypes.getOrCompute(trimString);
    putType(string, type);

    if (type instanceof CustomValueType customValueType && customValueType.variant() == ValueTypeVariant.UNKNOWN) {
      // Тип нам неизвестен, выведем ворнинг
      LOGGER.warn(WARN_TEMPLATE, string, string);
    }
    return type;
  }

  @Override
  public boolean canConvert(Class type) {
    return type == ValueType.class;
  }

  private static Map<String, ValueType> builtinTypes() {
    Map<String, ValueType> types = new ConcurrentHashMap<>();

    Arrays.stream(MDOValueType.values()).forEach(valueType ->
      types.put(valueType.nameEn().toLowerCase(Locale.ROOT), valueType));
    types.put("cfg:AnyIBRef".toLowerCase(Locale.ROOT), MDOValueType.ANY_REF);

    Arrays.stream(PrimitiveValueType.values()).forEach(valueType ->
      types.put(valueType.nameEn().toLowerCase(Locale.ROOT), valueType));
    types.put("xs:decimal".toLowerCase(Locale.ROOT), PrimitiveValueType.NUMBER);
    types.put("xs:dateTime".toLowerCase(Locale.ROOT), PrimitiveValueType.DATE);

    Arrays.stream(V8ValueType.values()).forEach(valueType ->
      types.put(valueType.nameEn().toLowerCase(Locale.ROOT), valueType));
    types.put("xs:base64Binary".toLowerCase(Locale.ROOT), V8ValueType.VALUE_STORAGE);

    types.put("v8:ValueListType".toLowerCase(Locale.ROOT), V8ValueType.VALUE_LIST);
    types.put("d5p1:FlowchartContextType".toLowerCase(Locale.ROOT), V8ValueType.GRAPHICAL_SCHEMA);

    return types;
  }

  private static void putType(String key, ValueType type) {
    ALL_TYPES.put(key.toLowerCase(Locale.ROOT), type);
  }

  @Nullable
  private static ValueType getType(String key) {
    return ALL_TYPES.get(key.toLowerCase(Locale.ROOT));
  }
}
