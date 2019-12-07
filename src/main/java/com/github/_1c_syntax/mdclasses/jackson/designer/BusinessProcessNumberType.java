

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum BusinessProcessNumberType {

    @JsonProperty("Number")
    NUMBER("Number"),
    @JsonProperty("String")
    STRING("String");
    private final String value;

    BusinessProcessNumberType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BusinessProcessNumberType fromValue(String v) {
        for (BusinessProcessNumberType c: BusinessProcessNumberType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
