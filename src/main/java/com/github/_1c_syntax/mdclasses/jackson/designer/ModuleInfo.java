

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ModuleInfo {

    protected List<String> securityInfo;
    @JsonProperty("moduleId")
    protected String moduleId;
    @JsonProperty("moduleName")
    protected String moduleName;


}
