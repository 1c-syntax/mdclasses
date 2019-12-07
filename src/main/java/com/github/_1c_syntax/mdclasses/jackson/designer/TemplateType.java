

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum TemplateType {

    @JsonProperty("SpreadsheetDocument")
    SPREADSHEET_DOCUMENT("SpreadsheetDocument"),
    @JsonProperty("BinaryData")
    BINARY_DATA("BinaryData"),
    @JsonProperty("ActiveDocument")
    ACTIVE_DOCUMENT("ActiveDocument"),
    @JsonProperty("HTMLDocument")
    HTML_DOCUMENT("HTMLDocument"),
    @JsonProperty("TextDocument")
    TEXT_DOCUMENT("TextDocument"),
    @JsonProperty("GeographicalSchema")
    GEOGRAPHICAL_SCHEMA("GeographicalSchema"),
    @JsonProperty("GraphicalSchema")
    GRAPHICAL_SCHEMA("GraphicalSchema"),
    @JsonProperty("DataCompositionSchema")
    DATA_COMPOSITION_SCHEMA("DataCompositionSchema"),
    @JsonProperty("DataCompositionAppearanceTemplate")
    DATA_COMPOSITION_APPEARANCE_TEMPLATE("DataCompositionAppearanceTemplate"),
    @JsonProperty("AddIn")
    ADD_IN("AddIn");
    private final String value;

    TemplateType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TemplateType fromValue(String v) {
        for (TemplateType c: TemplateType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
