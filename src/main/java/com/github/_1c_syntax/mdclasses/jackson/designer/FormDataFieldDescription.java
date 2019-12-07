

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormDataFieldDescription {

    @JsonProperty(required = true)
    protected FormDataTypeDescription type;
    @JsonProperty("id")
    protected String id;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("nameRu")
    protected String nameRu;
    @JsonProperty("mode")
    protected AccessMode mode;
    @JsonProperty("imode")
    protected AccessMode imode;
    @JsonProperty("fromCntx")
    protected Boolean fromCntx;

}
