

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CharacteristicTypes {

    @JsonProperty("KeyField")
    protected String keyField;
    @JsonProperty("TypesFilterField")
    protected String typesFilterField;
    @JsonProperty("TypesFilterValue")
    protected Object typesFilterValue;
    @JsonProperty("from")
    protected String from;


}
