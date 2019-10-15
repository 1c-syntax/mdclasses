package org.github._1c_syntax.mdclasses.metadata;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.github._1c_syntax.mdclasses.jabx.original.MetaDataObject;
import org.github._1c_syntax.mdclasses.jabx.original.ObjectFactory;
import org.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import org.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import org.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ConfigurationBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationBuilder.class.getSimpleName());

    public static final String EXTENSION_BSL = "bsl";
    public static final String FILE_SEPARATOR = Pattern.quote(System.getProperty("file.separator"));

    private ConfigurationSource configurationSource;
    private Path pathToRoot;
    private Path pathToConfig;

    private Configuration configurationMetadata;

    public ConfigurationBuilder(ConfigurationSource configurationSource, Path pathToRoot) {
        this.configurationSource = configurationSource;
        this.pathToRoot = pathToRoot;

        if(configurationSource == ConfigurationSource.DESIGNER) {
            pathToConfig = Paths.get(pathToRoot.toAbsolutePath().toString(), "Configuration.xml");
        } else if(configurationSource == ConfigurationSource.EDT) {
            pathToConfig = Paths.get(pathToRoot.toAbsolutePath().toString(), "src", "Configuration", "Configuration.mdo");
        } else {
            // TODO Новый формат надо будет поддержать
        }
    }

    public Configuration build() {

        configurationMetadata = new Configuration(configurationSource);

        if (!pathToConfig.toFile().exists()) {
            return configurationMetadata;
        }

        if (configurationSource == ConfigurationSource.DESIGNER) {

            MetaDataObject mdObject;
            File xml = pathToConfig.toFile();
            try {
                JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
                Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
                mdObject = (MetaDataObject) ((JAXBElement) jaxbUnmarshaller.unmarshal(xml)).getValue();
            } catch (JAXBException e) {
                LOGGER.error(e.getMessage(), e);
                return configurationMetadata;
            }

            org.github._1c_syntax.mdclasses.jabx.original.Configuration configurationXML = mdObject.getConfiguration();

            fillPropertiesDesinger(configurationXML);

            processConfigurationFilesDesinger();

        }
        else if (configurationSource == ConfigurationSource.EDT) {
            // TODO Реализовать разбор формата EDT

            org.github._1c_syntax.mdclasses.jabx.edt.Configuration configurationXML;
            File xml = pathToConfig.toFile();
            try {
                JAXBContext context = JAXBContext.newInstance(org.github._1c_syntax.mdclasses.jabx.edt.ObjectFactory.class);
                Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
                configurationXML = (org.github._1c_syntax.mdclasses.jabx.edt.Configuration) ((JAXBElement) jaxbUnmarshaller.unmarshal(xml)).getValue();
            } catch (JAXBException e) {
                LOGGER.error(e.getMessage(), e);
                return null; // TODO: пока так, переделать
            }

            fillPropertiesEDT(configurationXML);
            processConfigurationFilesDesinger();

        } else {

            // TODO Новый формат надо будет поддержать
        }

        return configurationMetadata;
    }

    private void fillPropertiesDesinger(org.github._1c_syntax.mdclasses.jabx.original.Configuration configurationXML) {

        // режим совместимости
        setCompatibilityMode(configurationXML);

        // режим встроенного языка
        setScriptVariant(configurationXML);
    }

    private void fillPropertiesEDT(org.github._1c_syntax.mdclasses.jabx.edt.Configuration configurationXML) {

        // режим совместимости
        CompatibilityMode compatibilityMode =
                new CompatibilityMode(
                        configurationXML.getConfigurationExtensionCompatibilityMode());
        configurationMetadata.setCompatibilityMode(compatibilityMode);

        // режим встроенного языка
        String scriptVariantString = configurationXML.getScriptVariant().toUpperCase();
        configurationMetadata.setScriptVariant(ScriptVariant.valueOf(scriptVariantString));
    }

    private void processConfigurationFilesDesinger() {

        Map<URI, ModuleType> modulesByType = new HashMap<>();
        String rootPathString = pathToRoot.toString() + System.getProperty("file.separator");
        Collection<File> files = FileUtils.listFiles(pathToRoot.toFile(), new String[]{EXTENSION_BSL}, true);
        files.parallelStream().forEach(file -> {
            String[] elementsPath =
              file.toPath().toString().replace(rootPathString, "").split(FILE_SEPARATOR);
            String secondFileName = elementsPath[elementsPath.length - 2];
            String fileName = FilenameUtils.getBaseName(elementsPath[elementsPath.length - 1]);
            ModuleType moduleType = changeModuleTypeByFileName(fileName, secondFileName);
            modulesByType.put(file.toURI(), moduleType);
        });

        configurationMetadata.setModulesByType(modulesByType);

    }

    private void setCompatibilityMode(org.github._1c_syntax.mdclasses.jabx.original.Configuration configurationXML) {

        CompatibilityMode compatibilityMode = new CompatibilityMode("Version_8_3_12");
        try {
            compatibilityMode =
              new CompatibilityMode(
                configurationXML.getProperties().getCompatibilityMode().name());
        }
        catch (NullPointerException e) {
            LOGGER.error("Не удалось получить значение CompatibilityMode. Причина " + e.getStackTrace().toString());
        }
        configurationMetadata.setCompatibilityMode(compatibilityMode);

    }

    private void setScriptVariant(org.github._1c_syntax.mdclasses.jabx.original.Configuration configurationXML) {

        String scriptVariantString = "RUSSIAN";
        try {
            scriptVariantString = configurationXML.getProperties().getScriptVariant().name().toUpperCase();
        }
        catch (NullPointerException e) {
            LOGGER.error("Не удалось получить значение ScriptVariant. Причина " + e.getStackTrace().toString());
        }
        configurationMetadata.setScriptVariant(ScriptVariant.valueOf(scriptVariantString));

    }

    private ModuleType changeModuleTypeByFileName(String fileName, String secondFileName) {
        ModuleType moduleType = null;

        if (fileName.equalsIgnoreCase("CommandModule")) {
            moduleType = ModuleType.CommandModule;
        } else if (fileName.equalsIgnoreCase("ObjectModule")) {
            moduleType = ModuleType.ObjectModule;
        } else if (fileName.equalsIgnoreCase("ManagerModule")) {
            moduleType = ModuleType.ManagerModule;
        } else if (fileName.equalsIgnoreCase("ManagedApplicationModule")) {
            moduleType = ModuleType.ManagedApplicationModule;
        } else if (fileName.equalsIgnoreCase("OrdinaryApplicationModule")) {
            moduleType = ModuleType.OrdinaryApplicationModule;
        } else if (fileName.equalsIgnoreCase("SessionModule")) {
            moduleType = ModuleType.SessionModule;
        } else if (fileName.equalsIgnoreCase("RecordSetModule")) {
            moduleType = ModuleType.RecordSetModule;
        } else if (fileName.equalsIgnoreCase("ExternalConnectionModule")) {
            moduleType = ModuleType.ExternalConnectionModule;
        } else if (fileName.equalsIgnoreCase("ApplicationModule")) {
            moduleType = ModuleType.ApplicationModule;
        } else if (fileName.equalsIgnoreCase("ValueManagerModule")) {
            moduleType = ModuleType.ValueManagerModule;
        } else if (fileName.equalsIgnoreCase("Module")) {
            if (secondFileName.equalsIgnoreCase("Form")) {
                moduleType = ModuleType.FormModule;
            } else {
                moduleType = ModuleType.CommonModule;
            }
        } else {
            LOGGER.error("Module type not find: " + fileName);
        }

        return moduleType;
    }

}
