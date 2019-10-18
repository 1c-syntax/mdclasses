package com.github._1c_syntax.mdclasses.metadata.commonmodules;

import com.github._1c_syntax.mdclasses.jabx.original.CommonModule;
import com.github._1c_syntax.mdclasses.jabx.original.CommonModuleProperties;
import com.github._1c_syntax.mdclasses.jabx.original.MetaDataObject;
import com.github._1c_syntax.mdclasses.jabx.original.ObjectFactory;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.ReturnValueReuse;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Path;

public class DesignCommonModule extends AbstractCommonModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(DesignCommonModule.class.getSimpleName());

    public DesignCommonModule(Path path) {
        super(path, ConfigurationSource.DESIGNER);
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
            CommonModule commonModuleXML = mdObject.getCommonModule();
            if(commonModuleXML != null) {
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

        server = ObjectUtils.defaultIfNull(properties.isServer(), false);
        global = ObjectUtils.defaultIfNull(properties.isGlobal(), false);
        clientManagedApplication = ObjectUtils.defaultIfNull(properties.isClientManagedApplication(), false);
        externalConnection = ObjectUtils.defaultIfNull(properties.isExternalConnection(), false);
        clientOrdinaryApplication = ObjectUtils.defaultIfNull(properties.isClientOrdinaryApplication(), false);
        serverCall = ObjectUtils.defaultIfNull(properties.isServerCall(), false);

        returnValuesReuse = ReturnValueReuse.DONT_USE;
        if (properties.getReturnValuesReuse() != null) {
            String returnValuesReuseString = properties.getReturnValuesReuse().name().toUpperCase();
            if (!returnValuesReuseString.isEmpty()) {
                returnValuesReuse = ReturnValueReuse.valueOf(returnValuesReuseString);
            }
        }

        privileged = ObjectUtils.defaultIfNull(properties.isPrivileged(), false);
        name = ObjectUtils.defaultIfNull(properties.getName(), "");
        uuid = ObjectUtils.defaultIfNull(commonModuleXML.getUuid(), "");
        comment = ObjectUtils.defaultIfNull(properties.getComment(), "");
    }
}
