

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum AutoSaveFormDataInSettings {

    @JsonProperty("DontUse")
    DONT_USE("DontUse"),
    @JsonProperty("Use")
    USE("Use");
    private final String value;

    AutoSaveFormDataInSettings(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AutoSaveFormDataInSettings fromValue(String v) {
        for (AutoSaveFormDataInSettings c: AutoSaveFormDataInSettings.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
