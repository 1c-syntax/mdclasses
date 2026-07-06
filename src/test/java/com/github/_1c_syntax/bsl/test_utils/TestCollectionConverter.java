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
package com.github._1c_syntax.bsl.test_utils;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Для оптимизации сериализации коллекций
 */
public class TestCollectionConverter extends CollectionConverter {
  private final boolean compact;

  public TestCollectionConverter(Mapper mapper, boolean compact) {
    super(mapper);
    this.compact = compact;
  }

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    switch (source) {
      case List<?> list -> {
        if (compact) {
          writeCompleteItem(list.size(), context, writer);
          return;
        }

        var sortedList = new ArrayList<>(list);
        sortedList.sort(Comparator.comparing(item -> item == null ? "" : item.toString()));
        for (var item : sortedList) {
          writeCompleteItem(item, context, writer);
        }
      }
      case Map<?, ?> map -> {
        var entries = new ArrayList<>(map.entrySet());
        entries.sort(Comparator.comparing(entry -> {
          var key = entry.getKey();
          return key == null ? "" : key.toString();
        }));

        for (Map.Entry<?, ?> entry : entries) {
          writeCompleteItem(entry.getKey(), context, writer);
          writeCompleteItem(entry.getValue(), context, writer);
        }
      }
      case Set<?> set -> {
        var sortedList = new ArrayList<>(set);
        sortedList.sort(Comparator.comparing(item -> item == null ? "" : item.toString()));
        for (var item : sortedList) {
          writeCompleteItem(item, context, writer);
        }
      }
      case null, default -> super.marshal(source, writer, context);
    }
  }

  @Override
  public boolean canConvert(Class type) {
    return super.canConvert(type)
      || type != null
      && (
      type.getName().startsWith("java.util.ImmutableCollections$List")
        || type.getName().startsWith("java.util.Collections$Unmodifiable")
        || type.getName().startsWith("java.util.ImmutableCollections$Map")
        || type.getName().startsWith("java.util.ImmutableCollections$Set")
    );
  }
}
