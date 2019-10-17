package com.github._1c_syntax.mdclasses.metadata.commonmodules;

import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;

import java.nio.file.Path;

public class EDTCommonModule extends AbstractCommonModule {
    public EDTCommonModule(Path path) {
        super(path, ConfigurationSource.EDT);
    }
}
