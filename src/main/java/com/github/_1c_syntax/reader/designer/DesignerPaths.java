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
package com.github._1c_syntax.reader.designer;

import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ModuleType;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс-хелпер для работы с файловыми путями конфигурации в формате конфигуратора
 */
@UtilityClass
public class DesignerPaths {

  private final String FILE_SEPARATOR = System.getProperty("file.separator");

  private final String EXTENSION_XML = "xml";
  private final String EXTENSION_XML_DOT = "." + EXTENSION_XML;
  private final String EXT_DIR_NAME = "Ext";

  /**
   * Возвращает путь к содержимому макета
   *
   * @param path Путь к MDO
   * @param name Имя объекта
   * @return Путь к макету
   */
  public Path templateDataPath(@NonNull Path path, @NonNull String name) {
    return dataPath(name, path, "Template.xml");
  }

  /**
   * Возвращает путь к файлу с описанием xsd-схемы xdto пакета
   *
   * @param path - Путь к MDO xdto пакета
   * @param name - Имя xdto пакета
   * @return - путь к файлу схемы
   */
  public Path packageDataPath(@NonNull Path path, @NonNull String name) {
    return dataPath(name, path, "Package.bin");
  }

  /**
   * Возвращает путь к файлу с составом плана обмена
   * Внимание! Только для формата конфигуратора!
   *
   * @param path Путь к MDO
   * @param name Имя плана обмена
   * @return Путь к составу плана обмена
   */
  public Path exchangePlanContentPath(@NonNull Path path, @NonNull String name) {
    return dataPath(name, path, "Content.xml");
  }

  /**
   * Получает путь к файлу прав роли для любого формата относительного описания роли
   *
   * @param path - базовый каталог конфигурации
   * @param name - имя объекта метаданных, без расширения
   * @return - путь к файлу прав конкретной роли
   */
  public Path roleDataPath(@NonNull Path path, @NonNull String name) {
    return dataPath(name, path, "Rights.xml");
  }

  /**
   * Получает путь к MDO файлу объекта метаданных относительно корня проекта с учетом указанном типа исходников
   */
  public Path mdoPath(@NonNull Path path, @NonNull MDOType type, @NonNull String name) {
    return mdoPath(mdoTypeFolderPath(path, type), name);
  }

  /**
   * Получает путь к MDO файлу объекта метаданных относительно корня проекта
   */
  public Path mdoPath(@NonNull Path folder, @NonNull String name) {
    return Paths.get(folder.toString(), name + EXTENSION_XML_DOT);
  }

  /**
   * Получает каталог проекта по файлу описания конфигурации
   */
  public Path rootPathByConfigurationMDO(@NonNull Path mdoPath) {
    return mdoTypeFolderPathByMDOPath(mdoPath);
  }

  /**
   * Получает каталог типа метаданных по файлу описания (MDO)
   */
  public Path mdoTypeFolderPathByMDOPath(@NonNull Path mdoPath) {
    return Paths.get(FilenameUtils.getFullPathNoEndSeparator(mdoPath.toString()));
  }

  /**
   * Получает путь к файлу-модулю объекта метаданных относительно корня проекта, по имени объекта метаданных
   * и типу модуля с учетом указанном типа исходников
   */
  public Path modulePath(@NonNull Path folder, @NonNull String name, @NonNull ModuleType moduleType) {
    String subdirectory = EXT_DIR_NAME;

    if (moduleType == ModuleType.FormModule) {
      subdirectory += FILE_SEPARATOR + "Form";
    }

    if (!ModuleType.byMDOType(MDOType.CONFIGURATION).contains(moduleType)) {
      subdirectory = name + FILE_SEPARATOR + subdirectory;
    }

    return Paths.get(folder.toString(), subdirectory, moduleType.getFileName());
  }

  /**
   * Возвращает путь к файлу описания поддержки
   */
  public Path parentConfigurationsPath(@NonNull Path rootPath) {
    return Paths.get(rootPath.toString(), EXT_DIR_NAME, "ParentConfigurations.bin");
  }

  /**
   * Находит каталог дочерних объектов по имени объекта MDO
   */
  public Path childrenFolder(@NonNull Path path, @NonNull MDOType type) {
    var mdoName = FilenameUtils.getBaseName(path.toString());
    return Paths.get(path.getParent().toString(), mdoName, type.getGroupName());
  }


  private Path mdoTypeFolderPath(Path rootPath, MDOType type) {
    return Paths.get(rootPath.toString(), type.getGroupName());
  }

  private Path dataPath(String name, Path path, String source) {
    return Paths.get(path.getParent().toString(), name, EXT_DIR_NAME, source);
  }
}
