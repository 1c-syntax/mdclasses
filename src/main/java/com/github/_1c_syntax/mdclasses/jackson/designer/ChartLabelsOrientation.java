

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ChartLabelsOrientation {

    @JsonProperty("Horizontal")
    HORIZONTAL("Horizontal"),
    @JsonProperty("Vertical")
    VERTICAL("Vertical"),
    @JsonProperty("Auto")
    AUTO("Auto");
    private final String value;

    ChartLabelsOrientation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ChartLabelsOrientation fromValue(String v) {
        for (ChartLabelsOrientation c: ChartLabelsOrientation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
