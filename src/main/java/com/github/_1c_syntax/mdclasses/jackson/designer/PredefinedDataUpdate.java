

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PredefinedDataUpdate {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("AutoUpdate")
    AUTO_UPDATE("AutoUpdate"),
    @JsonProperty("DontAutoUpdate")
    DONT_AUTO_UPDATE("DontAutoUpdate");
    private final String value;

    PredefinedDataUpdate(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PredefinedDataUpdate fromValue(String v) {
        for (PredefinedDataUpdate c: PredefinedDataUpdate.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
