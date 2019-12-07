

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TableRowSelectionMode {

    @JsonProperty("Cell")
    CELL("Cell"),
    @JsonProperty("Row")
    ROW("Row");
    private final String value;

    TableRowSelectionMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TableRowSelectionMode fromValue(String v) {
        for (TableRowSelectionMode c: TableRowSelectionMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
