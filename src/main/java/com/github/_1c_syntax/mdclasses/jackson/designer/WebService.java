

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class WebService
    extends MDObjectBase
{

    @JsonProperty("Properties")
    protected WebServiceProperties properties;
    @JsonProperty("ChildObjects")
    protected WebServiceChildObjects childObjects;

}
