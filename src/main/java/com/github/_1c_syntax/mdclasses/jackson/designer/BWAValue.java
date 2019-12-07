


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum BWAValue {

    @JsonProperty("true")
    TRUE("true"),
    @JsonProperty("false")
    FALSE("false"),
    @JsonProperty("auto")
    AUTO("auto");
    private final String value;

    BWAValue(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BWAValue fromValue(String v) {
        for (BWAValue c : BWAValue.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
