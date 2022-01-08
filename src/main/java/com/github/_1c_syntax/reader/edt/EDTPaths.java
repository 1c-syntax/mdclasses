package com.github._1c_syntax.reader.edt;

import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ModuleType;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@UtilityClass
public class EDTPaths {

  private final String EXTENSION_MDO = "mdo";
  private final String EXTENSION_MDO_DOT = "." + EXTENSION_MDO;
  private final String SRC_DIR_NAME = "src";

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
  public static Path packageDataPath(@NonNull Path path) {
    var currentPath = path.getParent();
    var basePath = currentPath.toString();
    return Path.of(basePath, "Package.xdto");
  }

  /**
   * Получает путь к файлу прав роли для любого формата относительного описания роли
   *
   * @param path - базовый каталог конфигурации
   * @return - путь к файлу прав конкретной роли
   */
  public static Path getRoleDataPath(Path path) {
    return Path.of(path.getParent().toString(), "Rights.rights");
  }

  /**
   * Получает путь к MDO файлу объекта метаданных относительно корня проекта с учетом указанном типа исходников
   */
  public Optional<Path> mdoPath(@NonNull Path rootPath, @NonNull MDOType type, @NonNull String name) {
    return Optional.of(mdoPath(mdoTypeFolderPath(rootPath, type), name));
  }

  /**
   * Получает путь к MDO файлу объекта метаданных относительно корня проекта
   */
  public Path mdoPath(@NonNull Path folder, @NonNull String name) {
    return Paths.get(folder.toString(), name, name + EXTENSION_MDO_DOT);
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

  /**
   * Возвращает путь к файлу описания поддержки
   */
  public Path parentConfigurationsPath(Path rootPath) {
    return Paths.get(rootPath.toString(), "src", MDOType.CONFIGURATION.getName(), "ParentConfigurations.bin");
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


  private Path mdoTypeFolderPath(Path rootPath, MDOType type) {
    return Paths.get(rootPath.toString(), SRC_DIR_NAME, type.getGroupName());
  }

  private Path mdoTypeFolderPathByMDOPath(Path mdoPath) {
    return Paths.get(FilenameUtils.getFullPathNoEndSeparator(
      FilenameUtils.getFullPathNoEndSeparator(mdoPath.toString())));
  }

}
