

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class Structure {

    @JsonProperty("Property")
    protected List<Property> property;


    @Getter
    @Setter
    public static class Property {

        @JsonProperty("Value")
        protected Object value;
        @JsonProperty("name")
        protected String name;

    }
}
