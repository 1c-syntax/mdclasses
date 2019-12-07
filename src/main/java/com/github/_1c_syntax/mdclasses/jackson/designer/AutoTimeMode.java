

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;


public enum AutoTimeMode {

    @JsonProperty("DontUse")
    DONT_USE("DontUse"),
    @JsonProperty("Last")
    LAST("Last"),
    @JsonProperty("First")
    FIRST("First"),
    @JsonProperty("CurrentOrLast")
    CURRENT_OR_LAST("CurrentOrLast"),
    @JsonProperty("CurrentOrFirst")
    CURRENT_OR_FIRST("CurrentOrFirst");
    private final String value;

    AutoTimeMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AutoTimeMode fromValue(String v) {
        for (AutoTimeMode c : AutoTimeMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
