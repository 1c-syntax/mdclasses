package com.github._1c_syntax.mdclasses.metadata.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Common {

  private static final Logger LOGGER = LoggerFactory.getLogger(Common.class.getSimpleName());

  public static final String EXTENSION_BSL = "bsl";
  public static final String FILE_SEPARATOR = Pattern.quote(System.getProperty("file.separator"));

  public static ModuleType changeModuleTypeByFileName(String fileName, String secondFileName) {

    ModuleType moduleType = null;

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
      if (secondFileName.equalsIgnoreCase("Form")) {
        moduleType = ModuleType.FormModule;
      } else {
        moduleType = ModuleType.CommonModule;
      }
    } else {
      LOGGER.error("Module type not find: " + fileName);
    }

    return moduleType;

  }

  public static Map<URI, ModuleType> getModuleTypesByPath(Path rootPath) {

    Map<URI, ModuleType> modulesByType = new HashMap<>();
    String rootPathString = rootPath.toString() + System.getProperty("file.separator");
    Collection<File> files = FileUtils.listFiles(rootPath.toFile(), new String[]{EXTENSION_BSL}, true);
    files.parallelStream().forEach(file -> {
      String[] elementsPath =
          file.toPath().toString().replace(rootPathString, "").split(FILE_SEPARATOR);
      String secondFileName = elementsPath[elementsPath.length - 2];
      String fileName = FilenameUtils.getBaseName(elementsPath[elementsPath.length - 1]);
      ModuleType moduleType = Common.changeModuleTypeByFileName(fileName, secondFileName);
      modulesByType.put(file.toURI(), moduleType);
    });

    return modulesByType;

  }

}
