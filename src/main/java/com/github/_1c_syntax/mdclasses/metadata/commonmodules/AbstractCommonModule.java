package com.github._1c_syntax.mdclasses.metadata.commonmodules;

import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.ReturnValueReuse;
import com.github._1c_syntax.mdclasses.metadata.utils.Common;
import lombok.Data;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;

@Data
public abstract class AbstractCommonModule {
    protected final Path path;
    protected Path metadataPath;
    protected final ConfigurationSource configurationSource;
    protected boolean server;
    protected boolean global;
    protected boolean clientManagedApplication;
    protected boolean externalConnection;
    protected boolean clientOrdinaryApplication;
    protected boolean serverCall;

    protected ReturnValueReuse returnValuesReuse;
    protected boolean privileged;

    protected AbstractCommonModule(Path path, ConfigurationSource configurationSource) {
        this.path = path;
        this.returnValuesReuse = ReturnValueReuse.NONE;
        this.configurationSource = configurationSource;
    }
}
