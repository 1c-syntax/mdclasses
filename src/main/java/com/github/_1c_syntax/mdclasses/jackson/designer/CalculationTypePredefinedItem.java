

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CalculationTypePredefinedItem
        extends PredefinedItemBase {

    @JsonProperty("ActionPeriodIsBase")
    protected boolean actionPeriodIsBase;
    @JsonProperty("Displaced")
    protected CalculationTypeList displaced;
    @JsonProperty("Base")
    protected List<CalculationTypeList> base;
    @JsonProperty("Leading")
    protected List<CalculationTypeList> leading;


}
