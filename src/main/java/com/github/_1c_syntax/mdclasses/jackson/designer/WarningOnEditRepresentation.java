

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum WarningOnEditRepresentation {

    @JsonProperty("Show")
    SHOW("Show"),
    @JsonProperty("DontShow")
    DONT_SHOW("DontShow"),
    @JsonProperty("Auto")
    AUTO("Auto");
    private final String value;

    WarningOnEditRepresentation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static WarningOnEditRepresentation fromValue(String v) {
        for (WarningOnEditRepresentation c: WarningOnEditRepresentation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
