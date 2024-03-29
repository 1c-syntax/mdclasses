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
package com.github._1c_syntax.bsl.reader.edt;

import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ModuleType;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

@UtilityClass
// todo убрать public
public class EDTPaths {

  /**
   * Путь к файлу описания конфигурации
   */
  public final String CONFIGURATION_MDO_PATH = Paths.get("src", "Configuration", "Configuration.mdo")
    .toString();

  /**
   * Расширение файлов-описаний
   */
  public final String EXTENSION = "mdo";
  /**
   * Расширение файлов-описаний с лидирующей точкой
   */
  public final String EXTENSION_DOT = "." + EXTENSION;

  private final String SRC_DIR_NAME = "src";
  private final String PARENT_CONFIGURATIONS_BIN_NAME = "ParentConfigurations.bin";

  public Path templateDataPath(@NonNull Path path, @NonNull String name, @NonNull MDOType type) {
    var currentPath = path.getParent();
    var basePath = currentPath.toString();
    if (type == MDOType.COMMON_TEMPLATE) {
      currentPath = Path.of(basePath, "Template.dcs");
    } else {
      currentPath = Path.of(basePath, MDOType.TEMPLATE.getGroupName(), name, "Template.dcs");
    }

    return currentPath;
  }

  /**
   * Возвращает путь к файлу с описанием xsd-схемы xdto пакета
   *
   * @param path - Путь к MDO xdto пакета
   * @return - путь к файлу схемы
   */
  public Path packageDataPath(@NonNull Path path) {
    var currentPath = path.getParent();
    var basePath = currentPath.toString();
    return Path.of(basePath, "Package.xdto");
  }

  /**
   * Получает путь к файлу прав роли относительного описания роли
   *
   * @param path - базовый каталог конфигурации
   * @return - путь к файлу прав конкретной роли
   */
  public Path roleDataPath(Path path) {
    return Path.of(path.getParent().toString(), "Rights.rights");
  }

  /**
   * Получает путь к файлу формы относительного описания формы
   *
   * @param path - базовый каталог конфигурации
   * @return - путь к файлу данных конкретной формы
   */
  public Path formDataPath(Path path, @NonNull MDOType type, @NonNull String name) {
    var basePath = path.getParent().toString();

    if (type == MDOType.COMMON_FORM) {
      return Path.of(basePath, "Form.form");
    } else {
      return Path.of(basePath, MDOType.FORM.getGroupName(), name, "Form.form");
    }
  }

  /**
   * Получает путь к MDO файлу объекта метаданных относительно корня проекта с учетом указанном типа исходников
   */
  public Path mdoPath(@NonNull Path rootPath, @NonNull MDOType type, @NonNull String name) {
    return mdoPath(mdoTypeFolderPath(rootPath, type), name);
  }

  /**
   * Получает путь к MDO файлу объекта метаданных относительно корня проекта
   */
  public Path mdoPath(@NonNull Path folder, @NonNull String name) {
    return Paths.get(folder.toString(), name, name + EXTENSION_DOT);
  }

  /**
   * Получает каталог проекта по файлу описания конфигурации
   */
  public Path rootPathByConfigurationMDO(@NonNull Path mdoPath) {
    return Paths.get(FilenameUtils.getFullPathNoEndSeparator(
      mdoTypeFolderPathByMDOPath(mdoPath).toString()));
  }

  /**
   * Получает путь к файлу-модулю объекта метаданных относительно корня проекта, по имени объекта метаданных
   * и типу модуля с учетом указанном типа исходников
   */
  public Path modulePath(Path folder, String name, ModuleType moduleType) {
    return Paths.get(folder.toString(), name, moduleType.getFileName());
  }

  public Path moduleFolder(Path mdoPath, MDOType mdoType) {
    if (mdoType == MDOType.EXTERNAL_DATA_SOURCE_TABLE) {
      return mdoPath.getParent().getParent();
    } else if (!MDOType.valuesWithoutChildren().contains(mdoType)) {
      return childrenFolder(mdoPath, mdoType);
    } else {
      return mdoTypeFolderPathByMDOPath(mdoPath);
    }
  }

  /**
   * Возвращает путь к файлу описания поддержки
   */
  public Path parentConfigurationsPath(Path rootPath) {
    return Paths.get(rootPath.toString(), SRC_DIR_NAME, MDOType.CONFIGURATION.getName(),
      PARENT_CONFIGURATIONS_BIN_NAME);
  }

  /**
   * Находит каталог дочерних объектов в текущем каталоге
   */
  public Path childrenFolder(@NonNull Path path, @NonNull MDOType type) {
    return Paths.get(path.getParent().toString(), type.getGroupName());
  }

  private Path mdoTypeFolderPath(Path rootPath, MDOType type) {
    return Paths.get(rootPath.toString(), SRC_DIR_NAME, type.getGroupName());
  }

  public Path mdoTypeFolderPathByMDOPath(Path mdoPath) {
    return Paths.get(FilenameUtils.getFullPathNoEndSeparator(
      FilenameUtils.getFullPathNoEndSeparator(mdoPath.toString())));
  }

}
