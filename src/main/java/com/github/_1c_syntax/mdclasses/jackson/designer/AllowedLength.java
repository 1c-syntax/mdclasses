

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum AllowedLength {

    @JsonProperty("Fixed")
    FIXED("Fixed"),
    @JsonProperty("Variable")
    VARIABLE("Variable");
    private final String value;

    AllowedLength(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AllowedLength fromValue(String v) {
        for (AllowedLength c: AllowedLength.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
