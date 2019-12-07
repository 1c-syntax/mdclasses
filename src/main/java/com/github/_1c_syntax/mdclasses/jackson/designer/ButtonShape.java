


package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ButtonShape {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("Usual")
    USUAL("Usual"),
    @JsonProperty("Oval")
    OVAL("Oval");
    private final String value;

    ButtonShape(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ButtonShape fromValue(String v) {
        for (ButtonShape c : ButtonShape.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
