

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class FormDataTypeDescription {

    protected List<String> value;
    @JsonProperty("object")
    protected Boolean object;
    @JsonProperty("digits")
    protected BigDecimal digits;
    @JsonProperty("precision")
    protected BigDecimal precision;
    @JsonProperty("unsigned")
    protected Boolean unsigned;
    @JsonProperty("length")
    protected BigDecimal length;
    @JsonProperty("fixed")
    protected Boolean fixed;
    @JsonProperty("dateFraction")
    protected DateFractions dateFraction;
    @JsonProperty("binaryLength")
    protected BigDecimal binaryLength;
    @JsonProperty("binaryFixed")
    protected Boolean binaryFixed;
    @JsonProperty("name")
    protected String name;

}
