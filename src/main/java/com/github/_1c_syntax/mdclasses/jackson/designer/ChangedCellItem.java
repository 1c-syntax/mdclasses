

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
public class ChangedCellItem {

    @JsonProperty(required = true)
    protected BigDecimal i;
    @JsonProperty(required = true)
    protected Cell c;


}
