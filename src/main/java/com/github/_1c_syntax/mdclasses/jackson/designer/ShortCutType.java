


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortCutType {

    @JsonProperty("Key")
    protected String key;
    @JsonProperty("Alt")
    protected Boolean alt;
    @JsonProperty("Ctrl")
    protected Boolean ctrl;
    @JsonProperty("Shift")
    protected Boolean shift;

}
