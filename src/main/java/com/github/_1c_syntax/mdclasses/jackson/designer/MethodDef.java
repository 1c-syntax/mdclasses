

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class MethodDef {

    protected List<ParamDef> param;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("params")
    protected BigDecimal params;

}
