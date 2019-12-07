

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum SearchControlLocation {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("None")
    NONE("None"),
    @JsonProperty("CommandBar")
    COMMAND_BAR("CommandBar");
    private final String value;

    SearchControlLocation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SearchControlLocation fromValue(String v) {
        for (SearchControlLocation c: SearchControlLocation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
