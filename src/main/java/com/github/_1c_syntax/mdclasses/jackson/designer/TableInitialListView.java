
package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum TableInitialListView {

    @JsonProperty("Beginning")
    BEGINNING("Beginning"),
    @JsonProperty("End")
    END("End"),
    @JsonProperty("Auto")
    AUTO("Auto");
    private final String value;

    TableInitialListView(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TableInitialListView fromValue(String v) {
        for (TableInitialListView c: TableInitialListView.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
