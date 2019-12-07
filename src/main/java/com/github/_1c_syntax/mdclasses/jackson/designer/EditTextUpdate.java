

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum EditTextUpdate {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("DontUse")
    DONT_USE("DontUse"),
    @JsonProperty("OnValueChange")
    ON_VALUE_CHANGE("OnValueChange"),
    @JsonProperty("Always")
    ALWAYS("Always");
    private final String value;

    EditTextUpdate(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EditTextUpdate fromValue(String v) {
        for (EditTextUpdate c: EditTextUpdate.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
