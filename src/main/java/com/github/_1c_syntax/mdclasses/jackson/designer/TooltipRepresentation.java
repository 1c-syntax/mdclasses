


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum TooltipRepresentation {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("None")
    NONE("None"),
    @JsonProperty("Balloon")
    BALLOON("Balloon"),
    @JsonProperty("Button")
    BUTTON("Button"),
    @JsonProperty("ShowAuto")
    SHOW_AUTO("ShowAuto"),
    @JsonProperty("ShowTop")
    SHOW_TOP("ShowTop"),
    @JsonProperty("ShowLeft")
    SHOW_LEFT("ShowLeft"),
    @JsonProperty("ShowBottom")
    SHOW_BOTTOM("ShowBottom"),
    @JsonProperty("ShowRight")
    SHOW_RIGHT("ShowRight");
    private final String value;

    TooltipRepresentation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TooltipRepresentation fromValue(String v) {
        for (TooltipRepresentation c : TooltipRepresentation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
