/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2021
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

import com.github._1c_syntax.bsl.types.ConfigurationSource;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ModuleType;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Методы для работы с путями MDO, BSL и т.д.
 */
@UtilityClass
public class MDOPathUtils {

  private final String FILE_SEPARATOR = System.getProperty("file.separator");
  private final String EXTENSION_XML = "xml";
  private final String EXTENSION_MDO = "mdo";

  /**
   * Расширение MDO файла с учетом типа исходников
   */
  public String mdoExtension(ConfigurationSource configurationSource, boolean withDot) {
    var dot = ".";
    if (!withDot) {
      dot = "";
    }

    switch (configurationSource) {
      case EDT:
        return dot + EXTENSION_MDO;
      case DESIGNER:
        return dot + EXTENSION_XML;
      default:
        return "";
    }
  }

  /**
   * Получает путь к MDO файлу объекта метаданных относительно корня проекта с учетом указанном типа исходников
   */
  public Optional<Path> getMDOPath(ConfigurationSource configurationSource,
                                   Path rootPath, MDOType type, String name) {
    Path value;
    if (configurationSource == ConfigurationSource.EDT) {
      value = getMDOPathEDT(getMDOTypeFolderPathEDT(rootPath, type), name);
    } else if (configurationSource == ConfigurationSource.DESIGNER) {
      value = getMDOPathDesigner(getMDOTypeFolderPathDesigner(rootPath, type), name);
    } else {
      return Optional.empty();
    }
    return Optional.of(value);
  }

  /**
   * Получает путь к MDO файлу объекта метаданных относительно корня проекта
   */
  public Optional<Path> getMDOPath(ConfigurationSource configurationSource, Path folder, String name) {
    Path value;
    if (configurationSource == ConfigurationSource.EDT) {
      value = getMDOPathEDT(folder, name);
    } else if (configurationSource == ConfigurationSource.DESIGNER) {
      value = getMDOPathDesigner(folder, name);
    } else {
      return Optional.empty();
    }
    return Optional.of(value);
  }

  /**
   * Получает каталог проекта по файлу описания конфигурации
   */
  public Optional<Path> getRootPathByConfigurationMDO(Path mdoPath) {
    var configurationSource = getConfigurationSourceByMDOPath(mdoPath);
    Path value;
    if (configurationSource == ConfigurationSource.EDT) {
      value = Paths.get(FilenameUtils.getFullPathNoEndSeparator(
        getMDOTypeFolderPathByMDOPathEDT(mdoPath).toString()));
    } else if (configurationSource == ConfigurationSource.DESIGNER) {
      value = getMDOTypeFolderPathByMDOPathDesigner(mdoPath);
    } else {
      return Optional.empty();
    }
    return Optional.of(value);
  }

  /**
   * Получает каталог проекта по файлу поддержки конфигурации
   */
  public Path getRootPathByParentConfigurations(Path mdoPath) {
    return Paths.get(
      FilenameUtils.getFullPathNoEndSeparator(
        FilenameUtils.getFullPathNoEndSeparator(mdoPath.toString())));
  }

  /**
   * Получает каталог типа объекта метаданных относительно корня проекта с учетом указанном типа исходников
   * по описанию объекта метаданных
   */
  public Optional<Path> getMDOTypeFolderByMDOPath(ConfigurationSource configurationSource, Path mdoPath) {
    Path value;
    if (configurationSource == ConfigurationSource.EDT) {
      value = getMDOTypeFolderPathByMDOPathEDT(mdoPath);
    } else if (configurationSource == ConfigurationSource.DESIGNER) {
      value = getMDOTypeFolderPathByMDOPathDesigner(mdoPath);
    } else {
      return Optional.empty();
    }
    return Optional.of(value);
  }

  /**
   * Получает каталог типа объекта метаданных относительно корня проекта с учетом указанном типа исходников
   * по описанию объекта метаданных
   */
  public Optional<Path> getMDOTypeFolderByMDOPath(Path mdoPath, MDOType type) {
    var configurationSource = getConfigurationSourceByMDOPath(mdoPath);
    Optional<Path> result;
    if (type == MDOType.CONFIGURATION) {
      // для конфигурации один уровень, а не 2
      if (configurationSource == ConfigurationSource.EDT) {
        result = Optional.of(Paths.get(FilenameUtils.getFullPathNoEndSeparator(mdoPath.toString())));
      } else if (configurationSource == ConfigurationSource.DESIGNER) {
        result = Optional.of(getMDOTypeFolderPathByMDOPathDesigner(mdoPath));
      } else {
        result = Optional.empty();
      }
    } else {
      result = getMDOTypeFolderByMDOPath(configurationSource, mdoPath);
    }
    return result;
  }

