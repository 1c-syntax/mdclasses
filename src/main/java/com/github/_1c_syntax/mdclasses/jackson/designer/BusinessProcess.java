


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BusinessProcess
        extends MDObjectBase {

    @JsonProperty("Properties")
    protected BusinessProcessProperties properties;
    @JsonProperty("ChildObjects")
    protected BusinessProcessChildObjects childObjects;

}
