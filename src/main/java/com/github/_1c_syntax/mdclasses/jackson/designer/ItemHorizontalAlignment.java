


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ItemHorizontalAlignment {

    @JsonProperty("Left")
    LEFT("Left"),
    @JsonProperty("Center")
    CENTER("Center"),
    @JsonProperty("Right")
    RIGHT("Right"),
    @JsonProperty("Auto")
    AUTO("Auto");
    private final String value;

    ItemHorizontalAlignment(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ItemHorizontalAlignment fromValue(String v) {
        for (ItemHorizontalAlignment c: ItemHorizontalAlignment.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
