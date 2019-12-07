

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SpreadsheetDocumentDrawingLineType {

    @JsonProperty("None")
    NONE("None"),
    @JsonProperty("Solid")
    SOLID("Solid"),
    @JsonProperty("Dashed")
    DASHED("Dashed"),
    @JsonProperty("Dotted")
    DOTTED("Dotted"),
    @JsonProperty("DashDotted")
    DASH_DOTTED("DashDotted"),
    @JsonProperty("DashDottedDotted")
    DASH_DOTTED_DOTTED("DashDottedDotted");
    private final String value;

    SpreadsheetDocumentDrawingLineType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SpreadsheetDocumentDrawingLineType fromValue(String v) {
        for (SpreadsheetDocumentDrawingLineType c: SpreadsheetDocumentDrawingLineType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
