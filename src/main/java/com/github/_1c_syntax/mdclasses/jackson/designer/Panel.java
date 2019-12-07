

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Panel {

    protected String uuid;
    protected String name;
    protected BigDecimal height;
    protected SectionPanelRepresentation kind;
    @JsonProperty("id")
    protected String id;

}
