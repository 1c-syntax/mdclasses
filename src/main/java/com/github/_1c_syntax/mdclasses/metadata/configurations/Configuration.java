package com.github._1c_syntax.mdclasses.metadata.configurations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github._1c_syntax.mdclasses.mdosource.common.MDObjectBase;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import com.github._1c_syntax.mdclasses.metadata.utils.Common;
import lombok.Getter;

import java.net.URI;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Configuration
        extends MDObjectBase {

    private ConfigurationSource configurationSource = ConfigurationSource.EDT;

    private ScriptVariant scriptVariant = ScriptVariant.ENGLISH;
    private CompatibilityMode compatibilityMode;
    private CompatibilityMode configurationExtensionCompatibilityMode;

    private String defaultRunMode;
    private String defaultLanguage;
    private String dataLockControlMode;
    private String objectAutonumerationMode;
    private String modalityUseMode;
    private String synchronousPlatformExtensionAndAddInCallUseMode;

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

        this.configurationSource = ConfigurationSource.DESIGNER;
    }

    private Map<URI, ModuleType> modulesByType = new HashMap<>();

    public ModuleType getModuleType(URI uri) {
        return modulesByType.getOrDefault(uri, ModuleType.Unknown);
    }

    public void setModulesByType(Path rootPath) {
        modulesByType = Common.getModuleTypesByPath(rootPath);
    }

    public void buildEmptyConfiguration() {
        this.configurationSource = ConfigurationSource.EMPTY;
    }
}
