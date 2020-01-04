package com.github._1c_syntax.mdclasses.metadata.additional;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ReturnValueReuse {

    @JsonProperty("DontUse")
    DONT_USE("DontUse"),
    @JsonProperty("DuringRequest")
    DURING_REQUEST("DuringRequest"),
    @JsonProperty("DuringSession")
    DURING_SESSION("DuringSession");

    private final String value;

    ReturnValueReuse(String v) {
        this.value = v;
    }

    public static ReturnValueReuse fromValue(String v) {
        for (ReturnValueReuse c : ReturnValueReuse.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    public String value() {
        return this.value;
    }
}
