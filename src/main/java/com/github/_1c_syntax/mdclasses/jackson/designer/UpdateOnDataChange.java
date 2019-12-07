

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum UpdateOnDataChange {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("DontUpdate")
    DONT_UPDATE("DontUpdate");
    private final String value;

    UpdateOnDataChange(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UpdateOnDataChange fromValue(String v) {
        for (UpdateOnDataChange c: UpdateOnDataChange.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
