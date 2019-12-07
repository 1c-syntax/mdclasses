

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChartOfCalculationTypes
        extends MDObjectBase {

    @JsonProperty("Properties")
    protected ChartOfCalculationTypesProperties properties;
    @JsonProperty("ChildObjects")
    protected ChartOfCalculationTypesChildObjects childObjects;
}
