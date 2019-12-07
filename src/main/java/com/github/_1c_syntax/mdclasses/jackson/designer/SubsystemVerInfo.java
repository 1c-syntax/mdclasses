

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubsystemVerInfo {

    @JsonProperty("id")
    protected String id;
    @JsonProperty("actionPanelVer")
    protected String actionPanelVer;
    @JsonProperty("navPanelVer")
    protected String navPanelVer;
}
