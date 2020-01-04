package com.github._1c_syntax.mdclasses.metadata;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github._1c_syntax.mdclasses.mdo.MDOConfiguration;
import com.github._1c_syntax.mdclasses.mdo.MetaDataObject;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.utils.Common;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Data
public class Configuration {

    private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class.getSimpleName());

    protected String name;
    protected String uuid;

    protected ConfigurationSource configurationSource;
    protected CompatibilityMode compatibilityMode;
    protected CompatibilityMode configurationExtensionCompatibilityMode;
    protected ScriptVariant scriptVariant;

    protected String defaultRunMode;
    protected String defaultLanguage;
    protected String dataLockControlMode;
    protected String objectAutonumerationMode;
    protected String modalityUseMode;
    protected String synchronousPlatformExtensionAndAddInCallUseMode;

    protected Map<URI, ModuleType> modulesByType = new HashMap<>();

    private Configuration() {
        this.configurationSource = ConfigurationSource.EMPTY;
    }

    private Configuration(MDOConfiguration configurationXml, ConfigurationSource configurationSource, Path rootPath) {
        this.configurationSource = configurationSource;
        this.compatibilityMode = configurationXml.getCompatibilityMode();
        this.scriptVariant = configurationXml.getScriptVariant();
        this.configurationExtensionCompatibilityMode = configurationXml.getConfigurationExtensionCompatibilityMode();
        this.defaultRunMode = configurationXml.getDefaultRunMode();
        this.defaultLanguage = configurationXml.getDefaultLanguage();
        this.dataLockControlMode = configurationXml.getDataLockControlMode();
        this.objectAutonumerationMode = configurationXml.getObjectAutonumerationMode();
        this.modalityUseMode = configurationXml.getModalityUseMode();
        this.synchronousPlatformExtensionAndAddInCallUseMode = configurationXml.getSynchronousPlatformExtensionAndAddInCallUseMode();
        this.name = configurationXml.getName();
        this.uuid = configurationXml.getUuid();
        setModulesByType(Common.getModuleTypesByPath(rootPath));
    }

    public static ConfigurationBuilder newBuilder(Path pathToRoot) {
        return new Configuration().new ConfigurationBuilder(pathToRoot);
    }

    public ModuleType getModuleType(URI uri) {
        return modulesByType.getOrDefault(uri, ModuleType.Unknown);
    }

    public class ConfigurationBuilder {

        private final Path pathToRoot;
        private ConfigurationSource configurationSource;
        private Path pathToConfig;

        private ConfigurationBuilder(Path pathToRoot) {
            this.pathToRoot = pathToRoot;
            this.configurationSource = ConfigurationSource.EMPTY;

            String rootPathString = pathToRoot.toAbsolutePath().toString();

            File rootConfiguration = new File(rootPathString, "Configuration.xml");
            if (rootConfiguration.exists()) {
                configurationSource = ConfigurationSource.DESIGNER;
            } else {
                rootConfiguration = Paths.get(rootPathString, "src", "Configuration", "Configuration.mdo").toFile();
                if (rootConfiguration.exists()) {
                    configurationSource = ConfigurationSource.EDT;
                }
            }
            if (configurationSource != ConfigurationSource.EMPTY) {
                this.pathToConfig = rootConfiguration.toPath();
            }
        }

        public Configuration build() {

            if (configurationSource == ConfigurationSource.EMPTY
                    || (pathToConfig != null && !pathToConfig.toFile().exists())) {
                return Configuration.this;
            }

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            MDOConfiguration configurationXML = null;

            if (configurationSource == ConfigurationSource.DESIGNER) {
                try {
                    MetaDataObject metaDataObject = xmlMapper.readValue(pathToConfig.toFile(), MetaDataObject.class);
                    configurationXML = metaDataObject.getConfiguration();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            } else {
                try {
                    configurationXML = xmlMapper.readValue(pathToConfig.toFile(),
                            MDOConfiguration.class);
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }

            return new Configuration(configurationXML, configurationSource, pathToRoot);
        }
    }
}
