


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ChartColorPalette {

    @JsonProperty("Palette8")
    PALETTE_8("Palette8"),
    @JsonProperty("Palette32")
    PALETTE_32("Palette32"),
    @JsonProperty("Pastel")
    PASTEL("Pastel"),
    @JsonProperty("Bright")
    BRIGHT("Bright"),
    @JsonProperty("Soft")
    SOFT("Soft"),
    @JsonProperty("Warm")
    WARM("Warm"),
    @JsonProperty("Cold")
    COLD("Cold"),
    @JsonProperty("Blue")
    BLUE("Blue"),
    @JsonProperty("Orange")
    ORANGE("Orange"),
    @JsonProperty("Green")
    GREEN("Green"),
    @JsonProperty("Yellow")
    YELLOW("Yellow"),
    @JsonProperty("Gray")
    GRAY("Gray"),
    @JsonProperty("Custom")
    CUSTOM("Custom"),
    @JsonProperty("Gradient")
    GRADIENT("Gradient"),
    @JsonProperty("Auto")
    AUTO("Auto");
    private final String value;

    ChartColorPalette(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ChartColorPalette fromValue(String v) {
        for (ChartColorPalette c: ChartColorPalette.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
