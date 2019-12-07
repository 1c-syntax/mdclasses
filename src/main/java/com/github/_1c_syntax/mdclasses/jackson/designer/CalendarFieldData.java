

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
public class CalendarFieldData {

    protected Font fnt;
    @JsonProperty("anySimpleType")
    protected String brdClr;
    protected Border brd;
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
    @JsonProperty("dateSel")
    protected FormDateSelectionMode dateSel;
    @JsonProperty("showCurDate")
    protected Boolean showCurDate;
    @JsonProperty("navigation")
    protected Boolean navigation;
    @JsonProperty("begPeriod")
    protected XMLGregorianCalendar begPeriod;
    @JsonProperty("endPeriod")
    protected XMLGregorianCalendar endPeriod;
    @JsonProperty("startDrag")
    protected Boolean startDrag;
    @JsonProperty("drag")
    protected Boolean drag;
    @JsonProperty("monthPanelVisible")
    protected Boolean monthPanelVisible;
    @JsonProperty("widthInMonths")
    protected BigDecimal widthInMonths;
    @JsonProperty("heightInMonths")
    protected BigDecimal heightInMonths;
    @JsonProperty("clearEvents")
    protected Boolean clearEvents;

}
