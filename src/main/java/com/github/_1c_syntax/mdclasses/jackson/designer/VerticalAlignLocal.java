

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum VerticalAlignLocal {

    @JsonProperty("Top")
    TOP("Top"),
    @JsonProperty("Bottom")
    BOTTOM("Bottom"),
    @JsonProperty("Center")
    CENTER("Center");
    private final String value;

    VerticalAlignLocal(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VerticalAlignLocal fromValue(String v) {
        for (VerticalAlignLocal c: VerticalAlignLocal.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
