

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class SearchControlData {

    @JsonProperty("anySimpleType")
    protected String bkClr;
    @JsonProperty("anySimpleType")
    protected String txtClr;
    @JsonProperty("anySimpleType")
    protected String brdClr;
    protected Font fnt;
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


}
