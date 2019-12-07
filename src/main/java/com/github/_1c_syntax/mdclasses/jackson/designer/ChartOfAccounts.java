

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChartOfAccounts
        extends MDObjectBase {

    @JsonProperty("Properties")
    protected ChartOfAccountsProperties properties;
    @JsonProperty("ChildObjects")
    protected ChartOfAccountsChildObjects childObjects;

}
