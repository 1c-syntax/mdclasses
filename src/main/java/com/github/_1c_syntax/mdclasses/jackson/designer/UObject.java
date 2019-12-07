

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UObject {

    protected FormDataDescriptions descriptions;
    protected String name;
    @JsonProperty(required = true)
    protected Object data;
    @JsonProperty("mode")
    protected String mode;

}
