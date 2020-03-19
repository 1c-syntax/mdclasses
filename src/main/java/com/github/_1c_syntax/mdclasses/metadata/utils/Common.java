package com.github._1c_syntax.mdclasses.metadata.utils;

import com.github._1c_syntax.mdclasses.mdo.MDObjectBase;
import com.github._1c_syntax.mdclasses.metadata.Configuration;
import com.github._1c_syntax.mdclasses.metadata.SupportConfiguration;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
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

      configuration.getChildren()
        .forEach((mdoType, stringMDObjectBaseMap) ->
          stringMDObjectBaseMap.forEach((name, mdObject) -> {
            var mdoModuleSupport = getMDObjectSupport(supportMap, mdObject);
            if (!mdoModuleSupport.isEmpty()) {
              modulesBySupport.putAll(mdoModuleSupport);
            }
            if (mdObject.getForms() != null) {
              mdObject.getForms().forEach(form -> {
                var formSupport = getMDObjectSupport(supportMap, form);
                if (!formSupport.isEmpty()) {
                  modulesBySupport.putAll(formSupport);
                }
              });
            }
          })
        );
    }
    return modulesBySupport;
  }

  private static Map<URI, Map<SupportConfiguration, SupportVariant>> getMDObjectSupport(Map<String, Map<SupportConfiguration, SupportVariant>> supportMap, MDObjectBase mdObject) {
    Map<URI, Map<SupportConfiguration, SupportVariant>> modulesBySupport = new HashMap<>();
    Set<URI> uris = new HashSet<>();
    if (mdObject.getMdoURI() != null) {
      uris.add(mdObject.getMdoURI());
    }
    if (mdObject.getModulesByType() != null) {
      mdObject.getModulesByType().forEach((uri, moduleType) -> uris.add(uri));
    }

    Map<SupportConfiguration, SupportVariant> moduleSupport = Collections.emptyMap();
    if (mdObject.getUuid() == null) {
      LOGGER.info("Не удалось найти идентфикатор по объекту " + mdObject);
    } else {
      moduleSupport = supportMap.getOrDefault(mdObject.getUuid(), Collections.emptyMap());
    }
    for (URI uri : uris) {
      modulesBySupport.put(uri, moduleSupport);
    }
    return modulesBySupport;
  }

}
