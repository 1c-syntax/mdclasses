

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public abstract class Type {

    @JsonProperty("clsid")
    protected String clsid;
    @JsonProperty("table")
    protected BigDecimal table;
    @JsonProperty("code")
    protected String code;


}
