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
import com.github._1c_syntax.mdclasses.metadata.utils.Common;
import com.github._1c_syntax.mdclasses.metadata.utils.MDOUtils;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Value
@Slf4j
public class Configuration {

  protected String name;
  protected String uuid;

  protected ConfigurationSource configurationSource;
  protected CompatibilityMode compatibilityMode;
  protected CompatibilityMode configurationExtensionCompatibilityMode;
  protected ScriptVariant scriptVariant;

  protected String defaultRunMode;
  protected String defaultLanguage;
  protected String dataLockControlMode;
  protected String objectAutonumerationMode;
  protected UseMode modalityUseMode;
  protected UseMode synchronousExtensionAndAddInCallUseMode;
  protected UseMode synchronousPlatformExtensionAndAddInCallUseMode;

  protected Map<URI, ModuleType> modulesByType;
  protected Map<URI, Map<SupportConfiguration, SupportVariant>> modulesBySupport;
  protected Map<MDOType, Map<String, MDObjectBase>> children;
  private Path rootPath;

  private Configuration() {
    this.configurationSource = ConfigurationSource.EMPTY;
    this.children = Collections.emptyMap();
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

    this.modulesByType = MDOUtils.getModuleTypesByPath(configurationSource, rootPath);
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

  public MDObjectBase getChild(MDOType type, String childName) {
    MDObjectBase child = null;
    Map<String, MDObjectBase> childrenByType = children.get(type);
    if (childrenByType == null) {
      childrenByType = getChildren(type);
    }
    if (!childrenByType.isEmpty()) {
      child = childrenByType.get(childName);
    }
    return child;
  }

  @SneakyThrows
  public Map<String, MDObjectBase> getChildren(MDOType type) {
    Map<String, MDObjectBase> childrenByType = children.get(type);
    if (childrenByType == null) {
      childrenByType = MDOUtils.getChildren(configurationSource, rootPath, type);
    }
    if (!childrenByType.isEmpty()) {
      children.put(type, childrenByType);
    }
    return childrenByType;
  }

  public ModuleType getModuleType(URI uri) {
    return modulesByType.getOrDefault(uri, ModuleType.Unknown);
  }

  public Map<SupportConfiguration, SupportVariant> getModuleSupport(URI uri) {
    return modulesBySupport.getOrDefault(uri, new HashMap<>());
  }
}
