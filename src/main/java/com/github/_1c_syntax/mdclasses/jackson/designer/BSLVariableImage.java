

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class BSLVariableImage {

    protected List<BSLAnnotation> annotation;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("isLocal")
    protected Boolean isLocal;
    @JsonProperty("isExternal")
    protected Boolean isExternal;
    @JsonProperty("isParam")
    protected Boolean isParam;
    @JsonProperty("isByValue")
    protected Boolean isByValue;
    @JsonProperty("isExported")
    protected Boolean isExported;
    @JsonProperty("initConstPos")
    protected Integer initConstPos;


}
