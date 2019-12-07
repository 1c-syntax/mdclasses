

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SearchStringModeOnInputByString {

    @JsonProperty("Begin")
    BEGIN("Begin"),
    @JsonProperty("AnyPart")
    ANY_PART("AnyPart");
    private final String value;

    SearchStringModeOnInputByString(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SearchStringModeOnInputByString fromValue(String v) {
        for (SearchStringModeOnInputByString c: SearchStringModeOnInputByString.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
