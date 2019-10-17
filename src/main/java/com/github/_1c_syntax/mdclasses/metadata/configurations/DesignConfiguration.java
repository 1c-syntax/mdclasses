package com.github._1c_syntax.mdclasses.metadata.configurations;

import com.github._1c_syntax.mdclasses.jabx.original.Configuration;
import com.github._1c_syntax.mdclasses.jabx.original.MetaDataObject;
import com.github._1c_syntax.mdclasses.jabx.original.ObjectFactory;
import com.github._1c_syntax.mdclasses.metadata.utils.Common;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Path;

public class DesignConfiguration extends AbstractConfiguration {

  private static final Logger LOGGER = LoggerFactory.getLogger(DesignConfiguration.class.getSimpleName());

  public DesignConfiguration(ConfigurationSource configurationSource, Path rootPath) {
    super(configurationSource, rootPath);
  }

  @Override
  public void initialize(File xml) {

    MetaDataObject mdObject;
    try {
      JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
      Unmarshaller jaxbUnpacked = context.createUnmarshaller();
      mdObject = (MetaDataObject) ((JAXBElement) jaxbUnpacked.unmarshal(xml)).getValue();
    } catch (JAXBException e) {
      mdObject = null;
      LOGGER.error(e.getMessage(), e);
    }

    if (mdObject != null) {

      Configuration configurationXML = mdObject.getConfiguration();
      initializeProperties(configurationXML);
      initializeModuleType();

    }

  }

  private void initializeProperties(Configuration configurationXML) {

    // Режим совместимости
    compatibilityMode = new CompatibilityMode("Version_8_3_12");
    try {
      compatibilityMode =
          new CompatibilityMode(
              configurationXML.getProperties().getCompatibilityMode().name());
    } catch (NullPointerException e) {
      LOGGER.error("Не удалось получить значение CompatibilityMode. Причина " + e.getStackTrace().toString());
    }

    // Язык скрипта
    String scriptVariantString = "RUSSIAN";
    try {
      scriptVariantString = configurationXML.getProperties().getScriptVariant().name().toUpperCase();
    } catch (NullPointerException e) {
      LOGGER.error("Не удалось получить значение ScriptVariant. Причина " + e.getStackTrace().toString());
    }
    scriptVariant = ScriptVariant.valueOf(scriptVariantString);

  }

  private void initializeModuleType() {
    setModulesByType(Common.getModuleTypesByPath(rootPath));
  }

}
