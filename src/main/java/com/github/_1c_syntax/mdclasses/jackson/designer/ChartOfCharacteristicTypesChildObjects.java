

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;


@Getter
public class ChartOfCharacteristicTypesChildObjects {

    @JsonProperty("Attribute")
    protected List<Attribute> attribute;
    @JsonProperty("TabularSection")
    protected List<TabularSection> tabularSection;
    @JsonProperty("Form")
    protected List<String> form;
    @JsonProperty("Template")
    protected List<String> template;
    @JsonProperty("Command")
    protected List<Command> command;


}