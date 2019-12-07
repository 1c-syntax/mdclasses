

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class BinaryDataQualifiersMAC {

    @JsonProperty("length")
    protected BigDecimal length;
    @JsonProperty("varying")
    protected Boolean varying;


}
