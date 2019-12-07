

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SpreadsheetDocumentTextPlacementType {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("Cut")
    CUT("Cut"),
    @JsonProperty("Block")
    BLOCK("Block"),
    @JsonProperty("Wrap")
    WRAP("Wrap");
    private final String value;

    SpreadsheetDocumentTextPlacementType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SpreadsheetDocumentTextPlacementType fromValue(String v) {
        for (SpreadsheetDocumentTextPlacementType c: SpreadsheetDocumentTextPlacementType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
