

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ViewStatusLocation {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("None")
    NONE("None"),
    @JsonProperty("Top")
    TOP("Top"),
    @JsonProperty("Bottom")
    BOTTOM("Bottom");
    private final String value;

    ViewStatusLocation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ViewStatusLocation fromValue(String v) {
        for (ViewStatusLocation c: ViewStatusLocation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
