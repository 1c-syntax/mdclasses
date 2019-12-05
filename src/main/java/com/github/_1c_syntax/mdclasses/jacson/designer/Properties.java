package com.github._1c_syntax.mdclasses.jacson.designer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Properties {
    @JsonProperty("Name")
    public String name;

    @JsonProperty("CompatibilityMode")
    public String compatibilityMode;
    @JsonProperty("ScriptVariant")
    public String scriptVariant;
}
