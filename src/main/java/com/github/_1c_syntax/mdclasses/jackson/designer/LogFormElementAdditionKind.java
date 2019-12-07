


package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum LogFormElementAdditionKind {

    @JsonProperty("SearchStringRepresentation")
    SEARCH_STRING_REPRESENTATION("SearchStringRepresentation"),
    @JsonProperty("ViewStatusRepresentation")
    VIEW_STATUS_REPRESENTATION("ViewStatusRepresentation"),
    @JsonProperty("SearchControl")
    SEARCH_CONTROL("SearchControl");
    private final String value;

    LogFormElementAdditionKind(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LogFormElementAdditionKind fromValue(String v) {
        for (LogFormElementAdditionKind c : LogFormElementAdditionKind.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
