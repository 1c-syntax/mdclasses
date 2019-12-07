

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class BSLProcedureImage {

    protected List<BSLLabel> label;
    protected List<BSLVariableImage> variable;
    protected List<String> paramsDefValue;
    protected List<BSLAnnotation> annotation;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("isFunction")
    protected Boolean isFunction;
    @JsonProperty("isExported")
    protected Boolean isExported;
    @JsonProperty("isExternal")
    protected Boolean isExternal;
    @JsonProperty("parametersCount")
    protected Long parametersCount;
    @JsonProperty("startAddr")
    protected Long startAddr;

}
