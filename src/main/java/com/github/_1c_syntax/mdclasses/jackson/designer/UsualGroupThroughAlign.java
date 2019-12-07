

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum UsualGroupThroughAlign {

    @JsonProperty("Use")
    USE("Use"),
    @JsonProperty("DontUse")
    DONT_USE("DontUse"),
    @JsonProperty("Auto")
    AUTO("Auto");
    private final String value;

    UsualGroupThroughAlign(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UsualGroupThroughAlign fromValue(String v) {
        for (UsualGroupThroughAlign c: UsualGroupThroughAlign.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
