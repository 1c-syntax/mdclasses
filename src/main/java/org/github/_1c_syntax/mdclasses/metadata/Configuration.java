package org.github._1c_syntax.mdclasses.metadata;

import org.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import org.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import org.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;

public class Configuration {

    private ConfigurationSource configurationSource;
    private CompatibilityMode compatibilityMode;
    private ScriptVariant scriptVariant;

    public Configuration(ConfigurationSource configurationSource){
        this.configurationSource = configurationSource;
    }

    public ConfigurationSource getConfigurationSource(){
        return configurationSource;
    }

    public void setCompatibilityMode(CompatibilityMode value){
        compatibilityMode = value;
    }

    public CompatibilityMode getCompatibilityMode(){
        return compatibilityMode;
    }

    public void setScriptVariant(ScriptVariant value){
        scriptVariant = value;
    }

    public ScriptVariant getScriptVariant(){
        return scriptVariant;
    }


}
