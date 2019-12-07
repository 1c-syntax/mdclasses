

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ButtonRepresentation {

    @JsonProperty("Text")
    TEXT("Text"),
    @JsonProperty("Picture")
    PICTURE("Picture"),
    @JsonProperty("PictureAndText")
    PICTURE_AND_TEXT("PictureAndText"),
    @JsonProperty("Auto")
    AUTO("Auto");
    private final String value;

    ButtonRepresentation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ButtonRepresentation fromValue(String v) {
        for (ButtonRepresentation c : ButtonRepresentation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
