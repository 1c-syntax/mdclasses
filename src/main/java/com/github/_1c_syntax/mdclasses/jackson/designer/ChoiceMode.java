

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ChoiceMode {

    @JsonProperty("FromForm")
    FROM_FORM("FromForm"),
    @JsonProperty("QuickChoice")
    QUICK_CHOICE("QuickChoice"),
    @JsonProperty("BothWays")
    BOTH_WAYS("BothWays");
    private final String value;

    ChoiceMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ChoiceMode fromValue(String v) {
        for (ChoiceMode c: ChoiceMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
