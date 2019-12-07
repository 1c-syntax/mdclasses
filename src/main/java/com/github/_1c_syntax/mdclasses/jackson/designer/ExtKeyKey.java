

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.namespace.QName;

@Getter
@Setter
public class ExtKeyKey {

    @JsonProperty("name")
    protected String name;
    @JsonProperty("type")
    protected QName type;

}
