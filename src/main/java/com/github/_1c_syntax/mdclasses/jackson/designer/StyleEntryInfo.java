

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StyleEntryInfo {

    @JsonProperty(required = true)
    protected Object value;
    @JsonProperty("uuid")
    protected String uuid;
    @JsonProperty("code")
    protected String code;
    @JsonProperty("description")
    protected String description;
    @JsonProperty("kind")
    protected StyleEntryKind kind;


}
