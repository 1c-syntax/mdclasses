package org.github._1c_syntax.mdclasses.metadata.configurations;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.github._1c_syntax.mdclasses.jabx.edt.Configuration;
import org.github._1c_syntax.mdclasses.jabx.edt.ObjectFactory;
import org.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import org.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import org.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import org.github._1c_syntax.mdclasses.metadata.utils.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Path;
import java.util.Collection;

public class EDTConfiguration extends AbstractConfiguration{

  private static final Logger LOGGER = LoggerFactory.getLogger(EDTConfiguration.class.getSimpleName());

  public EDTConfiguration(ConfigurationSource configurationSource, Path rootPath) {
    super(configurationSource, rootPath);
  }

  @Override
  public void initialize(File xml) {

    Configuration configurationXML;
    try {
      JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
      Unmarshaller jaxbUnpacked = context.createUnmarshaller();
      configurationXML = (Configuration) ((JAXBElement) jaxbUnpacked.unmarshal(xml)).getValue();
    } catch (JAXBException e) {
      configurationXML = null;
      LOGGER.error(e.getMessage(), e);
    }

    if (configurationXML != null) {

    }

  }

  private void initializeProperties(Configuration configurationXML) {

    // режим совместимости
    compatibilityMode = new CompatibilityMode(configurationXML.getConfigurationExtensionCompatibilityMode());

    // режим встроенного языка
    String scriptVariantString = configurationXML.getScriptVariant().toUpperCase();
    scriptVariant = ScriptVariant.valueOf(scriptVariantString);

  }

  private void initializeModuleType() {

    // TODO: Перенести в одно место
    String rootPathString = rootPath.toString() + System.getProperty("file.separator");
    Collection<File> files = FileUtils.listFiles(rootPath.toFile(), new String[]{Common.EXTENSION_BSL}, true);
    files.parallelStream().forEach(file -> {
      String[] elementsPath =
          file.toPath().toString().replace(rootPathString, "").split(Common.FILE_SEPARATOR);
      String secondFileName = elementsPath[elementsPath.length - 2];
      String fileName = FilenameUtils.getBaseName(elementsPath[elementsPath.length - 1]);
      ModuleType moduleType = Common.changeModuleTypeByFileName(fileName, secondFileName);
      modulesByType.put(file.toURI(), moduleType);
    });

  }

}
