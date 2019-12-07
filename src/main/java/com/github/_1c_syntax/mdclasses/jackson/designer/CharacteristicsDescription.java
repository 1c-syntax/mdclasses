

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CharacteristicsDescription {

    @JsonProperty("CharacteristicTypes")
    protected CharacteristicTypes characteristicTypes;
    @JsonProperty("CharacteristicValues")
    protected CharacteristicValues characteristicValues;

}
