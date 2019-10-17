package com.github._1c_syntax.mdclasses.metadata.commonmodules;

import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;

import java.nio.file.Path;

public class DesignCommonModule extends AbstractCommonModule {
    public DesignCommonModule(Path path) {
        super(path, ConfigurationSource.DESIGNER);
    }
}
