

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysEnumValue {

    @JsonProperty("name")
    protected String name;
    @JsonProperty("name2")
    protected String name2;
    @JsonProperty("name3")
    protected String name3;
    @JsonProperty("name4")
    protected String name4;
    @JsonProperty("presentation")
    protected String presentation;
}
