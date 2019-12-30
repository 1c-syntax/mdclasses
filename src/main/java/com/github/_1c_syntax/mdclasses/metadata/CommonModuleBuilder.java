package com.github._1c_syntax.mdclasses.metadata;

import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.commonmodules.AbstractCommonModule;
import com.github._1c_syntax.mdclasses.metadata.commonmodules.DesignCommonModule;
import com.github._1c_syntax.mdclasses.metadata.commonmodules.EDTCommonModule;
import com.github._1c_syntax.mdclasses.metadata.commonmodules.EmptyCommonModule;
import com.github._1c_syntax.mdclasses.metadata.utils.Common;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CommonModuleBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonModuleBuilder.class.getSimpleName());

    private AbstractCommonModule commonModule = new EmptyCommonModule();
    private ConfigurationSource configurationSource;
    private final Path path;
    private Path mdoPath;

    public CommonModuleBuilder(Path path) {
        this.path = path;
        ConfigurationSource configurationSrc = ConfigurationSource.EMPTY;
        Path mdoPathSrc = null;

        String[] elementsPath = path.toString().split(Common.FILE_SEPARATOR);
        if (elementsPath.length > 2) {
            String secondFileName = elementsPath[elementsPath.length - 2];
            String fileName = FilenameUtils.getBaseName(elementsPath[elementsPath.length - 1]);
            ModuleType moduleType = Common.changeModuleTypeByFileName(fileName, secondFileName);
            if (moduleType == ModuleType.CommonModule) {
                if (secondFileName.equalsIgnoreCase("Ext")) {
                    configurationSrc = ConfigurationSource.DESIGNER;
                    Path parent = path.getParent().getParent();
                    mdoPathSrc = Paths.get(parent.getParent().toString(), FilenameUtils.getBaseName(parent.toString()) + ".xml");
                } else {
                    configurationSrc = ConfigurationSource.EDT;
                    mdoPathSrc = Paths.get(path.getParent().toString(), secondFileName + ".mdo");
                }
            } else {
                LOGGER.error("Тип модуля не поддерживается %s", moduleType);
            }
        }

        this.configurationSource = configurationSrc;
        this.mdoPath = mdoPathSrc;
    }

    public AbstractCommonModule build() {
        if (configurationSource == ConfigurationSource.EMPTY) {
            return commonModule;
        }

        if (path != null && !path.toFile().exists()) {
            return commonModule;
        }

        if (configurationSource == ConfigurationSource.DESIGNER) {
            commonModule = new DesignCommonModule(path);
        } else if (configurationSource == ConfigurationSource.EDT) {
            commonModule = new EDTCommonModule(path);
        } else {
            LOGGER.error("Тип конфигурации не поддерживается", configurationSource);
            return commonModule;
        }

        commonModule.initialize(mdoPath.toFile());

        return commonModule;
    }
}
