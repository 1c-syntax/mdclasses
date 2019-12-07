

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum SpreadsheetDocumentPointerType {

    @JsonProperty("Special")
    SPECIAL("Special"),
    @JsonProperty("Regular")
    REGULAR("Regular");
    private final String value;

    SpreadsheetDocumentPointerType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SpreadsheetDocumentPointerType fromValue(String v) {
        for (SpreadsheetDocumentPointerType c: SpreadsheetDocumentPointerType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
