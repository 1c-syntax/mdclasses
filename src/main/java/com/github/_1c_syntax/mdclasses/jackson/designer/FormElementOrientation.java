

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum FormElementOrientation {

    @JsonProperty("Horizontal")
    HORIZONTAL("Horizontal"),
    @JsonProperty("Vertical")
    VERTICAL("Vertical");
    private final String value;

    FormElementOrientation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FormElementOrientation fromValue(String v) {
        for (FormElementOrientation c: FormElementOrientation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
