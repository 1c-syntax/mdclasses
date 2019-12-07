

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
public class SpreadsheetFieldData {

    protected String brdClr;
    protected List<Event> event;
    @JsonProperty("width")
    protected BigDecimal width;
    @JsonProperty("height")
    protected BigDecimal height;
    @JsonProperty("horStretch")
    protected Boolean horStretch;
    @JsonProperty("verStretch")
    protected Boolean verStretch;
    @JsonProperty("autoMaxWidth")
    protected Boolean autoMaxWidth;
    @JsonProperty("maxWidth")
    protected BigDecimal maxWidth;
    @JsonProperty("minWidth")
    protected BigDecimal minWidth;
    @JsonProperty("autoMaxHeight")
    protected Boolean autoMaxHeight;
    @JsonProperty("maxHeight")
    protected BigDecimal maxHeight;
    @JsonProperty("showGrid")
    protected Boolean showGrid;
    @JsonProperty("showHdrs")
    protected Boolean showHdrs;
    @JsonProperty("showVerScroll")
    protected String showVerScroll;
    @JsonProperty("showHorScroll")
    protected String showHorScroll;
    @JsonProperty("blackAndWhite")
    protected Boolean blackAndWhite;
    @JsonProperty("protection")
    protected Boolean protection;
    @JsonProperty("selectionMode")
    protected SelectionShowMode selectionMode;
    @JsonProperty("useOutput")
    protected UseOutput useOutput;
    @JsonProperty("edit")
    protected Boolean edit;
    @JsonProperty("showGroups")
    protected Boolean showGroups;
    @JsonProperty("startDrag")
    protected Boolean startDrag;
    @JsonProperty("drag")
    protected Boolean drag;
    @JsonProperty("clearEvents")
    protected Boolean clearEvents;
    @JsonProperty("selShow")
    protected SelectionShowMode selShow;
    @JsonProperty("viewScalingMode")
    protected ViewScalingMode viewScalingMode;
    @JsonProperty("showCellNames")
    protected Boolean showCellNames;
    @JsonProperty("showRowAndColumnNames")
    protected Boolean showRowAndColumnNames;
    @JsonProperty("pointerType")
    protected SpreadsheetDocumentPointerType pointerType;

}
