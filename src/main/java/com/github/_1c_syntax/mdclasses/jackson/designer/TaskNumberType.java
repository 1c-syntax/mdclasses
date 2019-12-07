

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum TaskNumberType {

    @JsonProperty("Number")
    NUMBER("Number"),
    @JsonProperty("String")
    STRING("String");
    private final String value;

    TaskNumberType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TaskNumberType fromValue(String v) {
        for (TaskNumberType c: TaskNumberType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
