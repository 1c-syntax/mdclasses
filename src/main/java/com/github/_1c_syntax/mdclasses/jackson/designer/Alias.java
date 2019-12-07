

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum Alias {

    @JsonProperty("en")
    EN("en"),
    @JsonProperty("ru")
    RU("ru");
    private final String value;

    Alias(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Alias fromValue(String v) {
        for (Alias c: Alias.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
