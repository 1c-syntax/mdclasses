

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RowsItem {

    @JsonProperty(required = true)
    protected BigDecimal index;
    protected BigDecimal indexTo;
    @JsonProperty(required = true)
    protected Row row;

}