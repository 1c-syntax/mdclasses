

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class StandardAttributeDescriptions {

    @JsonProperty("StandardAttribute")
    protected List<StandardAttributeDescription> standardAttribute;

}
