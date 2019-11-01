package com.github._1c_syntax.mdclasses.mdo.utils;

import com.github._1c_syntax.mdclasses.mdo.core.AbstractMDO;
import com.github._1c_syntax.mdclasses.mdo.core.ConfigurationSource;
import com.github._1c_syntax.mdclasses.mdo.core.MDOType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

public class MDO {
    private static final Logger LOGGER = LoggerFactory.getLogger(MDO.class.getSimpleName());
    // файл описания конфигурации для выгрузки из конфигуратора
    public static final String DESIGNER_CONFIGURATION_FILENAME = "Configuration.xml";
    // файл описания конфигурации для EDT
    public static final String EDT_CONFIGURATION_FILENAME = "src/Configuration/Configuration.mdo";

    public static <T, O> T unmarshalXML(File xml, Class<T> typeMDO, Class<O> objectFactoryClass) {
        T mdoObj;
        try {
            JAXBContext context = JAXBContext.newInstance(objectFactoryClass);
            Unmarshaller jaxbUnpacked = context.createUnmarshaller();
            mdoObj = typeMDO.cast(((JAXBElement) jaxbUnpacked.unmarshal(xml)).getValue());
        } catch (JAXBException e) {
            mdoObj = null;
            LOGGER.error(e.getMessage(), e);
        }
        return mdoObj;
    }

    public static ConfigurationSource getConfigurationSourceByPath(String rootPath) {
        File configurationFile = new File(rootPath, DESIGNER_CONFIGURATION_FILENAME);
        if (configurationFile.exists()) {
            return ConfigurationSource.DESIGNER;
        } else {
            configurationFile = new File(rootPath, EDT_CONFIGURATION_FILENAME);
            if (configurationFile.exists()) {
                return ConfigurationSource.EDT;
            }
        }
        return ConfigurationSource.EMPTY;
    }

    public static Class getMDOClassByType(MDOType type) {
        Class mdoClass = null;
        String tName = type.name();
        tName = tName.substring(0, 1).toUpperCase() + tName.substring(1).toLowerCase();
        try {
            mdoClass = Class.forName("com.github._1c_syntax.mdclasses.mdo.classes." + tName);
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return mdoClass;
    }

    public static AbstractMDO getMDOObject(MDOType type) {
        Class mdoClass = getMDOClassByType(type);
        if (mdoClass != null) {
            Constructor constructor = null;
            if(mdoClass.getDeclaredConstructors().length != 0) {
                constructor = mdoClass.getDeclaredConstructors()[0];
                constructor.setAccessible(true);
                Object mdoObject = null;
                try {
                    mdoObject = constructor.newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    LOGGER.error(e.getMessage(), e);
                }
                return (AbstractMDO) mdoObject;
            }
        }
        return null;
    }
}
