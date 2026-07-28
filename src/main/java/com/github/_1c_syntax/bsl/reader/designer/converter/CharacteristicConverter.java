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
package com.github._1c_syntax.bsl.reader.designer.converter;

import com.github._1c_syntax.bsl.mdo.storage.Characteristic;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Конвертер для чтения характеристик из Designer формата
 */
@DesignerConverter
public class CharacteristicConverter implements ReadConverter {

  private static final String CHARACTERISTIC_NODE = "Characteristic";
  private static final String CHARACTERISTIC_TYPES_NODE = "CharacteristicTypes";
  private static final String CHARACTERISTIC_VALUES_NODE = "CharacteristicValues";
  private static final String KEY_FIELD_NODE = "KeyField";
  private static final String TYPES_FILTER_FIELD_NODE = "TypesFilterField";
  private static final String DATA_PATH_FIELD_NODE = "DataPathField";
  private static final String MULTIPLE_VALUES_USE_FIELD_NODE = "MultipleValuesUseField";
  private static final String OBJECT_FIELD_NODE = "ObjectField";
  private static final String TYPE_FIELD_NODE = "TypeField";
  private static final String VALUE_FIELD_NODE = "ValueField";
  private static final String MULTIPLE_VALUES_KEY_FIELD_NODE = "MultipleValuesKeyField";
  private static final String MULTIPLE_VALUES_ORDER_FIELD_NODE = "MultipleValuesOrderField";
  private static final String FROM_ATTRIBUTE_NAME = "from";

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    List<Characteristic> result = new ArrayList<>();
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if (CHARACTERISTIC_NODE.equals(reader.getNodeName())) {
        var characteristic = readCharacteristic(reader);
        if (characteristic != null) {
          result.add(characteristic);
        }
      }
      reader.moveUp();
    }
    return result;
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
        case CHARACTERISTIC_TYPES_NODE -> {
          var from = reader.getAttribute(FROM_ATTRIBUTE_NAME);
          if (from != null) {
            builder.characteristicTypes(safeCreateRef(from));
          }
          readFields(reader, builder);
        }
        case CHARACTERISTIC_VALUES_NODE -> {
          var from = reader.getAttribute(FROM_ATTRIBUTE_NAME);
          if (from != null) {
            builder.characteristicValues(safeCreateRef(from));
          }
          readFields(reader, builder);
        }
        default -> {
          // no-op
        }
      }
      reader.moveUp();
    }
    return builder.build();
  }

  private static void readFields(HierarchicalStreamReader reader,
                                 Characteristic.CharacteristicBuilder builder) {
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var node = reader.getNodeName();
      switch (node) {
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
  }

  private static MdoReference safeCreateRef(String value) {
    try {
      return MdoReference.create(value);
    } catch (IllegalArgumentException e) {
      return MdoReference.EMPTY;
    }
  }
}
