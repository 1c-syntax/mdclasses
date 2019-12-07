

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SpreadsheetDocumentDetailUse {

    @JsonProperty("Cell")
    CELL("Cell"),
    @JsonProperty("Row")
    ROW("Row"),
    @JsonProperty("WithoutProcessing")
    WITHOUT_PROCESSING("WithoutProcessing");
    private final String value;

    SpreadsheetDocumentDetailUse(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SpreadsheetDocumentDetailUse fromValue(String v) {
        for (SpreadsheetDocumentDetailUse c: SpreadsheetDocumentDetailUse.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
