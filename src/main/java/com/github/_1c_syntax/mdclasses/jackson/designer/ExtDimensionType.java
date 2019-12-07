

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ExtDimensionType {

    @JsonProperty("Turnover")
    protected boolean turnover;
    @JsonProperty("AccountingFlags")
    protected AccountingFlags accountingFlags;
    @JsonProperty("name")
    protected String name;

}
