package com.github._1c_syntax.mdclasses.mdo.classes;

import com.github._1c_syntax.mdclasses.mdo.core.AbstractMDOSimple;
import com.github._1c_syntax.mdclasses.mdo.core.ConfigurationSource;
import com.github._1c_syntax.mdclasses.mdo.core.MDOType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
@EqualsAndHashCode(callSuper=false)
public class Configuration extends AbstractMDOSimple {

    private final Path rootPath;

    public Configuration(Path rootPath) {
        super(MDOType.CONFIGURATION);
        this.rootPath = rootPath;

        configurationSource = ConfigurationSource.EMPTY;

        String rootPathString = rootPath.toAbsolutePath().toString();

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
            mdoPath = rootConfiguration.toPath();
        }
    }

    @Override
    public void initialize() {
        if(configurationSource == ConfigurationSource.EDT) {

        } else if(configurationSource == ConfigurationSource.DESIGNER) {

        } else {

        }

    }
}
