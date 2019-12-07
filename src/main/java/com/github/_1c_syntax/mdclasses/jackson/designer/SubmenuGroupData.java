

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SubmenuGroupData {

    protected Picture pic;
    @JsonProperty(required = true)
    protected String cmdGroups;
    protected String bkClr;
    protected String brdClr;
    @JsonProperty("repres")
    protected ButtonRepresentation repres;
    @JsonProperty("shape")
    protected ButtonShape shape;
    @JsonProperty("shapeRepres")
    protected ButtonShapeRepresentation shapeRepres;

}
