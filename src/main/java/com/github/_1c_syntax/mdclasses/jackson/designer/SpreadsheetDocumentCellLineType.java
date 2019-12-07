

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum SpreadsheetDocumentCellLineType {

    @JsonProperty("None")
    NONE("None"),
    @JsonProperty("Solid")
    SOLID("Solid"),
    @JsonProperty("Dotted")
    DOTTED("Dotted"),
    @JsonProperty("Double")
    DOUBLE("Double"),
    @JsonProperty("ThinDashed")
    THIN_DASHED("ThinDashed"),
    @JsonProperty("ThickDashed")
    THICK_DASHED("ThickDashed"),
    @JsonProperty("LargeDashed")
    LARGE_DASHED("LargeDashed");
    private final String value;

    SpreadsheetDocumentCellLineType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SpreadsheetDocumentCellLineType fromValue(String v) {
        for (SpreadsheetDocumentCellLineType c: SpreadsheetDocumentCellLineType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
