/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2020
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
package com.github._1c_syntax.mdclasses.utils;

import com.github._1c_syntax.mdclasses.mdo.Form;
import com.github._1c_syntax.mdclasses.mdo.MDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.MetaDataObject;
import com.github._1c_syntax.mdclasses.mdo.Subsystem;
import com.github._1c_syntax.mdclasses.mdo.Template;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import io.vavr.control.Either;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@UtilityClass
public class MDOUtils {

  /**
   * Определяет тип исходников по корню проекта
   */
  public static ConfigurationSource getConfigurationSourceByPath(Path rootPath) {
    ConfigurationSource configurationSource = ConfigurationSource.EMPTY;
    if (rootPath != null) {
      String rootPathString = rootPath.toString();

      File rootConfiguration = new File(rootPathString, "Configuration.xml");
      if (rootConfiguration.exists()) {
        configurationSource = ConfigurationSource.DESIGNER;
      } else {
        rootConfiguration = Paths.get(rootPathString, "src", "Configuration", "Configuration.mdo").toFile();
        if (rootConfiguration.exists()) {
          configurationSource = ConfigurationSource.EDT;
        }
      }
    }
    return configurationSource;
  }

  /**
   * Получает объект метаданных по каталогу проекта, типу и имению объекта метаданных
   */
  public static MDObjectBase getMDObject(Path rootPath, MDOType type, String name) {
    return getMDObject(getConfigurationSourceByPath(rootPath), rootPath, type, name);
  }

  /**
   * Получает объект метаданных по каталогу проекта, типу и имению объекта метаданных с учетом типа исходников
   */
  public static MDObjectBase getMDObject(ConfigurationSource configurationSource,
                                         Path rootPath,
                                         MDOType type,
                                         String name) {
    Path mdoPath = MDOPathUtils.getMDOPath(configurationSource, rootPath, type, name);
    if (mdoPath == null || !mdoPath.toFile().exists()) {
      return null;
    }

    return getMDObject(configurationSource, type, mdoPath);
  }

  /**
   * Получает объект метаданных по типу исходников, типу объекта метаданных из MDO файла
   */
  public static MDObjectBase getMDObject(ConfigurationSource configurationSource,
                                         MDOType type,
                                         Path mdoPath) {
    MDObjectBase mdo = null;
    var mdoFile = mdoPath.toFile();
    if (mdoFile.exists()) {
      var xmlMapper = ObjectMapperFactory.getXmlMapper();
      if (configurationSource == ConfigurationSource.EDT) {
        try {
          mdo = (MDObjectBase) xmlMapper
            .readValue(mdoPath.toFile(),
              Class.forName(MDObjectBase.class.getPackageName() + "." + type.getShortClassName()));
        } catch (IOException | ClassNotFoundException e) {
          LOGGER.error(e.getMessage(), e);
        }
      } else if (configurationSource == ConfigurationSource.DESIGNER) {
        try {
          MetaDataObject metaDataObject = xmlMapper.readValue(mdoPath.toFile(), MetaDataObject.class);
          mdo = metaDataObject.getPropertyByType(type);
        } catch (IOException e) {
          LOGGER.error(e.getMessage(), e);
        }
      }
    }

    if (mdo != null) {
      var mdoFolder = MDOPathUtils.getMDOTypeFolderByMDOPath(configurationSource, mdoPath);
      mdo.setMdoURI(mdoPath.toUri());
      mdo.setModulesByType(getModuleTypesByMDOPath(
        configurationSource,
        mdoPath,
        mdoFolder,
        type));
      mdo.computeMdoRef();
      updateMDOForms(configurationSource, mdo, mdoFolder);
      updateMDOTemplates(configurationSource, mdo, mdoFolder);
      updateMDOCommands(configurationSource, mdo, mdoFolder);
      updateMDOAttributes(mdo);
    }

    return mdo;
  }

