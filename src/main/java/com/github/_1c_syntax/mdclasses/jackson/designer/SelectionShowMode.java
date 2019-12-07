

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum SelectionShowMode {

    @JsonProperty("WhenActive")
    WHEN_ACTIVE("WhenActive"),
    @JsonProperty("Always")
    ALWAYS("Always"),
    @JsonProperty("DontShow")
    DONT_SHOW("DontShow"),
    @JsonProperty("WhenMultipleCellsSelected")
    WHEN_MULTIPLE_CELLS_SELECTED("WhenMultipleCellsSelected"),
    @JsonProperty("WhenMultipleCellsSelectedWhenActive")
    WHEN_MULTIPLE_CELLS_SELECTED_WHEN_ACTIVE("WhenMultipleCellsSelectedWhenActive");
    private final String value;

    SelectionShowMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SelectionShowMode fromValue(String v) {
        for (SelectionShowMode c: SelectionShowMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
