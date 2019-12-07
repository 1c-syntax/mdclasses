

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class InformationRegisterChildObjects {

    @JsonProperty("Resource")
    protected List<Resource> resource;
    @JsonProperty("Attribute")
    protected List<Attribute> attribute;
    @JsonProperty("Dimension")
    protected List<Dimension> dimension;
    @JsonProperty("Form")
    protected List<String> form;
    @JsonProperty("Template")
    protected List<String> template;
    @JsonProperty("Command")
    protected List<Command> command;

}
