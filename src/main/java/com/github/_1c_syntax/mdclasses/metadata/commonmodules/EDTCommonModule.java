package com.github._1c_syntax.mdclasses.metadata.commonmodules;

import com.github._1c_syntax.mdclasses.jabx.edt.CommonModule;
import com.github._1c_syntax.mdclasses.jabx.edt.ObjectFactory;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.ReturnValueReuse;
import com.github._1c_syntax.mdclasses.metadata.configurations.EDTConfiguration;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Path;

public class EDTCommonModule extends AbstractCommonModule {

  private static final Logger LOGGER = LoggerFactory.getLogger(EDTConfiguration.class.getSimpleName());

  public EDTCommonModule(Path path) {
    super(path, ConfigurationSource.EDT);
  }

  @Override
  public void initialize(File xml) {
    CommonModule commonModuleXML;
    try {
      JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
      Unmarshaller jaxbUnpacked = context.createUnmarshaller();
      commonModuleXML = (CommonModule) ((JAXBElement) jaxbUnpacked.unmarshal(xml)).getValue();
    } catch (JAXBException e) {
      commonModuleXML = null;
      LOGGER.error(e.getMessage(), e);
    }

    if (commonModuleXML != null) {
      initializeProperties(commonModuleXML);
    }
  }

  private void initializeProperties(CommonModule commonModuleXML) {

    server = ObjectUtils.defaultIfNull(commonModuleXML.getServer(), false);
    global = ObjectUtils.defaultIfNull(commonModuleXML.getServer(), false);
    clientManagedApplication = ObjectUtils.defaultIfNull(commonModuleXML.getClientManagedApplication(), false);
    externalConnection = ObjectUtils.defaultIfNull(commonModuleXML.getExternalConnection(), false);
    clientOrdinaryApplication = ObjectUtils.defaultIfNull(commonModuleXML.getClientOrdinaryApplication(), false);
    serverCall = ObjectUtils.defaultIfNull(commonModuleXML.getServerCall(), false);

    returnValuesReuse = ReturnValueReuse.DONT_USE;
    if (commonModuleXML.getReturnValuesReuse() != null) {
      String returnValuesReuseString = commonModuleXML.getReturnValuesReuse().name().toUpperCase();
      if (!returnValuesReuseString.isEmpty()) {
        returnValuesReuse = ReturnValueReuse.valueOf(returnValuesReuseString);
      }
    }

    privileged = ObjectUtils.defaultIfNull(commonModuleXML.getPrivileged(), false);
    name = ObjectUtils.defaultIfNull(commonModuleXML.getName(), "");
    uuid = ObjectUtils.defaultIfNull(commonModuleXML.getUuid(), "");
    comment = ObjectUtils.defaultIfNull(commonModuleXML.getComment(), "");
  }
}
