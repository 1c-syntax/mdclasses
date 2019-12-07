

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum AutoColor {

    @JsonProperty("auto")
    AUTO("auto");
    private final String value;

    AutoColor(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AutoColor fromValue(String v) {
        for (AutoColor c : AutoColor.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
