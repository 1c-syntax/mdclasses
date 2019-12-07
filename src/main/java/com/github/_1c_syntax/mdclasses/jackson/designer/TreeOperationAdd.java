

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreeOperationAdd {

    @JsonProperty("parentId")
    protected Long parentId;
    @JsonProperty("index")
    protected long index;
    @JsonProperty("id")
    protected long id;


}
