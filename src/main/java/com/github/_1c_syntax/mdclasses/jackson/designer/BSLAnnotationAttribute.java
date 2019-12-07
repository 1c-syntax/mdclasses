

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BSLAnnotationAttribute {

    @JsonProperty("name")
    protected String name;
    @JsonProperty("value")
    protected String value;

}
