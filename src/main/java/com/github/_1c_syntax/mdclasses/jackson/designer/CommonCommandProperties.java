

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommonCommandProperties {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Synonym")
    protected LocalStringType synonym;
    @JsonProperty("Comment")
    protected String comment;
    @JsonProperty("ObjectBelonging")
    protected ObjectBelonging objectBelonging;
    @JsonProperty("Group")
    protected String group;
    @JsonProperty("Representation")
    protected ButtonRepresentation representation;
    @JsonProperty("ToolTip")
    protected LocalStringType toolTip;
    @JsonProperty("Picture")
    protected Picture picture;
    @JsonProperty("Shortcut")
    protected String shortcut;
    @JsonProperty("CommandModule")
    protected String commandModule;
    @JsonProperty("IncludeHelpInContents")
    protected boolean includeHelpInContents;
    @JsonProperty("Help")
    protected String help;
    @JsonProperty("CommandParameterType")
    protected TypeDescription commandParameterType;
    @JsonProperty("ParameterUseMode")
    protected CommandParameterUseMode parameterUseMode;
    @JsonProperty("ModifiesData")
    protected boolean modifiesData;

}
