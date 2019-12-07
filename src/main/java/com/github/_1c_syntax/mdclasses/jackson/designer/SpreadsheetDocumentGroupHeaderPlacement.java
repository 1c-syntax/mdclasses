

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum SpreadsheetDocumentGroupHeaderPlacement {

    @JsonProperty("Auto")
    AUTO("Auto"),
    @JsonProperty("Begin")
    BEGIN("Begin"),
    @JsonProperty("End")
    END("End");
    private final String value;

    SpreadsheetDocumentGroupHeaderPlacement(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SpreadsheetDocumentGroupHeaderPlacement fromValue(String v) {
        for (SpreadsheetDocumentGroupHeaderPlacement c: SpreadsheetDocumentGroupHeaderPlacement.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
