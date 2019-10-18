package com.github._1c_syntax.mdclasses.metadata.commonmodules;

import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;

import java.io.File;
import java.nio.file.Path;

public class EmptyCommonModule extends AbstractCommonModule {
    public EmptyCommonModule() {
        super(null, ConfigurationSource.EMPTY);
    }

    @Override
    public void initialize(File xml) {

    }
}
