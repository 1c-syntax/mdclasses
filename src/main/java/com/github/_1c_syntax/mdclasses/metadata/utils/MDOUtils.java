package com.github._1c_syntax.mdclasses.metadata.utils;

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

  private static final Map<MDOType, Set<ModuleType>> MODULE_TYPES_FOR_MDO_TYPES = moduleTypesForMDOTypes();
  private static final String FILE_SEPARATOR = System.getProperty("file.separator");

  /**
   * Получает каталог типа объекта метаданных для EDT формата относительно корня проекта
   */
  private static Path getMDOTypeFolderPathEDT(Path rootPath, MDOType type) {
    return Paths.get(rootPath.toString(), "src", type.getGroupName());
  }

  /**
   * Получает каталог типа объекта метаданных для EDT формата относительно корня проекта
   * по пути MDO файла
   */
  private static Path getMDOTypeFolderPathByMDOPathEDT(Path mdoPath) {
    return Paths.get(FilenameUtils.getFullPathNoEndSeparator(
      FilenameUtils.getFullPathNoEndSeparator(mdoPath.toString())));
  }

  /**
   * Получает путь к MDO файлу объекта метаданных для EDT формата относительно корня проекта
   */
  private static Path getMDOPathEDT(Path rootPath, MDOType type, String name) {
    return getMDOPathEDT(getMDOTypeFolderPathEDT(rootPath, type), name);
  }

  /**
   * Получает путь к MDO файлу объекта метаданных для EDT формата относительно произвольного каталога
   * Используется для получения дочерних метаданных
   */
  private static Path getMDOPathEDT(Path folder, String name) {
    return Paths.get(folder.toString(), name, name + "." + Common.EXTENSION_MDO);
  }

  /**
   * Получает путь к файлу-модулю объекта метаданных для EDT формата относительно корня проекта, по имени
   * и типу объекта метаднных и типу модуля
   */
  private static Path getModulePathEDT(Path rootPath, MDOType type, String name, ModuleType moduleType) {
    return getModulePathEDT(getMDOTypeFolderPathEDT(rootPath, type), name, moduleType);
  }

  /**
   * Получает путь к файлу-модулю объекта метаданных для EDT формата относительно произвольного каталога, по имени
   * объекта метаднных и типу модуля
   */
  private static Path getModulePathEDT(Path folder, String name, ModuleType moduleType) {
    return Paths.get(folder.toString(), name, moduleType.getFileName());
  }

  /**
   * Получает каталог типа объекта метаданных для формата конфигуратора относительно корня проекта
   */
  private static Path getMDOTypeFolderPathDesigner(Path rootPath, MDOType type) {
    return Paths.get(rootPath.toString(), type.getGroupName());
  }

  /**
   * Получает каталог типа объекта метаданных для формата конфигуратора относительно корня проекта
   * по пути MDO файла
   */
  private static Path getMDOTypeFolderPathByMDOPathDesigner(Path mdoPath) {
    return Paths.get(FilenameUtils.getFullPathNoEndSeparator(mdoPath.toString()));
  }

  /**
   * Получает путь к MDO файлу объекта метаданных для формата конфигуратора относительно корня проекта
   */
  private static Path getMDOPathDesigner(Path rootPath, MDOType type, String name) {
    return getMDOPathDesigner(getMDOTypeFolderPathDesigner(rootPath, type), name);
  }

  /**
   * Получает путь к MDO файлу объекта метаданных для формата конфигуратора относительно произвольного каталога
   * Используется для получения дочерних метаданных
   */
  private static Path getMDOPathDesigner(Path folder, String name) {
    return Paths.get(folder.toString(), name + "." + Common.EXTENSION_XML);
  }

  /**
   * Получает путь к файлу-модулю объекта метаданных для  формата конфигуратора относительно корня проекта, по имени
   * и типу объекта метаднных и типу модуля
   */
  private static Path getModulePathDesigner(Path rootPath, MDOType type, String name, ModuleType moduleType) {
    return getModulePathDesigner(getMDOTypeFolderPathDesigner(rootPath, type), name, moduleType);
  }

  /**
   * Получает путь к файлу-модулю объекта метаданных для формата конфигуратора относительно произвольного каталога, по имени
   * объекта метаднных и типу модуля
   */
  private static Path getModulePathDesigner(Path folder, String name, ModuleType moduleType) {
    var subdirectory = "Ext";
    if (moduleType == ModuleType.FormModule) {
      subdirectory += FILE_SEPARATOR + "Form";
    }

    if (!MODULE_TYPES_FOR_MDO_TYPES.get(MDOType.CONFIGURATION).contains(moduleType)) {
      subdirectory = name + FILE_SEPARATOR + subdirectory;
    }

    return Paths.get(folder.toString(), subdirectory, moduleType.getFileName());
  }

  /**
   * Получает каталог типа объекта метаданных относительно корня проекта с учетом указанном типа исходников
   */
  public static Path getMDOTypeFolder(ConfigurationSource configurationSource, Path rootPath, MDOType type) {
    if (configurationSource == ConfigurationSource.EDT) {
      return getMDOTypeFolderPathEDT(rootPath, type);
    } else if (configurationSource == ConfigurationSource.DESIGNER) {
      return getMDOTypeFolderPathDesigner(rootPath, type);
    } else {
      return null;
    }
  }

  /**
   * Получает каталог типа объекта метаданных относительно корня проекта с учетом указанном типа исходников
   * по описанию объекта метаданныъ
   */
  public static Path getMDOTypeFolderByMDOPath(ConfigurationSource configurationSource, Path mdoPath) {
    if (configurationSource == ConfigurationSource.EDT) {
      return getMDOTypeFolderPathByMDOPathEDT(mdoPath);
    } else if (configurationSource == ConfigurationSource.DESIGNER) {
      return getMDOTypeFolderPathByMDOPathDesigner(mdoPath);
    } else {
      return null;
    }
  }

  /**
   * Получает каталог типа объекта метаданных относительно корня проекта
   */
  public static Path getMDOTypeFolder(Path rootPath, MDOType type) {
    return getMDOTypeFolder(getConfigurationSourceByPath(rootPath), rootPath, type);
  }

  /**
   * Получает путь к MDO файлу объекта метаданных относительно корня проекта с учетом указанном типа исходников
   */
  public static Path getMDOPath(ConfigurationSource configurationSource, Path rootPath, MDOType type, String name) {
    if (configurationSource == ConfigurationSource.EDT) {
      return getMDOPathEDT(rootPath, type, name);
    } else if (configurationSource == ConfigurationSource.DESIGNER) {
      return getMDOPathDesigner(rootPath, type, name);
    } else {
      return null;
    }
  }

  /**
   * Получает путь к MDO файлу объекта метаданных относительно корня проекта
   */
  public static Path getMDOPath(ConfigurationSource configurationSource, Path folder, String name) {
    if (configurationSource == ConfigurationSource.EDT) {
      return getMDOPathEDT(folder, name);
    } else if (configurationSource == ConfigurationSource.DESIGNER) {
      return getMDOPathDesigner(folder, name);
    } else {
      return null;
    }
  }

  /**
   * Получает путь к файлу-модулю объекта метаданных относительно корня проекта, по имени и типу объекта метаднных
   * и типу модуля с учетом указанном типа исходников
   */
  public static Path getModulePath(ConfigurationSource configurationSource,
                                   Path rootPath,
                                   MDOType type,
                                   String name,
                                   ModuleType moduleType) {
    if (configurationSource == ConfigurationSource.EDT) {
      return getModulePathEDT(rootPath, type, name, moduleType);
    } else if (configurationSource == ConfigurationSource.DESIGNER) {
      return getModulePathDesigner(rootPath, type, name, moduleType);
    } else {
      return null;
    }
  }

  /**
   * Получает путь к файлу-модулю объекта метаданных относительно корня проекта, по имени объекта метаднных
   * и типу модуля с учетом указанном типа исходников
   */
  public static Path getModulePath(ConfigurationSource configurationSource,
                                   Path folder,
                                   String name,
                                   ModuleType moduleType) {
    if (configurationSource == ConfigurationSource.EDT) {
      return getModulePathEDT(folder, name, moduleType);
    } else if (configurationSource == ConfigurationSource.DESIGNER) {
      return getModulePathDesigner(folder, name, moduleType);
    } else {
      return null;
    }
  }

  /**
   * Расширение MDO файла с учетом типа исходников
   */
  public static String mdoExtension(ConfigurationSource configurationSource, boolean withDot) {
    String dot = ".";
    if (!withDot) {
      dot = "";
    }

    if (configurationSource == ConfigurationSource.EDT) {
      return dot + Common.EXTENSION_MDO;
    } else if (configurationSource == ConfigurationSource.DESIGNER) {
      return dot + Common.EXTENSION_XML;
    } else {
      return "";
    }
  }

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
    Path mdoPath = getMDOPath(configurationSource, rootPath, type, name);
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
      var mdoFolder = getMDOTypeFolderByMDOPath(configurationSource, mdoPath);
      mdo.setMdoURI(mdoPath.toUri());
      mdo.setModulesByType(getModuleTypesByMDOPath(
        configurationSource,
        mdoPath,
        mdoFolder,
        type));
      updateMDOForms(configurationSource, mdo, type, mdoFolder);
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

    var moduleTypes = MODULE_TYPES_FOR_MDO_TYPES.getOrDefault(mdoType, Collections.emptySet());
    if (!moduleTypes.isEmpty()) {
      moduleTypes.forEach(moduleType -> {
        var modulePath = getModulePath(configurationSource, folder, mdoName, moduleType);
        if (modulePath != null && modulePath.toFile().exists()) {
          modulesByType.put(modulePath.toUri(), moduleType);
        }
      });

      if (mdoType.isMayHaveCommand()) {
        modulesByType.putAll(getCommandsMDOModuleTypes(configurationSource, folder, mdoName));
      }
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

    for (MDOType mdoType : MDOType.values()) {
      var folder = getMDOTypeFolder(configurationSource, rootPath, mdoType);
      if (folder == null || !folder.toFile().exists()) {
        continue;
      }

      getMDOFilesInFolder(configurationSource, folder)
        .forEach(mdoPath -> {
            modulesByType.putAll(
              getModuleTypesByMDOPath(configurationSource, mdoPath, folder, mdoType));
            modulesByType.putAll(
              getFormsMDOModuleTypes(configurationSource,
                folder,
                FilenameUtils.getBaseName(mdoPath.toString())));
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
          .filter((Path f) -> Files.isDirectory(f))
          .forEach(mdoPath -> {
            var name = FilenameUtils.getBaseName(mdoPath.toString());
            var modulePath = getModulePath(configurationSource, rootPath, name, moduleType);
            if (modulePath != null && modulePath.toFile().exists()) {
              modulesByType.put(modulePath.toUri(), moduleType);
            }
          });
      }
    }

    return modulesByType;
  }

  private static Map<URI, ModuleType> getFormsMDOModuleTypes(ConfigurationSource configurationSource,
                                                             Path folder,
                                                             String name) {
    return getChildrenMDOModuleTypes(configurationSource,
      Paths.get(folder.toString(), name, "Forms"),
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

    Path folder = getMDOTypeFolder(configurationSource, rootPath, type);
    if (folder == null || !folder.toFile().exists()) {
      return children;
    }

    getChildrenNamesInFolder(configurationSource, folder)
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

    for (MDOType type : MDOType.values()) {
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
    AtomicReference<String> extension = new AtomicReference<>(mdoExtension(configurationSource, true));
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

  private void updateMDOForms(ConfigurationSource configurationSource, MDObjectBase mdo, MDOType type, Path folder) {
    if (!type.isMayHaveForm() || folder == null) {
      return;
    }
    var formFolder = Paths.get(folder.toString(), mdo.getName(), "Forms");
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
        var modulePath = getModulePath(configurationSource, formFolder, form.getName(), ModuleType.FormModule);
        if (modulePath != null && modulePath.toFile().exists()) {
          modulesByType.put(modulePath.toUri(), ModuleType.FormModule);
        }

        form.setModulesByType(modulesByType);
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
      AtomicReference<String> extension = new AtomicReference<>(mdoExtension(configurationSource, true));
      if (configurationSource == ConfigurationSource.EDT) {
        maxDepth = 2;
      }
      try (Stream<Path> files = Files.walk(folder, maxDepth)) {
        childrenNames = files
          .filter(f -> f.toString().endsWith(extension.get()))
          .filter(f -> !FilenameUtils.getBaseName(f.toString()).equals("ConfigDumpInfo"))
          .collect(Collectors.toList());
      }
    }

    return childrenNames;
  }

  private static Map<MDOType, Set<ModuleType>> moduleTypesForMDOTypes() {
    Map<MDOType, Set<ModuleType>> result = new HashMap<>();

    for (MDOType mdoType : MDOType.values()) {
      Set<ModuleType> types = new HashSet<>();
      switch (mdoType) {
        case ACCOUNTING_REGISTER:
        case ACCUMULATION_REGISTER:
        case CALCULATION_REGISTER:
        case INFORMATION_REGISTER:
          types.add(ModuleType.ManagerModule);
          types.add(ModuleType.RecordSetModule);
          break;
        case BUSINESS_PROCESS:
        case CATALOG:
        case CHART_OF_ACCOUNTS:
        case CHART_OF_CALCULATION_TYPES:
        case CHART_OF_CHARACTERISTIC_TYPES:
        case DATA_PROCESSOR:
        case DOCUMENT:
        case EXCHANGE_PLAN:
        case REPORT:
        case TASK:
          types.add(ModuleType.ManagerModule);
          types.add(ModuleType.ObjectModule);
          break;
        case COMMAND_GROUP:
        case COMMON_ATTRIBUTE:
        case COMMON_PICTURE:
        case COMMON_TEMPLATE:
        case DEFINED_TYPE:
        case DOCUMENT_NUMERATOR:
        case EVENT_SUBSCRIPTION:
        case FUNCTIONAL_OPTION:
        case ROLE:
        case SCHEDULED_JOB:
        case SESSION_PARAMETER:
        case STYLE_ITEM:
        case STYLE:
        case SUBSYSTEM:
        case WS_REFERENCE:
        case XDTO_PACKAGE:
          break;
        case COMMON_COMMAND:
          types.add(ModuleType.CommandModule);
          break;
        case COMMON_FORM:
          types.add(ModuleType.FormModule);
          break;
        case COMMON_MODULE:
          types.add(ModuleType.CommonModule);
          break;
        case CONFIGURATION:
          types.add(ModuleType.ApplicationModule);
          types.add(ModuleType.SessionModule);
          types.add(ModuleType.ExternalConnectionModule);
          types.add(ModuleType.ManagedApplicationModule);
          types.add(ModuleType.OrdinaryApplicationModule);
          break;
        case CONSTANT:
          types.add(ModuleType.ValueManagerModule);
          break;
        case DOCUMENT_JOURNAL:
        case ENUM:
        case FILTER_CRITERION:
        case SETTINGS_STORAGE:
          types.add(ModuleType.ManagerModule);
          break;
        case HTTP_SERVICE:
          types.add(ModuleType.HTTPServiceModule);
          break;
        case SEQUENCE:
          types.add(ModuleType.RecordSetModule);
          break;
        case WEB_SERVICE:
          types.add(ModuleType.WEBServiceModule);
          break;
        default:
          // non
      }

      result.put(mdoType, types);
    }

    return result;
  }

}
