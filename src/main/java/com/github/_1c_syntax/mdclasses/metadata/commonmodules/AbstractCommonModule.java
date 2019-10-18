package com.github._1c_syntax.mdclasses.metadata.commonmodules;

import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.ReturnValueReuse;
import lombok.Data;

import java.io.File;
import java.nio.file.Path;

@Data
public abstract class AbstractCommonModule {
    protected final Path path;
    protected final ConfigurationSource configurationSource;
    protected boolean server;
    protected boolean global;
    protected boolean clientManagedApplication;
    protected boolean externalConnection;
    protected boolean clientOrdinaryApplication;
    protected boolean serverCall;

    protected ReturnValueReuse returnValuesReuse;
    protected boolean privileged;

    protected String name;
    protected String uuid;
    protected String comment;

    // TODO синонимы надо добавить, мапа язык - значение

    protected AbstractCommonModule(Path path, ConfigurationSource configurationSource) {
        this.path = path;
        this.returnValuesReuse = ReturnValueReuse.DONT_USE;
        this.configurationSource = configurationSource;
    }

    public abstract void initialize(File xml);
}
