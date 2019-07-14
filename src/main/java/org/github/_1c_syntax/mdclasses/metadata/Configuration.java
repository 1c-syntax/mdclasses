package org.github._1c_syntax.mdclasses.metadata;

import lombok.Getter;
import lombok.Setter;
import org.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import org.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import org.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;

public class Configuration {

    @Getter @Setter
    private ConfigurationSource configurationSource;

    @Getter @Setter
    private CompatibilityMode compatibilityMode;

    @Getter @Setter
    private ScriptVariant scriptVariant;

    public Configuration(ConfigurationSource configurationSource){
        this.configurationSource = configurationSource;
    }

}
