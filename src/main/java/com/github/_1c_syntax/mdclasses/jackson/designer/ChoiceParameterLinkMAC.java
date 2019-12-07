


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChoiceParameterLinkMAC {

    @JsonProperty(required = true)
    protected String dataPath;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("mode")
    protected LinkedValueChangeMode mode;

}
