

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EditType {

    @JsonProperty("InList")
    IN_LIST("InList"),
    @JsonProperty("InDialog")
    IN_DIALOG("InDialog"),
    @JsonProperty("BothWays")
    BOTH_WAYS("BothWays");
    private final String value;

    EditType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EditType fromValue(String v) {
        for (EditType c: EditType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
