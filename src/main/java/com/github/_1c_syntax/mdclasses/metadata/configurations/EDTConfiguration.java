package com.github._1c_syntax.mdclasses.metadata.configurations;

import com.github._1c_syntax.mdclasses.jackson.edt.Configuration;
import com.github._1c_syntax.mdclasses.jackson.edt.ObjectFactory;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.utils.Common;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Path;

public class EDTConfiguration extends AbstractConfiguration {

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
