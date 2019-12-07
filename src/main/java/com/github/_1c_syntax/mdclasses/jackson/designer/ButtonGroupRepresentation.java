

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ButtonGroupRepresentation {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("Usual")
    USUAL("Usual"),
    @JsonProperty("Compact")
    COMPACT("Compact");
    private final String value;

    ButtonGroupRepresentation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ButtonGroupRepresentation fromValue(String v) {
        for (ButtonGroupRepresentation c: ButtonGroupRepresentation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
