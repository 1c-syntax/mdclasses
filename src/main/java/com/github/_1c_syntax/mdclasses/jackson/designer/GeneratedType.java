
package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GeneratedType {

    @JsonProperty("TypeId")
    protected String typeId;
    @JsonProperty("ValueId")
    protected String valueId;
    @JsonProperty("category")
    protected TypeCategories category;
    @JsonProperty("name")
    protected String name;

}
