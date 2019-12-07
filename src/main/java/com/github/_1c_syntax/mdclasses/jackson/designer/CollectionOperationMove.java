


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class CollectionOperationMove {

    @JsonProperty("index")
    protected long index;
    @JsonProperty("delta")
    protected BigInteger delta;

}
