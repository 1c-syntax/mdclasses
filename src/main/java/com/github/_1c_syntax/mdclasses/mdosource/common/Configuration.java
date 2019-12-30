package com.github._1c_syntax.mdclasses.mdosource.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import lombok.Getter;

import java.util.Map;


@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Configuration
        extends MDObjectBase {

    protected ScriptVariant scriptVariant = ScriptVariant.ENGLISH;
    protected CompatibilityMode compatibilityMode;
    protected CompatibilityMode configurationExtensionCompatibilityMode;

    protected String defaultRunMode;
    protected String defaultLanguage;
    protected String dataLockControlMode;
    protected String objectAutonumerationMode;
    protected String modalityUseMode;
    protected String synchronousPlatformExtensionAndAddInCallUseMode;

    @JsonProperty("Properties")
    private void unpackProperties(Map<String, Object> properties) {
        this.scriptVariant = ScriptVariant.fromValue((String) properties.get("ScriptVariant"));
        this.compatibilityMode = new CompatibilityMode((String) properties.get("CompatibilityMode"));
        this.configurationExtensionCompatibilityMode = new CompatibilityMode((String) properties.get("ConfigurationExtensionCompatibilityMode"));
        this.defaultRunMode = (String) properties.get("DefaultRunMode");
        this.defaultLanguage = (String) properties.get("DefaultLanguage");
        this.dataLockControlMode = (String) properties.get("DataLockControlMode");
        this.objectAutonumerationMode = (String) properties.get("ObjectAutonumerationMode");
        this.modalityUseMode = (String) properties.get("ModalityUseMode");
        this.synchronousPlatformExtensionAndAddInCallUseMode = (String) properties.get("SynchronousPlatformExtensionAndAddInCallUseMode");
    }

}
