/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2022
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
package com.github._1c_syntax.bsl.reader;

import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.thoughtworks.xstream.converters.Converter;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Path;

/**
 * Класс-читатель-заглушка
 */
public class FakeReader implements MDReader {

  @Nullable
  @Override
  public MDObject read(String fullName) {
    return null;
  }

  @Nullable
  @Override
  public MDObject read(Path folder, String fullName) {
    return null;
  }

  @Nullable
  @Override
  public MDObject read(Path fullMdoName) {
    return null;
  }

  @Nullable
  @Override
  public Object fromXml(File file) {
    return null;
  }

  @Nullable
  @Override
  public Converter getReflectionConverter() {
    return null;
  }

  @Nullable
  @Override
  public ExtendXStream getEXStream() {
    return null;
  }
}
