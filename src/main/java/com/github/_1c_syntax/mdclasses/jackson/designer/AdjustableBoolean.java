


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class AdjustableBoolean {

    @JsonProperty("Common")
    protected boolean common;
    @JsonProperty("Value")
    protected List<AdjustableBooleanItemType> value;

}
