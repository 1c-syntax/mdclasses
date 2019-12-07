

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum SpreadsheetDocumentCellAreaType {

    @JsonProperty("Table")
    TABLE("Table"),
    @JsonProperty("Rows")
    ROWS("Rows"),
    @JsonProperty("Columns")
    COLUMNS("Columns"),
    @JsonProperty("Rectangle")
    RECTANGLE("Rectangle");
    private final String value;

    SpreadsheetDocumentCellAreaType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SpreadsheetDocumentCellAreaType fromValue(String v) {
        for (SpreadsheetDocumentCellAreaType c: SpreadsheetDocumentCellAreaType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
