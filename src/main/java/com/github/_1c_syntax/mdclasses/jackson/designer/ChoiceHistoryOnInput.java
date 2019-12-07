

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ChoiceHistoryOnInput {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("DontUse")
    DONT_USE("DontUse");
    private final String value;

    ChoiceHistoryOnInput(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ChoiceHistoryOnInput fromValue(String v) {
        for (ChoiceHistoryOnInput c : ChoiceHistoryOnInput.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
