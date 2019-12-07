

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class ChoiceParameterLink {

    @JsonProperty("Name")
    protected List<String> name;
    @JsonProperty("DataPath")
    protected List<String> dataPath;
    @JsonProperty("ValueChange")
    protected LinkedValueChangeMode valueChange;


}
