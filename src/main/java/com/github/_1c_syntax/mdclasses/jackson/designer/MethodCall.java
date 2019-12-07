

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MethodCall {

//    protected JAXBElement<Object> ret;
    @JsonProperty()
    protected List<Object> param;
//    @JsonPropertyRef("pfc", namespace = "http://v8.1c.ru/8.2/managed-application/modules", type = JAXBElement.class, required = false)
//    protected JAXBElement<ProcessFormsCloseParam> pfc;
    @JsonProperty("name")
    protected String name;


}
