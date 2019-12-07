

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class ClientDisplayInformation {

    @JsonProperty("x")
    protected BigDecimal x;
    @JsonProperty("y")
    protected BigDecimal y;
    @JsonProperty("dpi")
    protected BigDecimal dpi;

}
