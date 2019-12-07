

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SysEnum {

    protected List<SysEnumValue> values;
    @JsonProperty("factoryClsid")
    protected String factoryClsid;
    @JsonProperty("clsid")
    protected String clsid;
    @JsonProperty("enumName")
    protected String enumName;
    @JsonProperty("enumName2")
    protected String enumName2;
    @JsonProperty("enumName3")
    protected String enumName3;
    @JsonProperty("enumName4")
    protected String enumName4;
    @JsonProperty("xmlName")
    protected String xmlName;
    @JsonProperty("namespace")
    protected String namespace;
    @JsonProperty("defValue")
    protected String defValue;


}
