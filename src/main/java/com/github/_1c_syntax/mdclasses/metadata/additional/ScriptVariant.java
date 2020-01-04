package com.github._1c_syntax.mdclasses.metadata.additional;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum ScriptVariant {
    @JsonProperty("English")
    ENGLISH("English"),
    @JsonProperty("Russian")
    RUSSIAN("Russian");

    private final String value;

    ScriptVariant(String v) {
        value = v;
    }

    public static ScriptVariant fromValue(String v) {
        return valueOf(v.toUpperCase());
    }

    public String value() {
        return value;
    }

}
