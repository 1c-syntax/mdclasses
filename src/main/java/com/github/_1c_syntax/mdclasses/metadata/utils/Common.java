package com.github._1c_syntax.mdclasses.metadata.utils;

import com.github._1c_syntax.mdclasses.metadata.Configuration;
import com.github._1c_syntax.mdclasses.metadata.SupportConfiguration;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.SupportVariant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Common {

  public static final String EXTENSION_BSL = "bsl";
  public static final String FILE_SEPARATOR = Pattern.quote(System.getProperty("file.separator"));

  public static final String REGEX_GUID = "uuid=\"(.*)\"";
  public static final Pattern PATTERN_REGEX_GUID = Pattern.compile(REGEX_GUID, Pattern.MULTILINE);
  public static final String EXTENSION_XML = "xml";
  public static final String EXTENSION_MDO = "mdo";

  private Common() {
    // only statics
  }

  public static ModuleType getModuleTypeByFileName(String[] partsFileName) {

    String fileName = partsFileName[0];

    ModuleType moduleType;
    if (fileName.equalsIgnoreCase("CommandModule")) {
      moduleType = ModuleType.CommandModule;
    } else if (fileName.equalsIgnoreCase("ObjectModule")) {
      moduleType = ModuleType.ObjectModule;
    } else if (fileName.equalsIgnoreCase("ManagerModule")) {
      moduleType = ModuleType.ManagerModule;
    } else if (fileName.equalsIgnoreCase("ManagedApplicationModule")) {
      moduleType = ModuleType.ManagedApplicationModule;
    } else if (fileName.equalsIgnoreCase("OrdinaryApplicationModule")) {
      moduleType = ModuleType.OrdinaryApplicationModule;
    } else if (fileName.equalsIgnoreCase("SessionModule")) {
      moduleType = ModuleType.SessionModule;
    } else if (fileName.equalsIgnoreCase("RecordSetModule")) {
      moduleType = ModuleType.RecordSetModule;
    } else if (fileName.equalsIgnoreCase("ExternalConnectionModule")) {
      moduleType = ModuleType.ExternalConnectionModule;
    } else if (fileName.equalsIgnoreCase("ApplicationModule")) {
      moduleType = ModuleType.ApplicationModule;
    } else if (fileName.equalsIgnoreCase("ValueManagerModule")) {
      moduleType = ModuleType.ValueManagerModule;
    } else if (fileName.equalsIgnoreCase("Module")) {
      if (partsFileName[1].equalsIgnoreCase("Form")
        || partsFileName[2].equalsIgnoreCase("Forms")) {
        moduleType = ModuleType.FormModule;
      } else {
        moduleType = ModuleType.CommonModule;
      }
    } else {
      moduleType = null;
      LOGGER.error("Module type not find: " + fileName);
    }
    return moduleType;
  }

  public static Map<URI, ModuleType> getModuleTypesByPath(Path rootPath) {

    Map<URI, ModuleType> modulesByType = new HashMap<>();
    String rootPathString = rootPath.toString() + System.getProperty("file.separator");
    Collection<File> files = FileUtils.listFiles(rootPath.toFile(), new String[]{EXTENSION_BSL}, true);

    files.stream().forEach(file -> {
      String[] elementsPath = getPartsByPath(file.toPath().toAbsolutePath());

      // TODO: отрефакторить
      String thirdPath = "";
      if (elementsPath.length > 2) {
        thirdPath = elementsPath[elementsPath.length - 3];
      }

      String[] partsFileName = new String[]{
        FilenameUtils.getBaseName(elementsPath[elementsPath.length - 1]),
        elementsPath[elementsPath.length - 2],
        thirdPath
      };

      ModuleType moduleType = getModuleTypeByFileName(partsFileName);
      modulesByType.put(getAbsoluteUri(file), moduleType);
    });

    return modulesByType;

  }

  public static Map<URI, Map<SupportConfiguration, SupportVariant>> getModuleSupports(Configuration configuration, Map<URI, ModuleType> modulesByType) {

    final Path rootPath;
    boolean isEDT = configuration.getConfigurationSource() == ConfigurationSource.EDT;
    Map<URI, Map<SupportConfiguration, SupportVariant>> modulesBySupport = new HashMap<>();

    File fileParentConfiguration;
    if (isEDT) {
      rootPath = Path.of(configuration.getRootPath().toString(), "src");
      fileParentConfiguration = new File(rootPath.toString(), "Configuration/ParentConfigurations.bin");
    } else {
      rootPath = configuration.getRootPath();
      fileParentConfiguration = new File(rootPath.toString(), "Ext/ParentConfigurations.bin");
    }

    if (fileParentConfiguration.exists()) {
      ParseSupportData supportData = new ParseSupportData(fileParentConfiguration.toPath());
      final Map<String, Map<SupportConfiguration, SupportVariant>> supportMap = supportData.getSupportMap();

      String rootPathString = rootPath.toString() + System.getProperty("file.separator");
      Collection<File> files = FileUtils.listFiles(rootPath.toFile(), new String[]{EXTENSION_BSL}, true);

      files.stream().forEach(file -> {

        // FIXME: неправильное поведение, считается от каталога внутри scr
        URI uri = getAbsoluteUri(file);
        String[] elementsPath = file.toPath().toString().replace(rootPathString, "").split(FILE_SEPARATOR);

        Map<SupportConfiguration, SupportVariant> moduleSupport = null;
        ModuleType moduleType = modulesByType.get(uri);
        String objectGuid = "";

        // TODO: доработать поиски гуидов форм
        if (isEDT) {
          objectGuid = getObjectGuidEDT(rootPath, elementsPath, moduleType);
        } else {
          objectGuid = getObjectGuidOriginal(rootPath, elementsPath, moduleType);
        }

        if (objectGuid.isEmpty()) {
          LOGGER.info("Не удалось найти идентфикатор по объекту " + uri.toString());
        } else {
          moduleSupport = supportMap.get(objectGuid);
        }

        if (moduleSupport == null) {
          moduleSupport = new HashMap<>();
        }

        modulesBySupport.put(uri, moduleSupport);

      });
    }
    return modulesBySupport;
  }

  private static String getObjectGuidEDT(Path rootPath, String[] elementsPath, ModuleType moduleType) {
    Path path = null;
    if (isModuleConfiguration(moduleType)) {

      path = new File(rootPath.toString(), "Configuration/Configuration.mdo").toPath();

    } else {
      String second = "";
      if (elementsPath.length >= 3) {
        second = elementsPath[elementsPath.length - 3];
      }
      if (second.equalsIgnoreCase("Commands") || (second.equalsIgnoreCase("Forms"))) {
        path = getSimplePath(rootPath, elementsPath, 4, EXTENSION_MDO);
      } else {
        path = getSimplePath(rootPath, elementsPath, 2, EXTENSION_MDO);
      }
    }
    return getGuidFromFile(path);
  }

  private static String getObjectGuidOriginal(Path rootPath, String[] elementsPath, ModuleType moduleType) {
    String guid = "";
    Path path;
    if (isModuleConfiguration(moduleType)) {
      path = new File(rootPath.toString(), "Configuration.xml").toPath();
    } else {
      String currentElement = elementsPath[elementsPath.length - 2];
      if (currentElement.equalsIgnoreCase("Ext")) {
        String second = "";
        if (elementsPath.length >= 4) {
          second = elementsPath[elementsPath.length - 4];
        }
        if (second.equalsIgnoreCase("Commands")) {
          path = getSimplePath(rootPath, elementsPath, 5, EXTENSION_XML);
        } else {
          path = getSimplePath(rootPath, elementsPath, 3, EXTENSION_XML);
        }
      } else if (currentElement.equalsIgnoreCase("Form")) {
        path = getSimplePath(rootPath, elementsPath, 4, EXTENSION_XML);
      } else {
        return guid;
      }
    }
    return getGuidFromFile(path);
  }

  public static String getGuidFromFile(Path path) {
    File xml = path.toFile();
    String guid = "";
    if (xml.exists()) {
      guid = findGuidIntoFileXML(xml);
    }
    return guid;
  }

  public static Path getSimplePath(Path rootPath, String[] elementsPath, int startPosition, String extension) {
    String path = "";
    String lastElement = "";
    int position = 0;
    for (String el : elementsPath) {
      if (position == elementsPath.length - startPosition + 1) {
        break;
      }
      path = path + (el + System.getProperty("file.separator"));
      lastElement = el;
      position++;
    }
    if (extension.equals(EXTENSION_XML)) {
      path = path.substring(0, path.length() - 1) + "." + extension;
    } else {
      path = path + lastElement + "." + extension;
    }
    return Path.of(rootPath.toString(), path).toAbsolutePath();
  }

  public static String findGuidIntoFileXML(File file) {
    String result = "";
    String content = getContentFile(file);
    Matcher matcher = PATTERN_REGEX_GUID.matcher(content);
    if (matcher.find()) {
      result = matcher.group(1);
    }
    return result;
  }

  public static String getContentFile(File file) {
    String content = "";
    try {
      content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
    } catch (IOException e) {
      LOGGER.error("Don't read file", e.getMessage());
    }
    return content;
  }

  public static boolean isModuleConfiguration(ModuleType moduleType) {
    return moduleType == ModuleType.ApplicationModule
      || moduleType == ModuleType.ExternalConnectionModule
      || moduleType == ModuleType.ManagedApplicationModule
      || moduleType == ModuleType.OrdinaryApplicationModule
      || moduleType == ModuleType.SessionModule;
  }

  private static String[] getPartsByPath(Path path) {
    var count = path.getNameCount();
    String[] array = new String[count];
    for (int position = 0; position < path.getNameCount(); position++) {
      //array[count - 1 - position] = path.getName(position).toString();
      array[position] = path.getName(position).toString();
    }
    return array;
  }

  public static Path getAbsolutePath(File file) {
    return file.toPath().toAbsolutePath();
  }

  public static URI getAbsoluteUri(File file) {
    return getAbsolutePath(file).toUri();
  }

}
