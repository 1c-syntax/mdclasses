

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TypePresentations {

    @JsonProperty(required = true)
    protected TypePresentation type;

}