  /**
   * Возвращает файлы модулей для объекта с указанием их типов из каталога проекта
   * по пути к файлу описания объекта
   */
  public static Map<URI, ModuleType> getModuleTypesByMDOPath(ConfigurationSource configurationSource, Path mdoPath, Path folder, MDOType mdoType) {
    Map<URI, ModuleType> modulesByType = new HashMap<>();
    var mdoName = FilenameUtils.getBaseName(mdoPath.toString());

    var moduleTypes = Common.getModuleTypesForMdoTypes().getOrDefault(mdoType, Collections.emptySet());
    if (!moduleTypes.isEmpty()) {
      moduleTypes.forEach(moduleType -> {
        var modulePath = MDOPathUtils.getModulePath(configurationSource, folder, mdoName, moduleType);
        if (modulePath != null && modulePath.toFile().exists()) {
          modulesByType.put(modulePath.toUri(), moduleType);
        }
      });
    }

    return modulesByType;
  }

  /**
   * Возвращает файлы модулей объектов с указанием их типов из каталога проекта
   */
  public static Map<URI, ModuleType> getModuleTypesByPath(Path rootPath) {
    return getModuleTypesByPath(getConfigurationSourceByPath(rootPath), rootPath);
  }

  /**
   * Возвращает файлы модулей объектов с указанием их типов из каталога проекта с учетом указанного типа исходников
   */
  public static Map<URI, ModuleType> getModuleTypesByPath(ConfigurationSource configurationSource, Path rootPath) {

    Map<URI, ModuleType> modulesByType = new HashMap<>();

    for (MDOType mdoType : MDOType.values(true)) {
      var folder = MDOPathUtils.getMDOTypeFolder(configurationSource, rootPath, mdoType);
      if (folder == null || !folder.toFile().exists()) {
        continue;
      }

      getMDOFilesInFolder(configurationSource, folder)
        .parallelStream()
        .forEach(mdoPath -> {
            var baseName = FilenameUtils.getBaseName(mdoPath.toString());
            modulesByType.putAll(getModuleTypesByMDOPath(configurationSource, mdoPath, folder, mdoType));
            modulesByType.putAll(getFormsMDOModuleTypes(configurationSource, folder, baseName));
            modulesByType.putAll(getCommandsMDOModuleTypes(configurationSource, folder, baseName));
          }
        );
    }

    return modulesByType;
  }

  /**
   * MDOModuleTypes
   */

  @SneakyThrows
  private static Map<URI, ModuleType> getChildrenMDOModuleTypes(ConfigurationSource configurationSource,
                                                                Path rootPath,
                                                                ModuleType moduleType) {
    Map<URI, ModuleType> modulesByType = new HashMap<>();

    if (rootPath.toFile().exists()) {
      try (Stream<Path> files = Files.walk(rootPath, 1)) {
        files
          .filter(MDOUtils::isDirectory)
          .forEach(mdoPath -> {
            var name = FilenameUtils.getBaseName(mdoPath.toString());
            var modulePath = MDOPathUtils.getModulePath(configurationSource, rootPath, name, moduleType);
            if (modulePath != null && modulePath.toFile().exists()) {
              modulesByType.put(modulePath.toUri(), moduleType);
            }
          });
      }
    }

    return modulesByType;
  }

  private static boolean isDirectory(Path path) {
    return Files.isDirectory(path);
  }

  private static Map<URI, ModuleType> getFormsMDOModuleTypes(ConfigurationSource configurationSource,
                                                             Path folder,
                                                             String name) {
    return getChildrenMDOModuleTypes(configurationSource,
      Paths.get(folder.toString(), name, MDOType.FORM.getGroupName()),
      ModuleType.FormModule);
  }

  private static Map<URI, ModuleType> getCommandsMDOModuleTypes(ConfigurationSource configurationSource,
                                                                Path folder,
                                                                String name) {

    return getChildrenMDOModuleTypes(configurationSource,
      Paths.get(folder.toString(), name, "Commands"),
      ModuleType.CommandModule);
  }

  /**
   * Children
   */

  public static Set<MDObjectBase> getChildren(ConfigurationSource configurationSource,
                                              Path rootPath,
                                              MDOType type) {

    Set<MDObjectBase> children = new HashSet<>();
    if (configurationSource == ConfigurationSource.EMPTY) {
      return children;
    }

    Path folder = MDOPathUtils.getMDOTypeFolder(configurationSource, rootPath, type);
    if (folder == null || !folder.toFile().exists()) {
      return children;
    }

    getChildrenNamesInFolder(configurationSource, folder)
      .parallelStream()
      .forEach(childName -> {
        MDObjectBase child = getMDObject(configurationSource, rootPath, type, childName);
        if (child != null) {
          children.add(child);
        }
      });

    return children;
  }

