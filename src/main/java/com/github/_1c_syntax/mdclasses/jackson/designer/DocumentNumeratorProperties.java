

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class DocumentNumeratorProperties {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Synonym")
    protected LocalStringType synonym;
    @JsonProperty("Comment")
    protected String comment;
    @JsonProperty("ObjectBelonging")
    protected ObjectBelonging objectBelonging;
    @JsonProperty("NumberType")
    protected DocumentNumberType numberType;
    @JsonProperty("NumberLength")
    protected BigDecimal numberLength;
    @JsonProperty("NumberAllowedLength")
    protected AllowedLength numberAllowedLength;
    @JsonProperty("NumberPeriodicity")
    protected DocumentNumberPeriodicity numberPeriodicity;
    @JsonProperty("CheckUnique")
    protected boolean checkUnique;
}
