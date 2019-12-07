

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FormItemSpacing {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("None")
    NONE("None"),
    @JsonProperty("Half")
    HALF("Half"),
    @JsonProperty("Single")
    SINGLE("Single"),
    @JsonProperty("OneAndHalf")
    ONE_AND_HALF("OneAndHalf"),
    @JsonProperty("Double")
    DOUBLE("Double");
    private final String value;

    FormItemSpacing(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FormItemSpacing fromValue(String v) {
        for (FormItemSpacing c: FormItemSpacing.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
