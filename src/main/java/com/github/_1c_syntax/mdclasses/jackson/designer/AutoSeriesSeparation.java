

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum AutoSeriesSeparation {

    @JsonProperty("None")
    NONE("None"),
    @JsonProperty("All")
    ALL("All"),
    @JsonProperty("Maximum")
    MAXIMUM("Maximum"),
    @JsonProperty("Minimum")
    MINIMUM("Minimum");
    private final String value;

    AutoSeriesSeparation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AutoSeriesSeparation fromValue(String v) {
        for (AutoSeriesSeparation c : AutoSeriesSeparation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
