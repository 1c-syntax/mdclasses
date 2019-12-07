

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class TextDocFieldData {

    protected String txtClr;
    protected String bkClr;
    protected String brdClr;
    protected Font fnt;
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
    @JsonProperty("useOutput")
    protected UseOutput useOutput;
}
