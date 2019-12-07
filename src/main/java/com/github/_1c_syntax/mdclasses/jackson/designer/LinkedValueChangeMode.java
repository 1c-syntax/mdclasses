

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum LinkedValueChangeMode {

    @JsonProperty("Clear")
    CLEAR("Clear"),
    @JsonProperty("DontChange")
    DONT_CHANGE("DontChange");
    private final String value;

    LinkedValueChangeMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LinkedValueChangeMode fromValue(String v) {
        for (LinkedValueChangeMode c: LinkedValueChangeMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
