

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;


public enum Indexing {

    @JsonProperty("DontIndex")
    DONT_INDEX("DontIndex"),
    @JsonProperty("Index")
    INDEX("Index"),
    @JsonProperty("IndexWithAdditionalOrder")
    INDEX_WITH_ADDITIONAL_ORDER("IndexWithAdditionalOrder");
    private final String value;

    Indexing(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Indexing fromValue(String v) {
        for (Indexing c : Indexing.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
