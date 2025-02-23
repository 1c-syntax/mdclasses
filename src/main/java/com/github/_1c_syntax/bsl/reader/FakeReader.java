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
package com.github._1c_syntax.bsl.reader;

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdclasses.ExternalReport;
import com.github._1c_syntax.bsl.mdclasses.ExternalSource;
import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.storage.EmptyFormData;
import com.github._1c_syntax.bsl.mdo.storage.FormData;
import com.github._1c_syntax.bsl.reader.common.context.AbstractReaderContext;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ModuleType;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.nio.file.Path;

/**
 * Класс-читатель-заглушка
 */
public class FakeReader implements MDReader {

  @Override
  @NonNull
  public ConfigurationSource getConfigurationSource() {
    return ConfigurationSource.EMPTY;
  }

  @Override
  @NonNull
  public MDClass readConfiguration() {
    return Configuration.EMPTY;
  }

  @Override
  @NonNull
  public ExternalSource readExternalSource() {
    return ExternalReport.EMPTY;
  }

  @Override
  @NonNull
  public Path getRootPath() {
    return Path.of("fake-path");
  }

  @Override
  @Nullable
  public MDObject read(Path path) {
    return null;
  }

  @Override
  @Nullable
  public MDObject read(Path folder, String fullName) {
    return null;
  }

  @Override
  @Nullable
  public ExtendXStream getXstream() {
    return null;
  }

  @Override
  @NonNull
  public FormData readFormData(Path currentPath, String name, MDOType mdoType) {
    return EmptyFormData.EMPTY;
  }

  @Override
  @NonNull
  public Path moduleFolder(Path mdoPath, MDOType mdoType) {
    return getRootPath();
  }

  @Override
  @NonNull
  public Path modulePath(Path folder, String name, ModuleType moduleType) {
    return getRootPath();
  }

  @Override
  @NonNull
  public Path mdoTypeFolderPath(Path mdoPath) {
    return getRootPath();
  }

  @Override
  @NonNull
  public String subsystemsNodeName() {
    return "";
  }

  @Override
  @NonNull
  public String configurationExtensionFilter() {
    return "";
  }

  @Override
  public void unmarshal(HierarchicalStreamReader reader,
                        UnmarshallingContext context,
                        AbstractReaderContext readerContext) {
    // no-op
  }
}
