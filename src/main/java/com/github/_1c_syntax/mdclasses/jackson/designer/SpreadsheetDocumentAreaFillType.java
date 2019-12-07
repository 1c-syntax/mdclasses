

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SpreadsheetDocumentAreaFillType {

    @JsonProperty("Text")
    TEXT("Text"),
    @JsonProperty("Parameter")
    PARAMETER("Parameter"),
    @JsonProperty("Template")
    TEMPLATE("Template");
    private final String value;

    SpreadsheetDocumentAreaFillType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SpreadsheetDocumentAreaFillType fromValue(String v) {
        for (SpreadsheetDocumentAreaFillType c: SpreadsheetDocumentAreaFillType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
