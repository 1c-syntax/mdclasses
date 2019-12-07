

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
public class TextFieldData {

    protected String txtClr;
    protected String bkClr;
    protected String brdClr;
    protected Border brd;
    protected Font fnt;
    protected List<Event> event;
    @JsonProperty("width")
    protected BigDecimal width;
    @JsonProperty("rowsCount")
    protected BigDecimal rowsCount;
    @JsonProperty("horStretch")
    protected BWAValue horStretch;
    @JsonProperty("verStretch")
    protected BWAValue verStretch;
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
    @JsonProperty("markNeg")
    protected BWAValue markNeg;
    @JsonProperty("format")
    protected String format;
    @JsonProperty("hyper")
    protected Boolean hyper;
    @JsonProperty("psw")
    protected BWAValue psw;
    @JsonProperty("clearEvents")
    protected Boolean clearEvents;
}
