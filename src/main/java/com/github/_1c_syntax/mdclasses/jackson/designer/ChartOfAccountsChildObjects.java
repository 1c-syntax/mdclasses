

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;


@Getter
public class ChartOfAccountsChildObjects {

    @JsonProperty("Attribute")
    protected List<Attribute> attribute;
    @JsonProperty("TabularSection")
    protected List<TabularSection> tabularSection;
    @JsonProperty("AccountingFlag")
    protected List<AccountingFlag> accountingFlag;
    @JsonProperty("ExtDimensionAccountingFlag")
    protected List<ExtDimensionAccountingFlag> extDimensionAccountingFlag;
    @JsonProperty("Form")
    protected List<String> form;
    @JsonProperty("Template")
    protected List<String> template;
    @JsonProperty("Command")
    protected List<Command> command;

}