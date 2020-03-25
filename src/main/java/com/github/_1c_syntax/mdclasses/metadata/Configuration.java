package com.github._1c_syntax.mdclasses.metadata;

import com.github._1c_syntax.mdclasses.mdo.MDOConfiguration;
import com.github._1c_syntax.mdclasses.mdo.MDObjectBase;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.additional.SupportVariant;
import com.github._1c_syntax.mdclasses.metadata.additional.UseMode;
import com.github._1c_syntax.mdclasses.utils.Common;
import com.github._1c_syntax.mdclasses.utils.MDOUtils;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Value
@Slf4j
public class Configuration {

  String name;
  String uuid;

  ConfigurationSource configurationSource;
  CompatibilityMode compatibilityMode;
  CompatibilityMode configurationExtensionCompatibilityMode;
  ScriptVariant scriptVariant;

  String defaultRunMode;
  String defaultLanguage;
  String dataLockControlMode;
  String objectAutonumerationMode;
  UseMode modalityUseMode;
  UseMode synchronousExtensionAndAddInCallUseMode;
  UseMode synchronousPlatformExtensionAndAddInCallUseMode;

  Map<URI, ModuleType> modulesByType;
  Map<URI, Map<SupportConfiguration, SupportVariant>> modulesBySupport;
  Set<MDObjectBase> children;
  Path rootPath;

  private Configuration() {
    this.configurationSource = ConfigurationSource.EMPTY;
    this.children = Collections.emptySet();
    this.modulesByType = Collections.emptyMap();
    this.modulesBySupport = Collections.emptyMap();

    this.rootPath = null;
    this.name = "";
    this.uuid = "";

    this.compatibilityMode = new CompatibilityMode();
    this.configurationExtensionCompatibilityMode = new CompatibilityMode();
    this.scriptVariant = ScriptVariant.ENGLISH;

    this.defaultRunMode = "";
    this.defaultLanguage = "";
    this.dataLockControlMode = "";
    this.objectAutonumerationMode = "";
    this.modalityUseMode = UseMode.USE;
    this.synchronousExtensionAndAddInCallUseMode = UseMode.USE;
    this.synchronousPlatformExtensionAndAddInCallUseMode = UseMode.USE;
  }

  private Configuration(MDOConfiguration configurationXml, ConfigurationSource configurationSource, Path rootPath) {
    this.configurationSource = configurationSource;
    this.children = MDOUtils.getAllChildren(configurationSource, rootPath, true);
    this.rootPath = rootPath;

    this.name = configurationXml.getName();
    this.uuid = configurationXml.getUuid();

    this.compatibilityMode = configurationXml.getCompatibilityMode();
    this.configurationExtensionCompatibilityMode = configurationXml.getConfigurationExtensionCompatibilityMode();
    this.scriptVariant = configurationXml.getScriptVariant();

    this.defaultRunMode = configurationXml.getDefaultRunMode();
    this.defaultLanguage = configurationXml.getDefaultLanguage();
    this.dataLockControlMode = configurationXml.getDataLockControlMode();
    this.objectAutonumerationMode = configurationXml.getObjectAutonumerationMode();
    this.modalityUseMode = configurationXml.getModalityUseMode();
    this.synchronousExtensionAndAddInCallUseMode = configurationXml.getSynchronousExtensionAndAddInCallUseMode();
    this.synchronousPlatformExtensionAndAddInCallUseMode = configurationXml.getSynchronousPlatformExtensionAndAddInCallUseMode();

    this.modulesByType = Common.getModuleTypesByPath(this);
    this.modulesBySupport = Common.getModuleSupports(this);
  }

  public static Configuration create() {
    return new Configuration();
  }

  public static Configuration create(Path rootPath) {
    ConfigurationSource configurationSource = MDOUtils.getConfigurationSourceByPath(rootPath);
    if (configurationSource != ConfigurationSource.EMPTY) {
      MDOConfiguration configurationXML = (MDOConfiguration) MDOUtils.getMDObject(configurationSource,
        rootPath, MDOType.CONFIGURATION, "Configuration");
      if (configurationXML != null) {
        return new Configuration(configurationXML, configurationSource, rootPath);
      }
    }

    return create();
  }

  public ModuleType getModuleType(URI uri) {
    return modulesByType.getOrDefault(uri, ModuleType.Unknown);
  }

  public Map<SupportConfiguration, SupportVariant> getModuleSupport(URI uri) {
    return modulesBySupport.getOrDefault(uri, new HashMap<>());
  }
}
