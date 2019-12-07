
package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountingRegister
        extends MDObjectBase {

    @JsonProperty("Properties")
    protected AccountingRegisterProperties properties;
    @JsonProperty("ChildObjects")
    protected AccountingRegisterChildObjects childObjects;

}
