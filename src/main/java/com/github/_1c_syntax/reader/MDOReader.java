/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2022
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
package com.github._1c_syntax.reader;

import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Читатель MDO
 */
@UtilityClass
public class MDOReader {

  private static final ConcurrentHashMap<Path, MDReader> readers = new ConcurrentHashMap<>();

  public MDReader getReader(@NonNull Path rootPath) {
    return getReader(rootPath, false);
  }

  public MDReader getReader(@NonNull Path rootPath, boolean skipSupport) {
    var reader = readers.get(rootPath);
    if (reader == null) {
      var configurationSource = getConfigurationSourceByPath(rootPath);
      reader = getReader(configurationSource, rootPath, skipSupport);
      readers.put(rootPath, reader);
    }
    return reader;
  }

  public MDClass readConfiguration(@NonNull Path rootPath) {
    return getReader(rootPath, false).readConfiguration();
  }

  public MDClass readConfiguration(@NonNull Path rootPath, boolean skipSupport) {
    return getReader(rootPath, skipSupport).readConfiguration();
  }

  private MDReader getReader(ConfigurationSource configurationSource, Path rootPath, boolean skipSupport) {
    if (configurationSource == ConfigurationSource.DESIGNER) {
      return new DesignerReader(rootPath, skipSupport);
    } else if (configurationSource == ConfigurationSource.EDT) {
      return new EDTReader(rootPath, skipSupport);
    } else {
      return new FakeReader();
    }
  }

  /**
   * Определяет тип исходников по корню проекта
   */
  private ConfigurationSource getConfigurationSourceByPath(Path rootPath) {
    var configurationSource = ConfigurationSource.EMPTY;
    if (rootPath != null) {
      var rootPathString = rootPath.toString();

      var rootConfiguration = new File(rootPathString, "Configuration.xml");
      if (rootConfiguration.exists()) {
        configurationSource = ConfigurationSource.DESIGNER;
      } else {
        rootConfiguration = Paths.get(rootPathString, "src", MDOType.CONFIGURATION.getName(),
          "Configuration.mdo").toFile();
        if (rootConfiguration.exists()) {
          configurationSource = ConfigurationSource.EDT;
        }
      }
    }
    return configurationSource;
  }
}
