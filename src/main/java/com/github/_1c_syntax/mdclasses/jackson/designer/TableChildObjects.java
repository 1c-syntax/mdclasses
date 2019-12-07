

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TableChildObjects {

    @JsonProperty("Field")
    protected List<Field> field;
    @JsonProperty("Form")
    protected List<String> form;
    @JsonProperty("Command")
    protected List<Command> command;
    @JsonProperty("Template")
    protected List<String> template;


}