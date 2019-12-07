

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum CheckBoxType {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("CheckBox")
    CHECK_BOX("CheckBox"),
    @JsonProperty("Tumbler")
    TUMBLER("Tumbler");
    private final String value;

    CheckBoxType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CheckBoxType fromValue(String v) {
        for (CheckBoxType c: CheckBoxType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
