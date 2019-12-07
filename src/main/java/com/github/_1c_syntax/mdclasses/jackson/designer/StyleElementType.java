

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StyleElementType {

    @JsonProperty("Color")
    COLOR("Color"),
    @JsonProperty("Font")
    FONT("Font"),
    @JsonProperty("Border")
    BORDER("Border");
    private final String value;

    StyleElementType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StyleElementType fromValue(String v) {
        for (StyleElementType c: StyleElementType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
