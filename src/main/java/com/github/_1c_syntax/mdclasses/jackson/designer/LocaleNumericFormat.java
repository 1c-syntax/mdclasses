

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LocaleNumericFormat {

    @JsonProperty("decimalSeparator")
    protected String decimalSeparator;
    @JsonProperty("groupingSeparator")
    protected String groupingSeparator;
    @JsonProperty("groupingSize")
    protected BigDecimal groupingSize;
    @JsonProperty("secondaryGroupingSize")
    protected BigDecimal secondaryGroupingSize;
    @JsonProperty("negativePrefix")
    protected String negativePrefix;
    @JsonProperty("negativeSuffix")
    protected String negativeSuffix;
    @JsonProperty("positivePrefix")
    protected String positivePrefix;
    @JsonProperty("positiveSuffix")
    protected String positiveSuffix;

}