  /**
   * Получает путь к файлу-модулю объекта метаданных относительно корня проекта, по имени объекта метаданных
   * и типу модуля с учетом указанном типа исходников
   */
  public Optional<Path> getModulePath(ConfigurationSource configurationSource,
                                      Path folder,
                                      String name,
                                      ModuleType moduleType) {
    Path value;
    if (configurationSource == ConfigurationSource.EDT) {
      value = getModulePathEDT(folder, name, moduleType);
    } else if (configurationSource == ConfigurationSource.DESIGNER) {
      value = getModulePathDesigner(folder, name, moduleType);
    } else {
      return Optional.empty();
    }
    return Optional.of(value);
  }

  /**
   * Получает путь к файлу-модулю объекта метаданных относительно корня проекта, по имени объекта метаданных
   * и типу модуля с учетом указанном типа исходников
   */
  public Optional<Path> getModulePath(Path mdoPath,
                                      String name,
                                      ModuleType moduleType) {
    var configurationSource = getConfigurationSourceByMDOPath(mdoPath);
    Path value;
    if (configurationSource == ConfigurationSource.EDT) {
      var folder = mdoPath.getParent().getParent();
      value = getModulePathEDT(folder, name, moduleType);
    } else if (configurationSource == ConfigurationSource.DESIGNER) {
      var folder = mdoPath.getParent();
      value = getModulePathDesigner(folder, name, moduleType);
    } else {
      return Optional.empty();
    }
    return Optional.of(value);
  }

  /**
   * Возвращает путь к файлу описания поддержки
   */
  public Optional<Path> getParentConfigurationsPath(ConfigurationSource configurationSource, Path rootPath) {
    Path value;
    if (configurationSource == ConfigurationSource.EDT) {
      value = Paths.get(rootPath.toString(), "src", MDOType.CONFIGURATION.getName(), "ParentConfigurations.bin");
    } else if (configurationSource == ConfigurationSource.DESIGNER) {
      value = Paths.get(rootPath.toString(), "Ext", "ParentConfigurations.bin");
    } else {
      return Optional.empty();
    }
    return Optional.of(value);
  }

  /**
   * Находит каталог дочерних объектов в текущем каталоге
   */
  public Optional<Path> getChildrenFolder(String mdoName, Path folder, MDOType type) {
    var formFolder = Paths.get(folder.toString(), mdoName, type.getGroupName());
    if (formFolder.toFile().exists()) {
      return Optional.of(formFolder);
    }
    return Optional.empty();
  }

  public Path getTemplateDataPath(Path path, String name, MDOType type) {
    var currentPath = path.getParent();
    var basePath = currentPath.toString();
    var configurationSource = getConfigurationSourceByMDOPath(path);
    if (configurationSource == ConfigurationSource.EDT) {
      if (type == MDOType.COMMON_TEMPLATE) {
        currentPath = Path.of(basePath, "Template.dcs");
      } else {
        currentPath = Path.of(basePath, MDOType.TEMPLATE.getGroupName(), name, "Template.dcs");
      }
    } else {
      currentPath = Paths.get(basePath, name, "Ext", "Template.xml");
    }
    return currentPath;
  }

  /**
   * Возвращает путь к файлу с описанием xsd-схемы xdto пакета
   *
   * @param path - Путь к MDO xdto пакета
   * @param name - Имя xdto пакета
   * @return - путь к файлу схемы
   */
  public static Path getPackageDataPath(Path path, String name) {
    var currentPath = path.getParent();
    var basePath = currentPath.toString();
    var configurationSource = getConfigurationSourceByMDOPath(path);
    if (configurationSource == ConfigurationSource.EDT) {
      currentPath = Path.of(basePath, "Package.xdto");
    } else {
      currentPath = Paths.get(basePath, name, "Ext", "Package.bin");
    }
    return currentPath;
  }

  /**
   * Возвращает путь к файлу с составом плана обмена
   * Внимание! Только для формата конфигуратора!
   *
   * @param path    Путь к MDO
   * @param mdoName Имя плана обмена
   * @return Путь к составу плана обмена
   */
  public static Path getExchangePlanContentPath(Path path, String mdoName) {
    var basePath = path.getParent().toString();
    return Paths.get(basePath, mdoName, "Ext", "Content.xml");
  }

  // Формат EDT

  /**
   * Получает каталог типа объекта метаданных для EDT формата относительно корня проекта
   */
  private Path getMDOTypeFolderPathEDT(Path rootPath, MDOType type) {
    return Paths.get(rootPath.toString(), "src", type.getGroupName());
  }

