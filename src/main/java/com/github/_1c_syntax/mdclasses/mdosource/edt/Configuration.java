package com.github._1c_syntax.mdclasses.mdosource.edt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import com.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;
import lombok.Getter;

import java.util.List;


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

}
