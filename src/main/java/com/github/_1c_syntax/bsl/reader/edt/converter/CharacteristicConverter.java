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
package com.github._1c_syntax.bsl.reader.edt.converter;

import com.github._1c_syntax.bsl.mdo.storage.Characteristic;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import org.jspecify.annotations.Nullable;

/**
 * Конвертер для чтения характеристик из EDT формата
 */
@EDTConverter
public class CharacteristicConverter implements ReadConverter {

  private static final String CHARACTERISTIC_TYPES_NODE = "characteristicTypes";
  private static final String CHARACTERISTIC_VALUES_NODE = "characteristicValues";
  private static final String KEY_FIELD_NODE = "keyField";
  private static final String TYPES_FILTER_FIELD_NODE = "typesFilterField";
  private static final String DATA_PATH_FIELD_NODE = "dataPathField";
  private static final String MULTIPLE_VALUES_USE_FIELD_NODE = "multipleValuesUseField";
  private static final String OBJECT_FIELD_NODE = "objectField";
  private static final String TYPE_FIELD_NODE = "typeField";
  private static final String VALUE_FIELD_NODE = "valueField";
  private static final String MULTIPLE_VALUES_KEY_FIELD_NODE = "multipleValuesKeyField";
  private static final String MULTIPLE_VALUES_ORDER_FIELD_NODE = "multipleValuesOrderField";

  @Override
  @Nullable
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    return readCharacteristic(reader);
  }

  @Override
  public boolean canConvert(Class type) {
    return Characteristic.class.isAssignableFrom(type);
  }

  private static @Nullable Characteristic readCharacteristic(HierarchicalStreamReader reader) {
    if (!reader.hasMoreChildren()) {
      return null;
    }

    var builder = Characteristic.builder();
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var node = reader.getNodeName();
      switch (node) {
        case CHARACTERISTIC_TYPES_NODE ->
          builder.characteristicTypes(safeCreateRef(reader.getValue()));
        case CHARACTERISTIC_VALUES_NODE ->
          builder.characteristicValues(safeCreateRef(reader.getValue()));
        case KEY_FIELD_NODE -> builder.keyField(safeCreateRef(reader.getValue()));
        case TYPES_FILTER_FIELD_NODE -> builder.typesFilterField(safeCreateRef(reader.getValue()));
        case DATA_PATH_FIELD_NODE -> builder.dataPathField(safeCreateRef(reader.getValue()));
        case MULTIPLE_VALUES_USE_FIELD_NODE ->
          builder.multipleValuesUseField(safeCreateRef(reader.getValue()));
        case OBJECT_FIELD_NODE -> builder.objectField(safeCreateRef(reader.getValue()));
        case TYPE_FIELD_NODE -> builder.typeField(safeCreateRef(reader.getValue()));
        case VALUE_FIELD_NODE -> builder.valueField(safeCreateRef(reader.getValue()));
        case MULTIPLE_VALUES_KEY_FIELD_NODE ->
          builder.multipleValuesKeyField(safeCreateRef(reader.getValue()));
        case MULTIPLE_VALUES_ORDER_FIELD_NODE ->
          builder.multipleValuesOrderField(safeCreateRef(reader.getValue()));
        default -> {
          // no-op
        }
      }
      reader.moveUp();
    }
    return builder.build();
  }

  private static MdoReference safeCreateRef(String value) {
    try {
      return MdoReference.create(value);
    } catch (IllegalArgumentException e) {
      return MdoReference.EMPTY;
    }
  }
}
