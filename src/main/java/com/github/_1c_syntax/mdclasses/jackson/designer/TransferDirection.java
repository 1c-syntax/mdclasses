

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum TransferDirection {

    @JsonProperty("In")
    IN("In"),
    @JsonProperty("Out")
    OUT("Out"),
    @JsonProperty("InOut")
    IN_OUT("InOut");
    private final String value;

    TransferDirection(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TransferDirection fromValue(String v) {
        for (TransferDirection c: TransferDirection.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
