

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ChartOfCalculationTypesCodeType {

    @JsonProperty("Number")
    NUMBER("Number"),
    @JsonProperty("String")
    STRING("String");
    private final String value;

    ChartOfCalculationTypesCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ChartOfCalculationTypesCodeType fromValue(String v) {
        for (ChartOfCalculationTypesCodeType c : ChartOfCalculationTypesCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
