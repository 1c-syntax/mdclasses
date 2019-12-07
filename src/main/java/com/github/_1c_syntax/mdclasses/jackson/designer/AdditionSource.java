

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AdditionSource {

    @JsonProperty(required = true)
    protected BigDecimal elementId;
    @JsonProperty(required = true)
    protected BigDecimal additionId;

}
