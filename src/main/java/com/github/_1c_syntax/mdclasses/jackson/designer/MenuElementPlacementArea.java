


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum MenuElementPlacementArea {

    @JsonProperty("mainCmdsLeft")
    MAIN_CMDS_LEFT("mainCmdsLeft"),
    @JsonProperty("autoCmds")
    AUTO_CMDS("autoCmds"),
    @JsonProperty("userCmds")
    USER_CMDS("userCmds"),
    @JsonProperty("mainCmdsRight")
    MAIN_CMDS_RIGHT("mainCmdsRight");
    private final String value;

    MenuElementPlacementArea(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MenuElementPlacementArea fromValue(String v) {
        for (MenuElementPlacementArea c : MenuElementPlacementArea.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
