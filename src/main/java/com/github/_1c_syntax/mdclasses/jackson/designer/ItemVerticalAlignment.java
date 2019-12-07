

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ItemVerticalAlignment {

    @JsonProperty("Top")
    TOP("Top"),
    @JsonProperty("Center")
    CENTER("Center"),
    @JsonProperty("Bottom")
    BOTTOM("Bottom"),
    @JsonProperty("Auto")
    AUTO("Auto");
    private final String value;

    ItemVerticalAlignment(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ItemVerticalAlignment fromValue(String v) {
        for (ItemVerticalAlignment c : ItemVerticalAlignment.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
