

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum CalculationTypeMainPresentation {

    @JsonProperty("AsCode")
    AS_CODE("AsCode"),
    @JsonProperty("AsDescription")
    AS_DESCRIPTION("AsDescription");
    private final String value;

    CalculationTypeMainPresentation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CalculationTypeMainPresentation fromValue(String v) {
        for (CalculationTypeMainPresentation c : CalculationTypeMainPresentation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
