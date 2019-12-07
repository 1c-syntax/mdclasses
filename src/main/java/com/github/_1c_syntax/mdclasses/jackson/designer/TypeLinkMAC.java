

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class TypeLinkMAC {

    @JsonProperty(required = true)
    protected List<String> pathItem;
    protected BigDecimal linkItem;
    protected String webDataPath;
    protected String strPath;

}
