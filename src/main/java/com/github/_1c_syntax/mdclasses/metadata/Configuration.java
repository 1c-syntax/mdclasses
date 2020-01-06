package com.github._1c_syntax.mdclasses.metadata;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github._1c_syntax.mdclasses.mdo.MDOConfiguration;
import com.github._1c_syntax.mdclasses.mdo.MDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.MetaDataObject;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.utils.Common;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
public class Configuration {

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
    protected HashMap<MDOType, HashMap<String, MDObjectBase>> children;
    private Path rootPath;

    private Configuration() {
        this.configurationSource = ConfigurationSource.EMPTY;
        this.children = new HashMap<>();
    }

    private Configuration(MDOConfiguration configurationXml, ConfigurationSource configurationSource, Path rootPath) {
        this.configurationSource = configurationSource;
        this.children = new HashMap<>();
        this.rootPath = rootPath;

        try {
            new BeanUtilsBean().copyProperties(this, configurationXml);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error(e.getMessage(), e);
        }

        setModulesByType(Common.getModuleTypesByPath(rootPath));
    }

    public static ConfigurationBuilder newBuilder(Path pathToRoot) {
        return new Configuration().new ConfigurationBuilder(pathToRoot);
    }

    private File getMDOPath(MDOType type, String name) {
        if (configurationSource == ConfigurationSource.EDT) {
            return Paths.get(rootPath.toAbsolutePath().toString(),
                    "src", type.getMdoClassName() + "s", name, name + ".mdo").toFile();
        } else {
            return Paths.get(rootPath.toAbsolutePath().toString(),
                    type.getMdoClassName() + "s", name + ".xml").toFile();
        }
    }

    public MDObjectBase getChild(MDOType type, String name) {
        HashMap<String, MDObjectBase> childrenByType = children.get(type);
        if (childrenByType != null) {
            MDObjectBase child = childrenByType.get(name);
            if(child != null) {
                return child;
            }
        }

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        MDObjectBase child = null;
        if (configurationSource == ConfigurationSource.EDT) {
            try {
                child = (MDObjectBase) xmlMapper.readValue(getMDOPath(type, name),
                        Class.forName(MDObjectBase.class.getPackageName() + "." + type.getMdoClassName()));
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.error(e.getMessage(), e);
            }
        } else if (configurationSource == ConfigurationSource.DESIGNER) {
            try {
                MetaDataObject metaDataObject = xmlMapper.readValue(getMDOPath(type, name), MetaDataObject.class);
                child = metaDataObject.getPropertyByName(type.getMdoClassName());
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        } else {
            return null;
        }

        if (child != null) {
            addChild(type, name, child);
        }
        return child;
    }

    private void addChild(MDOType type, String name, MDObjectBase child) {
        HashMap<String, MDObjectBase> childrenByType = children.get(type);
        if (childrenByType == null) {
            childrenByType = new HashMap<>();
        }
        childrenByType.put(name, child);
        children.put(type, childrenByType);
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
                    || pathToConfig == null
                    || !pathToConfig.toFile().exists()) {
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