  public static Set<MDObjectBase> getAllChildren(ConfigurationSource configurationSource,
                                                 Path rootPath,
                                                 boolean includeConfiguration) {


    Set<MDObjectBase> allChildren = new HashSet<>();
    if (configurationSource == ConfigurationSource.EMPTY) {
      return allChildren;
    }

    // сбор информации первого уровня
    for (MDOType type : MDOType.values(true)) {
      if (!includeConfiguration && type == MDOType.CONFIGURATION
        || type == MDOType.SUBSYSTEM) {
        continue;
      }
      allChildren.addAll(getChildren(configurationSource, rootPath, type));
    }

    Map<String, MDObjectBase> childrenByMdoRef = new HashMap<>();
    allChildren.forEach(mdo -> childrenByMdoRef.put(mdo.getMdoRef(), mdo));

    // догрузка подсистем
    var subsystems = getChildren(configurationSource, rootPath, MDOType.SUBSYSTEM);
    if (!subsystems.isEmpty()) {
      allChildren.addAll(subsystems);

      subsystems.forEach(subsystem -> computeChildren(configurationSource, (Subsystem) subsystem, childrenByMdoRef));
    }

    return allChildren;
  }

  private void computeChildren(ConfigurationSource configurationSource, Subsystem subsystem, Map<String, MDObjectBase> childrenByMdoRef) {
    var children = subsystem.getChildren();
    if (children == null) {
      return;
    }

    Set<Either<String, MDObjectBase>> newChildren = new HashSet<>();
    var rootFolder = MDOPathUtils.getMDOTypeFolderByMDOPath(configurationSource, Paths.get(subsystem.getMdoURI()));
    if (rootFolder == null) {
      return;
    }

    var folder = Paths.get(rootFolder.toString(), subsystem.getName(), MDOType.SUBSYSTEM.getGroupName());

    children.forEach(child -> {
      if (child.isLeft()) {
        var partNames = child.getLeft().split("\\.");
        if (partNames.length == 2 && partNames[0].startsWith(MDOType.SUBSYSTEM.getClassName())) {
          var mdoPath = MDOPathUtils.getMDOPath(configurationSource, folder, partNames[1]);
          Subsystem childSubsystem = (Subsystem) getMDObject(configurationSource, MDOType.SUBSYSTEM, mdoPath);
          // FIXME: нужен рефакторинг
          if(childSubsystem != null) {
            childSubsystem.setParent(subsystem);
            childSubsystem.computeMdoRef();
            newChildren.add(Either.right(childSubsystem));
            computeChildren(configurationSource, childSubsystem, childrenByMdoRef);
          }
        } else {
          var mdo = childrenByMdoRef.get(child.getLeft());
          if (mdo != null) {
            newChildren.add(Either.right(mdo));
            mdo.addIncludedSubsystems(subsystem);
          } else {
            LOGGER.error("Unknown MDO {}", child.getLeft());
          }
        }
      } else {
        newChildren.add(Either.right(child.get()));
      }
    });
    subsystem.setChildren(newChildren);
  }

  @SneakyThrows
  private List<String> getChildrenNamesInFolder(ConfigurationSource configurationSource, Path folder) {
    List<String> childrenNames;
    int maxDepth = 1;
    AtomicReference<String> extension = new AtomicReference<>(MDOPathUtils.mdoExtension(configurationSource, true));
    if (configurationSource == ConfigurationSource.EDT) {
      maxDepth = 2;
    }

    try (Stream<Path> files = Files.walk(folder, maxDepth)) {
      childrenNames = files
        .map(Path::toString)
        .filter(f -> f.endsWith(extension.get()))
        .map(FilenameUtils::getBaseName)
        .collect(Collectors.toList());
    }

    return childrenNames;
  }

  private void updateMDOForms(ConfigurationSource configurationSource, MDObjectBase mdo, Path folder) {
    var childrenFolder = getChildrenFolder(mdo, folder, MDOType.FORM);

    mdo.setForms(readDesignerMDOChildren(configurationSource, childrenFolder, MDOType.FORM, Form.class));

    if (mdo.getForms() != null) {
      mdo.getForms().forEach(form -> {
        setModulesByType(configurationSource, childrenFolder, form, ModuleType.FormModule);
        form.setParent(mdo);
        form.computeMdoRef();
      });
    }
  }

