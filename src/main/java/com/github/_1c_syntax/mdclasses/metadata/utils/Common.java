package com.github._1c_syntax.mdclasses.metadata.utils;

import com.github._1c_syntax.mdclasses.mdo.MDObjectBase;
import com.github._1c_syntax.mdclasses.metadata.Configuration;
import com.github._1c_syntax.mdclasses.metadata.SupportConfiguration;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.SupportVariant;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class Common {

  public static final String EXTENSION_XML = "xml";
  public static final String EXTENSION_MDO = "mdo";

  private Common() {
    // only statics
  }

  public static Map<URI, Map<SupportConfiguration, SupportVariant>> getModuleSupports(Configuration configuration) {
    Map<URI, Map<SupportConfiguration, SupportVariant>> modulesBySupport = new HashMap<>();
    File fileParentConfiguration;
    if (configuration.getConfigurationSource() == ConfigurationSource.EDT) {
      fileParentConfiguration = Paths.get(configuration.getRootPath().toString(),
        "src", "Configuration/ParentConfigurations.bin")
        .toFile();
    } else {
      fileParentConfiguration = Paths.get(configuration.getRootPath().toString(),
        "Ext/ParentConfigurations.bin")
        .toFile();
    }

    if (fileParentConfiguration.exists()) {
      ParseSupportData supportData = new ParseSupportData(fileParentConfiguration.toPath());
      final Map<String, Map<SupportConfiguration, SupportVariant>> supportMap = supportData.getSupportMap();

      configuration.getChildren().forEach(mdObject -> {
        modulesBySupport.putAll(getMDObjectSupport(supportMap, mdObject));
        if (mdObject.getForms() != null) {
          mdObject.getForms().forEach(form -> modulesBySupport.putAll(getMDObjectSupport(supportMap, form)));
        }
      });
    }

    return modulesBySupport;
  }

  private static Map<URI, Map<SupportConfiguration, SupportVariant>> getMDObjectSupport(
    Map<String, Map<SupportConfiguration, SupportVariant>> supportMap,
    MDObjectBase mdObject) {

    Map<URI, Map<SupportConfiguration, SupportVariant>> modulesBySupport = new HashMap<>();
    if (mdObject.getUuid() == null || mdObject.getModulesByType() == null) {
      return modulesBySupport;
    }

    Set<URI> uris = new HashSet<>(mdObject.getModulesByType().keySet());

    Map<SupportConfiguration, SupportVariant> moduleSupport =
      supportMap.getOrDefault(mdObject.getUuid(), Collections.emptyMap());

    for (URI uri : uris) {
      modulesBySupport.put(uri, moduleSupport);
    }
    return modulesBySupport;
  }

  public static Map<URI, ModuleType> getModuleTypesByPath(Configuration configuration) {
    Map<URI, ModuleType> modulesByType = new HashMap<>();

    configuration.getChildren().forEach(mdObject -> {
        if (mdObject.getModulesByType() != null) {
          mdObject.getModulesByType().forEach(modulesByType::put);
        }
        if (mdObject.getForms() != null) {
          mdObject.getForms().forEach(form -> {
            if (form.getModulesByType() != null) {
              form.getModulesByType().forEach(modulesByType::put);
            }
          });
        }
      }
    );
    return modulesByType;
  }

}
