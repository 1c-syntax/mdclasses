

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum VerticalAlign {

    @JsonProperty("Top")
    TOP("Top"),
    @JsonProperty("Center")
    CENTER("Center"),
    @JsonProperty("Bottom")
    BOTTOM("Bottom");
    private final String value;

    VerticalAlign(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VerticalAlign fromValue(String v) {
        for (VerticalAlign c: VerticalAlign.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
