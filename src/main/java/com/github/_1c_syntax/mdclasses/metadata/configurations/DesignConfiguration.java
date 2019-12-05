package com.github._1c_syntax.mdclasses.metadata.configurations;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github._1c_syntax.mdclasses.jacson.designer.Configuration;
import com.github._1c_syntax.mdclasses.jacson.designer.MetaDataObject;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.utils.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

//import com.github._1c_syntax.mdclasses.jacson.designer.Configuration;

public class DesignConfiguration extends AbstractConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DesignConfiguration.class.getSimpleName());

    public DesignConfiguration(ConfigurationSource configurationSource, Path rootPath) {
        super(configurationSource, rootPath);
    }

    @Override
    public void initialize(File xml) {

        MetaDataObject mdObject;
        XmlMapper xmlMapper = new XmlMapper();
        mdObject = null;
        try {
            mdObject = xmlMapper.readValue(xml, com.github._1c_syntax.mdclasses.jacson.designer.MetaDataObject.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
            compatibilityMode = new CompatibilityMode(configurationXML.getProperties().getCompatibilityMode());
        } catch (NullPointerException e) {
            LOGGER.error("Не удалось получить значение CompatibilityMode.", e);
        }

        // Язык скрипта
        String scriptVariantString = "RUSSIAN";
        try {
            scriptVariantString = configurationXML.getProperties().getScriptVariant().toUpperCase();
        } catch (NullPointerException e) {
            LOGGER.error("Не удалось получить значение ScriptVariant.", e);
        }
        scriptVariant = ScriptVariant.valueOf(scriptVariantString);

    }

    private void initializeModuleType() {
        setModulesByType(Common.getModuleTypesByPath(rootPath));
    }

}
