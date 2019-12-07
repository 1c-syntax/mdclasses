

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class StandardTabularSectionDescriptions {

    @JsonProperty("StandardTabularSection")
    protected List<StandardTabularSectionDescription> standardTabularSection;


}
