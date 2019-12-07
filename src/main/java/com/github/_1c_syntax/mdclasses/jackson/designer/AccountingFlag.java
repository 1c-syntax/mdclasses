


package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountingFlag
        extends MDObjectBase {

    @JsonProperty("Properties")
    protected AccountingFlagProperties properties;

}