  /**
   * Получает каталог типа объекта метаданных для EDT формата относительно корня проекта
   * по пути MDO файла
   */
  private Path getMDOTypeFolderPathByMDOPathEDT(Path mdoPath) {
    return Paths.get(FilenameUtils.getFullPathNoEndSeparator(
      FilenameUtils.getFullPathNoEndSeparator(mdoPath.toString())));
  }

  /**
   * Получает путь к MDO файлу объекта метаданных для EDT формата относительно произвольного каталога
   * Используется для получения дочерних метаданных
   */
  private Path getMDOPathEDT(Path folder, String name) {
    return Paths.get(folder.toString(), name, name + "." + EXTENSION_MDO);
  }

  /**
   * Получает путь к файлу-модулю объекта метаданных для EDT формата относительно произвольного каталога, по имени
   * объекта метаданных и типу модуля
   */
  private Path getModulePathEDT(Path folder, String name, ModuleType moduleType) {
    return Paths.get(folder.toString(), name, moduleType.getFileName());
  }

  // Формат Конфигуратора

  /**
   * Получает каталог типа объекта метаданных для формата конфигуратора относительно корня проекта
   */
  private Path getMDOTypeFolderPathDesigner(Path rootPath, MDOType type) {
    return Paths.get(rootPath.toString(), type.getGroupName());
  }

  /**
   * Получает каталог типа объекта метаданных для формата конфигуратора относительно корня проекта
   * по пути MDO файла
   */
  private Path getMDOTypeFolderPathByMDOPathDesigner(Path mdoPath) {
    return Paths.get(FilenameUtils.getFullPathNoEndSeparator(mdoPath.toString()));
  }

  /**
   * Получает путь к MDO файлу объекта метаданных для формата конфигуратора относительно произвольного каталога
   * Используется для получения дочерних метаданных
   */
  private Path getMDOPathDesigner(Path folder, String name) {
    return Paths.get(folder.toString(), name + "." + EXTENSION_XML);
  }

  /**
   * Получает путь к файлу-модулю объекта метаданных для формата конфигуратора относительно произвольного каталога,
   * по имени объекта метаданных и типу модуля
   */
  private Path getModulePathDesigner(Path folder, String name, ModuleType moduleType) {
    var subdirectory = "Ext";

    if (moduleType == ModuleType.FormModule) {
      subdirectory += FILE_SEPARATOR + "Form";
    }

    if (!ModuleType.byMDOType(MDOType.CONFIGURATION).contains(moduleType)) {
      subdirectory = name + FILE_SEPARATOR + subdirectory;
    }

    return Paths.get(folder.toString(), subdirectory, moduleType.getFileName());
  }

  /**
   * Получает путь к файлу прав роли для любого формата относительного базового каталога,
   * и имени объекта
   *
   * @param configurationSource - формат данных, конфигуратор или EDT
   * @param basePath            - базовый каталог конфигурации
   * @param mdoName             - имя объекта метаданных, без расширения
   * @return - путь к файлу прав конкретной роли
   */
  public static Path getRoleDataPath(ConfigurationSource configurationSource, String basePath, String mdoName) {
    Path path;
    if (configurationSource == ConfigurationSource.EDT) {
      path = Path.of(basePath, "Rights.rights");
    } else {
      path = Path.of(basePath, mdoName, "Ext", "Rights.xml");
    }

    return path;
  }

  /**
   * Получает путь к файлу прав роли для любого формата относительного описания роли
   *
   * @param path    - базовый каталог конфигурации
   * @param mdoName - имя объекта метаданных, без расширения
   * @return - путь к файлу прав конкретной роли
   */
  public static Path getRoleDataPath(Path path, String mdoName) {
    var currentPath = path.getParent();
    var basePath = currentPath.toString();
    var configurationSource = getConfigurationSourceByMDOPath(path);

    if (configurationSource == ConfigurationSource.EDT) {
      currentPath = Path.of(basePath, "Rights.rights");
    } else {
      currentPath = Path.of(basePath, mdoName, "Ext", "Rights.xml");
    }

    return currentPath;
  }

  /**
   * Определяет тип исходников по корню проекта
   */
  public ConfigurationSource getConfigurationSourceByPath(Path rootPath) {
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

  /**
   * Определяет тип исходников по MDO файлу
   */
  public ConfigurationSource getConfigurationSourceByMDOPath(Path path) {
    if (path.toString().endsWith(MDOPathUtils.mdoExtension(ConfigurationSource.DESIGNER, true))) {
      return ConfigurationSource.DESIGNER;
    } else {
      return ConfigurationSource.EDT;
    }
  }

}