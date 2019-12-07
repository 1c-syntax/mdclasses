


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ChoiceDataGetModeOnInputByString {

    @JsonProperty("Directly")
    DIRECTLY("Directly"),
    @JsonProperty("Background")
    BACKGROUND("Background");
    private final String value;

    ChoiceDataGetModeOnInputByString(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ChoiceDataGetModeOnInputByString fromValue(String v) {
        for (ChoiceDataGetModeOnInputByString c: ChoiceDataGetModeOnInputByString.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
