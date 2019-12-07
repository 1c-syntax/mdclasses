

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BSLModuleImage {

    @JsonProperty(required = true)
    protected String cmdOpCode;
    @JsonProperty(required = true)
    protected String cmdOperand;
    protected List<String> constant;
    protected List<BSLVariableImage> variable;
    protected List<BSLLabel> label;
    protected List<BSLProcedureImage> procedure;
    @JsonProperty("version")
    protected Long version;
    @JsonProperty("startAddr")
    protected Long startAddr;


}
