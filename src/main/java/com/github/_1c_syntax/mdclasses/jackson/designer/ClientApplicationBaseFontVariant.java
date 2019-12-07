

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ClientApplicationBaseFontVariant {

    @JsonProperty("Normal")
    NORMAL("Normal"),
    @JsonProperty("Large")
    LARGE("Large");
    private final String value;

    ClientApplicationBaseFontVariant(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ClientApplicationBaseFontVariant fromValue(String v) {
        for (ClientApplicationBaseFontVariant c: ClientApplicationBaseFontVariant.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
