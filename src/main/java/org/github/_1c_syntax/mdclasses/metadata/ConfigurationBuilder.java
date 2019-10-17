package org.github._1c_syntax.mdclasses.metadata;

import org.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import org.github._1c_syntax.mdclasses.metadata.configurations.AbstractConfiguration;
import org.github._1c_syntax.mdclasses.metadata.configurations.DesignConfiguration;
import org.github._1c_syntax.mdclasses.metadata.configurations.EDTConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigurationBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationBuilder.class.getSimpleName());

    private ConfigurationSource configurationSource;
    private AbstractConfiguration configuration = null;

    private final Path pathToRoot;
    private Path pathToConfig;

    public ConfigurationBuilder(Path pathToRoot) {
        this.pathToRoot = pathToRoot;

        configurationSource = null;

        File rootConfiguration = new File(pathToRoot.toAbsolutePath().toString(), "Configuration.xml");
        if (rootConfiguration.exists()) {
            configurationSource = ConfigurationSource.DESIGNER;
        }
        else {
            rootConfiguration =
                Paths.get(
                    pathToRoot.toAbsolutePath().toString(), "src", "Configuration", "Configuration.mdo")
                    .toFile();
            if (rootConfiguration.exists()) {
                configurationSource = ConfigurationSource.EDT;
            }
        }

        if (configurationSource != null) {
            pathToConfig = rootConfiguration.toPath();
        }

    }

    public AbstractConfiguration build() {

        if (configurationSource == null || !pathToConfig.toFile().exists()) {
            return configuration;
        }

        if (configurationSource == ConfigurationSource.DESIGNER) {
            configuration = new DesignConfiguration(configurationSource, pathToRoot);
        }
        else if (configurationSource == ConfigurationSource.EDT) {
            configuration = new EDTConfiguration(configurationSource, pathToRoot);
        }
        else {
            LOGGER.error("Тип конфигурации не поддерживается", configurationSource.toString());
            return configuration;
        }

        File xml = pathToConfig.toFile();
        configuration.initialize(xml);

        return configuration;

    }

}
