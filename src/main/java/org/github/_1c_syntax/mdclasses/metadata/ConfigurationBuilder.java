package org.github._1c_syntax.mdclasses.metadata;

import org.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import org.github._1c_syntax.mdclasses.metadata.configurations.AbstractConfiguration;
import org.github._1c_syntax.mdclasses.metadata.configurations.DesignConfiguration;
import org.github._1c_syntax.mdclasses.metadata.configurations.EDTConfiguration;
import org.github._1c_syntax.mdclasses.metadata.configurations.EmptyConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigurationBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationBuilder.class.getSimpleName());

    private ConfigurationSource configurationSource;
    private AbstractConfiguration configuration = new EmptyConfiguration();

    private final Path pathToRoot;
    private Path pathToConfig;

    public ConfigurationBuilder(Path pathToRoot) {

        this.pathToRoot = pathToRoot;

        configurationSource = ConfigurationSource.EMPTY;

        String rootPathString = pathToRoot.toAbsolutePath().toString();

        File rootConfiguration = new File(rootPathString, "Configuration.xml");
        if (rootConfiguration.exists()) {
            configurationSource = ConfigurationSource.DESIGNER;
        }
        else {
            rootConfiguration = Paths.get(rootPathString, "src", "Configuration", "Configuration.mdo").toFile();
            if (rootConfiguration.exists()) {
                configurationSource = ConfigurationSource.EDT;
            }
        }
        if (configurationSource != ConfigurationSource.EMPTY) {
            pathToConfig = rootConfiguration.toPath();
        }
    }

    public AbstractConfiguration build() {

        if (configurationSource == ConfigurationSource.EMPTY) {
            return configuration;
        }

        if (pathToConfig != null && !pathToConfig.toFile().exists()) {
            return configuration;
        }

        if (configurationSource == ConfigurationSource.DESIGNER) {
            configuration = new DesignConfiguration(configurationSource, pathToRoot);
        }
        else if (configurationSource == ConfigurationSource.EDT) {
            configuration = new EDTConfiguration(configurationSource, pathToRoot);
        }
        else {
            LOGGER.error("Тип конфигурации не поддерживается", configurationSource);
            return configuration;
        }

        configuration.initialize(pathToConfig.toFile());

        return configuration;

    }

}
