

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ButtonsGroupData {

    @JsonProperty(required = true)
    protected String cmdGroups;
    @JsonProperty(required = true)
    protected ButtonGroupRepresentation repres;

}
