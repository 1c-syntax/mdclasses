package com.github._1c_syntax.mdclasses.metadata.configurations;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github._1c_syntax.mdclasses.mdosource.common.Configuration;
import com.github._1c_syntax.mdclasses.mdosource.common.MetaDataObject;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.utils.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class EmptyConfiguration extends AbstractConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmptyConfiguration.class.getSimpleName());

    public EmptyConfiguration() {
        this(ConfigurationSource.EMPTY, null);
    }

    public EmptyConfiguration(ConfigurationSource configurationSource, Path rootPath) {
        super(configurationSource, rootPath);
    }

    @Override
    public void initialize(File xml, String className) {

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        Configuration configurationXML = null;

        if (className.equals(MetaDataObject.class.getName())) {
            try {
                MetaDataObject metaDataObject = xmlMapper.readValue(xml, MetaDataObject.class);
                configurationXML = metaDataObject.getConfiguration();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        } else {
            try {
                configurationXML = xmlMapper.readValue(xml, Configuration.class);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        if (configurationXML != null) {
            initializeProperties(configurationXML);
            initializeModuleType();
        }

    }

    private void initializeProperties(Configuration configurationXML) {
        compatibilityMode = configurationXML.getCompatibilityMode();
        scriptVariant = configurationXML.getScriptVariant();
    }

    private void initializeModuleType() {
        setModulesByType(Common.getModuleTypesByPath(rootPath));
    }

}
