


package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class Font {

    @JsonProperty("ref")
    protected String ref;
    @JsonProperty("faceName")
    protected String faceName;
    @JsonProperty("height")
    protected BigDecimal height;
    @JsonProperty("bold")
    protected Boolean bold;
    @JsonProperty("italic")
    protected Boolean italic;
    @JsonProperty("underline")
    protected Boolean underline;
    @JsonProperty("strikeout")
    protected Boolean strikeout;
    @JsonProperty("kind")
    protected FontType kind;
    @JsonProperty("scale")
    protected BigDecimal scale;

}
