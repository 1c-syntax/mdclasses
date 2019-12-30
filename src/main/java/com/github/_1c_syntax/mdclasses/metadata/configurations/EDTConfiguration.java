package com.github._1c_syntax.mdclasses.metadata.configurations;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github._1c_syntax.mdclasses.mdosource.edt.Configuration;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.utils.Common;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class EDTConfiguration extends AbstractConfiguration {

  private static final Logger LOGGER = LoggerFactory.getLogger(EDTConfiguration.class.getSimpleName());

  public EDTConfiguration(ConfigurationSource configurationSource, Path rootPath) {
    super(configurationSource, rootPath);
  }

  @Override
  public void initialize(File xml) {

    Configuration configurationXML = null;

    XmlMapper xmlMapper = new XmlMapper();
    xmlMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);


    try {
      configurationXML = xmlMapper.readValue(xml, Configuration.class);
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
    }

    if (configurationXML != null) {
      initializeProperties(configurationXML);
      initializeModuleType();
    }

  }

  private void initializeProperties(Configuration configurationXML) {

    // режим совместимости
    compatibilityMode = new CompatibilityMode(configurationXML.getConfigurationExtensionCompatibilityMode());

    // режим встроенного языка
    String scriptVariantString = ObjectUtils.defaultIfNull(configurationXML.getScriptVariant(), "");
    if (scriptVariantString.isEmpty()) {
      scriptVariant = ScriptVariant.ENGLISH;
    } else {
      scriptVariant = ScriptVariant.valueOf(scriptVariantString.toUpperCase());
    }
  }

  private void initializeModuleType() {
    setModulesByType(Common.getModuleTypesByPath(rootPath));
  }

}
