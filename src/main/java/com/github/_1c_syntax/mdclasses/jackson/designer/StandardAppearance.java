

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StandardAppearance {

    @JsonProperty("None")
    NONE("None"),
    @JsonProperty("Stone")
    STONE("Stone"),
    @JsonProperty("Classic")
    CLASSIC("Classic"),
    @JsonProperty("Classic2")
    CLASSIC_2("Classic2"),
    @JsonProperty("Classic3")
    CLASSIC_3("Classic3"),
    @JsonProperty("Spring")
    SPRING("Spring"),
    @JsonProperty("Summer")
    SUMMER("Summer"),
    @JsonProperty("Autumn")
    AUTUMN("Autumn"),
    @JsonProperty("Winter")
    WINTER("Winter"),
    @JsonProperty("Asphalt")
    ASPHALT("Asphalt"),
    @JsonProperty("Copper")
    COPPER("Copper"),
    @JsonProperty("Bronze")
    BRONZE("Bronze"),
    @JsonProperty("Platinum")
    PLATINUM("Platinum"),
    @JsonProperty("Silver")
    SILVER("Silver"),
    @JsonProperty("Turquoise")
    TURQUOISE("Turquoise"),
    @JsonProperty("Sand")
    SAND("Sand"),
    @JsonProperty("Grass")
    GRASS("Grass"),
    @JsonProperty("Ice")
    ICE("Ice"),
    @JsonProperty("Orange")
    ORANGE("Orange"),
    @JsonProperty("Textile")
    TEXTILE("Textile"),
    @JsonProperty("Wood")
    WOOD("Wood"),
    @JsonProperty("Interface")
    INTERFACE("Interface");
    private final String value;

    StandardAppearance(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StandardAppearance fromValue(String v) {
        for (StandardAppearance c: StandardAppearance.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
