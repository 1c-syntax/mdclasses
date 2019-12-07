

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Event {

    @JsonProperty(required = true)
    protected List<EventHandlers> handlers;
    @JsonProperty("id")
    protected String id;


}
