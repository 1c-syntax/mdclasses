

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EventHandlerCallType {

    @JsonProperty("Before")
    BEFORE("Before"),
    @JsonProperty("After")
    AFTER("After"),
    @JsonProperty("Override")
    OVERRIDE("Override");
    private final String value;

    EventHandlerCallType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EventHandlerCallType fromValue(String v) {
        for (EventHandlerCallType c: EventHandlerCallType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
