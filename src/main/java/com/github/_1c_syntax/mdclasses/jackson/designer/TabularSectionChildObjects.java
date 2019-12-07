

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class TabularSectionChildObjects {

    @JsonProperty("Attribute")
    protected List<Attribute> attribute;

}
