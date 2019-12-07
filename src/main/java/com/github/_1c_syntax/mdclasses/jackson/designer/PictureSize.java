


package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PictureSize {

    @JsonProperty("RealSize")
    REAL_SIZE("RealSize"),
    @JsonProperty("Stretch")
    STRETCH("Stretch"),
    @JsonProperty("Proportionally")
    PROPORTIONALLY("Proportionally"),
    @JsonProperty("Tile")
    TILE("Tile"),
    @JsonProperty("AutoSize")
    AUTO_SIZE("AutoSize"),
    @JsonProperty("RealSizeIgnoreScale")
    REAL_SIZE_IGNORE_SCALE("RealSizeIgnoreScale"),
    @JsonProperty("AutoSizeIgnoreScale")
    AUTO_SIZE_IGNORE_SCALE("AutoSizeIgnoreScale");
    private final String value;

    PictureSize(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PictureSize fromValue(String v) {
        for (PictureSize c: PictureSize.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
