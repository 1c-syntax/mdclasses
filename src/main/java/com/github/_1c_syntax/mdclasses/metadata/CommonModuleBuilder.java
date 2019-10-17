package com.github._1c_syntax.mdclasses.metadata;

import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.ReturnValueReuse;
import com.github._1c_syntax.mdclasses.metadata.commonmodules.AbstractCommonModule;
import com.github._1c_syntax.mdclasses.metadata.commonmodules.EmptyCommonModule;
import com.github._1c_syntax.mdclasses.metadata.utils.Common;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class CommonModuleBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonModuleBuilder.class.getSimpleName());

    private AbstractCommonModule commonModule = new EmptyCommonModule();
    private ConfigurationSource configurationSource;
    private final Path path;

    public CommonModuleBuilder(Path path) {
        this.path = path;
    }

    public AbstractCommonModule build() {
        ConfigurationSource configurationSrc = ConfigurationSource.EMPTY;
        Path mdoPath = null;

        String[] elementsPath = path.toString().split(Common.FILE_SEPARATOR);
        if (elementsPath.length > 2) {
            String secondFileName = elementsPath[elementsPath.length - 2];
            String fileName = FilenameUtils.getBaseName(elementsPath[elementsPath.length - 1]);
            ModuleType moduleType = Common.changeModuleTypeByFileName(fileName, secondFileName);
            if (moduleType == ModuleType.CommonModule) {
                if (secondFileName.equalsIgnoreCase("Ext")) {
                    configurationSrc = ConfigurationSource.DESIGNER;
                    String mdoFileName = elementsPath[elementsPath.length - 3] + ".mdo";
                    elementsPath.
                    mdoPath = ;
                } else {
                    configurationSrc = ConfigurationSource.EDT;
                }
            }
        }

        this.configurationSource = configurationSrc;
    }
}
