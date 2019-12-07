

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FormPagesRepresentation {

    @JsonProperty("None")
    NONE("None"),
    @JsonProperty("TabsOnTop")
    TABS_ON_TOP("TabsOnTop"),
    @JsonProperty("TabsOnBottom")
    TABS_ON_BOTTOM("TabsOnBottom"),
    @JsonProperty("TabsOnLeftHorizontal")
    TABS_ON_LEFT_HORIZONTAL("TabsOnLeftHorizontal"),
    @JsonProperty("TabsOnRightHorizontal")
    TABS_ON_RIGHT_HORIZONTAL("TabsOnRightHorizontal"),
    @JsonProperty("Swipe")
    SWIPE("Swipe");
    private final String value;

    FormPagesRepresentation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FormPagesRepresentation fromValue(String v) {
        for (FormPagesRepresentation c: FormPagesRepresentation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
