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

import com.github._1c_syntax.bsl.mdclasses.MDCReadSettings;
import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.reader.designer.DesignerReader;
import com.github._1c_syntax.bsl.reader.edt.EDTReader;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.jspecify.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Читатель MDO файлов (описаний метаданных)
 */
@UtilityClass
@Slf4j
public class MDOReader {

  /**
   * Производит чтение контейнера метаданных (конфигурации) по каталогу исходников
   *
   * @param rootPath Каталог исходников
   * @return Прочитанный контейнер метаданных (конфигурация)
   */
  public MDClass readConfiguration(Path rootPath) {
    return readConfiguration(rootPath, MDCReadSettings.DEFAULT);
  }

  /**
   * Производит чтение контейнера метаданных (конфигурации) по каталогу исходников
   *
   * @param rootPath     Каталог исходников
   * @param readSettings Настройки чтения
   * @return Прочитанный контейнер метаданных (конфигурация)
   */
  public MDClass readConfiguration(Path rootPath, MDCReadSettings readSettings) {
    return createReader(rootPath, readSettings, MDOType.CONFIGURATION).readConfiguration();
  }

  /**
   * Производит чтение указанного объекта метаданных или контейнера
   *
   * @param folder Каталог исходников
   * @return Прочитанный объект метаданных. Если чтение не удалось, то вернется NULL
   */
  @Nullable
  public Object read(Path folder, String fullName) {
    return read(folder, fullName, MDCReadSettings.DEFAULT);
  }

  /**
   * Производит чтение указанного объекта метаданных или контейнера
   *
   * @param folder       Каталог исходников
   * @param readSettings Настройки чтения
   * @return Прочитанный объект метаданных. Если чтение не удалось, то вернется NULL
   */
  @Nullable
  public Object read(Path folder, String fullName, MDCReadSettings readSettings) {
    var reader = createReader(folder, readSettings, MDOType.UNKNOWN);
    if (folder.toFile().isFile()) {
      return reader.read(fullName);
    } else {
      return reader.read(folder, fullName);
    }
  }

  /**
   * Производит чтение внешнего контейнера метаданных (внешней обработки или отчета) по файлу описания
   *
   * @param mdoPath Путь к файлу описания
   * @return Прочитанный контейнер метаданных (внешний отчет или обработка)
   */
  public MDClass readExternalSource(Path mdoPath) {
    return readExternalSource(mdoPath, MDCReadSettings.SKIP_SUPPORT);
  }

  /**
   * Производит чтение внешнего контейнера метаданных (внешней обработки или отчета) по файлу описания
   *
   * @param mdoPath      Путь к файлу описания
   * @param readSettings Настройки чтения
   * @return Прочитанный контейнер метаданных (внешний отчет или обработка)
   */
  public MDClass readExternalSource(Path mdoPath, MDCReadSettings readSettings) {
    return createReader(mdoPath, readSettings, MDOType.EXTERNAL_REPORT).readExternalSource();
  }

  private MDReader createReader(Path rootPath, MDCReadSettings readSettings, MDOType mdoType) {
    if (mdoType == MDOType.CONFIGURATION || mdoType == MDOType.UNKNOWN) {
      return createReader(rootPath, readSettings, getConfigurationSourceByPath(rootPath));
    } else {
      return createReader(rootPath, readSettings, getConfigurationSourceByPathSimple(rootPath));
    }
  }

  private MDReader createReader(Path rootPath, MDCReadSettings readSettings, ConfigurationSource configurationSource) {
    return switch (configurationSource) {
      case DESIGNER -> new DesignerReader(rootPath, readSettings);
      case EDT -> new EDTReader(rootPath, readSettings);
      default -> new FakeReader();
    };
  }

  private ConfigurationSource getConfigurationSourceByPath(@Nullable Path rootPath) {
    var configurationSource = ConfigurationSource.EMPTY;
    if (rootPath == null) {
      return configurationSource;
    }

    if (rootPath.toFile().isFile()) { // передали сам файл, а не каталог
      var filename = rootPath.getFileName().toString();
      if (DesignerReader.CONFIGURATION_MDO_FILE_NAME.equals(filename)) {
        configurationSource = ConfigurationSource.DESIGNER;
      } else if (EDTReader.CONFIGURATION_MDO_FILE_NAME.equals(filename)) {
        configurationSource = ConfigurationSource.EDT;
      }
    } else {
      var rootPathString = rootPath.toString();
      var rootConfiguration = new File(rootPathString, DesignerReader.CONFIGURATION_MDO_PATH);
      if (rootConfiguration.exists()) {
        configurationSource = ConfigurationSource.DESIGNER;
      } else {
        rootConfiguration = Paths.get(rootPathString, EDTReader.CONFIGURATION_MDO_PATH).toFile();
        if (rootConfiguration.exists()) {
          configurationSource = ConfigurationSource.EDT;
        }
      }
    }
    return configurationSource;
  }

  private ConfigurationSource getConfigurationSourceByPathSimple(@Nullable Path mdoPath) {
    var configurationSource = ConfigurationSource.EMPTY;
    if (mdoPath == null) {
      return configurationSource;
    }

    var mdoFile = mdoPath.toFile();
    if (!mdoFile.exists()) {
      return configurationSource;
    }

    if (FilenameUtils.isExtension(mdoPath.toString(), "mdo")) {
      return ConfigurationSource.EDT;
    } else if (FilenameUtils.isExtension(mdoPath.toString(), "xml")) {
      return ConfigurationSource.DESIGNER;
    } else {
      return ConfigurationSource.EMPTY;
    }
  }
}
