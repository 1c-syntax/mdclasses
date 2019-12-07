

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceString {

    @JsonProperty(required = true)
    protected String resName;
    @JsonProperty(required = true)
    protected String resString;

}
