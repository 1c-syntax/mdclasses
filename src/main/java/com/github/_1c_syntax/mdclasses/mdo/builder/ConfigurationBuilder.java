package com.github._1c_syntax.mdclasses.mdo.builder;

import com.github._1c_syntax.mdclasses.mdo.classes.Configuration;
import com.github._1c_syntax.mdclasses.mdo.core.AbstractMDO;
import com.github._1c_syntax.mdclasses.mdo.core.ConfigurationSource;
import com.github._1c_syntax.mdclasses.mdo.core.MDOType;
import com.github._1c_syntax.mdclasses.mdo.utils.MDO;

import java.nio.file.Path;

public class ConfigurationBuilder {

    private final Path rootPath;
    private Configuration configuration;
    private MDOBuilder mdoBuilder;

    public ConfigurationBuilder(Path rootPath) {
        this.rootPath = rootPath;
        ConfigurationSource configurationSource = MDO.getConfigurationSourceByPath(rootPath.toAbsolutePath().toString());
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
                configuration = (Configuration) mdoBuilder.build(MDOType.CONFIGURATION, rootPath.toAbsolutePath().toString(), "");
            } else {
                configuration = new Configuration();
            }
        }
        return configuration;
    }

    public AbstractMDO getChildMDO(MDOType type, String name) {
        if (configuration == null) {
            getConfiguration();
        }
        AbstractMDO child = configuration.getChild(type, name);
        if(child == null) {
            configuration.addChild(type, name, mdoBuilder.build(type, rootPath.toAbsolutePath().toString(), name));
            child = configuration.getChild(type, name);
        }
        return child;
    }
}

