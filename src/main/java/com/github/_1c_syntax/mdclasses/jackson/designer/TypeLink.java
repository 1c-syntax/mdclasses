

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
public class TypeLink {

    @JsonProperty("DataPath")
    protected List<String> dataPath;
    @JsonProperty("LinkItem")
    protected BigDecimal linkItem;

}
