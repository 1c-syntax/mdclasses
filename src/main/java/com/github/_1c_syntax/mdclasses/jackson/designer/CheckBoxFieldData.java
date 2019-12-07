

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class CheckBoxFieldData {

    @JsonProperty("anySimpleType")
    protected String brdClr;
    @JsonProperty("anySimpleType")
    protected String bkClr;
    @JsonProperty("anySimpleType")
    protected String txtClr;
    protected Font fnt;
    @JsonProperty("checkBoxType")
    protected CheckBoxType checkBoxType;
    @JsonProperty("threeState")
    protected Boolean threeState;
    @JsonProperty("format")
    protected String format;
    @JsonProperty("elemTitleHeight")
    protected BigDecimal elemTitleHeight;
    @JsonProperty("elemHeight")
    protected BigDecimal elemHeight;
    @JsonProperty("elemWidth")
    protected BigDecimal elemWidth;
    @JsonProperty("eqWidth")
    protected BWAValue eqWidth;

}
