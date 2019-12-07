

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class CommandLogForm {

    @JsonProperty(required = true)
    protected List<EventHandlers> handlers;
    @JsonProperty("id")
    protected String id;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("modifiesData")
    protected Boolean modifiesData;
    @JsonProperty("currentRowUse")
    protected CurrentRowUse currentRowUse;
    @JsonProperty("associatedTableElementId")
    protected BigDecimal associatedTableElementId;


}
