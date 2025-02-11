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

import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.reader.designer.DesignerReader;
import com.github._1c_syntax.bsl.reader.edt.EDTReader;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

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
  public MDClass readConfiguration(@NonNull Path rootPath) {
    return readConfiguration(rootPath, false);
  }

  /**
   * Производит чтение контейнера метаданных (конфигурации) по каталогу исходников
   *
   * @param rootPath    Каталог исходников
   * @param skipSupport Флаг управления необходимостью читать информацию о поддержке
   * @return Прочитанный контейнер метаданных (конфигурация)
   */
  public MDClass readConfiguration(@NonNull Path rootPath, boolean skipSupport) {
    return createReader(rootPath, skipSupport, MDOType.CONFIGURATION).readConfiguration();
  }

  /**
   * Производит чтение указанного объекта метаданных или контейнера
   *
   * @param folder Каталог исходников
   * @return Прочитанный объект метаданных
   */
  public Object read(@NonNull Path folder, @NonNull String fullName) {
    return read(folder, fullName, false);
  }

  /**
   * Производит чтение указанного объекта метаданных или контейнера
   *
   * @param folder      Каталог исходников
   * @param skipSupport Управление чтением поддержки
   * @return Прочитанный объект метаданных
   */
  public Object read(@NonNull Path folder, @NonNull String fullName, boolean skipSupport) {
    var reader = createReader(folder, skipSupport, MDOType.UNKNOWN);
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
  public MDClass readExternalSource(@NonNull Path mdoPath) {
    return createReader(mdoPath, true, MDOType.EXTERNAL_REPORT).readExternalSource();
  }

  private MDReader createReader(Path rootPath, boolean skipSupport, MDOType mdoType) {
    if (mdoType == MDOType.CONFIGURATION || mdoType == MDOType.UNKNOWN) {
      return createReader(rootPath, skipSupport, getConfigurationSourceByPath(rootPath));
    } else {
      return createReader(rootPath, skipSupport, getConfigurationSourceByPathSimple(rootPath));
    }
  }

  private MDReader createReader(Path rootPath, boolean skipSupport, ConfigurationSource configurationSource) {
    if (configurationSource == ConfigurationSource.DESIGNER) {
      return new DesignerReader(rootPath, skipSupport);
    } else if (configurationSource == ConfigurationSource.EDT) {
      return new EDTReader(rootPath, skipSupport);
    } else {
      return new FakeReader();
    }
  }

  private ConfigurationSource getConfigurationSourceByPath(Path rootPath) {
    var configurationSource = ConfigurationSource.EMPTY;
    if (rootPath != null) {
      if (rootPath.toFile().isFile()) { // передали сам файл, а не каталог
        var filename = rootPath.getFileName().toString();
        if ("Configuration.xml".equals(filename)) {
          configurationSource = ConfigurationSource.DESIGNER;
        } else if ("Configuration.mdo".equals(filename)) {
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
    }
    return configurationSource;
  }

  private ConfigurationSource getConfigurationSourceByPathSimple(Path mdoPath) {
    var configurationSource = ConfigurationSource.EMPTY;
    if (mdoPath != null) {
      var mdoFile = mdoPath.toFile();
      if (mdoFile.exists()) {
        if (FilenameUtils.isExtension(mdoPath.toString(), "mdo")) {
          configurationSource = ConfigurationSource.EDT;
        } else if (FilenameUtils.isExtension(mdoPath.toString(), "xml")) {
          configurationSource = ConfigurationSource.DESIGNER;
        } else {
          // no-op
        }
      }
    }
    return configurationSource;
  }
}
