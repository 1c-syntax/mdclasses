

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum FillChecking {

    @JsonProperty("DontCheck")
    DONT_CHECK("DontCheck"),
    @JsonProperty("ShowError")
    SHOW_ERROR("ShowError");
    private final String value;

    FillChecking(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FillChecking fromValue(String v) {
        for (FillChecking c: FillChecking.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
