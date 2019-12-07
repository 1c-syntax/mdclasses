

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum UsualGroupBehavior {

    @JsonProperty("Usual")
    USUAL("Usual"),
    @JsonProperty("Collapsible")
    COLLAPSIBLE("Collapsible"),
    @JsonProperty("Ballon")
    BALLON("Ballon");
    private final String value;

    UsualGroupBehavior(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UsualGroupBehavior fromValue(String v) {
        for (UsualGroupBehavior c: UsualGroupBehavior.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
