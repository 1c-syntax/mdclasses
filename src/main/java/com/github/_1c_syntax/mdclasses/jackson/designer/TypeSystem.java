

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypeSystem {

    protected EnumTypeSet enums;
    protected RefTypeSet refs;
    protected RegKeyTypeSet regs;
    protected InfoRegKeyTypeSet infoRegs;
    protected TypeSet typeSet;
    protected ExtRefTypeSet extRef;
    protected ExtKeyTypeSet extKey;
    @JsonProperty("namespace")
    protected String namespace;

}
