

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ChoiceParameter {

    @JsonProperty(required = true)
    protected String name;
    @JsonProperty(required = true)
    protected LinkedValueChangeMode mode;
    @JsonProperty(required = true)
    protected List<String> pathItem;
    protected String webDataPath;
    protected String strPath;

}
