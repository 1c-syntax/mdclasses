package com.github._1c_syntax.mdclasses.metadata.commonmodules;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github._1c_syntax.mdclasses.mdosource.original.CommonModule;
import com.github._1c_syntax.mdclasses.mdosource.original.CommonModuleProperties;
import com.github._1c_syntax.mdclasses.mdosource.original.MetaDataObject;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.ReturnValueReuse;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class DesignCommonModule extends AbstractCommonModule {
  private static final Logger LOGGER = LoggerFactory.getLogger(DesignCommonModule.class.getSimpleName());

  public DesignCommonModule(Path path) {
    super(path, ConfigurationSource.DESIGNER);
  }

  @Override
  public void initialize(File xml) {
    MetaDataObject mdObject = null;

    XmlMapper xmlMapper = new XmlMapper();
    xmlMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);


    try {
      mdObject = xmlMapper.readValue(xml, MetaDataObject.class);
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
    }

    if (mdObject != null) {
      CommonModule commonModuleXML = mdObject.getCommonModule();
      if (commonModuleXML != null) {
        initializeProperties(commonModuleXML);
      }
    }
  }

  private void initializeProperties(CommonModule commonModuleXML) {

    CommonModuleProperties properties = commonModuleXML.getProperties();
    if (properties == null) {
      LOGGER.error("Empty properties", path);
      return;
    }

    server = ObjectUtils.defaultIfNull(properties.getServer(), false);
    global = ObjectUtils.defaultIfNull(properties.getGlobal(), false);
    clientManagedApplication = ObjectUtils.defaultIfNull(properties.getClientManagedApplication(), false);
    externalConnection = ObjectUtils.defaultIfNull(properties.getExternalConnection(), false);
    clientOrdinaryApplication = ObjectUtils.defaultIfNull(properties.getClientOrdinaryApplication(), false);
    serverCall = ObjectUtils.defaultIfNull(properties.getServerCall(), false);

    returnValuesReuse = ReturnValueReuse.DONT_USE;
    if (properties.getReturnValuesReuse() != null) {
      String returnValuesReuseString = properties.getReturnValuesReuse().name().toUpperCase();
      if (!returnValuesReuseString.isEmpty()) {
        returnValuesReuse = ReturnValueReuse.valueOf(returnValuesReuseString);
      }
    }

    privileged = ObjectUtils.defaultIfNull(properties.getPrivileged(), false);
    name = ObjectUtils.defaultIfNull(properties.getName(), "");
    uuid = ObjectUtils.defaultIfNull(commonModuleXML.getUuid(), "");
    comment = ObjectUtils.defaultIfNull(properties.getComment(), "");
  }
}
