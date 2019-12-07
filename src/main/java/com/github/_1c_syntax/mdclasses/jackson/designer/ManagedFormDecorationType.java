

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ManagedFormDecorationType {

    @JsonProperty("Label")
    LABEL("Label"),
    @JsonProperty("Picture")
    PICTURE("Picture");
    private final String value;

    ManagedFormDecorationType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ManagedFormDecorationType fromValue(String v) {
        for (ManagedFormDecorationType c: ManagedFormDecorationType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
