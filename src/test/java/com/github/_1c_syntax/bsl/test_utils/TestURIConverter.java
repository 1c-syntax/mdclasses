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
package com.github._1c_syntax.bsl.test_utils;

import com.github._1c_syntax.utils.Absolute;
import com.thoughtworks.xstream.converters.basic.URIConverter;

import java.nio.file.Path;

/**
 * Для возможности сохранять в фикстурах пути относительно рабочего каталога
 */
public class TestURIConverter extends URIConverter {
  private final static String WORKDIR = Path.of("").toUri().getPath();

  @Override
  public String toString(Object obj) {
    return Absolute.uri(obj.toString()
        .replace("%D0%99", "_")
        .replace("%D0%B9", "_")
        .replace("%D0%98%CC%86", "_")
        .replace("%D0%B8%CC%86", "_"))
      .getPath()
      .replace(WORKDIR, "");
  }
}
