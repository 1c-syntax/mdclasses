

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PredefinedItemBase {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Code")
    protected String code;
    @JsonProperty("Description")
    protected String description;
    @JsonProperty("id")
    protected String id;

}
