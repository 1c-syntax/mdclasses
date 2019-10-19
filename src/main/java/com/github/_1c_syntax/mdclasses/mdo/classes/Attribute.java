package com.github._1c_syntax.mdclasses.mdo.classes;

import com.github._1c_syntax.mdclasses.mdo.core.AbstractMDO;
import com.github._1c_syntax.mdclasses.mdo.core.AttributeType;
import com.github._1c_syntax.mdclasses.mdo.core.MDOType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Attribute extends AbstractMDO {
    private AttributeType attributeType;

    public Attribute(MDOType mdoType) {
        super(mdoType);
    }
}
