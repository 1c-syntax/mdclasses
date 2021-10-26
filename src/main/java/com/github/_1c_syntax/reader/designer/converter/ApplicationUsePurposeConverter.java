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
package com.github._1c_syntax.reader.designer.converter;

import com.github._1c_syntax.bsl.mdo.support.ApplicationUsePurpose;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс-конвертер из строкового значения в элемент перечисления.
 * Для каждого конкретного перечисления надо создать собственный класс, унаследованный от EnumWithValues
 * и для каждого элемента указать все варианты значений.
 * Необходимо в конструкторе передать класс перечисления и зарегистрировать созданный класс конвертора в
 * XStreamFactory.
 */
public class ApplicationUsePurposeConverter implements Converter {

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    List<ApplicationUsePurpose> result = new ArrayList<>();
    if (reader.hasMoreChildren()) {
      while (reader.hasMoreChildren()) {
        reader.moveDown();
        result.add(fromValue(reader.getValue()));
        reader.moveUp();
      }
    }
    return result;
  }

  private static ApplicationUsePurpose fromValue(String value) {
    for (ApplicationUsePurpose item : ApplicationUsePurpose.values()) {
      if (item.getValues().contains(value)) {
        return item;
      }
    }
    throw new IllegalArgumentException(value);
  }

  @Override
  public boolean canConvert(Class type) {
    return ApplicationUsePurpose.class.isAssignableFrom(type);
  }
}