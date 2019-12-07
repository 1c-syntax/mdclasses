


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum RepresentationInContextMenu {

    @JsonProperty("None")
    NONE("None"),
    @JsonProperty("AdditionalInContextMenu")
    ADDITIONAL_IN_CONTEXT_MENU("AdditionalInContextMenu"),
    @JsonProperty("OnlyInContextMenu")
    ONLY_IN_CONTEXT_MENU("OnlyInContextMenu"),
    @JsonProperty("Auto")
    AUTO("Auto");
    private final String value;

    RepresentationInContextMenu(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RepresentationInContextMenu fromValue(String v) {
        for (RepresentationInContextMenu c: RepresentationInContextMenu.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
