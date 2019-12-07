

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum AutoShowClearButtonMode {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("Always")
    ALWAYS("Always"),
    @JsonProperty("FilledOnly")
    FILLED_ONLY("FilledOnly");
    private final String value;

    AutoShowClearButtonMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AutoShowClearButtonMode fromValue(String v) {
        for (AutoShowClearButtonMode c: AutoShowClearButtonMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
