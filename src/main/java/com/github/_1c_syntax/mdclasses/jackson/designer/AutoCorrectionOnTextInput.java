

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum AutoCorrectionOnTextInput {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("Use")
    USE("Use"),
    @JsonProperty("DontUse")
    DONT_USE("DontUse");
    private final String value;

    AutoCorrectionOnTextInput(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AutoCorrectionOnTextInput fromValue(String v) {
        for (AutoCorrectionOnTextInput c : AutoCorrectionOnTextInput.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
