

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum TaskMainPresentation {

    @JsonProperty("AsNumber")
    AS_NUMBER("AsNumber"),
    @JsonProperty("AsDescription")
    AS_DESCRIPTION("AsDescription");
    private final String value;

    TaskMainPresentation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TaskMainPresentation fromValue(String v) {
        for (TaskMainPresentation c: TaskMainPresentation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
