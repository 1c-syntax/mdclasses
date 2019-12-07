

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventHandler {

    @JsonProperty("handlerName")
    protected String handlerName;
    @JsonProperty("callType")
    protected EventHandlerCallType callType;

}
