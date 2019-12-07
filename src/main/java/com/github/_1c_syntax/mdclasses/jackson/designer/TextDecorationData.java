

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class TextDecorationData {

    protected String bkClr;
    protected String brdClr;
    protected Border brd;
    protected List<Event> event;
    @JsonProperty("hyper")
    protected Boolean hyper;
    @JsonProperty("hAlign")
    protected ItemHorizontalAlignment hAlign;
    @JsonProperty("vAlign")
    protected ItemVerticalAlignment vAlign;
    @JsonProperty("titleHeight")
    protected BigDecimal titleHeight;
    @JsonProperty("clearEvents")
    protected Boolean clearEvents;

}
