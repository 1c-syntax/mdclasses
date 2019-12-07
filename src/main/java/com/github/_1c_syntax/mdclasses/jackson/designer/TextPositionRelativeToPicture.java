

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum TextPositionRelativeToPicture {

    @JsonProperty("Left")
    LEFT("Left"),
    @JsonProperty("Right")
    RIGHT("Right"),
    @JsonProperty("Top")
    TOP("Top"),
    @JsonProperty("Bottom")
    BOTTOM("Bottom"),
    @JsonProperty("OnTop")
    ON_TOP("OnTop"),
    @JsonProperty("Auto")
    AUTO("Auto");
    private final String value;

    TextPositionRelativeToPicture(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TextPositionRelativeToPicture fromValue(String v) {
        for (TextPositionRelativeToPicture c: TextPositionRelativeToPicture.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
