

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class SearchBlock {

    @JsonProperty(required = true)
    protected Object value;
    @JsonProperty("fieldName")
    protected String fieldName;
    @JsonProperty("compareType")
    protected BigDecimal compareType;

}
