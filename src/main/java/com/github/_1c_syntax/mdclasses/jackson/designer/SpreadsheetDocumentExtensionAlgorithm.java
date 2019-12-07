

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum SpreadsheetDocumentExtensionAlgorithm {

    @JsonProperty("Replace")
    REPLACE("Replace"),
    @JsonProperty("Merge")
    MERGE("Merge");
    private final String value;

    SpreadsheetDocumentExtensionAlgorithm(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SpreadsheetDocumentExtensionAlgorithm fromValue(String v) {
        for (SpreadsheetDocumentExtensionAlgorithm c: SpreadsheetDocumentExtensionAlgorithm.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
