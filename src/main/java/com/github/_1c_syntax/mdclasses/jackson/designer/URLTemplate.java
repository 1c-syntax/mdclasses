

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class URLTemplate
    extends MDObjectBase
{

    @JsonProperty("Properties")
    protected URLTemplateProperties properties;
    @JsonProperty("ChildObjects")
    protected URLTemplateChildObjects childObjects;

}
