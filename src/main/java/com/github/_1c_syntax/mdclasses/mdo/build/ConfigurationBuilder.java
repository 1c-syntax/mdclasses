package com.github._1c_syntax.mdclasses.mdo.build;

import com.github._1c_syntax.mdclasses.mdo.classes.Configuration;
import com.github._1c_syntax.mdclasses.mdo.core.ConfigurationSource;
import com.github._1c_syntax.mdclasses.mdo.core.MDOType;
import com.github._1c_syntax.mdclasses.mdo.utils.MDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;

public class ConfigurationBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationBuilder.class.getSimpleName());

    private final Path rootPath;
    private ConfigurationSource configurationSource;
    private Configuration configuration;
    private MDOBuilder mdoBuilder;

    public ConfigurationBuilder(Path rootPath) {
        this.rootPath = rootPath;
        this.configurationSource = MDO.getConfigurationSourceByPath(rootPath.toAbsolutePath().toString());
        if (configurationSource == ConfigurationSource.DESIGNER) {
            mdoBuilder = new DesignerBuilder();
        } else if (configurationSource == ConfigurationSource.EDT) {
            mdoBuilder = new EDTBuilder();
        } else {
            mdoBuilder = null;
        }
    }

    public Configuration getConfiguration() {
        if (configuration == null) {
            if (mdoBuilder != null) {
                try {
                    configuration = (Configuration) mdoBuilder.build(MDOType.CONFIGURATION, rootPath.toAbsolutePath().toString());
                } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            } else {
                configuration = new Configuration();
            }
        }
        return configuration;
    }
}

