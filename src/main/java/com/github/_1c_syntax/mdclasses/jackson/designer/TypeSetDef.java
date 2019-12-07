

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.namespace.QName;


@Getter
@Setter
public class TypeSetDef {

    @JsonProperty("clsid")
    protected String clsid;
    @JsonProperty("en")
    protected String en;
    @JsonProperty("ru")
    protected String ru;
    @JsonProperty("name")
    protected QName name;

}
