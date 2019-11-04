package com.github._1c_syntax.mdclasses.mdo.classes;

import com.github._1c_syntax.mdclasses.mdo.core.AbstractMDOSimple;
import com.github._1c_syntax.mdclasses.mdo.core.MDOType;
import com.github._1c_syntax.mdclasses.mdo.core.ReturnValueReuse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommonModule extends AbstractMDOSimple {

    protected boolean server;
    protected boolean global;
    protected boolean clientManagedApplication;
    protected boolean externalConnection;
    protected boolean clientOrdinaryApplication;
    protected boolean serverCall;

    protected ReturnValueReuse returnValuesReuse;
    protected boolean privileged;

    public CommonModule() {
        super(MDOType.COMMON_MODULE);
    }
}
