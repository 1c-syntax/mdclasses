package com.github._1c_syntax.mdclasses.utils;

import com.github._1c_syntax.mdclasses.mdo.Form;
import com.github._1c_syntax.mdclasses.mdo.MDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.MetaDataObject;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
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
      updateMDOForms(configurationSource, mdo, mdoFolder);
      updateMDOCommands(configurationSource, mdo, mdoFolder);
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

    for (MDOType type : MDOType.values(true)) {
      if (!includeConfiguration && type == MDOType.CONFIGURATION) {
        continue;
      }
      allChildren.addAll(getChildren(configurationSource, rootPath, type));
    }
    return allChildren;
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
    if (folder == null) {
      return;
    }
    var formFolder = Paths.get(folder.toString(), mdo.getName(), MDOType.FORM.getGroupName());
    if (!formFolder.toFile().exists()) {
      return;
    }

    // для EDT пока ничего не делаем, MDO для формы нету
    if (configurationSource != ConfigurationSource.EDT) {
      List<Form> mdoForms = new ArrayList<>();
      getMDOFilesInFolder(configurationSource, formFolder)
        .forEach(mdoFile -> {
          Form mdoForm = null;
          var xmlMapper = ObjectMapperFactory.getXmlMapper();
          try {
            MetaDataObject metaDataObject = xmlMapper.readValue(mdoFile.toFile(), MetaDataObject.class);
            mdoForm = metaDataObject.getForm();
          } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
          }
          if (mdoForm != null) {
            mdoForms.add(mdoForm);
          }
        });

      mdo.setForms(mdoForms);
    }

    if (mdo.getForms() != null) {
      mdo.getForms().forEach(form -> {
        Map<URI, ModuleType> modulesByType = new HashMap<>();
        var modulePath = MDOPathUtils.getModulePath(configurationSource, formFolder, form.getName(), ModuleType.FormModule);
        if (modulePath != null && modulePath.toFile().exists()) {
          modulesByType.put(modulePath.toUri(), ModuleType.FormModule);
        }

        form.setModulesByType(modulesByType);
      });
    }
  }

  private void updateMDOCommands(ConfigurationSource configurationSource, MDObjectBase mdo, Path folder) {
    if (folder == null) {
      return;
    }
    var commandFolder = Paths.get(folder.toString(), mdo.getName(), MDOType.COMMAND.getGroupName());
    if (!commandFolder.toFile().exists()) {
      return;
    }

    if (mdo.getCommands() != null) {
      mdo.getCommands().forEach(command -> {
        Map<URI, ModuleType> modulesByType = new HashMap<>();
        var modulePath = MDOPathUtils.getModulePath(configurationSource, commandFolder, command.getName(), ModuleType.CommandModule);
        if (modulePath != null && modulePath.toFile().exists()) {
          modulesByType.put(modulePath.toUri(), ModuleType.CommandModule);
        }

        command.setModulesByType(modulesByType);
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

}
