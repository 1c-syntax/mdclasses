package com.github._1c_syntax.mdclasses.metadata;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github._1c_syntax.mdclasses.mdosource.common.MetaDataObject;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.configurations.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigurationBuilder {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationBuilder.class.getSimpleName());

  private ConfigurationSource configurationSource;
  private Configuration configuration;

  private final Path pathToRoot;
  private Path pathToConfig;

  public ConfigurationBuilder(Path pathToRoot) {

    this.pathToRoot = pathToRoot;

    configurationSource = ConfigurationSource.EMPTY;

    String rootPathString = pathToRoot.toAbsolutePath().toString();

    File rootConfiguration = new File(rootPathString, "Configuration.xml");
    if (rootConfiguration.exists()) {
      configurationSource = ConfigurationSource.DESIGNER;
    } else {
      rootConfiguration = Paths.get(rootPathString, "src", "Configuration", "Configuration.mdo").toFile();
      if (rootConfiguration.exists()) {
        configurationSource = ConfigurationSource.EDT;
      }
    }
    if (configurationSource != ConfigurationSource.EMPTY) {
      pathToConfig = rootConfiguration.toPath();
    }
  }

  public Configuration build() {

    if (pathToConfig != null && !pathToConfig.toFile().exists()) {
      return configuration;
    }

    if(configuration == null) {
      if (configurationSource == ConfigurationSource.EMPTY) {
        configuration = new Configuration();
        configuration.buildEmptyConfiguration();
      } else {
        initializeConfiguration(pathToConfig.toFile());
      }
    }
    return configuration;
  }

  public void initializeConfiguration(File xml) {

    XmlMapper xmlMapper = new XmlMapper();
    xmlMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    try {
      if (configurationSource == ConfigurationSource.DESIGNER) {
        MetaDataObject metaDataObject = xmlMapper.readValue(xml, MetaDataObject.class);
        configuration = metaDataObject.getConfiguration();
      } else if (configurationSource == ConfigurationSource.EDT) {
        configuration = xmlMapper.readValue(xml, Configuration.class);
      }
      configuration.setModulesByType(pathToRoot);
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
