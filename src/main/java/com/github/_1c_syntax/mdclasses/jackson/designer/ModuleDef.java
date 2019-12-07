

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;


@Getter
@Setter
public class ModuleDef {

    protected BSLModuleImage image;
    protected List<ModuleDef> extension;
    @JsonProperty(required = true)
    protected String securityInfo;
    @JsonProperty("id")
    protected String id;
    @JsonProperty("code")
    protected String code;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("cached")
    protected BigInteger cached;

}
