

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum SpreadsheetDocumentStepDirectionType {

    @JsonProperty("ByRows")
    BY_ROWS("ByRows"),
    @JsonProperty("ByColumns")
    BY_COLUMNS("ByColumns"),
    @JsonProperty("WithoutMove")
    WITHOUT_MOVE("WithoutMove");
    private final String value;

    SpreadsheetDocumentStepDirectionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SpreadsheetDocumentStepDirectionType fromValue(String v) {
        for (SpreadsheetDocumentStepDirectionType c: SpreadsheetDocumentStepDirectionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
