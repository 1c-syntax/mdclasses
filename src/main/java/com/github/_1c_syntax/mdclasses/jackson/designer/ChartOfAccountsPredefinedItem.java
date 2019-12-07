

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChartOfAccountsPredefinedItem
        extends PredefinedItemBase {

    @JsonProperty("AccountType")
    protected AccountType accountType;
    @JsonProperty("OffBalance")
    protected boolean offBalance;
    @JsonProperty("Order")
    protected String order;
    @JsonProperty("AccountingFlags")
    protected AccountingFlags accountingFlags;
    @JsonProperty("ExtDimensionTypes")
    protected ExtDimensionTypes extDimensionTypes;
    @JsonProperty("ChildItems")
    protected ChartOfAccountsPredefinedItems childItems;

}
