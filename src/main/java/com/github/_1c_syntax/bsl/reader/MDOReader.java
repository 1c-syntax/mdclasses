/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendReaderWrapper;
import com.github._1c_syntax.bsl.reader.designer.DesignerPaths;
import com.github._1c_syntax.bsl.reader.designer.DesignerReader;
import com.github._1c_syntax.bsl.reader.edt.EDTPaths;
import com.github._1c_syntax.bsl.reader.edt.EDTReader;
import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Читатель MDO файлов (описаний метаданных)
 */
@UtilityClass
@Slf4j
public class MDOReader {

  /**
   * Ридеры для проектов
   */
  private final Map<Path, MDReader> READERS = new ConcurrentHashMap<>();

  /**
   * Возвращает читатель исходников по каталогу проекта
   *
   * @param rootPath Каталог проекта
   * @return Читатель
   */
  public MDReader getReader(@NonNull Path rootPath) {
    return getReader(rootPath, false);
  }

  /**
   * Возвращает читатель исходников по каталогу проекта
   *
   * @param rootPath    Каталог проекта
   * @param skipSupport Флаг управления необходимостью читать информацию о поддержке
   * @return Читатель
   */
  public MDReader getReader(@NonNull Path rootPath, boolean skipSupport) {
    for (Map.Entry<Path, MDReader> reader : READERS.entrySet()) {
      if (rootPath.startsWith(reader.getKey())) {
        return reader.getValue();
      }
    }
    var rootPathFile = rootPath.toFile();
    if (rootPathFile.isFile() && rootPathFile.exists()) {
      return getReader(rootPathByFile(rootPath), skipSupport);
    }

    var reader = createReader(rootPath, skipSupport);
    READERS.put(rootPath, reader);
    return reader;
  }

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
    return getReader(rootPath, skipSupport).readConfiguration();
  }

  /**
   * Производит чтение указанного объекта метаданных по каталогу исходников и полному имени
   *
   * @param folder Каталог исходников
   * @return Прочитанный объект метаданных
   */
  public Object readMDObject(@NonNull Path folder, @NonNull String fullName) {
    return readMDObject(folder, fullName, false);
  }

  /**
   * Производит чтение указанного объекта метаданных по каталогу исходников и полному имени
   *
   * @param folder      Каталог исходников
   * @param skipSupport Управление чтением поддержки
   * @return Прочитанный объект метаданных
   */
  public Object readMDObject(@NonNull Path folder, @NonNull String fullName, boolean skipSupport) {
    var reader = getReader(folder, skipSupport);
    if (folder.toFile().isFile()) {
      return reader.read(fullName);
    } else {
      return reader.read(folder, fullName);
    }
  }

  /**
   * Производит чтение указанного объекта метаданных по каталогу исходников и полному имени
   *
   * @param fullMdoPath Путь к MDO файлу
   * @return Прочитанный объект метаданных
   */
  @Nullable
  public MDObject readMDObject(@NonNull Path fullMdoPath) {
    var data = read(fullMdoPath);
    if (data instanceof MDObject) {
      return (MDObject) data;
    } else if (data == null) {
      LOGGER.warn("Missing file " + fullMdoPath);
      return null;
    } else {
      throw new IllegalArgumentException("Wrong mdo file " + fullMdoPath);
    }
  }

  /**
   * Производит чтение файла
   *
   * @param fullMdoPath Путь к файлу
   * @return Прочитанный объект
   */
  public Object read(@NonNull Path fullMdoPath) {
    var reader = getReader(fullMdoPath);
    if (reader != null) {
      return reader.read(fullMdoPath);
    }
    throw new IllegalArgumentException("Unknown file " + fullMdoPath);
  }

  /**
   * Определяет тип исходников по корню проекта
   *
   * @param rootPath - Путь к корню проекта
   * @return Тип исходников конфигурации
   */
  public ConfigurationSource getConfigurationSourceByPath(Path rootPath) {
    var configurationSource = ConfigurationSource.EMPTY;
    if (rootPath != null) {
      var rootPathString = rootPath.toString();

      var rootConfiguration = new File(rootPathString, DesignerPaths.CONFIGURATION_MDO_PATH);
      if (rootConfiguration.exists()) {
        configurationSource = ConfigurationSource.DESIGNER;
      } else {
        rootConfiguration = Paths.get(rootPathString, EDTPaths.CONFIGURATION_MDO_PATH).toFile();
        if (rootConfiguration.exists()) {
          configurationSource = ConfigurationSource.EDT;
        }
      }
    }
    return configurationSource;
  }

  /**
   * Определяет тип исходников по корню проекта
   *
   * @param path - Путь к корню проекта
   * @return Тип исходников конфигурации
   */
  public ConfigurationSource getConfigurationSourceByMDOPath(Path path) {
    var pathString = path.toString();
    if (pathString.endsWith(DesignerPaths.EXTENSION_DOT)) {
      return ConfigurationSource.DESIGNER;
    } else if (pathString.endsWith(EDTPaths.EXTENSION_DOT)) {
      return ConfigurationSource.EDT;
    } else {
      return ConfigurationSource.EMPTY;
    }
  }

  // TODO Времянки

  /**
   * Получает ReflectionConverter по пути к файлу
   *
   * @param reader Ридер XML
   * @return Найденный конвертер
   */
  public Converter getReflectionConverter(HierarchicalStreamReader reader) {
    return getReader(((ExtendReaderWrapper) reader).getPath()).getReflectionConverter();
  }

  private MDReader createReader(Path rootPath, boolean skipSupport) {
    var configurationSource = getConfigurationSourceByPath(rootPath);
    if (configurationSource == ConfigurationSource.DESIGNER) {
      return new DesignerReader(rootPath, skipSupport);
    } else if (configurationSource == ConfigurationSource.EDT) {
      return new EDTReader(rootPath, skipSupport);
    } else {
      return new FakeReader();
    }
  }

  private Path rootPathByFile(Path configurationMDOPath) {
    if (configurationMDOPath.toString().endsWith(".form")) {
      // todo костыль
      if (configurationMDOPath.toString().contains("/CommonForms/")) {
        return EDTPaths.rootPathByConfigurationMDO(configurationMDOPath.getParent());
      } else {
        return EDTPaths.rootPathByConfigurationMDO(configurationMDOPath.getParent().getParent().getParent());
      }
    } else if (configurationMDOPath.toString().endsWith("Form.xml")) {
      // todo костыль
      if (configurationMDOPath.toString().contains("/CommonForms/")) {
        return DesignerPaths.rootPathByConfigurationMDO(configurationMDOPath.getParent().getParent().getParent());
      } else {
        return DesignerPaths.rootPathByConfigurationMDO(
          configurationMDOPath.getParent().getParent().getParent().getParent().getParent());
      }
    } else if (configurationMDOPath.toString().endsWith(".xml")) {
      if (configurationMDOPath.toString().contains("/CommonAttributes/")) {
        return DesignerPaths.rootPathByConfigurationMDO(configurationMDOPath.getParent());
      } else {
        return DesignerPaths.rootPathByConfigurationMDO(configurationMDOPath);
      }
    } else {
      return EDTPaths.rootPathByConfigurationMDO(configurationMDOPath);
    }
  }

}
