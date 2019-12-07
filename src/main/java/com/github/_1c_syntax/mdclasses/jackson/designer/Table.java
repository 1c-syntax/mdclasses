

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Table
        extends MDObjectBase {

    @JsonProperty("Properties")
    protected TableProperties properties;
    @JsonProperty("ChildObjects")
    protected TableChildObjects childObjects;


}
