

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FormElementCommandBarLocation {

    @JsonProperty("None")
    NONE("None"),
    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("Top")
    TOP("Top"),
    @JsonProperty("Bottom")
    BOTTOM("Bottom");
    private final String value;

    FormElementCommandBarLocation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FormElementCommandBarLocation fromValue(String v) {
        for (FormElementCommandBarLocation c: FormElementCommandBarLocation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
