

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum SpreadsheetDocumentFileType {

    MXL("MXL"),
    XLS("XLS"),
    HTML("HTML"),
    TXT("TXT"),
    @JsonProperty("MXL7")
    MXL_7("MXL7"),
    @JsonProperty("HTML3")
    HTML_3("HTML3"),
    @JsonProperty("HTML4")
    HTML_4("HTML4"),
    @JsonProperty("HTML5")
    HTML_5("HTML5"),
    @JsonProperty("XLS95")
    XLS_95("XLS95"),
    @JsonProperty("XLS97")
    XLS_97("XLS97"),
    ANSITXT("ANSITXT"),
    DOCX("DOCX"),
    XLSX("XLSX"),
    ODS("ODS"),
    PDF("PDF");
    private final String value;

    SpreadsheetDocumentFileType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SpreadsheetDocumentFileType fromValue(String v) {
        for (SpreadsheetDocumentFileType c: SpreadsheetDocumentFileType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
