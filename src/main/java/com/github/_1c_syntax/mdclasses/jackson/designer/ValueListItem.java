

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ValueListItem {

    @JsonProperty("Presentation")
    protected String presentation;
    @JsonProperty("CheckState")
    protected BigDecimal checkState;
    @JsonProperty("Value")
    protected Object value;
}
