


package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FormButtonPictureLocation {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("Left")
    LEFT("Left"),
    @JsonProperty("Right")
    RIGHT("Right");
    private final String value;

    FormButtonPictureLocation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FormButtonPictureLocation fromValue(String v) {
        for (FormButtonPictureLocation c: FormButtonPictureLocation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
