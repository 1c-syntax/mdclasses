


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CharacteristicValues {

    @JsonProperty("ObjectField")
    protected String objectField;
    @JsonProperty("TypeField")
    protected String typeField;
    @JsonProperty("ValueField")
    protected String valueField;
    @JsonProperty("from")
    protected String from;

}
