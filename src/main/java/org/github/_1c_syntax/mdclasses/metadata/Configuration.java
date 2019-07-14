package org.github._1c_syntax.mdclasses.metadata;

import org.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import org.github._1c_syntax.mdclasses.metadata.additional.ConfigurationVersion;
import org.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;

public class Configuration {

    private ConfigurationSource configurationSource;
    private ConfigurationVersion compatibilityMode;
    private ScriptVariant scriptVariant;

    public Configuration(ConfigurationSource configurationSource){
        this.configurationSource = configurationSource;
    }

    public ConfigurationSource getConfigurationSource(){
        return configurationSource;
    }

    public void setCompatibilityMode(ConfigurationVersion value){
        compatibilityMode = value;
    }

    public ConfigurationVersion getCompatibilityMode(){
        return compatibilityMode;
    }

    public void setScriptVariant(ScriptVariant value){
        scriptVariant = value;
    }

    public ScriptVariant getScriptVariant(){
        return scriptVariant;
    }


}
