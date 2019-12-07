

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MDObjectBase {

    @JsonProperty("InternalInfo")
    protected InternalInfo internalInfo;
    @JsonProperty("uuid")
    protected String uuid;

}
