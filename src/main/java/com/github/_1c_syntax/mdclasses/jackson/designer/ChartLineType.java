

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ChartLineType {

    @JsonProperty("None")
    NONE("None"),
    @JsonProperty("Solid")
    SOLID("Solid"),
    @JsonProperty("Dotted")
    DOTTED("Dotted"),
    @JsonProperty("Dashed")
    DASHED("Dashed"),
    @JsonProperty("DashDotted")
    DASH_DOTTED("DashDotted"),
    @JsonProperty("DashDottedDotted")
    DASH_DOTTED_DOTTED("DashDottedDotted");
    private final String value;

    ChartLineType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ChartLineType fromValue(String v) {
        for (ChartLineType c: ChartLineType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
