package com.github._1c_syntax.mdclasses.utils;

import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

@UtilityClass
public class MDOPathUtils {

  private static final String FILE_SEPARATOR = System.getProperty("file.separator");
  private static final String EXTENSION_XML = "xml";
  private static final String EXTENSION_MDO = "mdo";

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
    return getMDOTypeFolder(MDOUtils.getConfigurationSourceByPath(rootPath), rootPath, type);
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
      return dot + EXTENSION_MDO;
    } else if (configurationSource == ConfigurationSource.DESIGNER) {
      return dot + EXTENSION_XML;
    } else {
      return "";
    }
  }

  /**
   * Возвращает путь к файлу описания поддержки
   */
  public static Path getParentConfigurationsPath(ConfigurationSource configurationSource, Path rootPath) {
    if (configurationSource == ConfigurationSource.EDT) {
      return Paths.get(rootPath.toString(), "src", "Configuration", "ParentConfigurations.bin");
    } else if (configurationSource == ConfigurationSource.DESIGNER) {
      return Paths.get(rootPath.toString(), "Ext", "ParentConfigurations.bin");
    } else {
      return null;
    }
  }

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
    return Paths.get(folder.toString(), name, name + "." + EXTENSION_MDO);
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
    return Paths.get(folder.toString(), name + "." + EXTENSION_XML);
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

    if (!Common.getModuleTypesForMdoTypes().get(MDOType.CONFIGURATION).contains(moduleType)) {
      subdirectory = name + FILE_SEPARATOR + subdirectory;
    }

    return Paths.get(folder.toString(), subdirectory, moduleType.getFileName());
  }

}
