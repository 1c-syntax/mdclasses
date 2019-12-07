

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class NumericFormat {

    @JsonProperty("decimal")
    protected String decimal;
    @JsonProperty("group")
    protected String group;
    @JsonProperty("grouping")
    protected String grouping;
    @JsonProperty("negative")
    protected BigDecimal negative;

}
