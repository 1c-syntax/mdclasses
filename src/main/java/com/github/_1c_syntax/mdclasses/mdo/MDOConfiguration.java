package com.github._1c_syntax.mdclasses.mdo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import lombok.Getter;

import java.util.Map;


@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "Configuration")
public class MDOConfiguration
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
    protected void unpackProperties(Map<String, Object> properties) {
        this.scriptVariant = ScriptVariant.fromValue((String) properties.getOrDefault("ScriptVariant", ScriptVariant.ENGLISH.value()));
        this.compatibilityMode = new CompatibilityMode((String) properties.getOrDefault("CompatibilityMode", ""));
        this.configurationExtensionCompatibilityMode = new CompatibilityMode((String) properties.getOrDefault("ConfigurationExtensionCompatibilityMode", ""));
        this.defaultRunMode = (String) properties.getOrDefault("DefaultRunMode", "");
        this.defaultLanguage = (String) properties.getOrDefault("DefaultLanguage", "");
        this.dataLockControlMode = (String) properties.getOrDefault("DataLockControlMode", "");
        this.objectAutonumerationMode = (String) properties.getOrDefault("ObjectAutonumerationMode", "");
        this.modalityUseMode = (String) properties.getOrDefault("ModalityUseMode", "");
        this.synchronousPlatformExtensionAndAddInCallUseMode = (String) properties.getOrDefault("SynchronousPlatformExtensionAndAddInCallUseMode", "");
        this.name = (String) properties.getOrDefault("Name", "");
        this.comment = (String) properties.getOrDefault("Comment", "");
    }

}
