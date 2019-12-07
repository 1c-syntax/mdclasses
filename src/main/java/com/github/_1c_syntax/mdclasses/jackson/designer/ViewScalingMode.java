

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ViewScalingMode {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("Normal")
    NORMAL("Normal"),
    @JsonProperty("Large")
    LARGE("Large");
    private final String value;

    ViewScalingMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ViewScalingMode fromValue(String v) {
        for (ViewScalingMode c: ViewScalingMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
