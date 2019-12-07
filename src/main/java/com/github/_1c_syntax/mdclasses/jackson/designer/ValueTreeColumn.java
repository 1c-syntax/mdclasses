

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class ValueTreeColumn {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("ValueType")
    protected TypeDescription valueType;
    @JsonProperty("Title")
    protected String title;
    @JsonProperty("Width")
    protected BigInteger width;


}
