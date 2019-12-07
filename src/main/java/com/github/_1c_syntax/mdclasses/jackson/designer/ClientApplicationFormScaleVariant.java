


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ClientApplicationFormScaleVariant {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("Normal")
    NORMAL("Normal"),
    @JsonProperty("Compact")
    COMPACT("Compact");
    private final String value;

    ClientApplicationFormScaleVariant(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ClientApplicationFormScaleVariant fromValue(String v) {
        for (ClientApplicationFormScaleVariant c: ClientApplicationFormScaleVariant.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
