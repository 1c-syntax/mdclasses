

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TableSelectionMode {

    @JsonProperty("SingleRow")
    SINGLE_ROW("SingleRow"),
    @JsonProperty("MultiRow")
    MULTI_ROW("MultiRow");
    private final String value;

    TableSelectionMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TableSelectionMode fromValue(String v) {
        for (TableSelectionMode c: TableSelectionMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
