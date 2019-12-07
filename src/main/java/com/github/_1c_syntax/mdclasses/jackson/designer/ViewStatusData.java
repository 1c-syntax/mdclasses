

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;


public class ViewStatusData {

    @JsonProperty("anySimpleType")
    protected String bkClr;
    @JsonProperty("anySimpleType")
    protected String btnClr;
    @JsonProperty("anySimpleType")
    protected String txtClr;
    @JsonProperty("anySimpleType")
    protected String titleTxtClr;
    @JsonProperty("anySimpleType")
    protected String brdClr;
    protected Font fnt;
    protected Font titleFnt;
    protected Border brd;
    protected List<Event> event;
    @JsonProperty("width")
    protected BigDecimal width;
    @JsonProperty("autoMaxWidth")
    protected Boolean autoMaxWidth;
    @JsonProperty("maxWidth")
    protected BigDecimal maxWidth;
    @JsonProperty("minWidth")
    protected BigDecimal minWidth;
    @JsonProperty("horStretch")
    protected BWAValue horStretch;
    @JsonProperty("hAlign")
    protected ItemHorizontalAlignment hAlign;

}
