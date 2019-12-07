

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Dimension
        extends MDObjectBase {

    @JsonProperty("Properties")
    protected DimensionProperties properties;

}
