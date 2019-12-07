

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InternalInfo {

    @JsonProperty("ThisNode")
    protected String thisNode;
    @JsonProperty("GeneratedType")
    protected List<GeneratedType> generatedType;
    @JsonProperty("ContainedObject")
    protected List<ContainedObject> containedObject;

}
