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

import com.github._1c_syntax.bsl.support.CompatibilityMode;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

/**
 * Используется для преобразования режима совместимости из строкового вида в объектный вид
 */
@CommonConverter
public class CompatibilityModeConverter extends AbstractSingleValueConverter {

  @Override
  public Object fromString(String sourceString) {
    return new CompatibilityMode(sourceString);
  }

  @Override
  public boolean canConvert(Class type) {
    return CompatibilityMode.class.isAssignableFrom(type);
  }
}
