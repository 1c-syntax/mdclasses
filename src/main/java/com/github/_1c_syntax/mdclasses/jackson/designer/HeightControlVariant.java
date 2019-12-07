

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum HeightControlVariant {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("UseHeightInFormRows")
    USE_HEIGHT_IN_FORM_ROWS("UseHeightInFormRows"),
    @JsonProperty("UseContentHeight")
    USE_CONTENT_HEIGHT("UseContentHeight");
    private final String value;

    HeightControlVariant(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HeightControlVariant fromValue(String v) {
        for (HeightControlVariant c: HeightControlVariant.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
