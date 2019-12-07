

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TreeOperationSwap {

    @JsonProperty("parentId")
    protected Long parentId;
    @JsonProperty("index1")
    protected long index1;
    @JsonProperty("index2")
    protected long index2;


}