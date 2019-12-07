

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum UseOutput {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("Enable")
    ENABLE("Enable"),
    @JsonProperty("Disable")
    DISABLE("Disable");
    private final String value;

    UseOutput(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UseOutput fromValue(String v) {
        for (UseOutput c: UseOutput.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
