

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum UsualGroupRepresentation {

    @JsonProperty("None")
    NONE("None"),
    @JsonProperty("StrongSeparation")
    STRONG_SEPARATION("StrongSeparation"),
    @JsonProperty("WeakSeparation")
    WEAK_SEPARATION("WeakSeparation"),
    @JsonProperty("NormalSeparation")
    NORMAL_SEPARATION("NormalSeparation"),
    @JsonProperty("GroupBox")
    GROUP_BOX("GroupBox"),
    @JsonProperty("Line")
    LINE("Line"),
    @JsonProperty("Margin")
    MARGIN("Margin");
    private final String value;

    UsualGroupRepresentation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UsualGroupRepresentation fromValue(String v) {
        for (UsualGroupRepresentation c: UsualGroupRepresentation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
