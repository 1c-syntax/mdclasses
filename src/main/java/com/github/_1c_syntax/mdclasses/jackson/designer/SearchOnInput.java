

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum SearchOnInput {

    @JsonProperty("Use")
    USE("Use"),
    @JsonProperty("DontUse")
    DONT_USE("DontUse"),
    @JsonProperty("Auto")
    AUTO("Auto");
    private final String value;

    SearchOnInput(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SearchOnInput fromValue(String v) {
        for (SearchOnInput c: SearchOnInput.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
