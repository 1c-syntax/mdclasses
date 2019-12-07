

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandGroupProperties {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Synonym")
    protected LocalStringType synonym;
    @JsonProperty("Comment")
    protected String comment;
    @JsonProperty("ObjectBelonging")
    protected ObjectBelonging objectBelonging;
    @JsonProperty("Representation")
    protected ButtonRepresentation representation;
    @JsonProperty("ToolTip")
    protected LocalStringType toolTip;
    @JsonProperty("Picture")
    protected Picture picture;
    @JsonProperty("Category")
    protected CommandGroupCategory category;


}
