

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FormElementTitleLocation {

    @JsonProperty("None")
    NONE("None"),
    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("Left")
    LEFT("Left"),
    @JsonProperty("Top")
    TOP("Top"),
    @JsonProperty("Right")
    RIGHT("Right"),
    @JsonProperty("Bottom")
    BOTTOM("Bottom");
    private final String value;

    FormElementTitleLocation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FormElementTitleLocation fromValue(String v) {
        for (FormElementTitleLocation c: FormElementTitleLocation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
