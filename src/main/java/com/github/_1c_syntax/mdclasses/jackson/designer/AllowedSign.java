

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum AllowedSign {

    @JsonProperty("Any")
    ANY("Any"),
    @JsonProperty("Nonnegative")
    NONNEGATIVE("Nonnegative");
    private final String value;

    AllowedSign(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AllowedSign fromValue(String v) {
        for (AllowedSign c: AllowedSign.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
