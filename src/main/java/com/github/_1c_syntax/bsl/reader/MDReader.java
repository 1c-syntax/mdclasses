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
package com.github._1c_syntax.bsl.reader;

import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.thoughtworks.xstream.converters.Converter;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Path;

/**
 * Интерфейс для ридеров исходников
 */
public interface MDReader {

  ConfigurationSource getConfigurationSource();

  @Nullable
  Object read(String fullName);

  default Object read(Path fullMdoName) {
    return fromXml(fullMdoName.toFile());
  }

  @Nullable
  Object read(Path folder, String fullName);

  @Nullable
  default Path getRootPath() {
    return null;
  }

  default Object fromXml(File file) {
    return getEXStream().fromXML(file);
  }

  default Converter getReflectionConverter() {
    return getEXStream().getReflectionConverter();
  }

  ExtendXStream getEXStream();

  MDClass readConfiguration();

  MDClass readExternalSource();
}