  private void updateMDOTemplates(ConfigurationSource configurationSource, MDObjectBase mdo, Path folder) {
    var childrenFolder = getChildrenFolder(mdo, folder, MDOType.TEMPLATE);

    mdo.setTemplates(readDesignerMDOChildren(configurationSource, childrenFolder, MDOType.TEMPLATE, Template.class));

    if (mdo.getTemplates() != null) {
      mdo.getTemplates().forEach(template -> {
        template.setParent(mdo);
        template.computeMdoRef();
      });
    }
  }

  private void updateMDOCommands(ConfigurationSource configurationSource, MDObjectBase mdo, Path folder) {
    var childrenFolder = getChildrenFolder(mdo, folder, MDOType.COMMAND);

    if (mdo.getCommands() != null) {
      mdo.getCommands().forEach(command -> {
        setModulesByType(configurationSource, childrenFolder, command, ModuleType.CommandModule);
        command.setParent(mdo);
        command.computeMdoRef();
      });
    }
  }

  private void updateMDOAttributes(MDObjectBase mdo) {

    if (mdo.getAttributes() != null) {
      mdo.getAttributes().forEach(attribute -> {
        attribute.setParent(mdo);
        attribute.computeMdoRef();
        // у табличных частей есть дочерние элементы
        updateMDOAttributes(attribute);
      });
    }
  }

  /**
   * Other
   */

  @SneakyThrows
  private List<Path> getMDOFilesInFolder(ConfigurationSource configurationSource, Path folder) {
    List<Path> childrenNames = new ArrayList<>();
    if (folder.toFile().exists()) {
      int maxDepth = 1;
      AtomicReference<String> extension = new AtomicReference<>(MDOPathUtils.mdoExtension(configurationSource, true));
      if (configurationSource == ConfigurationSource.EDT) {
        maxDepth = 2;
      }
      try (Stream<Path> files = Files.walk(folder, maxDepth)) {
        childrenNames = files
          .parallel()
          .filter(f -> f.toString().endsWith(extension.get()))
          .filter(f -> !FilenameUtils.getBaseName(f.toString()).equals("ConfigDumpInfo"))
          .collect(Collectors.toList());
      }
    }

    return childrenNames;
  }

  private static void setModulesByType(ConfigurationSource configurationSource,
                                       Path folder,
                                       MDObjectBase mdo,
                                       ModuleType moduleType) {
    if (folder != null) {
      Map<URI, ModuleType> modulesByType = new HashMap<>();
      var modulePath = MDOPathUtils.getModulePath(configurationSource, folder, mdo.getName(), moduleType);
      if (modulePath != null && modulePath.toFile().exists()) {
        modulesByType.put(modulePath.toUri(), moduleType);
      }

      mdo.setModulesByType(modulesByType);
    }
  }

  private static Path getChildrenFolder(MDObjectBase mdo, Path folder, MDOType type) {
    if (folder != null) {
      var formFolder = Paths.get(folder.toString(), mdo.getName(), type.getGroupName());
      if (formFolder.toFile().exists()) {
        return formFolder;
      }
    }
    return null;
  }

  private static <T extends MDObjectBase> List<T> readDesignerMDOChildren(ConfigurationSource configurationSource,
                                                                          Path childrenFolder,
                                                                          MDOType type,
                                                                          Class<T> childClass) {
    List<T> mdos = new ArrayList<>();
    if (childrenFolder != null && configurationSource != ConfigurationSource.EDT) {
      getMDOFilesInFolder(configurationSource, childrenFolder)
        .forEach(mdoFile -> {
          MDObjectBase mdo = null;
          var xmlMapper = ObjectMapperFactory.getXmlMapper();
          try {
            MetaDataObject metaDataObject = xmlMapper.readValue(mdoFile.toFile(), MetaDataObject.class);
            mdo = metaDataObject.getPropertyByType(type);
          } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
          }
          if (mdo != null) {
            mdos.add(childClass.cast(mdo));
          }
        });
    }
    return mdos;
  }

}
