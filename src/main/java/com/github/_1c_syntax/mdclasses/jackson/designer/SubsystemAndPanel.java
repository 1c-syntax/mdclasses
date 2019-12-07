

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SubsystemAndPanel {

    @JsonProperty(required = true)
    protected String subsystem;
    @JsonProperty(required = true)
    protected BigDecimal panel;

}
