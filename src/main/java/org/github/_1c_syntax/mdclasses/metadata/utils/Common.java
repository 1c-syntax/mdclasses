package org.github._1c_syntax.mdclasses.metadata.utils;

import org.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

}
