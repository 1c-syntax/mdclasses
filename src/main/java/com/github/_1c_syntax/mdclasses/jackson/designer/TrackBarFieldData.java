

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class TrackBarFieldData {

    protected String brdClr;
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
    @JsonProperty("minVal")
    protected BigDecimal minVal;
    @JsonProperty("maxVal")
    protected BigDecimal maxVal;
    @JsonProperty("step")
    protected BigDecimal step;
    @JsonProperty("pageSize")
    protected BigDecimal pageSize;
    @JsonProperty("markingStep")
    protected BigDecimal markingStep;
    @JsonProperty("orientation")
    protected FormElementOrientation orientation;
    @JsonProperty("marking")
    protected MarkingStyle marking;

}
