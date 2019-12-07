

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum SpreadsheetDocumentDrawingType {

    @JsonProperty("Line")
    LINE("Line"),
    @JsonProperty("Rectangle")
    RECTANGLE("Rectangle"),
    @JsonProperty("Text")
    TEXT("Text"),
    @JsonProperty("Ellipse")
    ELLIPSE("Ellipse"),
    @JsonProperty("Picture")
    PICTURE("Picture"),
    @JsonProperty("Object")
    OBJECT("Object"),
    @JsonProperty("Group")
    GROUP("Group"),
    @JsonProperty("Chart")
    CHART("Chart"),
    @JsonProperty("GanttChart")
    GANTT_CHART("GanttChart"),
    @JsonProperty("PivotChart")
    PIVOT_CHART("PivotChart"),
    @JsonProperty("Dendrogram")
    DENDROGRAM("Dendrogram"),
    @JsonProperty("GeographicalSchema")
    GEOGRAPHICAL_SCHEMA("GeographicalSchema"),
    @JsonProperty("Control")
    CONTROL("Control"),
    @JsonProperty("Comment")
    COMMENT("Comment");
    private final String value;

    SpreadsheetDocumentDrawingType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SpreadsheetDocumentDrawingType fromValue(String v) {
        for (SpreadsheetDocumentDrawingType c: SpreadsheetDocumentDrawingType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
